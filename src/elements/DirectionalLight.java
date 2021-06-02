package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 * class DirectionalLight extends Light implements LightSource - Light source is far away
 * has intensity and direction ,No attenuation with distance
 * @author devora, rachel lea
 */
public class DirectionalLight extends Light implements LightSource{
    final private Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction.normalized();
    }

}
