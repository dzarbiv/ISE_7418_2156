package primitives;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.isZero;

public class Ray {
   protected Point3D p0;
   protected Vector dir;

   public Ray(Point3D p0, Vector dir)/**constructor*/
   {
      this.p0 = p0;
      double normal=sqrt((((dir.head.x.coord)*(dir.head.x.coord))+(dir.head.y.coord)*(dir.head.y.coord))+(dir.head.z.coord)*(dir.head.z.coord));
      this.dir = dir.normalize();
   }

   public Point3D getP0()/**getter*/
   {
      return p0;
   }

   public Vector getDir() /**getter*/
   {
      return dir;
   }
   @Override
   public boolean equals(Object obj)/**The operation compares the object on which it is applied to the object received as a parameter*/
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof Ray)) return false;
      Ray other = (Ray)obj;
      return this.p0.equals(other.p0) && this.dir.equals(other.dir);
   }

   @Override
   public String toString() /**The operation returns a string with the values of all the field fields*/
   {
      return "Ray{" +
              "p0=" + p0.toString() +
              ", dir=" + dir.toString() +
              '}';
   }
   public Point3D getPoint(double t)
   {
      if(isZero(t))
         return p0;
      else
         return p0.add(dir.scale(t));
   }

   /**
    *
    * @param lp Collection of points
    * @return The point closest to the beginning of the foundation
    */
   public Point3D findClosestPoint(List<Point3D> lp)
   {
      double distance=Double.POSITIVE_INFINITY;
      Point3D nearPoint=null;
      if(lp!=null) {
         for (Point3D point : lp) {
            double dis = point.distance(p0);
            if (dis < distance) {
               distance = dis;
               nearPoint = point;
            }
         }
      }
      return nearPoint;
   }
   public GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> gp)
   {
      double distance=Double.POSITIVE_INFINITY;
      GeoPoint nearPoint=null;
      if(gp!=null) {
         for (GeoPoint geo : gp) {
            double dis = geo.point.distance(p0);
            if (dis < distance) {
               distance = dis;
               nearPoint = geo;
            }
         }
      }
      return nearPoint;
   }

}
