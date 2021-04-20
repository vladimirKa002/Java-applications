package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;

class Enemy extends Sprite {
    private int [] weaponPos;
    private Knight knight;
    private Class<?> rrr = this.getClass();
    private InputStream inputIC = rrr.getResourceAsStream("images/enemy/knight0.png");
    private Image knightImg = new Image(inputIC);
    private int XY;

    private Rectangle2D getBoundaryOfWeapon() {
        gc.fillRect(X+weapon.getHeight()/2*Math.cos(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,
                Y+weapon.getHeight()/2*Math.sin(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,XY*2,XY*2);
        return new Rectangle2D(X+weapon.getHeight()/2*Math.cos(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,
                Y+weapon.getHeight()/2*Math.sin(Math.toRadians(angle)+Math.toRadians(angleHand))-XY,XY*2,XY*2);
    }

    Enemy(GraphicsContext gc,Knight knight, String weaponName){
        super(gc, "enemy");
        InputStream inputIC3 = rrr.getResourceAsStream("images/enemy/weapon/"+weaponName+".png");
        double beat;
        int index = Sprite.weaponNames.indexOf(weaponName);
        weaponPos= Sprite.weaponPos.get(index);
        beat = Sprite.weaponbeat.get(index);
        angleHandSpeed= beat;
        angleHandSpeedInit= beat;
        weapon = new Image(inputIC3);
        this.knight=knight;
        int k = rnd.nextInt(4);
        if (k==0) {
            positionX=rnd.nextInt(600);positionY=rnd.nextInt(50)-50;
        }
        else if (k==1) {
            positionX = rnd.nextInt(50) - 50;
            positionY = rnd.nextInt(600);
        }
        else if (k==2) {
            positionX = rnd.nextInt(50) + 600;
            positionY = rnd.nextInt(600);
        }
        else if (k==3) {
            positionX = rnd.nextInt(600);
            positionY = rnd.nextInt(50) + 600;
        }
        double we = weaponPos[0]+weaponPos[0]-12 - knightImg.getWidth()/2;
        double he = weaponPos[1]+weapon.getHeight()-5 - knightImg.getHeight()/2-23;
        r = Math.sqrt((we)*(we) +(he)*(he));
        alfa = Math.atan(he/we); // radians
        XY= Sprite.weaponRect.get(Sprite.weaponNames.indexOf(weaponName));
    }

    Rectangle2D getBoundaryOfBody() {
        if (!attackBool) {
            return new Rectangle2D(positionX,positionY,go.getFrame
                    (animIgo/animSpeed).getWidth(),go.getFrame(animIgo/animSpeed).getHeight());
        }
        return new Rectangle2D(positionX,positionY,knightImg.getWidth(),knightImg.getHeight());
    }

    void setAngle(){
        double[] target = knight.getPos();
        double w = target[0]-positionX;
        double h = target[1]-positionY;
        angle = Math.toDegrees(Math.atan(h/w));
        if (w<0) angle-=180;
    }

    void render() {
        gc.save();
        rotate(gc, angle, positionX + knightImg.getWidth() / 2, positionY + knightImg.getHeight() / 2);
        if (!attackBool) {
            gc.drawImage(go.getFrame(animIgo/animSpeed), positionX, positionY);
            animIgo++;
            if(animIgo==go.frames.length*animSpeed) animIgo=0;
        }
        else {
            gc.drawImage(knightImg, positionX, positionY);
            animIgo=0;
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
        if (attackBool) return;
        double radius = 40+XY-27;
        double []target = knight.getPos();
        if (Math.sqrt((positionX-target[0])*(positionX-target[0])+(positionY-target[1])*(positionY-target[1]))<=radius){
            setAttack();
        }
        else setPosition(1.8*Math.cos(Math.toRadians(angle)), 1.8*Math.sin(Math.toRadians(angle)));
    }

    boolean intersects() {
        if (!attackBool) return false;
        if (angleHandSpeed==-10 && -10 <= angleHand && angleHand<=10)
            return knight.getBoundaryOfBody().intersects( this.getBoundaryOfWeapon() );
        return false;
    }
}