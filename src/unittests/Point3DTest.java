package unittests;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Point3DTest {

    @Test
    void subtract() {
        Point3D p1 = new Point3D(1, 2, 3);
       assertEquals(new Vector(1, 1, 1),(new Point3D(2, 3, 4).subtract(p1)),"ERROR: Point - Point does not work correctly");

    }

    @Test
    void add() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(Point3D.ZERO,(p1.add(new Vector(-1, -2, -3))),"ERROR: Point + Vector does not work correctly");
    }

    @Test
    void distanceSquared() {
    }

    @Test
    void distance() {
    }
}