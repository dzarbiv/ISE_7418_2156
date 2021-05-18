package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTest {
    /**
     * Test method for
     * {@link primitives.Ray#findClosestPoint(List < Point3D >)}.
     */
    @Test
    void findClosestPoint() {
        Ray ray=new Ray(Point3D.ZERO,new Vector(1,0,0));
        Point3D point3D=new Point3D(1,0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the fund
        List<Point3D> lp= List.of(new Point3D(2, 0, 0),new Point3D(3, 0, 0), new Point3D(1, 0, 0), new Point3D(4, 0, 0), new Point3D(5, 0, 0));
        assertEquals(point3D,ray.findClosestPoint(lp), "not fund a point in the middle of the list");


        // =============== Boundary Values Tests ==================

        // TC01:  Empty list
        assertNull(ray.findClosestPoint(null));

        //TC02: The first point is closest to the beginning of the foundation
        List<Point3D> lp1= List.of(new Point3D(1, 0, 0),new Point3D(2, 0, 0),new Point3D(3, 0, 0),  new Point3D(4, 0, 0), new Point3D(5, 0, 0));
        assertEquals(point3D,ray.findClosestPoint(lp1), "not fund the point in first list");


        //TC02: The last point is closest to the beginning of the foundation
        List<Point3D> lp2= List.of(new Point3D(2, 0, 0),new Point3D(3, 0, 0),  new Point3D(4, 0, 0), new Point3D(5, 0, 0),new Point3D(1, 0, 0));
        assertEquals(point3D,ray.findClosestPoint(lp2), "not fund the point in end of th list");




    }
}