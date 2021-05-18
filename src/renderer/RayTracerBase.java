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
        if(scene==null){
            throw new IllegalArgumentException("scene can not be null");
        }
        _scene = scene;
    }

    /**
     * Abstract class
     * @param ray
     * @return A color that the ray strikes
     */
    public abstract Color traceRay(Ray ray);
}
