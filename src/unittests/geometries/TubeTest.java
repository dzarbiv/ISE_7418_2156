package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        /**case the point is on the surface */
        Tube t=new Tube(1,new Ray(new Point3D(0,-1,0),new Vector(0,1,0)));
        Vector v = t.getNormal(new Point3D(0, 1, 1));
        assertEquals(t.getNormal(new Point3D(0, 0, 1)), v, "Error: not expected normal, for surface");

        // =============== Boundary Values Tests ==================
        /**Connection of the point to the corner head of the tube axis creates a right angle with the axis*/
        Tube t1=new Tube(1,new Ray(new Point3D(0,0,0),new Vector(0,0,1)));
        Vector v1=t.getNormal(new Point3D(1,0,0));
        assertEquals(t1.getNormal(new Point3D(1,0,1)), v1, "Error: not expected normal, for top base");


    }
}