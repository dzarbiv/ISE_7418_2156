package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Tube implements Geometry{
    public Tube() {
    }

    @Override
    public String toString() {
        return "Tube{}";
    }

    @Override
    public Vector getNormal(Point3D p0) {
        return null;
    }
}
