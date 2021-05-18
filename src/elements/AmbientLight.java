package elements;

import primitives.Color;

public class AmbientLight extends Light {

    /**
     *constructor
     * @param Ia intensity color
     * @param Ka constructor fot intensity
     */
    public AmbientLight(Color Ia, double Ka) {

        super(Ia.scale(Ka)) ;
    }

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK) ;
    }

}
