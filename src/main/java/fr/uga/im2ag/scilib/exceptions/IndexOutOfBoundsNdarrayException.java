package fr.uga.im2ag.scilib.exceptions;

import fr.uga.im2ag.scilib.core.Shape;
import java.util.Arrays;

/**
 * Levée quand on accède à un index hors limites.
 * Ex: get(5, 0) sur une shape (2, 3).
 */
public class IndexOutOfBoundsNdarrayException extends RuntimeException {

    public IndexOutOfBoundsNdarrayException(int[] indices, Shape shape) {
        super("Index " + Arrays.toString(indices)
                + " is out of bounds for shape " + shape);
    }
}
