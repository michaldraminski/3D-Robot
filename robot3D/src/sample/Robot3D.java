package sample;

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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


public class Robot3D extends JFrame implements ActionListener, KeyListener{

    private boolean przyciskA;
    private boolean przyciskD;
    private boolean przyciskW;
    private boolean przyciskS;


    TransformGroup tranGroupRamienia = new TransformGroup();
    Transform3D t3dRamienia = new Transform3D();
    TransformGroup tranGroupPodstawki = new TransformGroup();
    TransformGroup tranGroupStojaka = new TransformGroup();
    Transform3D t3DPodstawki = new Transform3D();
    Transform3D t3DStojaka = new Transform3D();

    //Transform3D t3DPodstawkiPrzesuniecie = new Transform3D();
    //Transform3D t3DStojakaPrzesuniecie = new Transform3D();
    Transform3D t3dRamieniaPrzesuniecie = new Transform3D();

    Robot3D(){

        super("Grafika 3D");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));

        canvas3D.addKeyListener(this);
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

        TransformGroup obrot_animacja = new TransformGroup();
        obrot_animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        wezel_scena.addChild(obrot_animacja);

        Color3f kolor_swiatla_tla      = new Color3f(0.3f, 0.2f, 0.91f);
        Color3f kolor_swiatla_kier     = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f kolor_swiatla_pnkt     = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f kolor_swiatla_sto      = new Color3f(0.0f, 0.0f, 1.0f);

        BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);

        Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -1.0f);
        //Vector3f kierunek_swiatla_sto  = new Vector3f(-4.0f, -5.0f, -1.0f);

        Material material_walca = new Material(new Color3f(0.5f, 1f,0.5f), new Color3f(0.5f,1f,0.5f),
                new Color3f(0.5f, 1f, 0.5f), new Color3f(0.2f, 1f, 0.2f), 20.0f);

        ColoringAttributes cattr = new ColoringAttributes();
        cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);

        Appearance wyglad_walca = new Appearance();

        wyglad_walca.setMaterial(material_walca);

/*
        tg_podstawka.setCapability(TransformGroup.AramLLOW_TRANSFORM_WRITE);
        tg_podstawka.setTransform(t3d_podstawka);
        tg_podstawka.addChild(podstawka.getSceneGroup());
        glowna_scena.addChild(tg_podstawka);

        trans_walca2.addChild(w);*/



        tranGroupStojaka.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //tranGroupRamienia.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);



        AmbientLight swiatlo_tla = new AmbientLight(kolor_swiatla_tla);
        DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
        PointLight swiatlo_pnkt = new PointLight(kolor_swiatla_pnkt, new Point3f(-0.5f,0.5f,0.5f), new Point3f(0.1f,0.1f,0.1f));
        SpotLight swiatlo_sto = new SpotLight(kolor_swiatla_sto, new Point3f(0.5f, 0.5f, 0.5f), new Point3f(0.01f,0.01f,0.01f),
                new Vector3f(-1.0f, -1.0f, -1.0f), (float)(Math.PI), 100);

        swiatlo_tla.setInfluencingBounds(obszar_ogr);
        swiatlo_kier.setInfluencingBounds(obszar_ogr);
        swiatlo_pnkt.setInfluencingBounds(obszar_ogr);
        swiatlo_sto.setInfluencingBounds(obszar_ogr);

        Cylinder podstawka = new Cylinder(1f,0.001f,wyglad_walca);
        Cylinder stojak = new Cylinder(0.02f,0.4f);
        Cylinder ramie = new Cylinder(0.02f,0.4f);


        Transform3D  tmp_rot      = new Transform3D();
        tmp_rot.rotZ(Math.PI/2);

        tranGroupRamienia.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupRamienia.setTransform(t3dRamienia);
        //tranGroupRamienia.addChild(ramie);

        t3DPodstawki.set(new Vector3f(0.0f,-0.4f,0.0f));
        t3DStojaka.set(new Vector3f(0.0f,-0.15f,0.0f));
        t3dRamieniaPrzesuniecie.setTranslation(new Vector3f(-0.2f,0.2f,0.0f));

        t3dRamienia.mul(t3dRamieniaPrzesuniecie);
        tranGroupRamienia.setTransform(t3dRamienia);
        tranGroupStojaka.addChild(tranGroupRamienia);

        tmp_rot.rotZ(+Math.PI/2);
        t3dRamienia.mul(tmp_rot);

        tranGroupPodstawki.setTransform(t3DPodstawki);
        tranGroupStojaka.setTransform(t3DStojaka);
        tranGroupRamienia.setTransform(t3dRamienia);

        wezel_scena.addChild(tranGroupPodstawki);
        //wezel_scena.addChild(tranGroupStojaka);
        //wezel_scena.addChild(trans_walca3);
        wezel_scena.addChild(swiatlo_tla);
        wezel_scena.addChild(swiatlo_kier);
        wezel_scena.addChild(swiatlo_pnkt);
        wezel_scena.addChild(swiatlo_sto);
        tranGroupPodstawki.addChild(podstawka);
        tranGroupStojaka.addChild(stojak);
        tranGroupRamienia.addChild(ramie);

        obrot_animacja.addChild(tranGroupStojaka);
        //wezel_scena.addChild(tranGroupRamienia);

        return wezel_scena;

    }

    public static void main(String[] args){
        new Robot3D();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int przycisk = e.getKeyCode();

        switch (przycisk) {
            case KeyEvent.VK_A -> przyciskA = true;
            case KeyEvent.VK_D -> przyciskD = true;
            case KeyEvent.VK_W -> przyciskW = true;
            case KeyEvent.VK_S -> przyciskS = true;
        }
        obrot();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> przyciskA = false;
            case KeyEvent.VK_D -> przyciskD = false;
            case KeyEvent.VK_W -> przyciskW = false;
            case KeyEvent.VK_S -> przyciskS = false;
        }
    }
    public void obrot() {
        Transform3D akcja = new Transform3D();
        if (przyciskA) {
            akcja.rotY(Math.PI / 180);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (przyciskD) {
            akcja.rotY(-Math.PI / 180);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (przyciskW) {
            akcja.rotZ(Math.PI / 180);
            t3dRamienia.mul(akcja);
            tranGroupRamienia.setTransform(t3dRamienia);
        }
        if (przyciskS) {
            akcja.rotZ(-Math.PI / 180);
            t3dRamienia.mul(akcja);
            tranGroupRamienia.setTransform(t3dRamienia);
        }
        }
    }

