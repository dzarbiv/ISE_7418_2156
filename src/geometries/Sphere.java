package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {

    private static final double ZERO = 0;
    protected double radius;
    protected Point3D center;

    public Sphere(double radius, Point3D center) /**constructor*/{
        if(radius==ZERO)
            throw new IllegalArgumentException("Error: the radius is zero");
        this.radius = radius;
        this.center = center;
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

}
