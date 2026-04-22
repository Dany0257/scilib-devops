package fr.uga.im2ag.scilib.core;

import fr.uga.im2ag.scilib.exceptions.ShapeMismatchException;

/**
 * Utilitaire de Broadcasting pour les opérations sur Ndarray.
 * Supporte les opérations binaires élément par élément : add, sub, mul, div.
 */
public final class Broadcasting {

    private Broadcasting() {
        // classe utilitaire, pas d'instanciation
    }

    /**
     * Type d'opération pour broadcastOp.
     */
    public enum Op {
        ADD, SUB, MUL, DIV
    }

    /**
     * Calcule la forme de diffusion (broadcast shape) entre deux formes.
     * 
     * @throws ShapeMismatchException si les formes sont incompatibles.
     */
    public static int[] broadcastShape(int[] shapeA, int[] shapeB) {
        int ndim = Math.max(shapeA.length, shapeB.length);
        int[] result = new int[ndim];
        for (int i = 0; i < ndim; i++) {
            // alignement par la droite (dimensions de fin)
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

    /**
     * Retourne vrai si les deux formes peuvent être diffusées ensemble.
     */
    public static boolean isBroadcastable(int[] shapeA, int[] shapeB) {
        try {
            broadcastShape(shapeA, shapeB);
            return true;
        } catch (ShapeMismatchException e) {
            return false;
        }
    }

    /**
     * Effectue une opération binaire élément par élément avec broadcasting.
     * 
     * @return un nouveau Ndarray avec la forme diffusée.
     */
    public static Ndarray broadcastOp(Ndarray a, Ndarray b, Op op) {
        int[] shapeA = a.getShape().getDims();
        int[] shapeB = b.getShape().getDims();
        int[] outShape = broadcastShape(shapeA, shapeB);
        int outNdim = outShape.length;
        int outSize = 1;
        for (int d : outShape)
            outSize *= d;

        double[] result = new double[outSize];
        int[] outIdx = new int[outNdim];

        for (int k = 0; k < outSize; k++) {
            // construction des indices pour a et b à partir de outIdx, avec broadcasting
            int[] idxA = mapIndex(outIdx, shapeA);
            int[] idxB = mapIndex(outIdx, shapeB);
            double va = a.get(idxA);
            double vb = b.get(idxB);
            result[k] = applyOp(va, vb, op);

            // incrémentation de outIdx en ordre row-major
            for (int d = outNdim - 1; d >= 0; d--) {
                outIdx[d]++;
                if (outIdx[d] < outShape[d])
                    break;
                outIdx[d] = 0;
            }
        }
        return new Ndarray(result, outShape);
    }

    /**
     * Mappe un multi-indice de sortie vers le multi-indice d'un ndarray source,
     * en appliquant les règles de broadcasting (dim de taille 1 -> indice 0).
     */
    private static int[] mapIndex(int[] outIdx, int[] srcShape) {
        int srcNdim = srcShape.length;
        int outNdim = outIdx.length;
        int[] srcIdx = new int[srcNdim];
        for (int d = 0; d < srcNdim; d++) {
            // alignement par la droite
            int outDimPos = outNdim - srcNdim + d;
            int outVal = outIdx[outDimPos];
            // si la dimension source est 1, broadcast -> toujours l'indice 0
            srcIdx[d] = (srcShape[d] == 1) ? 0 : outVal;
        }
        return srcIdx;
    }

    /**
     * Applique l'opération binaire sur deux scalaires.
     */
    private static double applyOp(double a, double b, Op op) {
        switch (op) {
            case ADD:
                return a + b;
            case SUB:
                return a - b;
            case MUL:
                return a * b;
            case DIV:
                return a / b;
            default:
                throw new IllegalStateException("Unknown op: " + op);
        }
    }

    // Méthodes de commodité.
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