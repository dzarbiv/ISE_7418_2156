package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**composite class for all geometries*/
public class Geometries implements Intersectable {
private List<Intersectable> intersectable;

    public Geometries(List<Intersectable> intersectable) {
        this.intersectable = intersectable;
    }
    public Geometries() /**default constructor*/{
        this.intersectable = new LinkedList<>();
    }
    public Geometries(Intersectable... geometries)
    {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable item : geometries) {
            intersectable.add(item);
        }
    }

    @Override
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
    }

}
