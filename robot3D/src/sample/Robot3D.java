package sample;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
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


public class Robot3D extends JFrame implements ActionListener, KeyListener{

    /////deklarowanie zmiennych///////
    private boolean przyciskA;
    private boolean przyciskD;
    private boolean przyciskW;
    private boolean przyciskS;
    private boolean przyciskE;
    private boolean przyciskR;

    TransformGroup tranGroupPodstawki = new TransformGroup();
    TransformGroup tranGroupStojaka = new TransformGroup();
    TransformGroup tranGroupStawu1 = new TransformGroup();
    TransformGroup tranGroupRamienia1 = new TransformGroup();
    TransformGroup tranGroupStawu2 = new TransformGroup();
    TransformGroup tranGroupRamienia2 = new TransformGroup();

    Transform3D t3DPodstawki = new Transform3D();
    Transform3D t3DStojaka = new Transform3D();
    Transform3D t3dRamienia1 = new Transform3D();
    Transform3D t3Dstawu1 = new Transform3D();
    Transform3D t3dRamienia2 = new Transform3D();
    Transform3D t3Dstawu2 = new Transform3D();

    Robot3D(){

        super("Projekt - ramie robota 3D");

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
        Transform3D rot_obs = new Transform3D();
        rot_obs.rotY((float)(-Math.PI/7));
        przesuniecie_obserwatora.set(new Vector3f(-1.2f,1.5f,2.0f));
        przesuniecie_obserwatora.mul(rot_obs);
        rot_obs.rotX((float)(-Math.PI/6));
        przesuniecie_obserwatora.mul(rot_obs);

        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());
        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
        simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);

        simpleU.addBranchGraph(scena);
    }

    BranchGroup utworzScene(){

        BranchGroup wezel_scena = new BranchGroup();

        //////MATERIALY///////////////
        ColoringAttributes cattr = new ColoringAttributes();
        cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        cattr.setColor(new Color3f(1f,0f,0f));

         Material material_kuli = new Material(new Color3f(0f, 0.5f,0f), new Color3f(0.3f,0.147f,0.0f),
         new Color3f(0f, 0.6f, 0.f), new Color3f(1.0f, 1.0f, 1.0f), 20f);
         Appearance wyglad_kuli = new Appearance();

        wyglad_kuli.setMaterial(material_kuli);
        wyglad_kuli.setColoringAttributes(cattr);
        Sphere staw1 = new Sphere(0.03f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Sphere staw2 = new Sphere(0.03f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);

        BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);
        Material material_walca = new Material(new Color3f(0.5f, 1f,0.5f), new Color3f(0.5f,1f,0.5f),
                new Color3f(0.5f, 1f, 0.5f), new Color3f(0.2f, 1f, 0.2f), 20.0f);


        Appearance wyglad_walca = new Appearance();

        wyglad_walca.setMaterial(material_walca);
        ///////////////////////////////
        ///////////////////////////SWIATLO///////////////////////////
        Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -1.0f);
        //Vector3f kierunek_swiatla_sto  = new Vector3f(-4.0f, -5.0f, -1.0f);
        Color3f kolor_swiatla_tla      = new Color3f(0.3f, 0.2f, 0.91f);
        Color3f kolor_swiatla_kier     = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f kolor_swiatla_pnkt     = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f kolor_swiatla_sto      = new Color3f(0.0f, 0.0f, 1.0f);
        AmbientLight swiatlo_tla = new AmbientLight(kolor_swiatla_tla);
        DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
        PointLight swiatlo_pnkt = new PointLight(kolor_swiatla_pnkt, new Point3f(-0.5f,0.5f,0.5f), new Point3f(0.1f,0.1f,0.1f));
        SpotLight swiatlo_sto = new SpotLight(kolor_swiatla_sto, new Point3f(0.5f, 0.5f, 0.5f), new Point3f(0.01f,0.01f,0.01f),
                new Vector3f(-1.0f, -1.0f, -1.0f), (float)(Math.PI), 100);

        swiatlo_tla.setInfluencingBounds(obszar_ogr);
        swiatlo_kier.setInfluencingBounds(obszar_ogr);
        swiatlo_pnkt.setInfluencingBounds(obszar_ogr);
        swiatlo_sto.setInfluencingBounds(obszar_ogr);
        /////////////////////////////////////////////////////////////
        ///////////////////TRANSFORMACJE////////////////////////////
        Transform3D  tmp_rot = new Transform3D();
        tmp_rot.rotZ(Math.PI/2);
        t3DPodstawki.set(new Vector3f(0.0f,-0.35f,0.0f));
        t3DStojaka.set(new Vector3f(0.0f,-0.15f,0.0f));
        t3dRamienia1.setTranslation(new Vector3f(0.2f,0f,0.0f));
        tmp_rot.rotZ(+Math.PI/2);
        t3dRamienia1.mul(tmp_rot);
        t3dRamienia2.setTranslation(new Vector3f(0.2f,0f,0.0f));
        tmp_rot.rotZ(+Math.PI/2);
        t3dRamienia2.mul(tmp_rot);

        Cylinder podstawka = new Cylinder(1f,0.001f,wyglad_walca);
        Cylinder stojak = new Cylinder(0.02f,0.4f);
        Cylinder ramie1 = new Cylinder(0.02f,0.4f);
        Cylinder ramie2 = new Cylinder(0.02f,0.4f);

        t3Dstawu1.set(new Vector3f(0.0f, 0.2f,0.0f));
        t3Dstawu2.set(new Vector3f(0f, -0.2f,0.0f));

        tranGroupStawu1.setTransform(t3Dstawu1);
        tranGroupStawu2.setTransform((t3Dstawu2));

        //wezel_scena.addChild(tranGroupStawu);

        TransformGroup obrot_animacja = new TransformGroup();
        obrot_animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        wezel_scena.addChild(obrot_animacja);

        tranGroupStojaka.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawu1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawu2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupRamienia1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupRamienia2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //tranGroupRamienia1.setTransform(t3dRamienia1);
        //tranGroupRamienia2.setTransform(t3dRamienia2);
        //tranGroupRamienia.addChild(ramie1);


        tranGroupRamienia1.setTransform(t3Dstawu1);
        tranGroupStojaka.addChild(tranGroupStawu1);
        tranGroupStawu1.addChild(tranGroupRamienia1);

        tranGroupRamienia1.addChild(tranGroupStawu2);

        tranGroupRamienia2.setTransform(t3Dstawu2);
        tranGroupStawu2.addChild(tranGroupRamienia2);

        tranGroupPodstawki.setTransform(t3DPodstawki);
        tranGroupStojaka.setTransform(t3DStojaka);
        tranGroupRamienia1.setTransform(t3dRamienia1);
        tranGroupRamienia2.setTransform(t3dRamienia2);

        wezel_scena.addChild(tranGroupPodstawki);
        wezel_scena.addChild(swiatlo_tla);
        wezel_scena.addChild(swiatlo_kier);
        wezel_scena.addChild(swiatlo_pnkt);
        wezel_scena.addChild(swiatlo_sto);
        tranGroupPodstawki.addChild(podstawka);
        tranGroupStojaka.addChild(stojak);
        tranGroupRamienia1.addChild(ramie1);
        tranGroupStawu1.addChild(staw1);

        tranGroupRamienia2.addChild(ramie2);
        tranGroupStawu2.addChild(staw2);

        obrot_animacja.addChild(tranGroupStojaka);
        //obrot_animacja.addChild(tranGroupStawu);
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
            case KeyEvent.VK_E -> przyciskE = true;
            case KeyEvent.VK_R -> przyciskR = true;

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
            case KeyEvent.VK_E -> przyciskE = false;
            case KeyEvent.VK_R -> przyciskR = false;
        }
    }
    public void obrot() {
        Transform3D akcja = new Transform3D();
        if (przyciskA) {
            akcja.rotY(-Math.PI / 90);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (przyciskD) {
            akcja.rotY(Math.PI / 90);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (przyciskW) {
            akcja.rotZ(Math.PI / 90);
            t3Dstawu1.mul(akcja);
            tranGroupStawu1.setTransform(t3Dstawu1);
        }
        if (przyciskS) {
            akcja.rotZ(-Math.PI / 90);
            t3Dstawu1.mul(akcja);
            tranGroupStawu1.setTransform(t3Dstawu1);
        }
        if (przyciskE) {
            akcja.rotZ(-Math.PI / 90);
            t3Dstawu2.mul(akcja);
            tranGroupStawu2.setTransform(t3Dstawu2);
        }
        if (przyciskR) {
            akcja.rotZ(Math.PI / 90);
            t3Dstawu2.mul(akcja);
            tranGroupStawu2.setTransform(t3Dstawu2);
        }

        }
    }
