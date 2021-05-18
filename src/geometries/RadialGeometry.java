package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/***/
public abstract class RadialGeometry {
    final protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public abstract Vector getNormal(Point3D p0);

    public abstract List<Point3D> findIntersections(Ray ray);
}
