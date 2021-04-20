package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.ArrayList;

class Knight extends Sprite{
    private int []weaponPos;
    private double beat;
    private Class<?> rrr = this.getClass();
    private InputStream inputIC = rrr.getResourceAsStream("images/knight/knight0.png");
    private Image knightImg = new Image(inputIC);
    private ArrayList<Image> arsenal = new ArrayList<>();
    private ArrayList<String> arsenalNames = new ArrayList<>();
    private int arsenalNum =0;
    private int XY;

    private Rectangle2D getBoundaryOfWeapon() {
        return new Rectangle2D(X+weapon.getHeight()/2*Math.cos(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,
                Y+weapon.getHeight()/2*Math.sin(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,XY*2,XY*2);
    }

    boolean intersects(Enemy en) {
        Rectangle2D enemy = en.getBoundaryOfBody();
        Rectangle2D weapon = this.getBoundaryOfWeapon();
        if (!attackBool) {
            return false;
        }
        if (angleHandSpeed==-10 && -10 <= angleHand && angleHand<=10){
            return enemy.intersects(weapon);
        }
        return false;
    }

    Knight(GraphicsContext gc){
        super(gc, "knight");
        positionX=rnd.nextInt(200)+200;positionY=rnd.nextInt(200)+200;
        ArrayList <String> ind = new ArrayList<>();
        for (int i =0;i<Sprite.weaponNames.size(); i++){
            ind.add(i+"");
        }
        for (int i = 0; i<3;i++){
            int j =rnd.nextInt(ind.size());
            int h = Integer.parseInt(ind.get(j));
            ind.remove(j);
            arsenalNames.add(Sprite.weaponNames.get(h));
            InputStream inputIC3 = rrr.getResourceAsStream("images/knight/weapons/"+Sprite.weaponNames.get(h)+".png");
            weapon = new Image(inputIC3);
            arsenal.add(weapon);
        }
        int k = Sprite.weaponNames.indexOf(arsenalNames.get(arsenalNum));
        weapon = arsenal.get(arsenalNum);
        weaponPos = Sprite.weaponPos.get(k);
        double we = weaponPos[0]+weaponPos[0]-12- knightImg.getWidth()/2;
        double he = weaponPos[1]+weapon.getHeight()-5 - knightImg.getHeight()/2-25;
        r = Math.sqrt((we)*(we) +(he)*(he));
        alfa = Math.atan(he/we); // radians
        beat = Sprite.weaponbeat.get(k);
        XY = Sprite.weaponRect.get(k);
        angleHandSpeed=beat;
        angleHandSpeedInit=beat;
    }

    Rectangle2D getBoundaryOfBody() {
        if (Main.action==1) return new Rectangle2D(positionX,positionY,go.getFrame
                (animIgo/animSpeed).getWidth(),go.getFrame(animIgo/animSpeed).getHeight());
        return new Rectangle2D(positionX,positionY,knightImg.getWidth(),knightImg.getHeight());
    }

    void render() {
        gc.save();
        rotate(gc, angle, positionX + knightImg.getWidth() / 2, positionY + knightImg.getHeight() / 2);
        if (Main.action==1) {
            gc.drawImage( go.getFrame(animIgo/animSpeed), positionX, positionY);
            animIgo++;
            if(animIgo==go.frames.length*animSpeed) animIgo=0;
        }
        else {
            animIgo=0;
            gc.drawImage(knightImg, positionX, positionY );
        }
        if (attackBool){
            X = positionX+knightImg.getWidth()/2-r*Math.cos(alfa+Math.toRadians(angle));
            Y = positionY+knightImg.getHeight()/2-r*Math.sin(alfa+Math.toRadians(angle));
            rotate(gc, angle+angleHand, X, Y);
            gc.drawImage(weapon, X-weapon.getWidth()+weaponPos[0]-12, Y-weapon.getHeight()-5);
            angleHand+=angleHandSpeed;
            if (angleHand>=40) angleHandSpeed=-10;
            if (angleHand<=-20) {
                angleHandSpeed=angleHandSpeedInit;
                attackBool=false;
                angleHand=0;
            }
        }
        else {
            gc.drawImage(weapon, positionX+weaponPos[0], positionY+weaponPos[1]);
        }
        gc.restore();
    }

    void move() {
        setPosition(4*Math.cos(Math.toRadians(angle)), 4*Math.sin(Math.toRadians(angle)));
    }

    void changeWeapon(){
        arsenalNum++;
        if (arsenalNum>=arsenal.size()) arsenalNum = 0;
        int k = Sprite.weaponNames.indexOf(arsenalNames.get(arsenalNum));
        weaponPos = Sprite.weaponPos.get(k);
        beat = Sprite.weaponbeat.get(k);
        XY = Sprite.weaponRect.get(k);
        weapon = arsenal.get(arsenalNum);
        angleHandSpeed=beat;
        angleHandSpeedInit=beat;
    }
}
