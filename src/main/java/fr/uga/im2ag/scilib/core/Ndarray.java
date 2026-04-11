package fr.uga.im2ag.scilib.core;

import fr.uga.im2ag.scilib.exceptions.IndexOutOfBoundsNdarrayException;
import fr.uga.im2ag.scilib.exceptions.InvalidReshapeException;
import fr.uga.im2ag.scilib.exceptions.ShapeMismatchException;
import java.util.Arrays;

/**
 * Implémentation principale d'un ndarray de doubles.
 *
 * Stockage interne : tableau 1D à plat (row-major order).
 * Une matrice [[1, 2, 3], [4, 5, 6]] de shape (2, 3)
 * est stockée comme [1.0, 2.0, 3.0, 4.0, 5.0, 6.0].
 *
 * Accès : index_1D = ligne * nb_colonnes + colonne
 * (généralisé pour N dimensions via computeFlatIndex).
 */
public class Ndarray implements NdarrayInterface {

    private final double[] data;
    private final Shape shape;

    // ============================
    // CONSTRUCTEURS
    // ============================

    /**
     * Crée un ndarray rempli de zéros avec la forme donnée.
     *
     * @param dims les dimensions (ex: new Ndarray(2, 3) → matrice 2x3 de zéros)
     */
    public Ndarray(int... dims) {
        this.shape = new Shape(dims);
        this.data = new double[this.shape.getSize()];
    }

    /**
     * Constructeur interne : crée un ndarray à partir de données existantes.
     * Utilisé par la factory et les opérations internes.
     *
     * @param data les données (tableau 1D à plat)
     * @param dims les dimensions souhaitées
     */
    Ndarray(double[] data, int[] dims) {
        this.shape = new Shape(dims);
        if (data.length != this.shape.getSize()) {
            throw new IllegalArgumentException(
                    "Data length (" + data.length + ") does not match shape size ("
                            + this.shape.getSize() + ")");
        }
        this.data = Arrays.copyOf(data, data.length);
    }

    // ============================
    // ATTRIBUTS
    // ============================

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public int getNdim() {
        return shape.getNdim();
    }

    @Override
    public int getSize() {
        return shape.getSize();
    }

    /**
     * Retourne une copie des données internes (tableau 1D à plat).
     */
    public double[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    // ============================
    // ACCÈS AUX DONNÉES
    // ============================

    @Override
    public double get(int... indices) {
        validateIndices(indices);
        return data[computeFlatIndex(indices)];
    }

    @Override
    public void set(double value, int... indices) {
        validateIndices(indices);
        data[computeFlatIndex(indices)] = value;
    }

    // ============================
    // OPÉRATIONS (add)
    // ============================

    @Override
    public NdarrayInterface add(NdarrayInterface other) {
        checkSameShape(other);
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = this.data[i] + ((Ndarray) other).data[i];
        }
        return new Ndarray(result, shape.getDims());
    }

    @Override
    public NdarrayInterface add(double scalar) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = this.data[i] + scalar;
        }
        return new Ndarray(result, shape.getDims());
    }

    @Override
    public NdarrayInterface addInPlace(NdarrayInterface other) {
        checkSameShape(other);
        for (int i = 0; i < data.length; i++) {
            this.data[i] += ((Ndarray) other).data[i];
        }
        return this;
    }

    @Override
    public NdarrayInterface addInPlace(double scalar) {
        for (int i = 0; i < data.length; i++) {
            this.data[i] += scalar;
        }
        return this;
    }

    // ============================
    // RESHAPE
    // ============================

    @Override
    public NdarrayInterface reshape(int... newDims) {
        Shape newShape = new Shape(newDims);
        if (newShape.getSize() != this.shape.getSize()) {
            throw new InvalidReshapeException(this.shape, newDims);
        }
        return new Ndarray(this.data, newDims);
    }

    // ============================
    // AFFICHAGE
    // ============================

    @Override
    public String toString() {
        // On délègue l'affichage à la classe utilitaire Printer
        return NdarrayPrinter.format(this);
    }

    // ============================
    // MÉTHODES UTILITAIRES
    // ============================

    /**
     * Convertit des indices multi-dimensionnels en index 1D (row-major).
     * Ex: pour shape (2, 3), indices (1, 2) → 1*3 + 2 = 5
     */
    private int computeFlatIndex(int[] indices) {
        int[] dims = shape.getDims();
        int flatIndex = 0;
        int multiplier = 1;
        for (int i = dims.length - 1; i >= 0; i--) {
            flatIndex += indices[i] * multiplier;
            multiplier *= dims[i];
        }
        return flatIndex;
    }

    /**
     * Vérifie que les indices sont valides pour la shape courante.
     */
    private void validateIndices(int[] indices) {
        int[] dims = shape.getDims();
        if (indices.length != dims.length) {
            throw new IndexOutOfBoundsNdarrayException(indices, shape);
        }
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] < 0 || indices[i] >= dims[i]) {
                throw new IndexOutOfBoundsNdarrayException(indices, shape);
            }
        }
    }

    /**
     * Vérifie que l'autre ndarray a la même shape.
     */
    private void checkSameShape(NdarrayInterface other) {
        if (!this.shape.equals(other.getShape())) {
            throw new ShapeMismatchException(this.shape, other.getShape());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Ndarray other))
            return false;
        return this.shape.equals(other.shape) && Arrays.equals(this.data, other.data);
    }

    @Override
    public int hashCode() {
        return 31 * shape.hashCode() + Arrays.hashCode(data);
    }
}
