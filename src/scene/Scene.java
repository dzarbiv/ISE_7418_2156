package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String name;

    public Color background=Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK,0);
    public Geometries geometries=null;
    public List<LightSource> lights =new LinkedList<>();

    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
    }

//chaining methods
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
