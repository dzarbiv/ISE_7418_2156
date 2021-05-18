package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon
{
    public Triangle(Point3D... vertices) /**constructor*/{
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices.toString() +
                ", plane=" + plane.toString() +
                '}';
    }
    @Override
    public List<Point3D> findIntersections(Ray ray)
    {
        List<Point3D> result = plane.findIntersections(ray);
        if(result==null)
            return null;
        Vector v1=(vertices.get(0).subtract(ray.getP0())),
                v2=(vertices.get(1).subtract(ray.getP0())),
                v3=(vertices.get(2).subtract(ray.getP0()));
        Vector n1=(v1.crossProduct(v2)).normalize(),n2=(v2.crossProduct(v3)).normalize(),n3=(v3.crossProduct(v1)).normalize();

        double side1 = ray.getDir().dotProduct(v1.crossProduct(v2));
        double side2 = ray.getDir().dotProduct(v2.crossProduct(v3));
        double side3 = ray.getDir().dotProduct(v3.crossProduct(v1));
        if (isZero(side1)) return null;
        if (isZero(side2)) return null;
        if (isZero(side3)) return null;
        if(side1>0&&side2>0&&side3>0||side1<0&&side2<0&&side3<0)
        {
            return result;
        }
        return null;
    }
}
