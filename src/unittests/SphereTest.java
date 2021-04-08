package unittests;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class SphereTest {

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
}