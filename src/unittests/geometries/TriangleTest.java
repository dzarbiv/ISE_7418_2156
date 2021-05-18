package unittests.geometries;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Point3D  p0=new Point3D(1,1,-1);/**Vector starting point*/
        Point3D p1=new Point3D(0,1,0), p2=new Point3D(3,1,0), p3=new Point3D(0,5,0);/** The vertices of the triangle*/
        Triangle triangle=new Triangle(p1,p2,p3);
        // ============ Equivalence Partitions Tests ==============

        /** TC01: Inside triangle(1 point)*/
        List<Point3D> result=triangle.findIntersections(new Ray(p0,new Vector(0,1,1)));
        assertEquals( 1, result.size(),"Wrong number of points");
        result=List.of(result.get(0));
        assertEquals( List.of(new Point3D(1,2,0)), result,"Inside polygon");

        /** TC02: Outside against edge(0 point)*/
        assertNull(triangle.findIntersections(new Ray(p0,new Vector(1,2,1))),"Outside against edge");

        /** TC03: Outside against vertex(0 point)*/
        assertNull(triangle.findIntersections(new Ray(p0,new Vector(3,-1,1))),"Outside against vertex");

        // =============== Boundary Values Tests ==================
        /** **** Group: the ray begins"before" the plane*/
        /** TC11: On edge(0 point)*/
        assertNull(triangle.findIntersections(new Ray(p0,new Vector(-1,0,1))),"On edge");

        /** TC12: In vertex(0 point)*/
        assertNull(triangle.findIntersections(new Ray(p0,new Vector(-1,2,1))),"In vertex");

        /** TC13: On edge's continuation(0 point)*/
        assertNull(triangle.findIntersections(new Ray(p0,new Vector(3,0,1))),"On edge's continuation");





    }
}