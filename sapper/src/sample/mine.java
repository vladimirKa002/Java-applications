package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class mine {
    private boolean flagged = false;
    private Image imag;
    public static int openedCount = 0;
    private boolean opened = false;
    private Class<?> rrr = this.getClass();
    private InputStream input = rrr.getResourceAsStream("images/field.png");
    private Image image = new Image(input);
    private ImageView imageView = new ImageView(image);

    private InputStream inputFl = rrr.getResourceAsStream("images/flag.png");
    private Image imageFl = new Image(inputFl);

    private boolean mine;
    private boolean ch = false;
    mine (boolean b){
        mine = b;
        InputStream inpu;
        if (mine) {
            inpu = rrr.getResourceAsStream("images/mine.png");
            imag = new Image(inpu);
        }
    }

    public boolean getFl(){
        return flagged;
    }
    public void setFl(boolean b){
        flagged = b;
        if (flagged) imageView.setImage(imageFl);
        else imageView.setImage(image);
    }
    public void setPicSP(){
        imageView.setImage(imag);
        opened = true;
        openedCount++;
    }

    public boolean getCh(){
        return ch;
    }
    public boolean getMine(){
        return mine;
    }
    public boolean getOp(){
        return opened;
    }
    public ImageView getPic(){
        return imageView;
    }
    public void setImages (int aa) {
        if (!(aa==0)) ch=true;
        InputStream inpu;
        if (aa==0) {
            inpu = rrr.getResourceAsStream("images/empty.png");
            imag = new Image(inpu);
        }
        else if (aa==1){
            inpu = rrr.getResourceAsStream("images/f1.png");
            imag = new Image(inpu);

        }
        else if (aa==2){
            inpu = rrr.getResourceAsStream("images/f2.png");
            imag = new Image(inpu);

        }
        else if (aa==3){
            inpu = rrr.getResourceAsStream("images/f3.png");
            imag = new Image(inpu);

        }
        else if (aa==4){
            inpu = rrr.getResourceAsStream("images/f4.png");
            imag = new Image(inpu);

        }
        else if (aa==5){
            inpu = rrr.getResourceAsStream("images/f5.png");
            imag = new Image(inpu);

        }
        else if (aa==6){
            inpu = rrr.getResourceAsStream("images/f6.png");
            imag = new Image(inpu);

        }
        else if (aa==7){
            inpu = rrr.getResourceAsStream("images/f7.png");
            imag = new Image(inpu);

        }
        else if (aa==8){
            inpu = rrr.getResourceAsStream("images/f8.png");
            imag = new Image(inpu);

        }
    }

    private int i; private int j;
    public void setIJ (int i, int j) {
        this.i=i;
        this.j=j;
    }
    public int [] getIJ () {
        int [] a = {i, j};
        return a;
    }
}
