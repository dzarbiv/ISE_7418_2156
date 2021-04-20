package unittests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Geometries geometries = new Geometries();

        // ============ Equivalence Partitions Tests ==============
        /**part of the geometries has intersections points*/
        assertEquals("part of the geometries has intersections points", 2,
                geometries.findIntersections(new Ray(new Point3D(1,0,-1), new Vector(0,0,1))).size());

        // =============== Boundary Values Tests ==================
        /**empty geometries collections*/
        assertNull("empty geometries collections",
                geometries.findIntersections(new Ray(new Point3D(0,1,0), new Vector(1,0,5))));

        geometries.add(new Plane(new Point3D(1,1,0), new Vector(0,0,1)));
        geometries.add(new Triangle(new Point3D(1,0,0), new Point3D(0,1,0), new Point3D(0,0,1)));
        geometries.add((Intersectable) new Sphere(1d, new Point3D(1, 0, 0)));
        /**each geometry does'nt have intersections points*/
        assertNull("each geometry does'nt have intersections points",
                geometries.findIntersections(new Ray(new Point3D(0,0,2), new Vector(0,-1,0))));
        /**just one geometry has intersections point*/
        assertEquals("just one geometry has intersections point", 1,
                geometries.findIntersections(new Ray(new Point3D(0,5,-1), new Vector(0,0,1))).size());



    }
}