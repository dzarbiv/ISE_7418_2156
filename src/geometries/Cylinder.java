package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Cylinder extends Tube implements Geometry{
    private static final double ZERO = 0;
    protected double height;

    public Cylinder(double radius, Ray axis, double height) {
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
        Vector n=null ;
        if(p.equals(axis.getP0()))
        {
           if(p==axis.getP0())
               n=axis.getDir().scale(-1);
           else
               n=axis.getDir();
        }
        else {
            double t = (axis.getDir()).dotProduct(p.subtract(axis.getP0()));
            if (t <= height) {
                if (!isZero(t)) {
                    Point3D p0=axis.getP0();
                    Vector dir=axis.getDir();
                    Vector v=dir.scale(t);
                Point3D o = axis.getP0().add(axis.getDir().scale(t));
                n = (p.subtract(o)).normalize();
                }
                /**else
                 {

                 /**if(p==axis.getP0())
                 n=axis.getDir().scale(-1);
                 else
                 n=axis.getDir();
                 }*/
            } else
                throw new IllegalArgumentException("Error: the point does not on the cylinder");
        }
        return n.normalize();
    }
}
