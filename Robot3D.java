package sample;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
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
import java.util.ArrayList;


public class Robot3D extends JFrame implements ActionListener, KeyListener{

    /////deklarowanie zmiennych///////
    Integer koniecNagrywania = null;
    boolean czyOdtwarzanie = false;

    private boolean przyciskA;
    private boolean przyciskD;
    private boolean przyciskW;
    private boolean przyciskS;
    private boolean przyciskE;
    private boolean przyciskR;
    private boolean przyciskUP;
    private boolean przyciskDOWN;
    private boolean przyciskLEFT;
    private boolean przyciskRIGHT;
    private boolean przycisk1;
    private boolean przycisk2;
    private boolean przyciskM;
    private boolean przyciskN;
    private int a = 0;

    TransformGroup obrot_animacja = new TransformGroup();
    TransformGroup tranGroupPodstawki = new TransformGroup();
    TransformGroup tranGroupStojaka = new TransformGroup();
    TransformGroup tranGroupStawu1 = new TransformGroup();
    TransformGroup tranGroupRamienia1 = new TransformGroup();
    TransformGroup tranGroupStawu2 = new TransformGroup();
    TransformGroup tranGroupRamienia2 = new TransformGroup();
    TransformGroup tranGroupStawu3 = new TransformGroup();
    TransformGroup tranGroupRamienia3 = new TransformGroup();
    TransformGroup tranGroupDloni = new TransformGroup();
    TransformGroup tranGroupStawuPalca1 = new TransformGroup();
    TransformGroup tranGroupPalca1 = new TransformGroup();
    TransformGroup tranGroupStawuPalca2 = new TransformGroup();
    TransformGroup tranGroupPalca2 = new TransformGroup();
    TransformGroup tranGroupStawuPalca3 = new TransformGroup();
    TransformGroup tranGroupPalca3 = new TransformGroup();
    TransformGroup tranGroupStawuPalca4 = new TransformGroup();
    TransformGroup tranGroupPalca4 = new TransformGroup();

    Transform3D t3DPodstawki = new Transform3D();
    Transform3D t3DStojaka = new Transform3D();
    Transform3D t3dRamienia1 = new Transform3D();
    Transform3D t3Dstawu1 = new Transform3D();
    Transform3D t3dRamienia2 = new Transform3D();
    Transform3D t3Dstawu2 = new Transform3D();
    Transform3D t3dRamienia3 = new Transform3D();
    Transform3D t3Dstawu3 = new Transform3D();
    Transform3D t3Ddloni = new Transform3D();
    Transform3D t3dPalca1 = new Transform3D();
    Transform3D t3DstawuPalca1 = new Transform3D();
    Transform3D t3dPalca2 = new Transform3D();
    Transform3D t3DstawuPalca2 = new Transform3D();
    Transform3D t3dPalca3 = new Transform3D();
    Transform3D t3DstawuPalca3 = new Transform3D();
    Transform3D t3dPalca4 = new Transform3D();
    Transform3D t3DstawuPalca4 = new Transform3D();

    ArrayList wykonaneRuchy = new ArrayList();

    private JFrame ref_okno;
    private final JButton[] przyciski;

    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3D = new Canvas3D(config);
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

    private class ObslugaPrzycisku implements ActionListener{

        private final JFrame ref_okno;
        BranchGroup scena;

        ObslugaPrzycisku(JFrame okno, BranchGroup scena ){
            ref_okno = okno;
            this.scena = scena;
        }

        public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton)e.getSource();
            if(bt==przyciski[0])
            {
                JOptionPane.showMessageDialog(ref_okno, "Kamera zostanie przywrócona do punktu początkowego");
                UstawienieKamery(scena,false);
            }
            if(bt==przyciski[4])
            {
                JOptionPane.showMessageDialog(ref_okno, "INSTRUKCJA \n" +
                        " a-d \n" +
                        "w-s \n" +
                        "e-r \n" +
                        "strzałki do poruszania ręki \n" +
                        "1-2 do obracania ręki \n" +
                        "m-n do chwytania palcami");
            }
        }

    }

    private void UstawienieKamery(BranchGroup scena, boolean dodaj)
    {
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
        if (dodaj) {
            simpleU.addBranchGraph(scena);
        }
    }
    


    Robot3D(){
        //////////DODAWANIE ŚWIATA I INNE GŁUPOTY/////////////
        super("Projekt - ramie robota 3D by Michał Dramiński");

        BranchGroup scena = utworzScene();

        przyciski = new JButton[5];
        przyciski[0] = new JButton("Reset kamery");
        przyciski[0].addActionListener(new ObslugaPrzycisku(this, scena));
        przyciski[1] = new JButton("Rozpocznij nagrywanie");
        przyciski[1].addActionListener(this);
        przyciski[2] = new JButton("Zakończ nagrywanie");
        przyciski[2].addActionListener(this);
        przyciski[3] = new JButton("Odtwórz nagrywanie");
        przyciski[3].addActionListener(this);
        przyciski[4] = new JButton("Pokaż instrukcję");
        przyciski[4].addActionListener(new ObslugaPrzycisku(this, scena));

        JPanel panelPrzyciski = new JPanel(new FlowLayout());
        panelPrzyciski.add(przyciski[0]);
        panelPrzyciski.add(przyciski[1]);
        panelPrzyciski.add(przyciski[2]);
        panelPrzyciski.add(przyciski[3]);
        panelPrzyciski.add(przyciski[4]);
        Container content = getContentPane();
        content.add(panelPrzyciski,BorderLayout.SOUTH);

        canvas3D.setPreferredSize(new Dimension(800,600));

        canvas3D.addKeyListener(this);
        add(canvas3D);
        pack();
        setVisible(true);


        scena.compile();

        UstawienieKamery(scena,true);
    }

    Appearance getApperance(String modul) {
        Appearance app = new Appearance();
        switch (modul) {
            case "podstawka":
                Texture tex = new TextureLoader("obrazki/cegly.gif", this).getTexture();
                app.setTexture(tex);
                break;
            case "ramie":
                tex = new TextureLoader("obrazki/stal.gif", this).getTexture();
                app.setTexture(tex);
                break;
        }

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texAttr);

        return app;
    }

    BranchGroup utworzScene(){

        BranchGroup wezel_scena = new BranchGroup();

        ////////////////////MATERIALY////////////////////
        ColoringAttributes cattr = new ColoringAttributes();
        cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        cattr.setColor(new Color3f(1f,0f,0f));

        Material material_kuli = new Material(new Color3f(0f, 0.5f,0f), new Color3f(0.3f,0.147f,0.0f),
                new Color3f(0f, 0.6f, 0.f), new Color3f(1.0f, 1.0f, 1.0f), 20f);
        Appearance wyglad_kuli = new Appearance();

        wyglad_kuli.setMaterial(material_kuli);
        wyglad_kuli.setColoringAttributes(cattr);

        Material material_walca = new Material(new Color3f(0.5f, 1f,0.5f), new Color3f(0.5f,1f,0.5f),
                new Color3f(0.5f, 1f, 0.5f), new Color3f(0.2f, 1f, 0.2f), 20.0f);

        Appearance wyglad_walca = new Appearance();
        wyglad_walca.setMaterial(material_walca);

        ///////////////////////////SWIATLO///////////////////////////
        BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);
        Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -1.0f);
        Color3f kolor_swiatla_tla      = new Color3f(0.3f, 0.2f, 0.91f);
        Color3f kolor_swiatla_kier     = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f kolor_swiatla_pnkt     = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f kolor_swiatla_sto      = new Color3f(0.0f, 0.0f, 1.0f);
        AmbientLight swiatlo_tla = new AmbientLight(kolor_swiatla_tla);
        DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
        PointLight swiatlo_pnkt = new PointLight(kolor_swiatla_pnkt, new Point3f(-0.5f,0.5f,0.5f), new Point3f(0.1f,0.1f,0.1f));
        SpotLight swiatlo_sto = new SpotLight(kolor_swiatla_sto, new Point3f(0.5f, 0.5f, 0.5f), new Point3f(0.01f,0.01f,0.01f),
                new Vector3f(-1.0f, -1.0f, -1.0f), (float)(Math.PI), 100);

        //////////////////DODAWANIE ŚWIATŁA DO SCENY////////////////
        swiatlo_tla.setInfluencingBounds(obszar_ogr);
        swiatlo_kier.setInfluencingBounds(obszar_ogr);
        swiatlo_pnkt.setInfluencingBounds(obszar_ogr);
        swiatlo_sto.setInfluencingBounds(obszar_ogr);

        ////////////////////OBIEKTY/////////////////////////////
        Cylinder podstawka = new Cylinder(1f,0.001f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("podstawka"));
        Cylinder stojak = new Cylinder(0.02f,0.4f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere staw1 = new Sphere(0.03f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramie1 = new Cylinder(0.02f,0.4f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere staw2 = new Sphere(0.03f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramie2 = new Cylinder(0.02f,0.4f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere staw3 = new Sphere(0.03f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramie3 = new Cylinder(0.02f,0.1f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Box dlon = new Box(0.03f,0.004f,0.03f, wyglad_kuli);
        Sphere stawPalca1 = new Sphere(0.005f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramiePalca1 = new Cylinder(0.004f,0.05f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere stawPalca2 = new Sphere(0.005f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramiePalca2 = new Cylinder(0.004f,0.05f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere stawPalca3 = new Sphere(0.005f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramiePalca3 = new Cylinder(0.004f,0.05f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));
        Sphere stawPalca4 = new Sphere(0.005f,Sphere.GENERATE_NORMALS,80,wyglad_kuli);
        Cylinder ramiePalca4 = new Cylinder(0.004f,0.05f, Cylinder.GENERATE_TEXTURE_COORDS, getApperance("ramie"));

        ///////////////////TRANSLACJE////////////////////////////
        Transform3D  tmp_rot = new Transform3D();
        tmp_rot.rotZ(Math.PI/2);
        t3DPodstawki.set(new Vector3f(0.0f,-0.35f,0.0f));
        t3DStojaka.set(new Vector3f(0.0f,-0.15f,0.0f));
        t3dRamienia1.setTranslation(new Vector3f(0.2f,0f,0.0f));
        t3Dstawu1.set(new Vector3f(0.0f, 0.2f,0.0f));
        t3dRamienia2.setTranslation(new Vector3f(0.2f,0f,0.0f));
        t3Dstawu2.set(new Vector3f(0f, -0.2f,0.0f));
        t3dRamienia3.setTranslation(new Vector3f(-0.05f,0f,0.0f));
        t3Dstawu3.set(new Vector3f(0f, -0.2f,0.0f));
        t3Ddloni.setTranslation(new Vector3f(0f,0.052f,0f));

        t3DstawuPalca1.set(new Vector3f(-0.015f, 0.005f,-0.015f));
        t3dPalca1.setTranslation(new Vector3f(0f,0.025f,0.0f));
        t3DstawuPalca2.set(new Vector3f(-0.015f, 0.005f,0.015f));
        t3dPalca2.setTranslation(new Vector3f(0f,0.025f,0.0f));
        t3DstawuPalca3.set(new Vector3f(0.015f, 0.005f,-0.015f));
        t3dPalca3.setTranslation(new Vector3f(0f,0.025f,0.0f));
        t3DstawuPalca4.set(new Vector3f(0.015f, 0.005f,0.015f));
        t3dPalca4.setTranslation(new Vector3f(0f,0.025f,0.0f));

        ////////////ROTACJE//////////////////
        t3dRamienia1.mul(tmp_rot);
        t3dRamienia2.mul(tmp_rot);
        t3dRamienia3.mul(tmp_rot);

        ///////////POZWOLENIA NA TRANSFORMACJE/////////////
        obrot_animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStojaka.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawu1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawu2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawu3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupDloni.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        tranGroupStawuPalca1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawuPalca2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawuPalca3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tranGroupStawuPalca4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        //////////////////PODPINANIE 3D TRANSFORM///////////////////
        tranGroupPodstawki.setTransform(t3DPodstawki);
        tranGroupStojaka.setTransform(t3DStojaka);
        tranGroupStawu1.setTransform(t3Dstawu1);
        tranGroupRamienia1.setTransform(t3dRamienia1);
        tranGroupStawu2.setTransform((t3Dstawu2));
        tranGroupRamienia2.setTransform(t3dRamienia2);
        tranGroupStawu3.setTransform((t3Dstawu3));
        tranGroupRamienia3.setTransform(t3dRamienia3);
        tranGroupDloni.setTransform(t3Ddloni);

        tranGroupStawuPalca1.setTransform((t3DstawuPalca1));
        tranGroupPalca1.setTransform(t3dPalca1);
        tranGroupStawuPalca2.setTransform((t3DstawuPalca2));
        tranGroupPalca2.setTransform(t3dPalca2);
        tranGroupStawuPalca3.setTransform((t3DstawuPalca3));
        tranGroupPalca3.setTransform(t3dPalca3);
        tranGroupStawuPalca4.setTransform((t3DstawuPalca4));
        tranGroupPalca4.setTransform(t3dPalca4);


        /////////////DZIEDZICZENIE//////////
        wezel_scena.addChild(obrot_animacja);
        obrot_animacja.addChild(tranGroupStojaka);
        tranGroupStojaka.addChild(tranGroupStawu1);
        tranGroupStawu1.addChild(tranGroupRamienia1);
        tranGroupRamienia1.addChild(tranGroupStawu2);
        tranGroupStawu2.addChild(tranGroupRamienia2);
        tranGroupRamienia2.addChild(tranGroupStawu3);
        tranGroupStawu3.addChild(tranGroupRamienia3);
        tranGroupRamienia3.addChild(tranGroupDloni);
        tranGroupDloni.addChild(tranGroupStawuPalca1);
        tranGroupDloni.addChild(tranGroupStawuPalca2);
        tranGroupDloni.addChild(tranGroupStawuPalca3);
        tranGroupDloni.addChild(tranGroupStawuPalca4);
        tranGroupStawuPalca1.addChild(tranGroupPalca1);
        tranGroupStawuPalca2.addChild(tranGroupPalca2);
        tranGroupStawuPalca3.addChild(tranGroupPalca3);
        tranGroupStawuPalca4.addChild(tranGroupPalca4);

        ///////////////DODAWANIE OBIEKTÓW//////////////////
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
        tranGroupRamienia3.addChild(ramie3);
        tranGroupStawu3.addChild(staw3);
        tranGroupDloni.addChild(dlon);
        tranGroupPalca1.addChild(ramiePalca1);
        tranGroupStawuPalca1.addChild(stawPalca1);
        tranGroupPalca2.addChild(ramiePalca2);
        tranGroupStawuPalca2.addChild(stawPalca2);
        tranGroupPalca3.addChild(ramiePalca3);
        tranGroupStawuPalca3.addChild(stawPalca3);
        tranGroupPalca4.addChild(ramiePalca4);
        tranGroupStawuPalca4.addChild(stawPalca4);

        return wezel_scena;

    }


    public static void main(String[] args){
        new Robot3D();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton)e.getSource();



            if(bt==przyciski[1]) //poczatek nagrywania
            {
                JOptionPane.showMessageDialog(ref_okno, "Początek nagrywania");

                wykonaneRuchy.clear();
                koniecNagrywania = null;
            }
            if(bt==przyciski[2])//koniec nagrywania
            {
                koniecNagrywania = wykonaneRuchy.size();
                JOptionPane.showMessageDialog(ref_okno, "Koniec nagrywania");
            }
            if(bt==przyciski[3])//odtwarzanie
            {
                if (koniecNagrywania != null ) {
                    JOptionPane.showMessageDialog(ref_okno, "Nagrany ruch zostanie odtworzony");

                    czyOdtwarzanie = true;
                    for (int i = wykonaneRuchy.size()-1; i >=0; i--) {
                        keyPressed((KeyEvent) wykonaneRuchy.get(i));
                        keyReleased((KeyEvent)wykonaneRuchy.get(i));
                    }
                    czyOdtwarzanie = false;

                    for(int i=0; i < koniecNagrywania; ++ i ) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        keyPressed((KeyEvent) wykonaneRuchy.get(i));
                        keyReleased((KeyEvent) wykonaneRuchy.get(i));
                    }
                    while( wykonaneRuchy.size() > koniecNagrywania) {
                        wykonaneRuchy.remove(wykonaneRuchy.size()-1);
                    }
                }
            }
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
            case KeyEvent.VK_UP -> przyciskUP = true;
            case KeyEvent.VK_DOWN -> przyciskDOWN = true;
            case KeyEvent.VK_LEFT -> przyciskLEFT = true;
            case KeyEvent.VK_RIGHT -> przyciskRIGHT = true;
            case KeyEvent.VK_1 -> przycisk1 = true;
            case KeyEvent.VK_2 -> przycisk2 = true;
            case KeyEvent.VK_M -> przyciskM = true;
            case KeyEvent.VK_N -> przyciskN = true;

        }
        wykonaneRuchy.add(e);
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
            case KeyEvent.VK_UP -> przyciskUP = false;
            case KeyEvent.VK_DOWN ->  przyciskDOWN= false;
            case KeyEvent.VK_LEFT -> przyciskLEFT = false;
            case KeyEvent.VK_RIGHT -> przyciskRIGHT = false;
            case KeyEvent.VK_1 -> przycisk1 = false;
            case KeyEvent.VK_2 -> przycisk2 = false;
            case KeyEvent.VK_M -> przyciskM = false;
            case KeyEvent.VK_N -> przyciskN = false;
        }
    }
    public void obrot() {
        Transform3D akcja = new Transform3D();
        Transform3D akcja2 = new Transform3D();
        if (((przyciskA)&&(!czyOdtwarzanie)) || ((przyciskD) &&(czyOdtwarzanie)))
        {
            akcja.rotY(-Math.PI / 90);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (((przyciskD)&&(!czyOdtwarzanie)) || ((przyciskA) &&(czyOdtwarzanie)))
        {
            akcja.rotY(Math.PI / 90);
            t3DStojaka.mul(akcja);
            tranGroupStojaka.setTransform(t3DStojaka);
        }
        if (((przyciskW)&&(!czyOdtwarzanie)) || ((przyciskS) &&(czyOdtwarzanie))) {
            akcja.rotZ(Math.PI / 90);
            t3Dstawu1.mul(akcja);
            tranGroupStawu1.setTransform(t3Dstawu1);
        }
        if (((przyciskS)&&(!czyOdtwarzanie)) || ((przyciskW) &&(czyOdtwarzanie))) {
            akcja.rotZ(-Math.PI / 90);
            t3Dstawu1.mul(akcja);
            tranGroupStawu1.setTransform(t3Dstawu1);
        }
        if (((przyciskE)&&(!czyOdtwarzanie)) || ((przyciskR) &&(czyOdtwarzanie))) {
            akcja.rotZ(-Math.PI / 90);
            t3Dstawu2.mul(akcja);
            tranGroupStawu2.setTransform(t3Dstawu2);
        }
        if (((przyciskR)&&(!czyOdtwarzanie)) || ((przyciskE) &&(czyOdtwarzanie))) {
            akcja.rotZ(Math.PI / 90);
            t3Dstawu2.mul(akcja);
            tranGroupStawu2.setTransform(t3Dstawu2);
        }
        if (((przyciskUP)&&(!czyOdtwarzanie)) || ((przyciskDOWN) &&(czyOdtwarzanie))) {
            akcja.rotZ(Math.PI / 90);
            t3Dstawu3.mul(akcja);
            tranGroupStawu3.setTransform(t3Dstawu3);
        }
        if (((przyciskDOWN)&&(!czyOdtwarzanie)) || ((przyciskUP) &&(czyOdtwarzanie))) {
            akcja.rotZ(-Math.PI / 90);
            t3Dstawu3.mul(akcja);
            tranGroupStawu3.setTransform(t3Dstawu3);
        }
        if (((przyciskLEFT)&&(!czyOdtwarzanie)) || ((przyciskRIGHT) &&(czyOdtwarzanie))) {
            akcja.rotY(Math.PI / 90);
            t3Dstawu3.mul(akcja);
            tranGroupStawu3.setTransform(t3Dstawu3);
        }
        if (((przyciskRIGHT)&&(!czyOdtwarzanie)) || ((przyciskLEFT) &&(czyOdtwarzanie))) {
            akcja.rotY(-Math.PI / 90);
            t3Dstawu3.mul(akcja);
            tranGroupStawu3.setTransform(t3Dstawu3);
        }
        if (((przycisk1)&&(!czyOdtwarzanie)) || ((przycisk2) &&(czyOdtwarzanie))) {
            akcja.rotY(Math.PI / 180);
            t3Ddloni.mul(akcja);
            tranGroupDloni.setTransform(t3Ddloni);
        }
        if (((przycisk2)&&(!czyOdtwarzanie)) || ((przycisk1) &&(czyOdtwarzanie))) {
            akcja.rotY(-Math.PI / 180);
            t3Ddloni.mul(akcja);
            tranGroupDloni.setTransform(t3Ddloni);
        }
        if (przyciskM && (a<52)) {
            akcja.rotX(Math.PI / 180);
            akcja2.rotZ(-Math.PI / 180);
            t3DstawuPalca1.mul(akcja);
            t3DstawuPalca1.mul(akcja2);
            tranGroupStawuPalca1.setTransform(t3DstawuPalca1);
            a++;
        }
        if (przyciskM && (a<52)) {
            akcja.rotX(-Math.PI / 180);
            akcja2.rotZ(-Math.PI / 180);
            t3DstawuPalca2.mul(akcja);
            t3DstawuPalca2.mul(akcja2);
            tranGroupStawuPalca2.setTransform(t3DstawuPalca2);
            a++;
        }
        if (przyciskM && (a<52)) {
            akcja.rotX(Math.PI / 180);
            akcja2.rotZ(Math.PI / 180);
            t3DstawuPalca3.mul(akcja);
            t3DstawuPalca3.mul(akcja2);
            tranGroupStawuPalca3.setTransform(t3DstawuPalca3);
            a++;
        }
        if (przyciskM && (a<52)) {
            akcja.rotX(-Math.PI / 180);
            akcja2.rotZ(Math.PI / 180);
            t3DstawuPalca4.mul(akcja);
            t3DstawuPalca4.mul(akcja2);
            tranGroupStawuPalca4.setTransform(t3DstawuPalca4);
            a++;
        }
        if (przyciskN && (a>-52)) {
            akcja.rotX(-Math.PI / 180);
            akcja2.rotZ(Math.PI / 180);
            t3DstawuPalca1.mul(akcja);
            t3DstawuPalca1.mul(akcja2);
            tranGroupStawuPalca1.setTransform(t3DstawuPalca1);
            a--;
        }
        if (przyciskN && (a>-52)) {
            akcja.rotX(Math.PI / 180);
            akcja2.rotZ(Math.PI / 180);
            t3DstawuPalca2.mul(akcja);
            t3DstawuPalca2.mul(akcja2);
            tranGroupStawuPalca2.setTransform(t3DstawuPalca2);
            a--;
        }
        if (przyciskN && (a>-52)) {
            akcja.rotX(-Math.PI / 180);
            akcja2.rotZ(-Math.PI / 180);
            t3DstawuPalca3.mul(akcja);
            t3DstawuPalca3.mul(akcja2);
            tranGroupStawuPalca3.setTransform(t3DstawuPalca3);
            a--;
        }
        if (przyciskN && (a>-52))  {
            akcja.rotX(Math.PI / 180);
            akcja2.rotZ(-Math.PI / 180);
            t3DstawuPalca4.mul(akcja);
            t3DstawuPalca4.mul(akcja2);
            tranGroupStawuPalca4.setTransform(t3DstawuPalca4);
            a--;
        }
    }
}