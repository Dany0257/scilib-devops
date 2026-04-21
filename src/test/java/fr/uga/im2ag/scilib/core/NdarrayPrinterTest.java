package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour l'utilitaire d'affichage NdarrayPrinter.
 */
class NdarrayPrinterTest {

    @Test
    void testFormat1D() {
        NdarrayInterface a = NdarrayFactory.array(new double[]{1.0, 2.5, 3.0});
        String expected = "[1.0, 2.5, 3.0]";
        assertEquals(expected, NdarrayPrinter.format(a));
    }

    @Test
    void testFormat2D() {
        // On crée un tableau 2x2 rempli de zéros puis on modifie les valeurs
        NdarrayInterface a = NdarrayFactory.zeros(2, 2);
        a.set(1.0, 0, 0);
        a.set(2.0, 0, 1);
        a.set(3.0, 1, 0);
        a.set(4.5, 1, 1);
        
        String expected = "[[1.0, 2.0]\n [3.0, 4.5]]";
        assertEquals(expected, NdarrayPrinter.format(a));
    }

    @Test
    void testFormatFallback3D() {
        NdarrayInterface a = NdarrayFactory.zeros(2, 2, 2);
        String expected = "Ndarray(shape=(2, 2, 2))";
        assertEquals(expected, NdarrayPrinter.format(a));
    }
}