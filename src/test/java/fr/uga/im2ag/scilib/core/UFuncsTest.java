package fr.uga.im2ag.scilib.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Unit tests for the UFuncs utility class.
public class UFuncsTest {

    private static final double EPS = 1e-9;

    // SQRT

    @Test
    public void testSqrt1D() {
        Ndarray a = new Ndarray(new double[] { 1, 4, 9, 16 }, new int[] { 4 });
        Ndarray r = UFuncs.sqrt(a);
        assertEquals(1.0, r.get(0), EPS);
        assertEquals(2.0, r.get(1), EPS);
        assertEquals(3.0, r.get(2), EPS);
        assertEquals(4.0, r.get(3), EPS);
        // a unchanged
        assertEquals(4.0, a.get(1), EPS);
    }

    @Test
    public void testSqrt2D() {
        Ndarray a = new Ndarray(new double[] { 1, 4, 9, 16 }, new int[] { 2, 2 });
        Ndarray r = UFuncs.sqrt(a);
        assertEquals(1.0, r.get(0, 0), EPS);
        assertEquals(2.0, r.get(0, 1), EPS);
        assertEquals(3.0, r.get(1, 0), EPS);
        assertEquals(4.0, r.get(1, 1), EPS);
    }

    @Test
    public void testSqrtInPlace() {
        Ndarray a = new Ndarray(new double[] { 1, 4, 9 }, new int[] { 3 });
        Ndarray r = UFuncs.sqrtInPlace(a);
        assertSame(a, r);
        assertEquals(1.0, a.get(0), EPS);
        assertEquals(2.0, a.get(1), EPS);
        assertEquals(3.0, a.get(2), EPS);
    }

    // EXP

    @Test
    public void testExp() {
        Ndarray a = new Ndarray(new double[] { 0, 1, 2 }, new int[] { 3 });
        Ndarray r = UFuncs.exp(a);
        assertEquals(1.0, r.get(0), EPS);
        assertEquals(Math.E, r.get(1), EPS);
        assertEquals(Math.E * Math.E, r.get(2), EPS);
    }

    @Test
    public void testExpInPlace() {
        Ndarray a = new Ndarray(new double[] { 0, 1 }, new int[] { 2 });
        UFuncs.expInPlace(a);
        assertEquals(1.0, a.get(0), EPS);
        assertEquals(Math.E, a.get(1), EPS);
    }

    // LOG

    @Test
    public void testLog() {
        Ndarray a = new Ndarray(new double[] { 1, Math.E, Math.E * Math.E }, new int[] { 3 });
        Ndarray r = UFuncs.log(a);
        assertEquals(0.0, r.get(0), EPS);
        assertEquals(1.0, r.get(1), EPS);
        assertEquals(2.0, r.get(2), EPS);
    }

    @Test
    public void testLogInPlace() {
        Ndarray a = new Ndarray(new double[] { 1, Math.E }, new int[] { 2 });
        UFuncs.logInPlace(a);
        assertEquals(0.0, a.get(0), EPS);
        assertEquals(1.0, a.get(1), EPS);
    }

    // ABS

    @Test
    public void testAbs() {
        Ndarray a = new Ndarray(new double[] { -1, 2, -3, 4 }, new int[] { 4 });
        Ndarray r = UFuncs.abs(a);
        assertEquals(1.0, r.get(0), EPS);
        assertEquals(2.0, r.get(1), EPS);
        assertEquals(3.0, r.get(2), EPS);
        assertEquals(4.0, r.get(3), EPS);
        // a unchanged
        assertEquals(-1.0, a.get(0), EPS);
    }

    @Test
    public void testAbsInPlace() {
        Ndarray a = new Ndarray(new double[] { -5, -10, 3 }, new int[] { 3 });
        UFuncs.absInPlace(a);
        assertEquals(5.0, a.get(0), EPS);
        assertEquals(10.0, a.get(1), EPS);
        assertEquals(3.0, a.get(2), EPS);
    }

    // NEG

    @Test
    public void testNeg() {
        Ndarray a = new Ndarray(new double[] { 1, -2, 3 }, new int[] { 3 });
        Ndarray r = UFuncs.neg(a);
        assertEquals(-1.0, r.get(0), EPS);
        assertEquals(2.0, r.get(1), EPS);
        assertEquals(-3.0, r.get(2), EPS);
    }

    @Test
    public void testNegInPlace() {
        Ndarray a = new Ndarray(new double[] { 1, -2, 3 }, new int[] { 3 });
        UFuncs.negInPlace(a);
        assertEquals(-1.0, a.get(0), EPS);
        assertEquals(2.0, a.get(1), EPS);
        assertEquals(-3.0, a.get(2), EPS);
    }

    // SQUARE

    @Test
    public void testSquare() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4 }, new int[] { 4 });
        Ndarray r = UFuncs.square(a);
        assertEquals(1.0, r.get(0), EPS);
        assertEquals(4.0, r.get(1), EPS);
        assertEquals(9.0, r.get(2), EPS);
        assertEquals(16.0, r.get(3), EPS);
    }

    @Test
    public void testSquareInPlace() {
        Ndarray a = new Ndarray(new double[] { 2, 5 }, new int[] { 2 });
        UFuncs.squareInPlace(a);
        assertEquals(4.0, a.get(0), EPS);
        assertEquals(25.0, a.get(1), EPS);
    }

    // SIN / COS / TAN

    @Test
    public void testSin() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI / 2, Math.PI }, new int[] { 3 });
        Ndarray r = UFuncs.sin(a);
        assertEquals(0.0, r.get(0), EPS);
        assertEquals(1.0, r.get(1), EPS);
        assertEquals(0.0, r.get(2), EPS);
    }

    @Test
    public void testSinInPlace() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI / 2 }, new int[] { 2 });
        UFuncs.sinInPlace(a);
        assertEquals(0.0, a.get(0), EPS);
        assertEquals(1.0, a.get(1), EPS);
    }

    @Test
    public void testCos() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI / 2, Math.PI }, new int[] { 3 });
        Ndarray r = UFuncs.cos(a);
        assertEquals(1.0, r.get(0), EPS);
        assertEquals(0.0, r.get(1), EPS);
        assertEquals(-1.0, r.get(2), EPS);
    }

    @Test
    public void testCosInPlace() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI }, new int[] { 2 });
        UFuncs.cosInPlace(a);
        assertEquals(1.0, a.get(0), EPS);
        assertEquals(-1.0, a.get(1), EPS);
    }

    @Test
    public void testTan() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI / 4 }, new int[] { 2 });
        Ndarray r = UFuncs.tan(a);
        assertEquals(0.0, r.get(0), EPS);
        assertEquals(1.0, r.get(1), EPS);
    }

    @Test
    public void testTanInPlace() {
        Ndarray a = new Ndarray(new double[] { 0, Math.PI / 4 }, new int[] { 2 });
        UFuncs.tanInPlace(a);
        assertEquals(0.0, a.get(0), EPS);
        assertEquals(1.0, a.get(1), EPS);
    }

    // SHAPE PRESERVATION

    @Test
    public void testShapePreserved() {
        Ndarray a = new Ndarray(new double[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 3 });
        Ndarray r = UFuncs.square(a);
        assertArrayEquals(new int[] { 2, 3 }, r.getShape().getDims());
        assertEquals(2, r.getNdim());
        assertEquals(6, r.getSize());
    }
}
