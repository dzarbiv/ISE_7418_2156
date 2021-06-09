package primitives;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors
 *
 * @author Dan Zilberstein
 */
public class Color{
    /**
     * The internal fields tx`o maintain RGB components as double numbers from 0 to
     * whatever...
     */
    private double r;
    private double g;
    private double b;



    /**
     * Black color = (0,0,0)
     */
    public static final Color BLACK = new Color();

    /**
     * Default constructor - to generate Black Color (privately)
     */
    private Color() {
        r = g = b = 0.0;
    }

    /**
     * Constructor to generate a color according to RGB components Each component in
     * range 0..255 (for printed white color) or more [for lights]
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     */
    public Color(double r, double g, double b) {
        if (r < 0 || g < 0 || b < 0)
            throw new IllegalArgumentException("Negative color component is illegal");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Copy constructor for Color
     *
     * @param other the source color
     */
    public Color(Color other) {
        r = other.r;
        g = other.g;
        b = other.b;
    }

    /**
     * Constructor on base of java.awt.Color object
     *
     * @param other java.awt.Color's source object
     */
    public Color(java.awt.Color other) {
        r = other.getRed();
        g = other.getGreen();
        b = other.getBlue();
    }
    /**
     * Color setter to generate a color according to RGB components Each component
     * in range 0..255 (for printed white color) or more [for lights]
     *
     * @param _r Red component
     * @param _g Green component
     * @param _b Blue component
     * @return the Color object itself for chaining calls
     */
    public Color setColor(double _r, double _g, double _b) {
        if (r < 0 || g < 0 || b < 0)
            throw new IllegalArgumentException("Negative color component is illegal");
        r = _r;
        g = _g;
        b = _b;
        return this;
    }

    /**
     * Color setter to copy RGB components from another color
     *
     * @param other source Color object
     * @return the Color object itself for chaining calls
     */
    public Color setColor(Color other) {
        r = other.r;
        g = other.g;
        b = other.b;
        return this;
    }

    /**
     * Color setter to take components from an base of java.awt.Color object
     *
     * @param other java.awt.Color's source object
     * @return the Color object itself for chaining calls
     */
    public Color setColor(java.awt.Color other) {
        r = other.getRed();
        g = other.getGreen();
        b = other.getBlue();
        return this;
    }



    /**
     * Color getter - returns the color after converting it into java.awt.Color
     * object During the conversion any component bigger than 255 is set to 255
     *
     * @return java.awt.Color object based on this Color RGB components
     */
    public java.awt.Color getColor() {
        int ir = (int) r;
        int ig = (int) g;
        int ib = (int) b;
        return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
    }


    /**
     * Operation of adding this and one or more other colors (by component)
     *
     * @param colors one or more other colors to add
     * @return new Color object which is a result of the operation
     */
    public Color add(Color... colors) {
        double rr = r;
        double rg = g;
        double rb = b;
        for (Color c : colors) {
            rr += c.r;
            rg += c.g;
            rb += c.b;
        }
        return new Color(rr, rg, rb);
    }

    /**
     * Scale the color by a scalar
     *
     * @param k scale factor
     * @return new Color object which is the result of the operation
     */
    public Color scale(double k) {
        if (k < 0)
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        return new Color(r * k, g * k, b * k);
    }

    /**
     * Scale the color by (1 / reduction factor)
     *
     * @param k reduction factor
     * @return new Color object which is the result of the operation
     */
    public Color reduce(double k) {
        if (k < 1)
            throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        return new Color(r / k, g / k, b / k);
    }


    /**
     * @author kayla nayman and naama holzer
     * This function compares colors and returns a boolean variable whether they are equal or not
     * @param color the color to compare
     * @return true if the colors equals, otherwise false
     */
    public boolean isColorsEqual(Color color) {
        //thanks to kayla neyman
        Point3D p1 = new Point3D(this.r, this.g, this.b);
        Point3D p2 = new Point3D(color.r, color.g, color.b);
        double c1 = Math.sqrt((this.r) * (this.r) + (this.g) * (this.g) + (this.b) * (this.b));
        double c2 = Math.sqrt((color.r) * (color.r) + (color.g) * (color.g) + (color.b) * (color.b));
        double avg = (c1 + c2) / 2d;
        return !(p1.distance(p2) > 0.25 * avg);
    }



}

