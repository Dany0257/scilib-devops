package fr.uga.im2ag.scilib.core;

import java.util.Arrays;

/**
 * Représente la forme (dimensions) d'un Ndarray.
 * Objet immuable : une fois créé, il ne peut pas être modifié.
 *
 * Exemple :
 * Shape s = new Shape(2, 3); // matrice 2x3
 * s.getNdim() → 2
 * s.getSize() → 6
 * s.getDims() → [2, 3]
 */
public class Shape {

    private final int[] dims;
    private final int ndim;
    private final int size;

    /**
     * Crée une Shape à partir des dimensions données.
     *
     * @param dims les dimensions (ex: 2, 3 pour une matrice 2x3)
     * @throws IllegalArgumentException si aucune dimension n'est fournie
     *                                  ou si une dimension est négative ou nulle
     */
    public Shape(int... dims) {
        if (dims == null || dims.length == 0) {
            throw new IllegalArgumentException("Shape must have at least one dimension.");
        }
        for (int d : dims) {
            if (d <= 0) {
                throw new IllegalArgumentException(
                        "Each dimension must be positive, got: " + d);
            }
        }
        this.dims = Arrays.copyOf(dims, dims.length);
        this.ndim = dims.length;
        this.size = computeSize(dims);
    }

    /**
     * Calcule le nombre total d'éléments (produit de toutes les dimensions).
     */
    private static int computeSize(int[] dims) {
        int total = 1;
        for (int d : dims) {
            total *= d;
        }
        return total;
    }

    /** Retourne le nombre de dimensions. */
    public int getNdim() {
        return ndim;
    }

    /** Retourne le nombre total d'éléments. */
    public int getSize() {
        return size;
    }

    /** Retourne une copie du tableau de dimensions. */
    public int[] getDims() {
        return Arrays.copyOf(dims, dims.length);
    }

    /** Retourne la taille de la dimension à l'index donné. */
    public int getDim(int index) {
        return dims[index];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Shape other))
            return false;
        return Arrays.equals(this.dims, other.dims);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dims);
    }

    /** Affichage style NumPy : "(2, 3)" */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < dims.length; i++) {
            sb.append(dims[i]);
            if (i < dims.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
