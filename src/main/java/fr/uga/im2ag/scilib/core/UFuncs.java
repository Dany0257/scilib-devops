package fr.uga.im2ag.scilib.core;

/**
 * Fonctions universelles (ufuncs) pour les Ndarrays.
 * Fournit des opérations mathématiques élément par élément.
 */

public final class UFuncs {

    private UFuncs() {
        // classe utilitaire, pas d'instanciation
    }

    /**
     * Applique une fonction f à chaque élément et retourne un nouveau ndarray.
     */
    private static Ndarray apply(Ndarray a, java.util.function.DoubleUnaryOperator f) {
        double[] src = a.getData();
        double[] dst = new double[src.length];
        for (int i = 0; i < src.length; i++) {
            dst[i] = f.applyAsDouble(src[i]);
        }
        return new Ndarray(dst, a.getShape().getDims());
    }

    /**
     * Applique une fonction f à chaque élément en place (modifie a).
     */
    private static Ndarray applyInPlace(Ndarray a, java.util.function.DoubleUnaryOperator f) {
        int n = a.getSize();
        int[] dims = a.getShape().getDims();
        double[] data = a.getData();
        for (int i = 0; i < n; i++) {
            data[i] = f.applyAsDouble(data[i]);
        }
        writeBackFlat(a, data, dims);
        return a;
    }

    /**
     * Réécrit un tableau plat dans un ndarray via des indices multi-dimensionnels.
     */
    private static void writeBackFlat(Ndarray a, double[] flat, int[] dims) {
        int ndim = dims.length;
        int[] idx = new int[ndim];
        for (int k = 0; k < flat.length; k++) {
            a.set(flat[k], idx);
            // incrémentation de idx en ordre row-major
            for (int d = ndim - 1; d >= 0; d--) {
                idx[d]++;
                if (idx[d] < dims[d])
                    break;
                idx[d] = 0;
            }
        }
    }

    /**
     * Racine carrée élément par élément.
     */
    public static Ndarray sqrt(Ndarray a) {
        return apply(a, Math::sqrt);
    }

    public static Ndarray sqrtInPlace(Ndarray a) {
        return applyInPlace(a, Math::sqrt);
    }

    /**
     * Exponentielle élément par élément.
     */
    public static Ndarray exp(Ndarray a) {
        return apply(a, Math::exp);
    }

    public static Ndarray expInPlace(Ndarray a) {
        return applyInPlace(a, Math::exp);
    }

    /**
     * Logarithme naturel élément par élément.
     */
    public static Ndarray log(Ndarray a) {
        return apply(a, Math::log);
    }

    public static Ndarray logInPlace(Ndarray a) {
        return applyInPlace(a, Math::log);
    }

    /**
     * Valeur absolue élément par élément.
     */
    public static Ndarray abs(Ndarray a) {
        return apply(a, Math::abs);
    }

    public static Ndarray absInPlace(Ndarray a) {
        return applyInPlace(a, Math::abs);
    }

    /**
     * Négation élément par élément.
     */
    public static Ndarray neg(Ndarray a) {
        return apply(a, x -> -x);
    }

    public static Ndarray negInPlace(Ndarray a) {
        return applyInPlace(a, x -> -x);
    }

    /**
     * Carré élément par élément.
     */
    public static Ndarray square(Ndarray a) {
        return apply(a, x -> x * x);
    }

    public static Ndarray squareInPlace(Ndarray a) {
        return applyInPlace(a, x -> x * x);
    }

    /**
     * Sinus élément par élément.
     */
    public static Ndarray sin(Ndarray a) {
        return apply(a, Math::sin);
    }

    public static Ndarray sinInPlace(Ndarray a) {
        return applyInPlace(a, Math::sin);
    }

    /**
     * Cosinus élément par élément.
     */
    public static Ndarray cos(Ndarray a) {
        return apply(a, Math::cos);
    }

    public static Ndarray cosInPlace(Ndarray a) {
        return applyInPlace(a, Math::cos);
    }

    /**
     * Tangente élément par élément.
     */
    public static Ndarray tan(Ndarray a) {
        return apply(a, Math::tan);
    }

    public static Ndarray tanInPlace(Ndarray a) {
        return applyInPlace(a, Math::tan);
    }
}
