package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe Shape.
 */
public class ShapeTest {

    // --- Tests constructeur ---

    @Test
    public void testShape1D() {
        Shape s = new Shape(5);
        assertEquals(1, s.getNdim());
        assertEquals(5, s.getSize());
        assertArrayEquals(new int[] { 5 }, s.getDims());
    }

    @Test
    public void testShape2D() {
        Shape s = new Shape(2, 3);
        assertEquals(2, s.getNdim());
        assertEquals(6, s.getSize());
        assertArrayEquals(new int[] { 2, 3 }, s.getDims());
    }

    @Test
    public void testShape3D() {
        Shape s = new Shape(2, 3, 4);
        assertEquals(3, s.getNdim());
        assertEquals(24, s.getSize());
    }

    // --- Tests getDim ---

    @Test
    public void testGetDim() {
        Shape s = new Shape(2, 3);
        assertEquals(2, s.getDim(0));
        assertEquals(3, s.getDim(1));
    }

    // --- Tests immutabilité ---

    @Test
    public void testGetDimsReturnsCopy() {
        Shape s = new Shape(2, 3);
        int[] dims = s.getDims();
        dims[0] = 999; // modifier la copie
        assertEquals(2, s.getDim(0)); // l'original ne change pas
    }

    // --- Tests equals et hashCode ---

    @Test
    public void testEqualsSameShape() {
        Shape s1 = new Shape(2, 3);
        Shape s2 = new Shape(2, 3);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    public void testEqualsDifferentShape() {
        Shape s1 = new Shape(2, 3);
        Shape s2 = new Shape(3, 2);
        assertNotEquals(s1, s2);
    }

    @Test
    public void testEqualsSameObject() {
        Shape s = new Shape(4);
        assertEquals(s, s);
    }

    @Test
    public void testEqualsNull() {
        Shape s = new Shape(4);
        assertNotEquals(null, s);
    }

    // --- Tests toString ---

    @Test
    public void testToString1D() {
        Shape s = new Shape(5);
        assertEquals("(5)", s.toString());
    }

    @Test
    public void testToString2D() {
        Shape s = new Shape(2, 3);
        assertEquals("(2, 3)", s.toString());
    }

    // --- Tests erreurs ---

    @Test
    public void testShapeEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Shape());
    }

    @Test
    public void testShapeNegativeDimThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Shape(-1));
    }

    @Test
    public void testShapeZeroDimThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Shape(0));
    }

    @Test
    public void testShapeNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Shape(null));
    }
}
