package unittests.geometries;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D,Point3D)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        Point3D p1=new Point3D(0,1,1);
        Point3D p2=new Point3D(-2,1,0);
        Point3D p3=new Point3D(5,0,2);
        Plane p=new Plane(p1, p2, p3);
        Vector v=p.getNormal(p1);
        assertEquals(new Vector(-1/Math.sqrt(14),-3/Math.sqrt(14),2/Math.sqrt(14)),v,"Error: constructor does not work correctly");
        // =============== Boundary Values Tests ==================
        /**test for p1=p2*/
        Plane pl=new Plane(p1, p2, p3);
        /**test for p1 p2 p3 on the same line*/
        Vector v1=p1.subtract(p2);
        try{
            Plane pla=new Plane(p1, p2,v1.getHead());
            throw new IllegalArgumentException("can not creat plane if 3 of the point are on the same line");
        }
        catch (IllegalArgumentException e){}
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(1,0,1);
        Point3D p3=new Point3D(3,1,2);
        Plane pl=new Plane(p1,p2,p3);
        Vector normal=pl.getNormal(p1);

        /**check if the function return correct normal to this plane*/
        assertEquals(new Vector(0,-4,4).normalized(),pl.getNormal(p1),"Error: plane getNormal does not return correct normal");

        /**check that  normal to plane is a unit vector*/
        assertEquals(1, normal.length(), 0.01,"Error: normal to plane not normalized");

        // =============== Boundary Values Tests ==================
        /**check that normal is orthogonal to plane*/
        Vector v1=new Vector((p1.subtract(p2).getHead()));
        Vector v2=new Vector((p2.subtract(p3).getHead()));
        assertTrue(isZero(v1.dotProduct(normal)),"Error: normal not orthogonal to plane");
        assertTrue(isZero(v2.dotProduct(normal)),"Error: normal not orthogonal to plane");

    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Plane plane=new Plane(new Point3D(0,1,0), new Vector(0,0,1));
        Point3D p=new Point3D(0,-1,0);
        // ============ Equivalence Partitions Tests ==============
        /** TC01: Ray intersects the plane(1 point)*/
        List<Point3D> result=plane.findIntersections(new Ray(new Point3D(0,0,-1),new Vector(0,-1,1)));
        assertEquals( 1, result.size(),"Wrong number of points");
        result=List.of(result.get(0));
        assertEquals( List.of(p), result," Ray intersects the plane");

        /**TC02: Ray does not intersect the plane(0 point)*/
        assertNull(plane.findIntersections(new Ray(new Point3D(0,0,1),new Vector(0,-1,1))),"Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        /** **** Group: Ray is parallel to the plane*/

        /** TC11: the ray included in the plane(0 point)*/
        assertNull(plane.findIntersections(new Ray(new Point3D(-1,0,0),new Vector(1,-1,0))),"the ray included in the plane");

        /** TC12: the ray not included in the plane(0 point)*/
        assertNull(plane.findIntersections(new Ray(new Point3D(0,0,3),new Vector(0,1,0))),"the ray not included in the plane");

        /** **** Group: Ray is orthogonal to the plane*/

        /** TC13: according to p0 before the plane(1 point)*/
        List<Point3D> result1=plane.findIntersections(new Ray(new Point3D(0,-1,-1),new Vector(0,0,1)));
        assertEquals( 1, result1.size(),"Wrong number of points");
        result1=List.of(result1.get(0));
        assertEquals( List.of(p), result1," according to p0 before the plane");

        /** TC14: according to p0  in the plane(0 point)*/
        assertNull(plane.findIntersections(new Ray(p,new Vector(0,0,1))),"according to p0  in the plane");

        /** TC15: according to p0  after the plane(0 point)*/
        assertNull(plane.findIntersections(new Ray(new Point3D(0,-1,2),new Vector(0,0,1)))," according to p0  after the plane");


        /** TC16:Ray is neither orthogonal nor parallel to and begins at the plane(𝑃0 is in the plane, but not the ray)(0 point)*/
        assertNull(plane.findIntersections(new Ray(p,new Vector(0,1,1)))," Ray is neither orthogonal nor parallel to and begins at the plane");


         /** TC17: Ray is neither orthogonal nor parallel to the plane and begins in
         the same point which appears as reference point in the plane (Q)(0 point)*/
        assertNull(plane.findIntersections(new Ray(new Point3D(0,1,0),new Vector(0,-1,1))),
                " Ray is neither orthogonal nor parallel to the plane and begins in\n" +
                        "         the same point which appears as reference point in the plane");

    }
}