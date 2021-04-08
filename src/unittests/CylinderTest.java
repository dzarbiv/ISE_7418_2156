package unittests;

import geometries.Cylinder;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CylinderTest {

    @org.junit.jupiter.api.Test
    void getNormal() {
        Cylinder c=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v = c.getNormal(new Point3D(0, 1, 1));
        assertEquals(c.getNormal(new Point3D(0, 0, 1)), v, "Error: the normal of tube does not good");

        Cylinder c1=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v1= c1.getNormal(new Point3D(0, 1, -0.5));
        assertEquals(c1.getNormal(new Point3D(0, 0, -1)), v1, "Error: the normal of tube does not good");

        Cylinder c2=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v2= c2.getNormal(new Point3D(0, 6, -0.25));
        assertEquals(c2.getNormal(new Point3D(0, 0, -1)), v2, "Error: the normal of tube does not good");



        Cylinder c3=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v3= c3.getNormal(new Point3D(0, -1, 0));
        assertEquals(c3.getNormal(new Point3D(0, 5, 0)), v3, "Error: the normal of tube does not good");

        Cylinder c4=new Cylinder(1, new Ray(new Point3D(0,-1,0),new Vector(0,1,0)),1);
        Vector v4= c4.getNormal(new Point3D(0, 6, 0));
        assertEquals(c4.getNormal(new Point3D(0, 0, -1)), v4, "Error: the normal of tube does not good");

    }
}