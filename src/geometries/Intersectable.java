package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

public interface Intersectable{
    /**
     * Static Internal Auxiliary Department
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (!geometry.equals(geoPoint.geometry)) return false;
            return point.equals(geoPoint.point);
        }
    }
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    List<GeoPoint> findGeoIntersections(Ray ray);


}
