package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder implements Geometry{
    public Cylinder() {
    }

    @Override
    public String toString() {
        return "Cylinder{}";
    }

    @Override
    public Vector getNormal(Point3D p0) {
        return null;
    }
}
