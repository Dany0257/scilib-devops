package fr.uga.im2ag.scilib.exceptions;

import fr.uga.im2ag.scilib.core.Shape;

/**
 * Levée quand un reshape change le nombre total d'éléments.
 * Ex: reshape (2, 3) size=6 en (2, 4) size=8.
 */
public class InvalidReshapeException extends RuntimeException {

    public InvalidReshapeException(Shape currentShape, int[] newDims) {
        super("Cannot reshape array of size " + currentShape.getSize()
                + " " + currentShape + " into shape " + formatDims(newDims));
    }

    private static String formatDims(int[] dims) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < dims.length; i++) {
            sb.append(dims[i]);
            if (i < dims.length - 1)
                sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}
