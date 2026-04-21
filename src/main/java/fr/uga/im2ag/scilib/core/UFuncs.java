package fr.uga.im2ag.scilib.core;

// Universal functions (ufuncs) for Ndarray.

public final class UFuncs {

    private UFuncs() {
        // utility class, no instantiation
    }

    // Applies a function f to each element and returns a new ndarray.
    private static Ndarray apply(Ndarray a, java.util.function.DoubleUnaryOperator f) {
        double[] src = a.getData();
        double[] dst = new double[src.length];
        for (int i = 0; i < src.length; i++) {
            dst[i] = f.applyAsDouble(src[i]);
        }
        return new Ndarray(dst, a.getShape().getDims());
    }

    // Applies a function f to each element in place (modifies a).
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

    // Writes a flat array back into a ndarray via multi-index set().
    private static void writeBackFlat(Ndarray a, double[] flat, int[] dims) {
        int ndim = dims.length;
        int[] idx = new int[ndim];
        for (int k = 0; k < flat.length; k++) {
            a.set(flat[k], idx);
            // increment idx in row-major order
            for (int d = ndim - 1; d >= 0; d--) {
                idx[d]++;
                if (idx[d] < dims[d])
                    break;
                idx[d] = 0;
            }
        }
    }

    // Element-wise square root.
    public static Ndarray sqrt(Ndarray a) {
        return apply(a, Math::sqrt);
    }

    public static Ndarray sqrtInPlace(Ndarray a) {
        return applyInPlace(a, Math::sqrt);
    }

    // Element-wise exponential.
    public static Ndarray exp(Ndarray a) {
        return apply(a, Math::exp);
    }

    public static Ndarray expInPlace(Ndarray a) {
        return applyInPlace(a, Math::exp);
    }

    // Element-wise natural logarithm.
    public static Ndarray log(Ndarray a) {
        return apply(a, Math::log);
    }

    public static Ndarray logInPlace(Ndarray a) {
        return applyInPlace(a, Math::log);
    }

    // Element-wise absolute value.
    public static Ndarray abs(Ndarray a) {
        return apply(a, Math::abs);
    }

    public static Ndarray absInPlace(Ndarray a) {
        return applyInPlace(a, Math::abs);
    }

    // Element-wise negation.
    public static Ndarray neg(Ndarray a) {
        return apply(a, x -> -x);
    }

    public static Ndarray negInPlace(Ndarray a) {
        return applyInPlace(a, x -> -x);
    }

    // Element-wise square.
    public static Ndarray square(Ndarray a) {
        return apply(a, x -> x * x);
    }

    public static Ndarray squareInPlace(Ndarray a) {
        return applyInPlace(a, x -> x * x);
    }

    // Element-wise sine.
    public static Ndarray sin(Ndarray a) {
        return apply(a, Math::sin);
    }

    public static Ndarray sinInPlace(Ndarray a) {
        return applyInPlace(a, Math::sin);
    }

    // Element-wise cosine.
    public static Ndarray cos(Ndarray a) {
        return apply(a, Math::cos);
    }

    public static Ndarray cosInPlace(Ndarray a) {
        return applyInPlace(a, Math::cos);
    }

    // Element-wise tangent.
    public static Ndarray tan(Ndarray a) {
        return apply(a, Math::tan);
    }

    public static Ndarray tanInPlace(Ndarray a) {
        return applyInPlace(a, Math::tan);
    }
}
