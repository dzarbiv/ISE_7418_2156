package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere extends RadialGeometry implements Geometry {

    private static final double ZERO = 0;
    final Point3D center;

    public Sphere(double radius, Point3D center) /**constructor*/{
        super(radius);
        if(radius==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        this.center = center;
    }


    public Point3D getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + radius +
                ", center=" + center +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p0) {
        Vector n= (p0.subtract(center));
        return n.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector u=this.center.subtract(ray.getP0());
        double tm=u.dotProduct(ray.getDir());
        double d=Math.sqrt((u.lengthSquared()-(tm*tm)));
        if(d<radius) {/**If the point is inside the sphere*/
            double th = Math.sqrt((radius * radius) - (d * d));
            double t1, t2;
            t1 = tm - th;
            t2 = tm + th;
            Point3D p1 = null, p2=null;
            if (t1 > 0 && t2>0) {/**There are two intersection points in the sphere.*/
                p1 = (ray.getPoint(t1));
                p2 = ray.getPoint(t2);
                return List.of(p1, p2);
            }
            else/**There is one intersection*/
            {
                if(t1>0){
                    p1 = (ray.getPoint(t1));
                return List.of(p1);
                }
                if(t2>0)
                {
                    p2 = ray.getPoint(t2);
                    return List.of(p2);
                }

            }
        }
        return null;
    }
}
