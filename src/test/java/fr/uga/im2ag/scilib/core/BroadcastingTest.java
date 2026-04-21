package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.*;

import fr.uga.im2ag.scilib.exceptions.ShapeMismatchException;
import org.junit.jupiter.api.Test;

// Unit tests for the Broadcasting utility class.
public class BroadcastingTest {

    private static final double EPS = 1e-9;

    // BROADCAST SHAPE

    @Test
    public void testBroadcastShapeSameShape() {
        int[] result = Broadcasting.broadcastShape(new int[] { 2, 3 }, new int[] { 2, 3 });
        assertArrayEquals(new int[] { 2, 3 }, result);
    }

    @Test
    public void testBroadcastShapeVectorAndMatrix() {
        // (3,) and (2, 3) -> (2, 3)
        int[] result = Broadcasting.broadcastShape(new int[] { 3 }, new int[] { 2, 3 });
        assertArrayEquals(new int[] { 2, 3 }, result);
    }

    @Test
    public void testBroadcastShapeColumnAndMatrix() {
        // (2, 1) and (2, 3) -> (2, 3)
        int[] result = Broadcasting.broadcastShape(new int[] { 2, 1 }, new int[] { 2, 3 });
        assertArrayEquals(new int[] { 2, 3 }, result);
    }

    @Test
    public void testBroadcastShapeOuter() {
        // (4, 1) and (1, 5) -> (4, 5)
        int[] result = Broadcasting.broadcastShape(new int[] { 4, 1 }, new int[] { 1, 5 });
        assertArrayEquals(new int[] { 4, 5 }, result);
    }

    @Test
    public void testBroadcastShapeIncompatible() {
        assertThrows(ShapeMismatchException.class,
                () -> Broadcasting.broadcastShape(new int[] { 2, 3 }, new int[] { 4, 3 }));
    }

    @Test
    public void testIsBroadcastableTrue() {
        assertTrue(Broadcasting.isBroadcastable(new int[] { 3 }, new int[] { 2, 3 }));
    }

    @Test
    public void testIsBroadcastableFalse() {
        assertFalse(Broadcasting.isBroadcastable(new int[] { 2, 3 }, new int[] { 4, 3 }));
    }

    // ADD with broadcasting

    @Test
    public void testAddSameShape() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 2, 2 });
        Ndarray b = new Ndarray(new double[] { 10, 20, 30, 40 }, new int[] { 2, 2 });
        Ndarray r = Broadcasting.add(a, b);
        assertEquals(11.0, r.get(0, 0), EPS);
        assertEquals(22.0, r.get(0, 1), EPS);
        assertEquals(33.0, r.get(1, 0), EPS);
        assertEquals(44.0, r.get(1, 1), EPS);
    }

    @Test
    public void testAddVectorToMatrix() {
        // matrix (2,3) + vector (3,) -> matrix (2,3)
        Ndarray m = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray v = new Ndarray(new double[] { 10, 20, 30 }, new int[] { 3 });
        Ndarray r = Broadcasting.add(m, v);
        assertArrayEquals(new int[] { 2, 3 }, r.getShape().getDims());
        assertEquals(11.0, r.get(0, 0), EPS);
        assertEquals(22.0, r.get(0, 1), EPS);
        assertEquals(33.0, r.get(0, 2), EPS);
        assertEquals(14.0, r.get(1, 0), EPS);
        assertEquals(25.0, r.get(1, 1), EPS);
        assertEquals(36.0, r.get(1, 2), EPS);
    }

    @Test
    public void testAddColumnVectorToMatrix() {
        // matrix (2,3) + column (2,1) -> matrix (2,3)
        Ndarray m = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray col = new Ndarray(new double[] { 10, 20 }, new int[] { 2, 1 });
        Ndarray r = Broadcasting.add(m, col);
        assertEquals(11.0, r.get(0, 0), EPS);
        assertEquals(12.0, r.get(0, 1), EPS);
        assertEquals(13.0, r.get(0, 2), EPS);
        assertEquals(24.0, r.get(1, 0), EPS);
        assertEquals(25.0, r.get(1, 1), EPS);
        assertEquals(26.0, r.get(1, 2), EPS);
    }

    @Test
    public void testAddOuter() {
        // (4,1) + (1,5) -> (4,5)
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 4, 1 });
        Ndarray b = new Ndarray(new double[] { 10, 20, 30, 40, 50 }, new int[] { 1, 5 });
        Ndarray r = Broadcasting.add(a, b);
        assertArrayEquals(new int[] { 4, 5 }, r.getShape().getDims());
        assertEquals(11.0, r.get(0, 0), EPS);
        assertEquals(51.0, r.get(0, 4), EPS);
        assertEquals(14.0, r.get(3, 0), EPS);
        assertEquals(54.0, r.get(3, 4), EPS);
    }

    @Test
    public void testAddIncompatible() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray b = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 2, 2 });
        assertThrows(ShapeMismatchException.class, () -> Broadcasting.add(a, b));
    }

    // SUB / MUL / DIV with broadcasting

    @Test
    public void testSubBroadcasting() {
        Ndarray m = new Ndarray(new double[] { 10, 20, 30, 40, 50, 60 }, new int[] { 2, 3 });
        Ndarray v = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray r = Broadcasting.sub(m, v);
        assertEquals(9.0, r.get(0, 0), EPS);
        assertEquals(18.0, r.get(0, 1), EPS);
        assertEquals(27.0, r.get(0, 2), EPS);
        assertEquals(39.0, r.get(1, 0), EPS);
        assertEquals(48.0, r.get(1, 1), EPS);
        assertEquals(57.0, r.get(1, 2), EPS);
    }

    @Test
    public void testMulBroadcasting() {
        Ndarray m = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray v = new Ndarray(new double[] { 10, 100, 1000 }, new int[] { 3 });
        Ndarray r = Broadcasting.mul(m, v);
        assertEquals(10.0, r.get(0, 0), EPS);
        assertEquals(200.0, r.get(0, 1), EPS);
        assertEquals(3000.0, r.get(0, 2), EPS);
        assertEquals(40.0, r.get(1, 0), EPS);
        assertEquals(500.0, r.get(1, 1), EPS);
        assertEquals(6000.0, r.get(1, 2), EPS);
    }

    @Test
    public void testDivBroadcasting() {
        Ndarray m = new Ndarray(new double[] { 10, 20, 30, 40 }, new int[] { 2, 2 });
        Ndarray v = new Ndarray(new double[] { 2, 4 }, new int[] { 2 });
        Ndarray r = Broadcasting.div(m, v);
        assertEquals(5.0, r.get(0, 0), EPS);
        assertEquals(5.0, r.get(0, 1), EPS);
        assertEquals(15.0, r.get(1, 0), EPS);
        assertEquals(10.0, r.get(1, 1), EPS);
    }

    // PRESERVATION

    @Test
    public void testInputsUnchanged() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3 }, new int[] { 3 });
        Ndarray b = new Ndarray(new double[] { 10, 20, 30 }, new int[] { 3 });
        Broadcasting.add(a, b);
        // a and b unchanged
        assertEquals(1.0, a.get(0), EPS);
        assertEquals(10.0, b.get(0), EPS);
    }
}