package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Tube  extends Geometry{
    private static final double ZERO = 0;
    final Ray _axis;
    final double _radius;
    public Tube(double radius, Ray axis)/**constructor*/ {
        if(radius==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        _radius=radius;
        this._axis = axis;

    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + _radius +
                ", axis=" + axis.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p)
    {
     double t=(_axis.getDir()).dotProduct(p.subtract(_axis.getP0()));
        Vector n ;
        if (!isZero(t)) {
            Point3D o=_axis.getP0().add(_axis.getDir().scale(t));
           n = (p.subtract(o));
        }
        else
            n=p.subtract(_axis.getP0()).normalize();
        return n.normalize();

    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return null;
    }
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return null;
    }
}
