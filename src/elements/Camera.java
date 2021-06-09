package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * Camera class representing a camera in 3d space.
 *
 * @author devora zarbiv and rachel lea kohen
 */
public class Camera {

    private Point3D _p0; // The center of the camera lens
    final private Vector _vUp, _vTo, _vRight; // The direction vectors
    private double _width, _height, _distance; // The grid values. (distance is the distance between the grid and the camera)
    private static final Random rnd = new Random();

    /**
     * c-tor receive 1 starting point and 2 orthogonal vectors (and normalized them).
     *
     * @param p0  starting point (Point3D)
     * @param vUp 'up' vector (Vector)
     * @param vTo 'to' vector (Vector)
     */
    public Camera(Point3D p0, Vector vUp, Vector vTo) {

        // Check if the direction vectors are orthogonal
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The received vectors are not orthogonal");

        // Initialize the values center point and up and to direction vectors
        _p0 =p0;
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();

        // Calculate the right direction vector
        _vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Setting the size of the View Plane
     *
     * @param width  width of the View Plane (double)
     * @param height height of the View Plane (double)
     * @return return the camera itself (Camera)
     */
    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;

        // return this for chaining
        return this;
    }

    /**
     * Set the distance between the View Plane and the Camera
     *
     * @param distance the distance (double)
     * @return return the camera itself (Camera)
     */
    public Camera setDistance(double distance) {
        _distance = distance;

        // return this for chaining
        return this;
    }


    /**
     * Receives pixel coordinates and constructs a ray through it.
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j j coordinate of pixel
     * @param i i coordinate of pixel
     * @param distance distance of screen from camera
     * @param width screen width
     * @param height screen height
     * @return ray through receives pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double width, double height, double distance) {

        //The distance between the screen and the camera cannot be 0
        if (isZero(distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        Point3D Pc =  _p0.add(_vTo.scale(distance)); //the center of the screen point
        double Ry = height/nY; //The number of pixels on the y axis
        double Rx = width/nX;  //The number of pixels on the x axis
        double yi =  ((i - nY/2d)*Ry + Ry/2d); //The midpoint of the pixel on the y axis
        double xj=   ((j - nX/2d)*Rx + Rx/2d); //The midpoint of the pixel on the x axis

        Point3D Pij = Pc;//The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!Util.isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!Util.isZero(yi))
        {
            Pij = Pij.add(_vUp.scale(-yi));
        }
        Vector Vij = Pij.subtract(_p0);
        return new Ray(_p0,Vij);//create the ray throw the point we calculate here
    }


    /**
     * Receives pixel coordinates and constructs a beam of rays through it.
     * This method is used for super sampling. Creating a beam of rays allows a more exact calculation of the color of the pixel.
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j j coordinate of pixel
     * @param i i coordinate of pixel
     * @param distance distance of screen from camera
     * @param width screen width
     * @param height screen height
     * @param num_of_sample_rays number of sample rays required
     * @return beam of rays through receives pixel
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double distance,
                                               double width, double height, int num_of_sample_rays)
    {
        //The distance between the screen and the camera cannot be 0
        if (isZero(distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sample_rays = new ArrayList<>();

        double Ry = height/nY; //The number of pixels on the y axis
        double Rx = width/nX;  //The number of pixels on the x axis
        double yi =  ((i - nY/2d)*Ry); //distance of original pixel from (0,0) on Y axis
        double xj=   ((j - nX/2d)*Rx); //distance of original pixel from (0,0) on x axis
        double pixel_Ry = Ry/num_of_sample_rays; //The height of each grid block we divided the parcel into
        double pixel_Rx = Rx/num_of_sample_rays; //The width of each grid block we divided the parcel into

        for (int row = 0; row < num_of_sample_rays; ++row) {//foreach place in the pixel grid
            for (int column = 0; column < num_of_sample_rays; ++column) {
                sample_rays.add(constructRaysThroughPixel(pixel_Ry,pixel_Rx,yi, xj, row, column,distance));//add the ray
            }
        }
        sample_rays.add(constructRayThroughPixel(nX, nY, j, i, distance, width, height));//add the center screen ray
        return sample_rays;
    }

    /**
     * In this function we treat each pixel like a little screen of its own and divide it to smaller "pixels".
     * Through each one we construct a ray. This function is similar to ConstructRayThroughPixel.
     * @param Ry height of each grid block we divided the pixel into
     * @param Rx width of each grid block we divided the pixel into
     * @param yi distance of original pixel from (0,0) on Y axis
     * @param xj distance of original pixel from (0,0) on X axis
     * @param j j coordinate of small "pixel"
     * @param i i coordinate of small "pixel"
     * @param distance distance of screen from camera

     * @return beam of rays through pixel
     */
    private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i, double distance)
    {
        Point3D Pc = _p0.add(_vTo.scale(distance)); //the center of the screen point

        double y_sample_i =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
        double x_sample_j=   (j *Rx + Rx/2d); //The pixel starting point on the x axis

        Point3D Pij = Pc; //The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!Util.isZero(x_sample_j + xj))
        {
            Pij = Pij.add(_vRight.scale(x_sample_j + xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!Util.isZero(y_sample_i + yi))
        {
            Pij = Pij.add(_vUp.scale(-y_sample_i -yi ));
        }
        Vector Vij = Pij.subtract(_p0);
        return new Ray(_p0,Vij);//create the ray throw the point we calculate here
    }


        public Point3D getP0() {
        return _p0;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
    }
}
