package unittests;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class SphereTest {

    @Test
    public void getNormal() {
        Sphere s=new Sphere(3,new Point3D(1,2,3));
        Vector v=s.getNormal(new Point3D(1,2,3));
        assertEquals(new Vector(0,-1,0),v,"Error: normal to plane not normalized");

    }
}