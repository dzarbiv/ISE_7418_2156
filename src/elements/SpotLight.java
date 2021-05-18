package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{
    private final Vector _direction;
    /**
     * constructor
     *  @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double cosTetha=_direction.dotProduct(getL(p));
        Color intensity=super.getIntensity(p);
        return intensity.scale(Math.max(0,cosTetha));
    }
}
