package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    final private Point3D p0;
    final private Vector vTo;
    final private Vector vUp ;
    final private Vector vRight;
    private double width;
    private double height;
    private double distance;

    public Point3D getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }
    public Vector getvRight() {
        return vRight;
    }

    public Camera(Point3D p0,Vector vTo , Vector vUp)/**constructor*/ {

        this.p0 = p0;
        if(isZero(vUp.dotProduct(vTo))) {
            this.vUp = vUp.normalized();
            this.vTo = vTo.normalized();
            this.vRight=vTo.crossProduct(vUp).normalized();
        }
        else
            throw new IllegalArgumentException("Vup is not ortogonal to Vto");
    }
    /**chaining method*/
    public Camera setViewPlaneSize(double width, double height)/**Update Method for View Plane Size*/{
        this.width=width;
        this.height=height;
        return this;
    }
    public Camera setDistance(double distance)/**Update method for the View Plane distance from the camera*/
    {
        this.distance=distance;
        return this;
    }

    /**
     *
     * @param nX Represents the amount of columns
     * @param nY Represents the amount of rows
     * @param j Pixel column
     * @param i A line of pixels
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D pc=p0.add(vTo.scale(distance));
        double rx=width/nX;
        double ry=height/nY;
        Point3D pij=pc;
        double yi=-ry*(i-(nY-1)/2d);
        double xj=rx*(j-(nX-1)/2d);
        if(!isZero(xj))
        {
            pij=pij.add(vRight.scale(xj));
        }
        if(!isZero(yi))
        {
            pij=pij.add(vUp.scale(yi));
        }
        Vector vij=pij.subtract(p0);
        return new Ray(p0,vij) ;
        //return null;
    }

}
