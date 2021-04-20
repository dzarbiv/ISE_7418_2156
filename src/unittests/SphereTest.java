package unittests;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SphereTest  {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        /**Test whether the point on the Sphere*/
        Sphere s=new Sphere(1,new Point3D(0,0,0));
        Vector v=s.getNormal(new Point3D(1,0,0));
        assertEquals(new Vector(1,0,0),v,"Error: normal to plane not normalized");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

            Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

            // ============ Equivalence Partitions Tests ==============

            // TC01: Ray's line is outside the sphere (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");

            // TC02: Ray starts before and crosses the sphere (2 points)
            Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
            Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
            List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
            assertEquals( 2, result.size(),"Wrong number of points");
            result = List.of(result.get(1), result.get(0));
            assertEquals( List.of(p2, p1), result,"Ray crosses sphere");



        /**TC03: Ray starts inside the sphere (1 point)*/
            List<Point3D> result1=sphere.findIntersections(new Ray(new Point3D(1.47,-0.43,0.1),new Vector(0.26,0.43,-0.1)));
            Point3D p3 = new Point3D(1.9365079444273536, 0.34153236962985417,-0.07942613247205912);
            assertEquals( 1, result1.size(),"Wrong number of points");
            result1=List.of(result1.get(0));
            assertEquals( List.of(p3), result1,"Ray crosses sphere");

            /**TC04: Ray starts after the sphere (0 points)*/
            assertNull(sphere.findIntersections(new Ray(new Point3D(1.5, 1.5, 0), new Vector(0, 1, 0))),"Ray's line out of sphere");
        // =============== Boundary Values Tests ==================

        /** **** Group: Ray's line crosses the sphere (but not the center)*/

        /**TC11: Ray starts at sphere and goes inside (1 points)*/
        Point3D p4 = new Point3D(2, 0, 0);
        List<Point3D> result2=sphere.findIntersections(new Ray(new Point3D(1,0,1),new Vector(1,0,-1)));
        assertEquals( 1, result2.size(),"Wrong number of points");
        result2=List.of(result2.get(0));
        assertEquals( List.of(p4), result2,"Ray crosses sphere but not on the center");

        /** TC12: Ray starts at sphere and goes outside (0 points)*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(1,0,1),new Vector(0.5,1.5,1))),"Ray's line out of sphere");

        /** **** Group: Ray's line goes through the center*/

        /** TC13: Ray starts before the sphere (2 points)*/
        Point3D p5 = new Point3D(1, 1, 0);
        Point3D p6 = new Point3D(1, -1, 0);
        List<Point3D> result3 = sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));
        assertEquals( 2, result3.size(),"Wrong number of points");
        result3 = List.of(result3.get(1), result3.get(0));
        assertEquals( List.of(p5, p6), result3,"Ray crosses sphere on the center");

        /**TC14: Ray starts at sphere and goes inside (1 points)*/
        List<Point3D> result4=sphere.findIntersections(new Ray(new Point3D(1,-1,0),new Vector(0,1,0)));
        assertEquals( 1, result4.size(),"Wrong number of points");
        result4=List.of(result4.get(0));
        assertEquals( List.of(p5), result4,"Ray crosses sphere on the center and start at sphere");

        /**TC15: Ray starts inside (1 points)*/
        List<Point3D> result5=sphere.findIntersections(new Ray(new Point3D(1,0.5,0),new Vector(0,1,0)));
        assertEquals( 1, result5.size(),"Wrong number of points");
        result5=List.of(result5.get(0));
        assertEquals( List.of(p5), result5,"Ray start inside the sphere but not on the center");

        /** TC16: Ray starts at the center (1 points)*/
        List<Point3D> result6=sphere.findIntersections(new Ray(new Point3D(1,0,0),new Vector(0,1,0)));
        assertEquals( 1, result6.size(),"Wrong number of points");
        result6=List.of(result6.get(0));
        assertEquals( List.of(p5), result6,"Ray start inside the sphere on the center");

        /** TC17: Ray starts at sphere and goes outside (0 points)*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(1,1,0),new Vector(0,1,0))),"Ray starts at sphere and goes outside");

        /** TC18: Ray starts after sphere (0 points)*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(1,2,0),new Vector(0,1,0))),"Ray starts after sphere");

        /** **** Group: Ray's line is tangent to the sphere (all tests 0 points)*/

        /** TC19: Ray starts before the tangent point*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(0,1,0),new Vector(1,0,0))),"Ray starts before the tangent point");

        /** TC20: Ray starts at the tangent point*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(1,1,0),new Vector(1,0,0))),"Ray starts at the tangent point");

        /** TC21: Ray starts after the tangent point*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(2,1,0),new Vector(1,0,0))),"Ray starts after the tangent point");


        /** **** Group: Special cases*/

        /** TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line*/
        assertNull(sphere.findIntersections(new Ray(new Point3D(1,2,0),new Vector(0,1,0))),"Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }

}

