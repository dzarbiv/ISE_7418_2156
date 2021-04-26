package unittests;

import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * For integration tests between the formation of beams from a camera and the calculation of beam cuts with geometric bodies
 */
public class CameraIntegrationsTest {
    @Test
    /**
     * Test method for
     * {@link elements.Camera#testSphereWithcam()}.
     */
   public void testSphereWithcam()
    {
        Vector vto=new Vector(0,0,-1);
        Vector vup=new Vector(0,1,0);

        //---------------sphere---------------

        /**TC01: First test case Sphere r=1*/
        Sphere sphere=new Sphere(1, new Point3D(0,0,-3));
        Camera camera=new Camera(Point3D.ZERO,vto,vup).setDistance(1).setViewPlaneSize(3,3);
        List<Point3D> allPoints=null;
        allPoints = intersectionWithSphere(sphere, camera, allPoints);
        assertEquals(2,allPoints.size(),"wrong number of intersection");

        /**TC02: Second test case (r=2.5)*/
        Sphere sphere1=new Sphere(2.5, new Point3D(0,0,-2.5));
        Camera camera1=new Camera(new Point3D(0,0,0.5), vto,vup).setDistance(1).setViewPlaneSize(3,3);
        List<Point3D> allPoints1=null;
        allPoints1 = intersectionWithSphere(sphere1, camera1, allPoints1);
        assertEquals(18,allPoints1.size(),"wrong number of intersection");

        /**TC03: Third test case (r=2)*/
        Sphere sphere2=new Sphere(2, new Point3D(0,0,-2));
        Camera camera2=new Camera(new Point3D(0,0,0.5), vto,vup).setDistance(1).setViewPlaneSize(3,3);
        List<Point3D> allPoints2=null;
        allPoints2 = intersectionWithSphere(sphere2, camera2, allPoints2);
        assertEquals(10,allPoints2.size(),"wrong number of intersection");

        /**TC04: Fourth test case (r=4)*/
        Sphere sphere3=new Sphere(4, new Point3D(0,0,-1));
        List<Point3D> allPoints3=null;
        allPoints3= intersectionWithSphere(sphere3, camera, allPoints3);
        assertEquals(9,allPoints3.size(),"wrong number of intersection");

        /**TC05: Fifth test case (r=0.5)*/
        Sphere sphere4=new Sphere(0.5, new Point3D(0,0,1));
        Camera camera4=new Camera(new Point3D(0,0,-1), vto,vup).setDistance(1).setViewPlaneSize(3,3);
        List<Point3D> allPoints4=null;
        allPoints4= intersectionWithSphere(sphere4, camera4, allPoints4);
        assertNull(allPoints4,"wrong number of intersection");

        //---------------plane---------------

        /**TC01: First test case-the plane parallel to the view plane */
        Plane plane=new Plane(new Point3D(0,0,-10),vto);
        List<Point3D> allPoints5=null;
        allPoints5= intersectionWithPlane(plane, camera, allPoints5);
        assertEquals(9,allPoints5.size(),"wrong number of intersection");

        /**TC02: Second test case -the plane does not parallel to the view plane
         * and for each pixel there is a ray that passes through the plane*/
        Plane plane1=new Plane(new Point3D(0,-1,-2),new Vector(0,-1,-2));
        List<Point3D> allPoints6=null;
        allPoints6= intersectionWithPlane(plane1, camera, allPoints6);
        assertEquals(9,allPoints6.size(),"wrong number of intersection");

        /**TC03: Third test case-the plane does not parallel to the view plane
         * and not for each pixel there is a ray that passes through the plane*/
        Plane plane2=new Plane(new Point3D(0,-1,-1),new Vector(0,-1,-1));
        List<Point3D> allPoints7=null;
        allPoints7= intersectionWithPlane(plane2, camera, allPoints7);
        assertEquals(6,allPoints7.size(),"wrong number of intersection");

        //---------------triangle---------------
        /**TC01: First test case-A small triangle in front of the camera*/
        Triangle triangle=new Triangle(new Point3D(0,1,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2));
        List<Point3D> allPoints8=null;
        allPoints8= intersectionWithTriangle(triangle, camera, allPoints8);
        assertEquals(1,allPoints8.size(),"wrong number of intersection");

        /**TC02: Second test case-A high triangle in front of the camera*/
        Triangle triangle1=new Triangle(new Point3D(0,20,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2));
        List<Point3D> allPoints9=null;
        allPoints9= intersectionWithTriangle(triangle1, camera, allPoints9);
        assertEquals(2,allPoints9.size(),"wrong number of intersection");
    }

    /**
     *For each pixel in the view plane calculates the intersection points of the ray passing through the pixel
     *  and striking the sphere
     * @param allPoints Cutting points of the foundation
     * @return Cutting points of the foundation
     */
    private static List<Point3D> intersectionWithSphere(Sphere sphere, Camera camera, List<Point3D> allPoints) {
        for(int i=0; i<3;i++)
        {
            for (int j=0; j<3;j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,j,i);
                List<Point3D> lp= sphere.findIntersections(ray);
                if(lp!=null)
                {
                    if(allPoints ==null)
                    {
                        allPoints =new LinkedList<>();
                    }
                    allPoints.addAll(lp);
                }
            }
        }
        return allPoints;
    }

    /**
     *For each pixel in the view plane calculates the intersection points of the ray passing through the pixel
     *  and striking the plane
     * @param allPoints Cutting points of the foundation
     * @return Cutting points of the foundation
     */
    private static List<Point3D> intersectionWithPlane(Plane plane, Camera camera, List<Point3D> allPoints) {
        for(int i=0; i<3;i++)
        {
            for (int j=0; j<3;j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,j,i);
                List<Point3D> lp= plane.findIntersections(ray);
                if(lp!=null)
                {
                    if(allPoints ==null)
                    {
                        allPoints =new LinkedList<>();
                    }
                    allPoints.addAll(lp);
                }
            }
        }
        return allPoints;
    }

    /**
     *For each pixel in the view plane calculates the intersection points of the ray passing through the pixel
     *  and striking the triangle
     * @param allPoints Cutting points of the foundation
     * @return Cutting points of the foundation
     */
    private static List<Point3D> intersectionWithTriangle(Triangle triangle, Camera camera, List<Point3D> allPoints) {
        for(int i=0; i<3;i++)
        {
            for (int j=0; j<3;j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,j,i);
                List<Point3D> lp= triangle.findIntersections(ray);
                if(lp!=null)
                {
                    if(allPoints ==null)
                    {
                        allPoints =new LinkedList<>();
                    }
                    allPoints.addAll(lp);
                }
            }
        }
        return allPoints;
    }


}
