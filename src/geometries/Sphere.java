package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere extends Geometry {

    private static final double ZERO = 0;
    final Point3D _center;
    final double _radius;
    public Sphere(double radius, Point3D center) {
        if(radius==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        _radius=radius;
        this._center = center;
    }


    public Point3D getCenter() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + _radius +
                ", center=" + _center +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p0) {
        Vector n= (p0.subtract(_center));
        return n.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector u=this._center.subtract(ray.getP0());
        double tm=u.dotProduct(ray.getDir());
        double d=Math.sqrt((u.lengthSquared()-(tm*tm)));
        if(d<_radius) {
            double th = Math.sqrt((_radius * _radius) - (d * d));
            double t1, t2;
            t1 = tm - th;
            t2 = tm + th;
            Point3D p1 = null, p2=null;
            if (t1 > 0 && t2>0) {
                p1 = (ray.getPoint(t1));
                p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p1), new GeoPoint(this,p2));
            }
            else
            {
                if(t1>0){
                    p1 = (ray.getPoint(t1));
                return List.of(new GeoPoint(this,p1));
                }
                if(t2>0)
                {
                    p2 = ray.getPoint(t2);
                    return List.of(new GeoPoint(this, p2));
                }

            }
        }
        return null;
    }
}