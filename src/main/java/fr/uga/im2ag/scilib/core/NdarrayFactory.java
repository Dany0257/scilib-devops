package fr.uga.im2ag.scilib.core;

/**
 * Usine de création pour les objets Ndarray.
 * Centralise les méthodes permettant de générer des tableaux (zeros, ones, arange, array).
 */

public final class NdarrayFactory {

    // Constructeur privé pour empêcher l'instanciation de cette classe utilitaire
    private NdarrayFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Crée un Ndarray rempli de zéros.
     *
     * @param dims Les dimensions du tableau
     * @return Un nouveau Ndarray initialisé à 0.0
     */
    public static NdarrayInterface zeros(int... dims) {
        // En Java, un tableau de double est initialisé à 0.0 par défaut
        return new Ndarray(dims);
    }

    /**
     * Crée un Ndarray rempli de uns.
     *
     * @param dims Les dimensions du tableau
     * @return Un nouveau Ndarray initialisé à 1.0
     */
    public static NdarrayInterface ones(int... dims) {
        NdarrayInterface ndarray = new Ndarray(dims);
        // On utilise l'addition en place sur un tableau de zéros
        ndarray.addInPlace(1.0);
        return ndarray;
    }

    /**
     * Crée un Ndarray 1D initialisé avec une séquence de nombres.
     *
     * @param start La valeur de départ (incluse)
     * @param stop  La valeur de fin (exclue)
     * @param step  Le pas entre chaque valeur
     * @return Un nouveau Ndarray 1D contenant la séquence
     * @throws IllegalArgumentException si le step est nul ou mal défini par rapport aux bornes
     */
    public static NdarrayInterface arange(double start, double stop, double step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step cannot be zero.");
        }
        
        // Calcul du nombre d'éléments nécessaires
        int size = (int) Math.ceil((stop - start) / step);
        
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid bounds and step combination.");
        }

        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = start + i * step;
        }

        // On utilise le constructeur package-private de Ndarray
        return new Ndarray(data, new int[]{size});
    }

    /**
     * Crée un Ndarray 1D à partir d'un tableau Java existant.
     *
     * @param data Le tableau de données source
     * @return Un nouveau Ndarray 1D
     */
    public static NdarrayInterface array(double[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data array cannot be null or empty.");
        }
        return new Ndarray(data, new int[]{data.length});
    }
}