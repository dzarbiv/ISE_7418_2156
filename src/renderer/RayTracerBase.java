package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene _scene;

    /**
     * constructor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Abstract class
     * @param ray
     * @return A color that the ray strikes
     */
    public abstract Color traceRay(Ray ray);
}
