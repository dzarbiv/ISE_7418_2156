package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class responsible to ray tracer
 *
 * @author devora zarbiv and rachel lea kohen
 */
public abstract class RayTracerBase {

    protected Scene _scene;

    /**
     * c-tor, initiate the _scene field with the receiving value
     *
     * @param scene The scene (Scene)
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Trace the ray and calculate the color of the intersection point
     * of the ray and any object (or the background if no intersections exist)
     *
     * @param ray The ray to trace after (Ray)
     * @return The color of the intersection point (or background if no intersections exist) (Color)
     */
    public abstract Color traceRay(Ray ray);
}
