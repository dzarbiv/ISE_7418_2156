package unittests;

import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
public class PolygonTest{

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(Point3D...)}.
     */
    @Test
    public void testConstructor1() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }
    }
    @Test
    public void testConstructor() {
            // ============ Equivalence Partitions Tests ==============

            // TC01: Correct concave quadrangular with vertices in correct order
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
            } catch (IllegalArgumentException e) {
                fail("Failed constructing a correct polygon");
            }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to trinagle");
    }

    @Test
    void findIntersections() {
        Point3D  p0=new Point3D(1,1,-1);/**Vector starting point*/
        Point3D p1=new Point3D(0,1,0), p2=new Point3D(3,1,0), p3=new Point3D(2,3,0),p4=new Point3D(0,5,0);/** The vertices of the polygon*/
        Polygon polygon=new Polygon(p1,p2,p3,p4);
        // ============ Equivalence Partitions Tests ==============

        /** TC01: Inside polygon(1 point)*/
        List<Point3D> result=polygon.findIntersections(new Ray(p0,new Vector(0,1,1)));
        assertEquals( 1, result.size(),"Wrong number of points");
        result=List.of(result.get(0));
        assertEquals( List.of(new Point3D(1,2,0)), result,"Inside polygon");

        /** TC02: Outside against edge(0 point)*/
        assertNull(polygon.findIntersections(new Ray(p0,new Vector(1,3,1))),"Outside against edge");

        /** TC03: Outside against vertex(0 point)*/
        assertNull(polygon.findIntersections(new Ray(p0,new Vector(3,-1,1))),"Outside against vertex");

        // =============== Boundary Values Tests ==================
        /** **** Group: the ray begins"before" the plane*/
        /** TC11: On edge(0 point)*/
        assertNull(polygon.findIntersections(new Ray(p0,new Vector(-1,0,1))),"On edge");

        /** TC12: In vertex(0 point)*/
        assertNull(polygon.findIntersections(new Ray(p0,new Vector(-1,2,1))),"In vertex");

        /** TC13: On edge's continuation(0 point)*/
        assertNull(polygon.findIntersections(new Ray(p0,new Vector(3,0,1))),"On edge's continuation");

    }
}
