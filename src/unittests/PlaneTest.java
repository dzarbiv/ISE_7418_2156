package unittests;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
class PlaneTest {

    @Test
    void testConstructor(){
        Point3D p1=new Point3D(0,1,1);
        Point3D p2=new Point3D(-2,1,0);
        Point3D p3=new Point3D(5,0,2);
        Plane p=new Plane(p1, p2, p3);
        Vector v=p.getNormal();
        assertEquals(new Vector(-1/Math.sqrt(14),-3/Math.sqrt(14),2/Math.sqrt(14)),v,"Error: constructor does not work correctly");
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
    @Test
    void getNormal() {
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(1,0,1);
        Point3D p3=new Point3D(3,1,2);
        Plane pl=new Plane(p1,p2,p3);
        Vector normal=pl.getNormal();

        /**check if the function return correct normal to this plane*/
        assertEquals(new Vector(0,-4,4).normalized(),pl.getNormal(),"Error: plane getNormal does not return correct normal");

        /**check that  normal to plane is a unit vector*/
        assertEquals(1, normal.length(), 0.01,"Error: normal to plane not normalized");

        /**check that normal is orthogonal to plane*/
        Vector v1=new Vector((p1.subtract(p2).getHead()));
        Vector v2=new Vector((p2.subtract(p3).getHead()));
        assertTrue(isZero(v1.dotProduct(normal)),"Error: normal not orthogonal to plane");
        assertTrue(isZero(v2.dotProduct(normal)),"Error: normal not orthogonal to plane");

    }


}