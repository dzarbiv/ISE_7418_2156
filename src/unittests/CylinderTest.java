package unittests;

import geometries.Cylinder;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @org.junit.jupiter.api.Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder c=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),7);

        /**case the point is on the surface*/
        Vector v = c.getNormal(new Point3D(0, 1, 1));
        assertEquals(c.getNormal(new Point3D(0, 0, 1)), v, "Error: not expected normal, the point not on the surface");

        /**case the point is on the top base*/
        Vector v1= c.getNormal(new Point3D(0, 1, -0.5));
        assertEquals(c.getNormal(new Point3D(0, 0, -1)), v1, "Error: not expected normal, for top base");

        /**case the point is on the bottom base*/
        Vector v2= c.getNormal(new Point3D(0, 6, -0.25));
        assertEquals(c.getNormal(new Point3D(0, 0, -1)), v2, "Error: not expected normal, of bottom base");


        // =============== Boundary Values Tests ==================
        /**case the point is on the center of top base*/
        Cylinder c1=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v3= c1.getNormal(new Point3D(0, -1, 0));
        assertEquals(c1.getNormal(new Point3D(0, -1, 0)), v3, "Error: not expected normal,for center of top base");

        /**case the point is on the center of bottom base*/
        Cylinder c2=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),2);
        Vector v4= c2.getNormal(new Point3D(0, 1, 0));
        assertEquals(c2.getNormal(new Point3D(0, 1, 0)), v4, "Error: not expected normal,for center of bottom base");

    }
}