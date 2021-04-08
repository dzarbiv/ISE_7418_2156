package unittests;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for primitives.Vector class
 * @author Devora Zarbiv and Rachel Lea Kohen
 */

public class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        /***/
        Point3D p1 = new Point3D(1, 2, 3);
        assertFalse (!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))),"ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p1 = new Point3D(1, 2, 3);
        assertFalse (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)),"ERROR: Point - Point does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1.scale(3),new Vector(3,6,9), "Error: scalar vector does not work good");

        assertEquals(v1.scale(-3),new Vector(-3,-6,-9), "Error: scalar vector does not work good");

        assertEquals(v1.scale(0.5),new Vector(0.5,1,1.5), "Error: scalar vector does not work good");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {

        Vector v3 = new Vector(0, 3, -2);
        // =============== Boundary Values Tests ==================
        assertFalse (!isZero(v1.dotProduct(v3)),"ERROR: dotProduct() for orthogonal vectors is not zero");
        // ============ Equivalence Partitions Tests ==============
        assertTrue(!isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {


        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}


    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertFalse (!isZero(v1.lengthSquared() - 14),"ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        assertFalse(!isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");

    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertFalse(vCopy != vCopyNormalize,"ERROR: normalize() function creates a new vector");
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    void normalized() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse(u == v,"ERROR: normalized() function does not create a new vector");

    }
}