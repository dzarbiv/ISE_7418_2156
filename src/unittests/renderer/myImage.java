package unittests.renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import static java.awt.Color.*;

public class myImage {


    private Scene scene = new Scene("Test scene");

    //front view-point
    private Camera camera = new Camera(new Point3D(1000, 0,0 ), new Vector(0, 0, 1), new Vector(-1, 0, 0)) //
           .setViewPlaneSize(400, 400).setDistance(1000);
    //side view-point
	//private Camera camera = new Camera(new Point3D(0, 0,1000 ), new Vector(1, 0, 0), new Vector(0, 0, -1)) //
	//		.setViewPlaneSize(200, 200).setDistance(1000);


    //Light blue: 51-153-255
    public static final Color Light_blue = new Color(51, 153, 255);
    //Very_light_blue: 51-204-255
    public static final Color Very_light_blue = new Color(51, 204, 255);
    //Dark blue: 0-0-204
    public static final Color Dark_blue = new Color(0, 0, 204);
    //Purple: 102-0-153
    public static final Color Purple = new Color(102, 0, 153);
    //Gold: 255-204- 51
    public static final Color Gold = new Color(255, 204, 51);
    //Very light red: 255-102-102
    public static final Color Very_light_red = new Color(255, 102, 102);
    //Light red: 255-51-51
    public static final Color Light_red = new Color(255, 51, 51);
    //Dark red: 160-0-0
    public static final Color Dark_red = new Color(160, 0, 0);
    //Very dark red: 153-0 -0
    public static final Color Very_Dark_red = new Color(153, 0, 0);
    //Light brown: 153-102-0
    public static final Color Light_brown = new Color(153, 102, 0);
    //Brown: 102-51-0
    public static final Color Brown = new Color(102, 51, 0);
    //Dark brown: 51-0-0
    public static final Color Dark_brown = new Color(51, 0, 0);


    @Test
    public void OurPicture()
    {
        scene.setBackground(new Color(LIGHT_GRAY));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));

        try {
            scene._geometries.add(
                    //Head
                    new Sphere( new Point3D(0, 0, 30),30)
                            .setEmission(new Color(Gold))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Head Scope
                    new Sphere( new Point3D(-3, 0, 30),31)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Right eye
                    new Sphere( new Point3D(30, 11, 35),7.5)
                            .setEmission(new Color(Gold))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Right eye Scope
                    new Sphere( new Point3D(27, 11, 35),8.5)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Left eye
                    new Sphere( new Point3D(30, -11, 35),7.5)
                            .setEmission(new Color(Gold))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Left eye Scope
                    new Sphere( new Point3D(27, -11, 35),8.5)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Right Pupil
                    new Sphere( new Point3D(35, 10, 34),5)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //Left Pupil
                    new Sphere( new Point3D(35, -12, 34),5)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //beak
                    new Triangle(new Point3D(30, -6, 22), new Point3D(30, 6, 22), new Point3D(30, 0, 12))
                            .setEmission(new Color(red))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //beak scope
                    new Triangle(new Point3D(30, -8, 23), new Point3D(30, 8, 23), new Point3D(30, 0, 10))
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //belly
                    new Sphere( new Point3D(0, 0, -60),60)
                            .setEmission(new Color(Gold))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),
                    //belly Scope
                    new Sphere( new Point3D(-3.5, 0, -60.25),61)
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)),

                     //right hand
                     new Triangle(new Point3D(50, 36, -60), new Point3D(50, 42, -20), new Point3D(50, 79, -68))
                             .setEmission(new Color(Gold))
                             .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //right hand scope
                    new Triangle(new Point3D(49, 34, -62), new Point3D(49, 41, -15), new Point3D(49, 83, -70.5))
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //Left hand
                    new Triangle(new Point3D(50, -36, -60), new Point3D(50, -42, -20), new Point3D(50, -79, -68))
                            .setEmission(new Color(Gold))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //Left hand scope
                    new Triangle(new Point3D(49, -34, -62), new Point3D(49, -41, -15), new Point3D(49, -83, -70.5))
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //right leg
                    new Triangle(new Point3D(50, 10, -136), new Point3D(50, 25, -110), new Point3D(50, 53, -150))
                            .setEmission(new Color(red))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //right leg scope
                    new Triangle(new Point3D(49, 7.5, -137), new Point3D(49, 24, -106), new Point3D(49, 56, -153))
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //left leg
                     new Triangle(new Point3D(50, -10, -136), new Point3D(50, -25, -110), new Point3D(50, -53, -150))
                          .setEmission(new Color(red))
                          .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                    //left leg scope
                    new Triangle(new Point3D(49, -7.5, -137), new Point3D(49, -24, -106), new Point3D(49, -56, -153))
                            .setEmission(new Color(black))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))

            );
        } catch (Exception e) {

        }
      // scene._lightSourceList.add( //
      //         new SpotLight(new Color(WHITE), new Point3D(40, 80, 115), new Vector(-1, -1, -4))
      //                 .setKl(4E-4).setKq(2E-5));
      // scene._lightSourceList.add(new PointLight(new Color(160,80,240), new Point3D(-100, -100, 100))//
      //         .setKl(0.00000000001).setKq(0.0000000001));
      // scene._lightSourceList.add( //
      //         new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
      //                 .setKl(1E-5).setKq(1.5E-7));
        scene._lightSourceList.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(100, 0, 150), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("OurPicture", 600, 600)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

}