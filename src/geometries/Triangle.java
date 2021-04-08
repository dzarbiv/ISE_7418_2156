package geometries;

import primitives.Point3D;

public class Triangle extends Polygon implements Geometry{
    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices.toString() +
                ", plane=" + plane.toString() +
                '}';
    }

}
