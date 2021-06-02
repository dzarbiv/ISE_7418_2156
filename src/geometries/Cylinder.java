package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube {
    private static final double ZERO = 0;
    protected double height;

    public Cylinder(double radius, Ray axis, double height)/**constructor*/ {
        super(radius, axis);
        if(height==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cylinder{" + super.toString()+
                "height=" + height + '}';
    }

    @Override
    public Vector getNormal(Point3D p) {
        Vector n ;
        /**If the point is on one of the bases of the final cylinder*/
        if(p.equals(_axis.getP0())||p.equals(_axis.getP0().add(_axis.getDir().scale(height))))
        {
            /**If the point is in the middle of the circle of the beginning of the final cylinder*/
           if(p== _axis.getP0())
               n= _axis.getDir().scale(-1);/**Return -v*/
           else/**If the point is in the middle of the circle of the end of the final cylinder*/
               n= _axis.getDir();/**return v*/
        }
        else {
            double t = (_axis.getDir()).dotProduct(p.subtract(_axis.getP0()));
            if (t <= height) {
                Point3D o = _axis.getP0().add(_axis.getDir().scale(t));
                n = (p.subtract(o)).normalize();
            }
        else/**The point is not on the cylinder*/
            throw new IllegalArgumentException("Error: the point does not on the cylinder");
        }
        return n.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
