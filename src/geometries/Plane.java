package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class  Plane extends Geometry{
    protected Point3D q0;
    protected Vector normal;

    public Plane(Point3D q0, Vector normal) /**constructor*/
    {
        this.q0 = q0;
        this.normal = normal;
    }
    public Plane(Point3D p0, Point3D p1, Point3D p2)/**constructor*/
    {
        q0=p0;
        Vector u=p1.subtract(p0);
        Vector v=p2.subtract(p1);
        Vector n=u.crossProduct(v);
        n.normalize();
        normal=n;
       // this.q0=q2;
        //this.normal=q2.subtract(q0).crossProduct(q1.subtract(q0));
    }

    public Point3D getQ0()/**getter*/
    {
        return q0;
    }
    @Override
    public Vector getNormal(Point3D p0) /***getter*/
    {
        return normal;
    }
    @Override
    public boolean equals(Object obj)/**The operation compares the object on which it is applied to the object received as a parameter*/
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Plane)) return false;
        Plane other = (Plane)obj;
        return this.q0.equals(other.q0) && this.normal.equals(other.normal);
    }

    @Override
    public String toString()/**The operation returns a string with the values of all the field fields*/
    {
        return "Plane{" +
                "q0=" + q0.toString() +
                ", normal=" + normal.toString() +
                '}';
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector p0q;
        try {
            p0q = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv))/** ray is parallel to the plane - no intersections*/
            return null;
        double t = Util.alignZero(normal.dotProduct(p0q) / nv);
        if (t <= 0)
            return null;
        else {
            Point3D p = ray.getPoint(t);
            return List.of(new GeoPoint(this,p));
        }
    }
}

