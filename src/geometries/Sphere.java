package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {
    public Sphere() {}/**constructor*/

    @Override
    public String toString() {
        return "Sphere{}";
    }

    @Override
    public Vector getNormal(Point3D p0) {
        return null;
    }
}
