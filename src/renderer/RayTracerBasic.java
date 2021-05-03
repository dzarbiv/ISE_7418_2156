package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    /**
     * constructor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    /**Method of ray scanning
     * @param ray
     * @return A color that the ray strikes
     */
    @Override
    public Color traceRay(Ray ray) {
       List<Point3D> intersections=_scene.geometries.findIntersections(ray);
        if(intersections!=null)
        {
            Point3D closesPoint= ray.findClosestPoint(intersections);
            return calcColor(closesPoint);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    /**
     * @param p
     * @return Fill / environmental lighting color of the scene
     */
    private Color calcColor(Point3D p)
    {
        return _scene.ambientLight.getIntensity();
    }
}
