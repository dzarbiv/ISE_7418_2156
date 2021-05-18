package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter = null;
    private Camera _camera = null;
    private RayTracerBase _rayTracer = null;

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }


    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }

    /**
     * This will first check that a blank value has been entered in all fields and then throw an exception
     */
    public void renderImage() {
        try {
            if (_imageWriter == null)
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
             if (_camera == null)
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            if (_rayTracer == null)
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = _rayTracer.traceRay(ray);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("not implemented yet" + e.getClassName());
        }
    }

    /**
     * Will create a network of lines similar to what was performed in the test in the first stage
     * First the method will first check that a non-empty value was entered in the field of the image manufacturer
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Initially the method will check that in the field of the image manufacturer a non-empty value was entered
     * and in case of lack of throwing a suitable deviation
     */
    public void writeToImage() {
        if (_imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        _imageWriter.writeToImage();
    }
}
