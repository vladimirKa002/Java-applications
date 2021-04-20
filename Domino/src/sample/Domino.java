package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Domino{
    private boolean b = true;
    private int x = 0;
    private int y = 0;
    private String namePic;
    private Class<?> rrr = this.getClass();
    private InputStream input;
    private Image image;
    private ImageView imageView;
    private boolean used = true;
    ArrayList<Domino> yourDominos;
    HBox yourDomino;
    Domino d = this;
    Domino(int x,int y, ArrayList<Domino> a, HBox ddad){
        yourDomino = ddad;
        yourDominos = a;
        this.x = x;
        this.y = y;
        namePic = new String("images/" + x + y + ".png");
        input = rrr.getResourceAsStream(namePic);
        image = new Image(input);
        imageView = new ImageView(image);
        getPic().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (Main.choice != d & getUse()) {
                    if (Main.choice != null) Main.choice.changeSize();
                    changeSize();
                    Main.choice = d;
                }
                if (Main.choice !=null & Main.bord1 == d){
                    if (Main.bord1.getVal()[0] == Main.choice.getVal()[0] | Main.bord1.getVal()[0] == Main.choice.getVal()[1]) {
                        if (Main.bord1.getVal()[0] == Main.choice.getVal()[0]){
                            Main.choice.rotate();
                        }
                        Main.choice.changeSize();
                        if (Main.first) {
                            Main.bord2 = Main.bord1;
                            Main.first = false;
                        }
                        Main.bord1 = Main.choice;
                        Main.choice.setUsed();
                        Main.YoAm--;
                        Main.gameField.getChildren().add(0, Main.choice.getPic());
                        yourDomino.getChildren().remove(Main.choice.getPic());
                        Main.choice=null;
                        Main.setTexts();

                        if (Main.YoAm ==0) {
                            Main.Winner(0);
                            return;
                        }

                        if (Main.first) {
                            int dn = 0;
                            int max=0;
                            System.out.println(Main.opponDominos.size());
                            for (int i=0;i<Main.opponDominos.size();i++){
                                if (!Main.opponDominos.get(i).getUse()) {
                                    int y = Main.opponDominos.get(i).getVal()[0] + Main.opponDominos.get(i).getVal()[0];
                                    if (y > max) {
                                        dn = i;
                                        max = y;
                                    }
                                }
                            }
                            Main.bord1 = Main.opponDominos.get(dn);
                            Main.gameField.getChildren().add(0, Main.opponDominos.get(dn).getPic());
                            Main.opponDominos.get(dn).setUsed();
                            Main.OpAm--;
                        }
                        else {
                            Main.oppTurn();
                        }

                        if (Main.OpAm ==0)  {
                            Main.Winner(1);
                            return;
                        }

                        Main.setTexts();
                        Main.turnOppTXT.setText("");
                        Main.turnYourTXT.setText("Ваш ход...");

                        Main.checkYOURDominos();

                        if (Main.passOpp & Main.pass){
                            Main.Winner(-1);
                            return;
                        }
                    }
                }
                else if (Main.choice !=null & Main.bord2 == d){
                    if (Main.bord2.getVal()[1] == Main.choice.getVal()[0] | Main.bord2.getVal()[1] == Main.choice.getVal()[1]) {
                        if (Main.bord2.getVal()[1] == Main.choice.getVal()[1]){
                            Main.choice.rotate();
                        }
                        Main.bord2 = Main.choice;
                        Main.gameField.getChildren().add(Main.choice.getPic());
                        yourDomino.getChildren().remove(Main.choice.getPic());
                        Main.choice.setUsed();
                        Main.YoAm--;
                        Main.setTexts();
                        Main.turnOppTXT.setText("Ход соперника...");
                        Main.turnYourTXT.setText("");
                        Main.choice.changeSize();
                        Main.choice=null;
                        if (Main.YoAm ==0) {
                            Main.Winner(0);
                            return;
                        }
                        Main.oppTurn();

                        if (Main.OpAm ==0)  {
                            Main.Winner(1);
                            return;
                        }
                        Main.setTexts();
                        Main.turnOppTXT.setText("");
                        Main.turnYourTXT.setText("Ваш ход...");

                        Main.checkYOURDominos();

                        if (Main.passOpp & Main.pass){
                            Main.Winner(-1);
                            return;
                        }
                    }
                }
            }
        });
    }

    Domino(Domino dom){
        this.x = dom.x;
        this.y = dom.y;
        namePic = new String("images/" + x + y + ".png");
        input = rrr.getResourceAsStream(namePic);
        image = new Image(input);
        imageView = new ImageView(image);
    }

    public int[] getVal(){
        int a[] = {x,y};
        return a;
    }

    public ImageView getPic(){
        return imageView;
    }

    public Image getImg(){
        return image;
    }

    public void changeSize(){
        if (b) {
            input = rrr.getResourceAsStream(namePic);
            image = new Image(input, 132,60, false,true);
            imageView.setImage(image);
            b = false;
        }
        else {
            input = rrr.getResourceAsStream(namePic);
            image = new Image(input);
            imageView.setImage(image);
            b = true;
        }
    }

    public boolean getUse(){
        return used;
    }
    public void setUsed(){
        used = false;
    }

    public void rotate(){
        imageView.getTransforms().add(new Rotate(180, 55, 25));
        int t = x;
        x=y;
        y=t;
    }
}
