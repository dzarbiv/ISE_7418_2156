package geometries;
/***/
public abstract class RadialGeometry {
    final protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
