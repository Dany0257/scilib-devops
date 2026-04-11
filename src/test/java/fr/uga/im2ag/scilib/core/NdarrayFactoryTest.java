package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la fabrique NdarrayFactory.
 */
public class NdarrayFactoryTest {

    @Test
    public void testZeros() {
        NdarrayInterface a = NdarrayFactory.zeros(2, 3);
        assertEquals(2, a.getNdim());
        assertEquals(6, a.getSize());
        assertEquals(0.0, a.get(1, 2)); // Vérifie qu'il est bien à zéro
    }

    @Test
    public void testOnes() {
        NdarrayInterface a = NdarrayFactory.ones(3);
        assertEquals(1, a.getNdim());
        assertEquals(3, a.getSize());
        assertEquals(1.0, a.get(0));
        assertEquals(1.0, a.get(2));
    }

    @Test
    public void testArange() {
        NdarrayInterface a = NdarrayFactory.arange(0, 5, 1);
        assertEquals(5, a.getSize());
        assertEquals(0.0, a.get(0));
        assertEquals(4.0, a.get(4));
    }

    @Test
    public void testArangeWithDecimals() {
        NdarrayInterface a = NdarrayFactory.arange(0, 1, 0.2);
        assertEquals(5, a.getSize());
        assertEquals(0.0, a.get(0));
        assertEquals(0.8, a.get(4), 0.0001); // Marge d'erreur pour les doubles
    }

    @Test
    public void testArangeExceptions() {
        assertThrows(IllegalArgumentException.class, () -> NdarrayFactory.arange(0, 5, 0));
        assertThrows(IllegalArgumentException.class, () -> NdarrayFactory.arange(5, 0, 1));
    }

    @Test
    public void testArray() {
        double[] input = {10.5, 20.5, 30.5};
        NdarrayInterface a = NdarrayFactory.array(input);
        assertEquals(1, a.getNdim());
        assertEquals(3, a.getSize());
        assertEquals(20.5, a.get(1));
    }

    @Test
    public void testArrayExceptions() {
        assertThrows(IllegalArgumentException.class, () -> NdarrayFactory.array(null));
        assertThrows(IllegalArgumentException.class, () -> NdarrayFactory.array(new double[]{}));
    }
}