package sample;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.media.j3d.Transform3D;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.io.File;


public class Robot3D extends JFrame{

    Robot3D(){

        super("Grafika 3D");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));

        add(canvas3D);
        pack();
        setVisible(true);

        BranchGroup scena = utworzScene();
        scena.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,3.0f));

        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        simpleU.addBranchGraph(scena);
    }

    BranchGroup utworzScene(){

        BranchGroup wezel_scena = new BranchGroup();

        Color3f kolor_swiatla_tla      = new Color3f(0.3f, 0.2f, 0.91f);
        Color3f kolor_swiatla_kier     = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f kolor_swiatla_pnkt     = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f kolor_swiatla_sto      = new Color3f(0.0f, 0.0f, 1.0f);

        BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);

        Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -1.0f);
        Vector3f kierunek_swiatla_sto  = new Vector3f(-4.0f, -5.0f, -1.0f);

        Material material_kuli = new Material(new Color3f(0.0f, 0.1f,0.0f), new Color3f(0.0f,0.0f,0.3f),
                new Color3f(0.6f, 0.0f, 0.0f), new Color3f(1.0f, 1.0f, 1.0f), 80.0f);



        Material material_walca = new Material(new Color3f(0.5f, 0.3f,0.2f), new Color3f(0.1f,0.1f,0.1f),
                new Color3f(0.8f, 0.3f, 0.5f), new Color3f(0.2f, 0.2f, 0.2f), 20.0f);

        ColoringAttributes cattr = new ColoringAttributes();
        cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);

        Appearance wyglad_kuli = new Appearance();
        Appearance wyglad_walca = new Appearance();

        wyglad_kuli.setMaterial(material_kuli);
        wyglad_kuli.setColoringAttributes(cattr);
        wyglad_walca.setMaterial(material_walca);
        wyglad_kuli.setColoringAttributes(cattr);




        AmbientLight swiatlo_tla = new AmbientLight(kolor_swiatla_tla);
        DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
        PointLight swiatlo_pnkt = new PointLight(kolor_swiatla_pnkt, new Point3f(-0.5f,0.5f,0.5f), new Point3f(0.1f,0.1f,0.1f));
        SpotLight swiatlo_sto = new SpotLight(kolor_swiatla_sto, new Point3f(0.5f, 0.5f, 0.5f), new Point3f(0.01f,0.01f,0.01f),
                new Vector3f(-1.0f, -1.0f, -1.0f), (float)(Math.PI), 100);

        swiatlo_tla.setInfluencingBounds(obszar_ogr);
        swiatlo_kier.setInfluencingBounds(obszar_ogr);
        swiatlo_pnkt.setInfluencingBounds(obszar_ogr);
        swiatlo_sto.setInfluencingBounds(obszar_ogr);



        //Sphere kula = new Sphere(0.3f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder walec = new Cylinder(1f,0.001f,wyglad_walca);
        Cylinder walec2 = new Cylinder(0.02f,0.4f);
        Cylinder walec3 = new Cylinder(0.02f,0.4f);

        TransformGroup trans_kuli = new TransformGroup();
        TransformGroup trans_walca = new TransformGroup();
        TransformGroup trans_walca2 = new TransformGroup();
        TransformGroup trans_walca3 = new TransformGroup();

        Transform3D przesuniecie_kuli = new Transform3D();
        Transform3D przesuniecie_walca = new Transform3D();
        Transform3D przesuniecie_walca2 = new Transform3D();
        Transform3D przesuniecie_walca3 = new Transform3D();
        Transform3D  tmp_rot      = new Transform3D();
        tmp_rot.rotZ(Math.PI/2);

        przesuniecie_kuli.set(new Vector3f(0.0f, 0.3f,0.0f));
        przesuniecie_walca.set(new Vector3f(0.0f,-0.4f,0.0f));
        przesuniecie_walca2.set(new Vector3f(0.0f,-0.15f,0.0f));
        przesuniecie_walca3.set(new Vector3f(0.0f,-0.5f,0.0f));

        tmp_rot.rotZ(+Math.PI/2);
        przesuniecie_walca3.mul(tmp_rot);

        trans_kuli.setTransform(przesuniecie_kuli);
        trans_walca.setTransform(przesuniecie_walca);
        trans_walca2.setTransform(przesuniecie_walca2);
        trans_walca3.setTransform(przesuniecie_walca2);

        wezel_scena.addChild(trans_kuli);
        wezel_scena.addChild(trans_walca);
        wezel_scena.addChild(trans_walca2);
        wezel_scena.addChild(trans_walca3);
        wezel_scena.addChild(swiatlo_tla);
        wezel_scena.addChild(swiatlo_kier);
        wezel_scena.addChild(swiatlo_pnkt);
        wezel_scena.addChild(swiatlo_sto);
        //trans_kuli.addChild(kula);
        trans_walca.addChild(walec);
        trans_walca2.addChild(walec2);
        trans_walca2.addChild(walec3);

        return wezel_scena;

    }

    public static void main(String args[]){
        new Robot3D();

    }

}
