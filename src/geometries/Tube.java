package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Tube class representing three-dimensional tube (infinite cylinder) in 3D Cartesian coordinate
 * system
 *
 * @author devora zarbiv and rachel lea kohen
 */
public class Tube extends Geometry {

    protected Ray _axisRay; // The centered ray of the tube
    protected double _radius; // The radius of the tube

    /**
     * c-tor initiate the fields with the receiving values
     *
     * @param axisRay The centered ray of the tube
     * @param radius  The radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * Return the centered ray of the tube
     *
     * @return The centered ray of the tube (Ray)
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * Return the radius value of the tube
     *
     * @return The radius value of the tube (double)
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * Receiving a point, calculate and return the normal of the tube in the current point.
     *
     * @param point A point on the tube
     * @return The normal of the tube in the receiving point (Vector)
     */
    @Override
    public Vector getNormal(Point3D point) {

        Vector centeredVectorDirection = _axisRay.getDir();
        Point3D p0 = _axisRay.getP0();

        // If the projection equals to zero we cant calculate a normal.
        double projection = centeredVectorDirection.dotProduct(point.subtract(p0));
        if (projection == 0) throw new IllegalArgumentException("the projection cannot be 0");

        // Calculate the point on the centered ray of the tube to calculate the normal with it.
        Point3D center = p0.add(centeredVectorDirection.scale(projection));

        // Calculate the normal
        Vector v = point.subtract(center);
        return v.normalize(); // Return the normalized normal
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tube tube = (Tube) o;

        if (Double.compare(tube._radius, _radius) != 0) return false;
        return _axisRay != null ? _axisRay.equals(tube._axisRay) : tube._axisRay == null;
    }

    public List<Point3D> findIntersections(Ray ray) {
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(ray.getPoint(_radius));

            double t = alignZero(Math.sqrt(_radius * _radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(ray.getPoint(t));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(_radius * _radius / a));
                return t == 0 ? null : List.of(ray.getPoint(t));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - _radius * _radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        else // t2 is behind the head
            return List.of(ray.getPoint(t1));

//        return null;
    }




    /**
     * Return list of intersection GeoPoint
     *
     * @param ray The light ray
     * @return List of intersection GeoPoint
     */
    // NOT IMPLEMENTED
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
