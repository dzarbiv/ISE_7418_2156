package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;


/**
 * class camera
 *  _p0,_vTo, _vUp ,_vRight,_width,_height,_distance
 *  @author devora ,rachel lea
 */
public class Camera {
    final private Point3D _p0;
    final private Vector _vTo;
    final private Vector _vUp;
    final private Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;

    /**
     * constructor
     * @param p0-the position of the camera
     * @param vTo-the forward vector
     * @param vUp-the up vector
     */
    public Camera(Point3D p0,Vector vTo , Vector vUp){

        this._p0 = p0;
        if(isZero(vUp.dotProduct(vTo))) {
            this._vUp = vUp.normalized();
            this._vTo = vTo.normalized();
            this._vRight =vTo.crossProduct(vUp).normalized();
        }
        else
            throw new IllegalArgumentException("Vup is not orthogonal to Vto");
    }


    //*********************get***********************

    public Point3D getP0() { return _p0; }

    public Vector getvUp() { return _vUp; }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public Camera setViewPlaneSize(double width, double height)/**Update Method for View Plane Size*/{
        this._width =width;
        this._height =height;
        return this;
    }
    public Camera setDistance(double distance)/**Update method for the View Plane distance from the camera*/
    {
        this._distance =distance;
        return this;
    }

    //**************rays constructors ************************

    /**
     *
     * @param nX Represents the amount of columns
     * @param nY Represents the amount of rows
     * @param j Pixel column
     * @param i A line of pixels
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D pc= _p0.add(_vTo.scale(_distance));
        double rx= _width /nX;
        double ry= _height /nY;
        Point3D pij=pc;
        double yi=-ry*(i-(nY-1)/2d);
        double xj=rx*(j-(nX-1)/2d);
        if(!isZero(xj))
        {
            pij=pij.add(_vRight.scale(xj));
        }
        if(!isZero(yi))
        {
            pij=pij.add(_vUp.scale(yi));
        }
        Vector vij=pij.subtract(_p0);
        return new Ray(_p0,vij) ;
        //return null;
    }

}