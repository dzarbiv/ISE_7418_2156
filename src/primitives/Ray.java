package primitives;
import static java.lang.Math.sqrt;

public class Ray {
   protected Point3D p0;
   protected Vector dir;

   public Ray(Point3D p0, Vector dir)/**constructor*/
   {
      this.p0 = p0;
      double normal=sqrt((((dir.head.x.coord)*(dir.head.x.coord))+(dir.head.y.coord)*(dir.head.y.coord))+(dir.head.z.coord)*(dir.head.z.coord));
      this.dir = dir;
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
}
