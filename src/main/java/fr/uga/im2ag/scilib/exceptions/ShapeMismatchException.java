package fr.uga.im2ag.scilib.exceptions;

import fr.uga.im2ag.scilib.core.Shape;

/**
 * Levée quand deux ndarrays ont des shapes incompatibles.
 * Ex: additionner un (2, 3) avec un (3, 2).
 */
public class ShapeMismatchException extends RuntimeException {

    public ShapeMismatchException(Shape expected, Shape actual) {
        super("Shape mismatch: " + expected + " vs " + actual);
    }
}
