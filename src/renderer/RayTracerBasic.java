package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

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
       List<GeoPoint> intersections=_scene.geometries.findGeoIntersections(ray);
        if(intersections!=null)
        {
            GeoPoint closesPoint= ray.findClosestGeoPoint(intersections);
            return calcColor(closesPoint,ray);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    /**
     * @param gp
     * @param ray
     * @return Fill / environmental lighting color of the scene
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        Color intensity=gp.geometry.getEmission();
        intensity=intensity.add(_scene.ambientLight.getIntensity());
        Color basicColor=intensity;
        /// add calculated light contribution from all light sources)
        return basicColor.add(calcLocalEffects(gp, ray));
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     *The Phong Reflectance Model
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus=v.scale(-1).dotProduct(r);
        double vrn=Math.pow(vrMinus,nShininess);
        return lightIntensity.scale(ks*vrn);
    }

    /**
     * The Phong Reflectance Model
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd*ln);
    }


}
