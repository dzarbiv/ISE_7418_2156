package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Tube implements Geometry{
    private static final double ZERO = 0;
    final double radius;
    final Ray axis;

    public Tube(double radius, Ray axis)/**constructor*/ {
        if(radius==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        this.radius = radius;
        this.axis = axis;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + radius +
                ", axis=" + axis.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p)
    {
    /**O is projection of P on cylinder's ray*/
     double t=(axis.getDir()).dotProduct(p.subtract(axis.getP0()));
        Vector n ;
        if (!isZero(t)) {
            Point3D o=axis.getP0().add(axis.getDir().scale(t));
           n = (p.subtract(o));
        }
        else
            n=p.subtract(axis.getP0()).normalize();
        return n.normalize();

    }

}
