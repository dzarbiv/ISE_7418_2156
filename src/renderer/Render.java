package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan
 *
 */
public class Render {
    private final Scene _scene;
    private ImageWriter imageWriter;
    private boolean superSampling;//is super sampling wanted?
    private static final double DELTA = 0.1;//small number used for scaling shadow rays
    private static final int MAX_CALC_COLOR_LEVEL = 50; //the maximum recursion level for calcColor
    private static final double MIN_CALC_COLOR_K = 0.0000001;//minimum transparency level for transparency rays
    private static final int NUM_OF_SAMPLE_RAYS = 9; //81 rays throw pixel for super sampling
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean adaptiveSuperSampling;//is adaptive super sampling wanted?
    private static final int LEVEL_OF_ADAPTIVE = NUM_OF_SAMPLE_RAYS/2;//number of times to repeat recursion of adaptive super samling
    private int _threads = 1;//number of threads to use when running program- default is one
    private boolean _print = true;//print completion of thread?




    private Camera camera;
    private RayTracerBase tracer;
   // private static final String RESOURCE_ERROR = "Renderer resource not set";
   // private static final String RENDER_CLASS = "Render";
   // private static final String IMAGE_WRITER_COMPONENT = "Image writer";
//
   // private static final String CAMERA_COMPONENT = "Camera";
   // private static final String RAY_TRACER_COMPONENT = "Ray tracer";
   //private int threadsCount = 0;



    /**
     * Constructor for render
     * @param _scene scene to render
     */
    public Render(Scene _scene,Camera _camera) {
        this._scene = _scene;
        setCamera(_camera);
        superSampling=true;
        adaptiveSuperSampling=false;
    }

    public Render setSuperSampling(boolean superSampling) {
        this.superSampling = superSampling;
        return this;
    }

    public Render setAdaptiveSuperSampling(boolean adaptiveSuperSampling) {
        this.adaptiveSuperSampling = adaptiveSuperSampling;
        return this;

    }

    /**
     * constructor for render
     * @param scene scene to render
     * @param imageWriter image writer
     */
    public Render(Scene scene,Camera _camera, ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        this._scene = scene;
        setCamera(_camera);
        superSampling=true;
        adaptiveSuperSampling=false;
    }

    /**
     * Constructor for Render that also receives from user wethere he would like to use improvements in the picture
     * @param scene scene to render
     * @param imageWriter image writer
     * @param _superSampling is super sampling requested
     * @param _adaptiveSuperSampling is adaptive super sampling requested
     */
    public Render(Scene scene,Camera camera, ImageWriter imageWriter,boolean _superSampling,boolean _adaptiveSuperSampling){
        this.imageWriter = imageWriter;
        this._scene = scene;
        setCamera(camera);
        superSampling=_superSampling;
        adaptiveSuperSampling=_adaptiveSuperSampling;
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)//threads cannot be less than zero
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)//any number that is not zero is acceptable
            _threads = threads;
        else {//if number received was zero:
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;//if cores is smaller than 2 than threads is 1, i=otherwise it is equal to cores
        }
        return this;
    }

    /**
     * Camera setter
     *
     * @param _camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera _camera) {
        camera = _camera;
        _scene.setCamera(camera);
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param _tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(RayTracerBase _tracer) {
        this.tracer = _tracer;
        return this;
    }
    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    public Render setImageWriter(ImageWriter _imageWriter) {
        imageWriter = _imageWriter;
        return this;
    }


    /**
     * @param light light source
     * @param l vector from light source to geometry
     * @param n normal of geometry
     * @param geopoint GeoPoint
     * @return level of transparency of object
     */
    private double transparency(LightSource light, Vector l, Vector n, Intersectable.GeoPoint geopoint) {
        double transparency_level=1;
        double un_transparency_level=0;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);//ray in opposite direction of vector L from light, scaled by delta
        Point3D pointGeo = geopoint.getPoint();

        List<Intersectable.GeoPoint> intersections = _scene.getGeometries().findGeoIntersections(lightRay);//geometries that might be in between light source and geometry
        if (intersections == null) {
            return transparency_level;//if there are no geometries that intersect with the ray then it is transparent
        }
        double lightDistance = light.getDistance(pointGeo);//distance of light source from geometry
        double ktr = transparency_level;//transparency level
        for (Intersectable.GeoPoint gp : intersections) {
            //for each geometry that was intersected by ray, check whethter it is
            //located in front or behind the light: if the distance from the geometry to
            // the point is smaller than the distance of the light source to the point
            // then it is in front of the light.
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) {
                //if the geometry was in front of the light multiply ktr by its transparency level
                ktr *= gp.getGeometry().getMaterial().getkT();
                if (ktr < MIN_CALC_COLOR_K) {
                    //if it is smaller than MIN_CALC_COLOR_K then consider it opaque,
                    // no light goes through it and its transparency level is zero
                    return un_transparency_level;
                }
            }
        }
        return ktr;//return ktr
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */

    public void renderImage() {
        Camera camera = _scene.getCamera();

        //Nx and Ny are the number of pixels in the rows
        //and columns of the view plane
        int Nx = imageWriter.getNx(); //columns
        int Ny = imageWriter.getNy(); //rows

        final Pixel thePixel = new Pixel(Ny, Nx);

        //create thread foreach ray calculation
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();

                while (thePixel.nextPixel(pixel)) {
                    if (superSampling == false) {// without super sampling
                        //create ray throw this pixel
                        Ray ray = camera.constructRayThroughPixel(Nx, Ny, pixel.col, pixel.row, camera.getWidth(), camera.getHeight());
                        Color pixelColor = calcColor(ray);  //calculate the color for the pixel
                        imageWriter.writePixel(pixel.col, pixel.row, pixelColor.getColor());  //write the color to the pixel
                    } else {//super sampling
                        if (adaptiveSuperSampling){// super sampling and adaptive
                            //calculate the color for the pixel using adaptive improve
                            Color result = adaptiveSuperSampling(camera, Nx, Ny, pixel.col, pixel.row);
                            imageWriter.writePixel(pixel.col, pixel.row, result.getColor()); //write the color to the pixel
                        }
                        else {//        super sampling without adaptive
                            //calculate the color for the pixel using super sampling improve
                            Color pixelColor = superSampling(camera, Nx, Ny, pixel);
                            imageWriter.writePixel(pixel.col, pixel.row, pixelColor.getColor()); //write the color to the pixel
                        }
                    }

                }
            });
        }
        // Start threads
        for (Thread thread : threads) thread.start();
        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
            }
        }
        if (_print) {
            printMessage("100%\n");
        }
    }
    /**
     * This function returns the color in pixels using the enhanced calculation super Sampling
     * @param camera the scene camera
     * @param nx number of pixel on the x axis
     * @param ny number of pixel on the y axis
     * @param pixel the pixel to calculate its color
     * @return color of pixel
     */
    private Color superSampling(Camera camera, int nx, int ny, Pixel pixel) {
        List<Ray> rays = camera.constructRaysThroughPixel(nx, ny, pixel.col, pixel.row, camera.getWidth(), camera.getHeight(), NUM_OF_SAMPLE_RAYS);  //calculate rays throw this pixel
        return calcColor(rays);  //calculate the color of the pixel
    }

    /**
     * Finding the closest point to the P0 of the camera.
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */

    private Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> intersectionPoints) {
        Intersectable.GeoPoint result = null;
        double mindist = Double.MAX_VALUE; //max number

        Point3D p0 = this._scene.getCamera().getP0();

        for (Intersectable.GeoPoint geo : intersectionPoints) { //foreach intersection point
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt); //the distance between the point and the camera
            if (distance < mindist) {  //set the new minimum distance
                mindist = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     * @param colorsep the color of the lines
     */
    public void printGrid(int interval, Color colorsep) {
        double rows = this.imageWriter.getNy();
        double collumns = imageWriter.getNx();
        //Writing the lines.
        for (int row = 0; row < rows; ++row) {
            for (int collumn = 0; collumn < collumns; ++collumn) {
                if (collumn % interval == 0 || row % interval == 0) {
                    imageWriter.writePixel(collumn, row, colorsep);
                }
            }
        }
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * calculate the avarage color of the rays in the list
     * @param superSamplingRays list of rays
     * @return color
     */
    private Color calcColor(List<Ray> superSamplingRays){
        Color color = Color.BLACK;
        for(Ray ray: superSamplingRays){    //Calculate the color for each of the rays
            color = color.add(calcColor(ray));  //sum up the color scheme
        }
        color = color.reduce(superSamplingRays.size());   //Reduces the result color by the number of rays in the list (average calculation)
        return color;
    }

    /**
     * recursive fucntion to calculate color in certain point
     * @param coloredPoint GeoPoint- the point on the geometry where color is calculated
     * @param inRay ray
     * @param level counts how many times recursion has occured
     * @param k k
     * @return color of point
     */
    private Color calcColor(Intersectable.GeoPoint coloredPoint, Ray inRay, int level, double k) {
        List<LightSource> lightSources = _scene.getLightSourceList(); //the light sources of the scene
        Color result = coloredPoint.getGeometry().getEmission(); //emission light of geometry

        Vector v = coloredPoint.getPoint().subtract(_scene.getCamera().getP0()).normalize();//vector from camera to geometry
        Vector n = coloredPoint.getGeometry().getNormal(coloredPoint.getPoint());//normal to the point on the geometry

        Material material = coloredPoint.getGeometry().getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();

        if (lightSources != null) {
            for (LightSource lightSource : lightSources) { //for each light source calculate the color in the point
                Vector l = lightSource.getL(coloredPoint.getPoint());//vector from light source to point on geometry
                double nl = alignZero(n.dotProduct(l));//dot product of normal off geometry and l
                double nv = alignZero(n.dotProduct(v));//dot product of normal off geometry and vector from camera to geometry

                if (nl * nv > 0) {
                    double ktr = transparency(lightSource, l, n, coloredPoint);//get transparency level of geometry

                    if (ktr * k > MIN_CALC_COLOR_K) {//if the transparency level is not below saved minimum
                        //calculation of color according to phong reflectance model (with diffusive and specular...)
                        Color ip = lightSource.getIntensity(coloredPoint.getPoint()).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, ip), //add the diffuse
                                calcSpecular(ks, l, n, nl, v, nShininess, ip));  //add the specular
                    }
                }
            }
        }

        if (level == 1)//end of recursion
            return Color.BLACK;
        double kr = material.getkR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K){//if the reflectance level is not below saved minimum
            Ray reflectedRay = constructReflectedRay(coloredPoint._point, inRay, n);//reflectance ray
            Intersectable.GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);//closest intersection point of reflection ray with geometries to the geometry
            if (reflectedPoint != null)
                result = result.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));//recursion
        }
        double kt = material.getkT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {//if the transparency level is not below saved minimum
            Ray refractedRay = constructRefractedRay(coloredPoint._point, inRay, n);//refraction ray
            Intersectable.GeoPoint refractedPoint = findClosestIntersection(refractedRay);//closest intersection point of refraction ray with geometries to the geometry
            if (refractedPoint != null)
                result = result.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));//recursion
        }
        return result;

    }

    /**
     * calls the recursive calcColor function
     * @param inRay ray
     * @return color of point
     */
    private Color calcColor(Ray inRay) {
        Intersectable.GeoPoint closestPoint = findClosestIntersection(inRay); //the closest intersection with the geometries point
        if (closestPoint == null){ //If the Ray is not cut with any geometry the pixel will get the background color of the scene
            return _scene.getBackground();
        }
        Color color = calcColor(closestPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);//Calculate recursive color in pixels
        color = color.add(_scene.getAmbientLight().getIntensity());//add ambient light
        return color;
    }


    /**
     * the function call to the adaptiveSuperSampling recursion function
     *@param camera camera in scene
     * @param nx number of pixels on X axis
     * @param ny number of pixels on Y axis
     * @param col the pixel position
     * @param row the pixel position
     * @return color of pixel
     */
    private  Color adaptiveSuperSampling(Camera camera, int nx, int ny, int col, int row) {
        List<Ray> rays = camera.constructRaysThroughPixel(nx, ny, col, row, camera.getWidth(), camera.getHeight(), NUM_OF_SAMPLE_RAYS); // calculate the rays throw the pixel
        int ray1Index = (NUM_OF_SAMPLE_RAYS-1)*NUM_OF_SAMPLE_RAYS+(NUM_OF_SAMPLE_RAYS-1); //the index of the up and right ray
        int ray2Index =(NUM_OF_SAMPLE_RAYS-1)*NUM_OF_SAMPLE_RAYS;  //the index of the up and left ray
        int ray3Index= 0;  //the index of the button and left ray
        int ray4Index = (NUM_OF_SAMPLE_RAYS-1);  // the index of the button and right ray

        Color color = adaptiveSuperSampling(rays, LEVEL_OF_ADAPTIVE, ray1Index, ray2Index, ray3Index, ray4Index); //calculate the color for the pixel
        return color;
    }


    /**
     * Calculates the color of a pixel recursively using adaptive supersampling
     * @param rays rays through pixel
     * @param level_of_adaptive the level of the recursion
     * @param ray1Index ray through corner 1
     * @param ray2Index ray through corner 2
     * @param ray3Index ray through corner 3
     * @param ray4Index ray through corner 4
     * @return Color
     */
    private Color adaptiveSuperSampling(List<Ray> rays, int level_of_adaptive,int ray1Index, int ray2Index, int ray3Index, int ray4Index) {
        int numOfAdaptiveRays = 5;

        Ray centerRay = rays.get(rays.size()-1); //get the center screen ray
        Color centerColor=calcColor(centerRay); //get the color of the center
        Ray ray1 = rays.get(ray1Index);  //get the up and right screen ray
        Color color1=calcColor(ray1); //get the color of the up and right
        Ray ray2 = rays.get(ray2Index); //get the up and left ray
        Color color2=calcColor(ray2);//get the color of the up and left
        Ray ray3 = rays.get(ray3Index);//get the button and left ray
        Color color3=calcColor(ray3);//get the color of the button and left
        Ray ray4 = rays.get(ray4Index);//get the button and right ray
        Color color4=calcColor(ray4);//get the color of the button and right
        if (level_of_adaptive == 0)
        {
            //Calculate the average color of the corners and the center
            centerColor = centerColor.add(color1,color2, color3,color4);
            return centerColor.reduce(numOfAdaptiveRays);
        }
        //If the corner color is the same as the center color, returns the center color
        if (color1.isColorsEqual(centerColor) && color2.isColorsEqual(centerColor) && color3.isColorsEqual(centerColor) && color4.isColorsEqual(centerColor))
        {
            return centerColor;
        }
        //Otherwise, for each color that is different from the center, the recursion goes down to the depth of the pixel and sums up
        // the colors until it gets the same color as the center color,
        else {
            if (!color1.isColorsEqual(centerColor)) {
                color1 = color1.add(adaptiveSuperSampling(rays,
                        level_of_adaptive - 1,
                        ray1Index - (NUM_OF_SAMPLE_RAYS+1),
                        ray2Index,
                        ray3Index,
                        ray4Index ));
                color1 = color1.reduce(2);
            }
            if (!color2.isColorsEqual(centerColor)) {;
                color2 = color2.add(adaptiveSuperSampling(rays,level_of_adaptive - 1,ray1Index,
                        ray2Index-(NUM_OF_SAMPLE_RAYS-1), ray3Index, ray4Index));
                color2 = color2.reduce(2);
            }
            if (!color3.isColorsEqual(centerColor)) {
                color3 = color3.add(adaptiveSuperSampling(rays, level_of_adaptive - 1,ray1Index, ray2Index, ray3Index+(NUM_OF_SAMPLE_RAYS+1), ray4Index));
                color3 = color3.reduce(2);
            }
            if (!color4.isColorsEqual(centerColor)) {
                color4 = color4.add(adaptiveSuperSampling(rays, level_of_adaptive - 1,ray1Index, ray2Index, ray3Index, ray4Index+(NUM_OF_SAMPLE_RAYS-1)));
                color4 = color4.reduce(2);
            }
            //Calculate and return the average color
            centerColor = centerColor.add(color1, color2, color3, color4);
            return centerColor.reduce(numOfAdaptiveRays);

        }
    }


    /**
     *   Find intersections of a ray with the scene geometries and get the
     *       intersection point that is closest to the ray head. If there are no
     *       intersections, null will be returned.
     *
     * @param ray the ray
     * @return null if there were no intersections, otherwise closest point
     */
    private Intersectable.GeoPoint findClosestIntersection(Ray ray) {

        if (ray == null) {
            return null;
        }
        List<Intersectable.GeoPoint> intersections = _scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null)//there are no intersection points
            return null;
        Intersectable.GeoPoint closestPoint = getClosestPoint(intersections);//get closest point
        return closestPoint;
    }

    /**
     * construct a refracted ray
     * @param pointGeo GeoPoint- the point on the geometry where color is calculated
     * @param inRay ray
     * @param n normal
     * @return refracted ray
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDir(), n);//creates new ray that is scaled by delta- it is moved a little from surface of geometry
    }

    /**
     * construct a reflected ray
     * @param pointGeo GeoPoint- the point on the geometry where color is calculated
     * @param inRay ray
     * @param n normal
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) { //Perpendicular vectors
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }


    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs](-V.R)^p
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * The diffuse component is that dot product nâ€¢L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value,
     * so this term would in general be a color defined as: [rd,gd,bd](nâ€¢L)
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) {
            nl = -nl;
        }

        return ip.scale(nl * kd);
    }
    /**
     * @param val value
     * @return sign of value
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * print message
     * @param msg message to print
     */
    private synchronized void printMessage(String msg) {
        synchronized (System.out) {
            System.out.println(msg);
        }
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = 0;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this._print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col; //Promotes columns
            ++counter;
            if (col < maxCols) {//the row did'nt end
                target.row = this.row;
                target.col = this.col;
                if (_print && counter == nextCounter) {//promote the percents of the work that done
                    ++percents;
                    nextCounter = pixels * (percents + 1) / 100;
                    return percents;
                }
                return 0;
            }
            ++row;//Promotes rows
            if (row < maxRows) { //we didnt finish
                col = 0;
                if (_print && counter == nextCounter) {
                    ++percents;
                    nextCounter = pixels * (percents + 1) / 100;//promote the percents of the work that done
                    return percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);//get the next pixel
            if (_print && percents > 0) { //print the percent of work that done
                printMessage(String.format(" %02d%%", percents));
            }
            if (percents >= 0)//image writer progress started
                return true;
            if (_print) {//print the percent of work that done
                printMessage(String.format(" %02d%%", 100));
            }
            return false;//image writer progress did not started
        }
    }
}