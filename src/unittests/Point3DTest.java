package unittests;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Point3DTest {
    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        /**Subtraction between 2 points*/
        Point3D p1 = new Point3D(1, 2, 3);
       assertEquals(new Vector(1, 1, 1),(new Point3D(2, 3, 4).subtract(p1)),"ERROR: Point - Point does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        /** Adding a point to the vector*/
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(Point3D.ZERO,(p1.add(new Vector(-1, -2, -3))),"ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}.
     */
    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        /**Distance between 2 points squared*/
        Point3D p=new Point3D(1,1,1);
        assertEquals(p.distanceSquared(new Point3D(1,1,0)),1,"Error: distanceSquared does not calculated correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        /**Distance between 2 points*/
        Point3D p=new Point3D(1,1,1);
        assertEquals(p.distance(new Point3D(1,1,0)),1,"Error: distance does not calculated correctly");
    }
}