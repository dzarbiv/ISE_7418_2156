package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**composite class for all geometries*/
public class Geometries implements Intersectable {
private List<Intersectable> intersectable=new LinkedList<>();

    public Geometries(List<Intersectable> intersectable) {
        this.intersectable = intersectable;
    }
    public Geometries() /**default constructor*/{
        this.intersectable = new ArrayList<>();
    }
    public Geometries(Intersectable... geometries)
    {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(intersectable,geometries);
    }

    /**@Override
        public List<Point3D> findIntersections (Ray ray) {
        List<Point3D> result = null;
        for (Intersectable item : intersectable) {
            List<Point3D> itemIntersectionPoint = item.findIntersections(ray);
            if (itemIntersectionPoint != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(itemIntersectionPoint);
            }
        }
        return result;
    }*/

/**
 *
 * @param ray
 * @return
 */
@Override
public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : intersectable) {
        List<GeoPoint> itemIntersectionPoint = item.findGeoIntersections(ray);
        if (itemIntersectionPoint == null) {
        continue;
        }
        if (result == null){
        result = new LinkedList<>(itemIntersectionPoint);
        continue;
        }
        result.addAll(itemIntersectionPoint);
        }
        return result;
        }

}
