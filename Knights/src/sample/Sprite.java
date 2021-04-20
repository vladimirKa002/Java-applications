package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

class Sprite {
    double positionX;
    double positionY;
    double angle;
    double r;double alfa;
    double X;
    double Y;
    Random rnd  = new Random(); Image weapon;
    GraphicsContext gc;
    AnimatedImage go;
    double angleHandSpeed = 5;
    double angleHandSpeedInit = 5;
    double angleHand = 0;
    int animIgo=0;
    int animSpeed=5;
    boolean attackBool = false;
    static ArrayList<String> weaponNames= new ArrayList<>();
    static ArrayList<int []> weaponPos= new ArrayList<>();
    static ArrayList<Integer> weaponRect= new ArrayList<>();
    static ArrayList<Double>  weaponbeat= new ArrayList<>();

    Sprite(GraphicsContext gc, String name){
        this.gc = gc;
        go = new Sprite.AnimatedImage();
        Image[] imageArray = new Image[7];
        for (int i = 0; i < 7; i++){
            Class<?> rrr = this.getClass();
            InputStream inputIC = rrr.getResourceAsStream("images/"+name+"/knight" + i + ".png" );
            imageArray[i] = new Image( inputIC);
        }
        go.frames = imageArray;
    }

    void setPosition(double x, double y){
        positionX = positionX+x;
        positionY = positionY+y;
    }

    void setAttack(){
        attackBool=true;
    }

    boolean getAttack(){
        return !attackBool;
    }

    double[] getPos(){
        double[] a = new double[2];
        a[0]=positionX;
        a[1]=positionY;
        return a;
    }

    void setAngle(double an){
        angle= an;
    }

    void rotate(GraphicsContext gc, double angle, double px, double py) {
        angle+=90;
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    class AnimatedImage {
        Image[] frames;

        Image getFrame(int i) {
            return frames[i];
        }
    }

    static void setArmory(int[] a, double d, int b){
        weaponPos.add(a);
        weaponRect.add(b);
        weaponbeat.add(d);
    }
    static void setArmoryNames(ArrayList<String> ar){
        weaponNames = ar;
    }
}
