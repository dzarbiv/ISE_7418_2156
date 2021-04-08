package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube implements Geometry{
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
        if(p.equals(axis.getP0())||p.equals(axis.getP0().add(axis.getDir().scale(height))))
        {
            /**If the point is in the middle of the circle of the beginning of the final cylinder*/
           if(p==axis.getP0())
               n=axis.getDir().scale(-1);/**Return -v*/
           else/**If the point is in the middle of the circle of the end of the final cylinder*/
               n=axis.getDir();/**return v*/
        }
        else {
            double t = (axis.getDir()).dotProduct(p.subtract(axis.getP0()));
            if (t <= height) {
                Point3D o = axis.getP0().add(axis.getDir().scale(t));
                n = (p.subtract(o)).normalize();
            }
        else/**The point is not on the cylinder*/
            throw new IllegalArgumentException("Error: the point does not on the cylinder");
        }
        return n.normalize();
    }
}
