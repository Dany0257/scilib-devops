package fr.uga.im2ag.scilib.core;

import fr.uga.im2ag.scilib.exceptions.ShapeMismatchException;

// Broadcasting utility for Ndarray operations.

// Supports element-wise binary operations: add, sub, mul, div.
public final class Broadcasting {

    private Broadcasting() {
        // utility class, no instantiation
    }

    // Operation kind for broadcastOp.
    public enum Op {
        ADD, SUB, MUL, DIV
    }

    // Computes the broadcast shape between two shapes.
    // Throws ShapeMismatchException if incompatible.
    public static int[] broadcastShape(int[] shapeA, int[] shapeB) {
        int ndim = Math.max(shapeA.length, shapeB.length);
        int[] result = new int[ndim];
        for (int i = 0; i < ndim; i++) {
            // align from the right (trailing dimensions)
            int dimA = (i < shapeA.length) ? shapeA[shapeA.length - 1 - i] : 1;
            int dimB = (i < shapeB.length) ? shapeB[shapeB.length - 1 - i] : 1;
            if (dimA != dimB && dimA != 1 && dimB != 1) {
                throw new ShapeMismatchException(
                        new Shape(shapeA), new Shape(shapeB));
            }
            result[ndim - 1 - i] = Math.max(dimA, dimB);
        }
        return result;
    }

    // Returns true if the two shapes can be broadcast together.
    public static boolean isBroadcastable(int[] shapeA, int[] shapeB) {
        try {
            broadcastShape(shapeA, shapeB);
            return true;
        } catch (ShapeMismatchException e) {
            return false;
        }
    }

    // Performs an element-wise binary operation with broadcasting.
    // Returns a new Ndarray with the broadcast shape.
    public static Ndarray broadcastOp(Ndarray a, Ndarray b, Op op) {
        int[] shapeA = a.getShape().getDims();
        int[] shapeB = b.getShape().getDims();
        int[] outShape = broadcastShape(shapeA, shapeB);
        int outNdim = outShape.length;
        int outSize = 1;
        for (int d : outShape) outSize *= d;

        double[] result = new double[outSize];
        int[] outIdx = new int[outNdim];

        for (int k = 0; k < outSize; k++) {
            // build indices into a and b from outIdx, with broadcasting
            int[] idxA = mapIndex(outIdx, shapeA);
            int[] idxB = mapIndex(outIdx, shapeB);
            double va = a.get(idxA);
            double vb = b.get(idxB);
            result[k] = applyOp(va, vb, op);

            // increment outIdx in row-major order
            for (int d = outNdim - 1; d >= 0; d--) {
                outIdx[d]++;
                if (outIdx[d] < outShape[d]) break;
                outIdx[d] = 0;
            }
        }
        return new Ndarray(result, outShape);
    }

    // Maps an output multi-index to a source ndarray's multi-index,
    // applying broadcasting rules (dim of size 1 -> index 0, missing leading dims -> dropped).
    private static int[] mapIndex(int[] outIdx, int[] srcShape) {
        int srcNdim = srcShape.length;
        int outNdim = outIdx.length;
        int[] srcIdx = new int[srcNdim];
        for (int d = 0; d < srcNdim; d++) {
            // align from the right
            int outDimPos = outNdim - srcNdim + d;
            int outVal = outIdx[outDimPos];
            // if src dim is 1, broadcast -> always index 0
            srcIdx[d] = (srcShape[d] == 1) ? 0 : outVal;
        }
        return srcIdx;
    }

    // Applies the binary operation on two scalars.
    private static double applyOp(double a, double b, Op op) {
        switch (op) {
            case ADD: return a + b;
            case SUB: return a - b;
            case MUL: return a * b;
            case DIV: return a / b;
            default:  throw new IllegalStateException("Unknown op: " + op);
        }
    }

    // Convenience methods.
    public static Ndarray add(Ndarray a, Ndarray b) {
        return broadcastOp(a, b, Op.ADD);
    }

    public static Ndarray sub(Ndarray a, Ndarray b) {
        return broadcastOp(a, b, Op.SUB);
    }

    public static Ndarray mul(Ndarray a, Ndarray b) {
        return broadcastOp(a, b, Op.MUL);
    }

    public static Ndarray div(Ndarray a, Ndarray b) {
        return broadcastOp(a, b, Op.DIV);
    }
}