package elements;
import primitives.Color;

/**
 * abstract class
 */
public abstract class Light {
     protected final Color intensity;

    /**
     * constructor
     * @param intensity
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get intensity
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }

}
