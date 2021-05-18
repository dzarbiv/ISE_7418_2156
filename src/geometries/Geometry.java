package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public abstract class Geometry  implements Intersectable{
     /**
      * returns a nurmal for a given point on the geometry
      * @param p0 a point on the geometry
      * @return a normal vector
      */
     protected Color emission=Color.BLACK;
     public abstract Vector getNormal(Point3D p0);
     private Material _material=new Material();

     /**
      * getter
      * @return material
      */
     public Material getMaterial() {
          return _material;
     }

     /**
      *
      * @param material
      * @return The geometry itself
      */
     public Geometry setMaterial(Material material) {
          _material = material;
          return this;
     }

     /**
      *
      * @return
      */
     public Color getEmission() {
          return emission;
     }
     /**
      *
      * @param _emmission
      * @return
      */
     public Geometry setEmission(Color _emmission){
          emission=_emmission;
          return this;
     }

}
