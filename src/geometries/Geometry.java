package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface is the base level of all the geometries'
 *
 * @author devora zarbiv and rachel lea kohen
 */
public abstract class Geometry implements Intersectable {

     protected Color emission = Color.BLACK; // The color of the geometry
     private Material _material = new Material(); // The material type of the geometry

     /**
      * Calculate the normal of the geometry in the received point
      *
      * @param point Point on the surface of the geometry shape
      * @return The normal of the geometry shape (Vector type)
      */
     public abstract Vector getNormal(Point3D point);

     /**
      * Return the emission of the object
      *
      * @return The emission (Color)
      */
     public Color getEmission() {
          return emission;
     }

     /**
      * Return the material type of the geometry
      *
      * @return the material of the geometry (Material)
      */
     public Material getMaterial() {
          return _material;
     }

     /**
      * Set the emission of the object
      *
      * @param emission the emission of the object
      * @return this
      */
     public Geometry setEmission(Color emission) {
          this.emission = emission;
          return this; // return this for chaining
     }

     /**
      * Set the material type of the geometry
      *
      * @param material The material type of the geometry (Material)
      * @return the geometry (Geometry)
      */
     public Geometry setMaterial(Material material) {
          _material = material;
          return this; // return this for chaining
     }
}
