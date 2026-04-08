package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.*;

import fr.uga.im2ag.scilib.exceptions.IndexOutOfBoundsNdarrayException;
import fr.uga.im2ag.scilib.exceptions.InvalidReshapeException;
import fr.uga.im2ag.scilib.exceptions.ShapeMismatchException;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe Ndarray.
 */
public class NdarrayTest {

    // ===================================
    // CONSTRUCTEURS
    // ===================================

    @Test
    public void testConstructorZeros1D() {
        Ndarray a = new Ndarray(3);
        assertEquals(1, a.getNdim());
        assertEquals(3, a.getSize());
        assertArrayEquals(new int[] { 3 }, a.getShape().getDims());
        // Vérifie que tout est à zéro
        for (int i = 0; i < 3; i++) {
            assertEquals(0.0, a.get(i));
        }
    }

    @Test
    public void testConstructorZeros2D() {
        Ndarray a = new Ndarray(2, 3);
        assertEquals(2, a.getNdim());
        assertEquals(6, a.getSize());
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 3; c++) {
                assertEquals(0.0, a.get(r, c));
            }
        }
    }

    @Test
    public void testConstructorWithData() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        assertEquals(2, a.getNdim());
        assertEquals(6, a.getSize());
        assertEquals(1.0, a.get(0, 0));
        assertEquals(6.0, a.get(1, 2));
    }

    @Test
    public void testConstructorDataSizeMismatch() {
        assertThrows(IllegalArgumentException.class, () -> new Ndarray(new double[] { 1, 2, 3 }, new int[] { 2, 3 }));
    }

    // ===================================
    // GET / SET
    // ===================================

    @Test
    public void testGetSet1D() {
        Ndarray a = new Ndarray(3);
        a.set(5.0, 0);
        a.set(10.0, 2);
        assertEquals(5.0, a.get(0));
        assertEquals(0.0, a.get(1));
        assertEquals(10.0, a.get(2));
    }

    @Test
    public void testGetSet2D() {
        Ndarray a = new Ndarray(2, 3);
        a.set(42.0, 1, 2);
        assertEquals(42.0, a.get(1, 2));
        assertEquals(0.0, a.get(0, 0));
    }

    @Test
    public void testGetIndexOutOfBounds() {
        Ndarray a = new Ndarray(2, 3);
        assertThrows(IndexOutOfBoundsNdarrayException.class, () -> a.get(5, 0));
    }

    @Test
    public void testGetNegativeIndex() {
        Ndarray a = new Ndarray(3);
        assertThrows(IndexOutOfBoundsNdarrayException.class, () -> a.get(-1));
    }

    @Test
    public void testGetWrongNumberOfIndices() {
        Ndarray a = new Ndarray(2, 3);
        assertThrows(IndexOutOfBoundsNdarrayException.class, () -> a.get(0));
    }

    @Test
    public void testSetIndexOutOfBounds() {
        Ndarray a = new Ndarray(3);
        assertThrows(IndexOutOfBoundsNdarrayException.class, () -> a.set(1.0, 5));
    }

    // ===================================
    // GETDATA
    // ===================================

    @Test
    public void testGetDataReturnsCopy() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        double[] data = a.getData();
        data[0] = 999;
        assertEquals(1.0, a.get(0)); // l'original ne change pas
    }

    // ===================================
    // ADD (nouveau ndarray)
    // ===================================

    @Test
    public void testAdd1D() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 4, 5, 6 }, new int[] { 3 });
        NdarrayInterface result = a.add(b);
        assertEquals(5.0, result.get(0));
        assertEquals(7.0, result.get(1));
        assertEquals(9.0, result.get(2));
        // Vérifie que a n'a pas changé
        assertEquals(1.0, a.get(0));
    }

    @Test
    public void testAdd2D() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 2, 2 });
        Ndarray b = new Ndarray(new double[] { 10, 20, 30, 40 }, new int[] { 2, 2 });
        NdarrayInterface result = a.add(b);
        assertEquals(11.0, result.get(0, 0));
        assertEquals(44.0, result.get(1, 1));
    }

    @Test
    public void testAddShapeMismatch() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2 }, new int[] { 2 });
        assertThrows(ShapeMismatchException.class, () -> a.add(b));
    }

    @Test
    public void testAddScalar() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        NdarrayInterface result = a.add(10.0);
        assertEquals(11.0, result.get(0));
        assertEquals(12.0, result.get(1));
        assertEquals(13.0, result.get(2));
        // a n'a pas changé
        assertEquals(1.0, a.get(0));
    }

    // ===================================
    // ADD IN PLACE (modifie l'objet)
    // ===================================

    @Test
    public void testAddInPlace() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 10, 20, 30 }, new int[] { 3 });
        NdarrayInterface result = a.addInPlace(b);
        assertEquals(11.0, a.get(0));
        assertEquals(22.0, a.get(1));
        assertEquals(33.0, a.get(2));
        assertSame(a, result); // retourne this
    }

    @Test
    public void testAddInPlaceShapeMismatch() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2 }, new int[] { 2 });
        assertThrows(ShapeMismatchException.class, () -> a.addInPlace(b));
    }

    @Test
    public void testAddInPlaceScalar() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        NdarrayInterface result = a.addInPlace(5.0);
        assertEquals(6.0, a.get(0));
        assertEquals(7.0, a.get(1));
        assertEquals(8.0, a.get(2));
        assertSame(a, result);
    }

    // ===================================
    // RESHAPE
    // ===================================

    @Test
    public void testReshape() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 6 });
        NdarrayInterface reshaped = a.reshape(2, 3);
        assertEquals(2, reshaped.getNdim());
        assertArrayEquals(new int[] { 2, 3 }, reshaped.getShape().getDims());
        assertEquals(1.0, reshaped.get(0, 0));
        assertEquals(4.0, reshaped.get(1, 0));
        assertEquals(6.0, reshaped.get(1, 2));
    }

    @Test
    public void testReshape2Dto1D() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 2, 2 });
        NdarrayInterface reshaped = a.reshape(4);
        assertEquals(1, reshaped.getNdim());
        assertEquals(1.0, reshaped.get(0));
        assertEquals(4.0, reshaped.get(3));
    }

    @Test
    public void testReshapeInvalidSize() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 6 });
        assertThrows(InvalidReshapeException.class, () -> a.reshape(2, 4));
    }

    // ===================================
    // TOSTRING
    // ===================================

    @Test
    public void testToString1D() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        assertEquals("[1.0, 2.0, 3.0]", a.toString());
    }

    @Test
    public void testToString2D() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 2, 2 });
        String expected = "[[1.0, 2.0]\n [3.0, 4.0]]";
        assertEquals(expected, a.toString());
    }

    @Test
    public void testToStringDecimals() {
        Ndarray a = new Ndarray(new double[] { 1.5, 2.75 }, new int[] { 2 });
        assertEquals("[1.5, 2.75]", a.toString());
    }

    // ===================================
    // EQUALS / HASHCODE
    // ===================================

    @Test
    public void testEqualsSame() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqualsDifferentData() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2, 4 }, new int[] { 3 });
        assertNotEquals(a, b);
    }

    @Test
    public void testEqualsDifferentShape() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 3, 2 });
        assertNotEquals(a, b);
    }

    @Test
    public void testEqualsSameObject() {
        Ndarray a = new Ndarray(new double[] { 1, 2 }, new int[] { 2 });
        assertEquals(a, a);
    }

    @Test
    public void testEqualsNull() {
        Ndarray a = new Ndarray(new double[] { 1, 2 }, new int[] { 2 });
        assertNotEquals(null, a);
    }
}
