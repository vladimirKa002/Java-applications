package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Main extends Application {
    int MinesAm = 10;
    int H = 9;
    int W = 9;
    int minesC=10;
    Label minesCText;
    VBox minesFieldVer = new VBox(0);
    ArrayList<mine> mines = new ArrayList<mine>(81);
    ArrayList<Integer> integer = new ArrayList<Integer>(81);
    VBox cont1;
    Class<?> rrr = this.getClass();
    InputStream input1;
    InputStream inputSap = rrr.getResourceAsStream("images/sapper.png");
    Image im = new Image (inputSap);
    ImageView imgvSap = new ImageView(im);
    Button toMenu = new Button("В меню");
    Stage st;
    Scene main;
    boolean win = false;
    boolean loss = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        st = primaryStage;
        InputStream inputIC = rrr.getResourceAsStream("images/icon.ico");
        Image imIC = new Image (inputIC);
        primaryStage.getIcons().add(imIC);
        minesFieldVer.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Сапер");
        Button btnstart = new Button("Начать");
        HBox fieldSt = new HBox(10, btnstart); fieldSt.setAlignment(Pos.CENTER);
        VBox mainfield = new VBox(10, imgvSap, fieldSt); mainfield.setAlignment(Pos.CENTER);

        RadioButton easy = new RadioButton("Новичок");
        RadioButton medium = new RadioButton("Любитель");
        RadioButton hard = new RadioButton("Профессионал");

        ToggleGroup group = new ToggleGroup();

        easy.fire();
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);

        easy.setOnAction(event -> {
            H=9; W = 9; MinesAm = 10;
            minesC = MinesAm;
        });
        medium.setOnAction(event -> {
            H=16; W = 16; MinesAm = 40;
            minesC = MinesAm;
        });
        hard.setOnAction(event -> {
            H=16; W = 30; MinesAm = 99;
            minesC = MinesAm;
        });
        HBox choice = new HBox(10, easy, medium, hard);
        choice.setAlignment(Pos.CENTER);
        mainfield.getChildren().add(choice);
        main = new Scene(mainfield, 900, 600);
        primaryStage.setScene(main);
        primaryStage.show();

        minesCText = new Label("Осталось мин: " + minesC);
        cont1 = new VBox(10, minesFieldVer, minesCText);
        cont1.setAlignment(Pos.CENTER);
        Scene gamescn = new Scene(cont1, 1300, 700);
        btnstart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fillField ();
                startgame();
                primaryStage.setScene(gamescn);
            }
        });
    }

    public void startgame (){
        mines.forEach(new Consumer<mine>() {
            @Override
            public void accept(mine mi) {
                    mi.getPic().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            if (!loss & !win & !mi.getOp() & event.getButton() == MouseButton.SECONDARY) {
                                if (mi.getFl()) {
                                    mi.setFl(false);
                                    minesC++;
                                }
                                else {
                                    mi.setFl(true);
                                    minesC--;
                                    if (mine.openedCount==H*W-MinesAm & minesC==0) {
                                        input1 = rrr.getResourceAsStream("images/win.png");
                                        Image img = new Image(input1);
                                        ImageView imgv = new ImageView(img);
                                        cont1.getChildren().remove(1);
                                        cont1.getChildren().add(0, imgv);
                                        cont1.getChildren().add(2, toMenu);
                                        win = true;
                                    }
                                }
                                minesCText.setText("Осталось мин: " + minesC);
                            }
                            else if (!loss & !win & !mi.getOp() & !mi.getFl() & event.getButton() == MouseButton.PRIMARY) {
                                if (mi.getMine()){
                                    for (int i = 0; i<H*W;i++){
                                        mines.get(i).setPicSP();
                                    }
                                    loss = true;
                                    input1 = rrr.getResourceAsStream("images/loss.png");
                                    Image img = new Image(input1);
                                    ImageView imgv = new ImageView(img);
                                    cont1.getChildren().remove(1);
                                    cont1.getChildren().add(0, imgv);
                                    cont1.getChildren().add(2, toMenu);
                                }
                                else if (!mi.getCh()) {
                                    opennear(mi);
                                    if (mine.openedCount==H*W-MinesAm & minesC==0) {
                                        input1 = rrr.getResourceAsStream("images/win.png");
                                        Image img = new Image(input1);
                                        ImageView imgv = new ImageView(img);
                                        cont1.getChildren().remove(1);
                                        cont1.getChildren().add(0, imgv);
                                        cont1.getChildren().add(2, toMenu);
                                        win = true;
                                    }
                                }
                                else {
                                    mi.setPicSP();
                                    if (mine.openedCount==H*W-MinesAm & minesC==0) {
                                        input1 = rrr.getResourceAsStream("images/win.png");
                                        Image img = new Image(input1);
                                        ImageView imgv = new ImageView(img);
                                        cont1.getChildren().remove(1);
                                        cont1.getChildren().add(0, imgv);
                                        cont1.getChildren().add(2, toMenu);
                                        win = true;
                                    }
                                }
                            }
                        }
                    });
            }
        });
        toMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                st.setScene(main);
            }
        });
    }

    int [][] aa;

    public void fillField (){
        minesC=MinesAm;
        minesCText.setText("Осталось мин: " + minesC);
        win = false;
        loss = false;
        if (minesFieldVer.getChildren().size()>0) {
            minesFieldVer.getChildren().remove(0,minesFieldVer.getChildren().size());
            cont1.getChildren().remove(0);
            cont1.getChildren().remove(1);
            cont1.getChildren().add(1, minesCText);
        }
        mine.openedCount=0;
        mines.clear();
        integer.clear();
        Random rnd = new Random();
        for (int i = 0; i<H*W;i++){
            integer.add(i);
        }
        for (int i = 0; i<H*W;i++){
            mines.add(new mine(false));
        }
        for (int i = 0; i<MinesAm;i++){
            int u = rnd.nextInt(integer.size());
            int y = integer.get(u);
            mines.remove(y);
            mines.add(y, new mine(true));
            integer.remove(u);
        }
        int y = 0;
        for (int i =0;i<H;i++){
            HBox line = new HBox(0);
            line.setAlignment(Pos.CENTER);
            for (int j =0;j<W;j++){
                line.getChildren().add(mines.get(y).getPic());
                y++;
            }
            minesFieldVer.getChildren().add(line);
        }
        int [][] u = new int[H][W];
        aa = new int[H][W];
        int p =0;
        for (int i =0;i<H;i++){
            for (int j =0;j<W;j++){
                mines.get(p).setIJ(i, j);
                if (mines.get(p).getMine()) u[i][j]=1;
                p++;
            }
        }
        for (int i =0;i<H;i++){
            for (int j =0;j<W;j++){
                if (u[i][j]==1) {
                    aa[i][j]=-1;
                    continue;
                }
                try {
                    if (u[i+1][j+1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i-1][j-1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i+1][j-1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i-1][j+1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i][j+1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i][j-1]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i-1][j]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
                try {
                    if (u[i+1][j]==1) aa[i][j]++;
                } catch (IndexOutOfBoundsException e){}
            }
        }
        p=0;
        for (int i =0;i<H;i++){
            for (int j =0;j<W;j++){
                if (aa[i][j]!=-1){
                    mines.get(p).setImages(aa[i][j]);
                }
                p++;
            }
        }
    }

    public void opennear (mine mi){
        if (mi.getFl()){
            mi.setFl(false);
            minesC++;
            minesCText.setText("Осталось мин: " + minesC);
        }
        mi.setPicSP();
        int i = mi.getIJ()[0]; int j = mi.getIJ()[1];
            if (i!=H-1 && j!=W-1 && !mines.get((i+1)*W+1+j).getOp()){
                if (aa[i+1][j+1]==0) {
                    opennear(mines.get((i+1)*W+1+j));
                }
                else if (aa[i+1][j+1]>0){
                    if (mines.get((i+1)*W+1+j).getFl()){
                        mines.get((i+1)*W+1+j).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                    mines.get((i+1)*W+1+j).setPicSP();
                }
            }
            if (i!=0 && j!=0 && !mines.get((i-1)*W+j-1).getOp()){
                if (aa[i-1][j-1]==0) {
                opennear(mines.get((i-1)*W+j-1));
            }
                else if (aa[i-1][j-1]>0){
                    if (mines.get((i-1)*W+j-1).getFl()){
                        mines.get((i-1)*W+j-1).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                    mines.get((i-1)*W+j-1).setPicSP();
            }
            }
            if (j!=W-1 && i!=0 && !mines.get((i-1)*W+j+1).getOp()){
                if (aa[i-1][j+1]==0) {
                opennear(mines.get((i-1)*W+j+1));
            }
                else if (aa[i-1][j+1]>0){
                    if (mines.get((i-1)*W+j+1).getFl()){
                        mines.get((i-1)*W+j+1).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                mines.get((i-1)*W+j+1).setPicSP();
            }
            }
            if (j!=0 && i!=H-1 && !mines.get((i+1)*W+j-1).getOp()){
                if (aa[i+1][j-1]==0) {
                opennear(mines.get((i+1)*W+j-1));
            }
                else if (aa[i+1][j-1]>0){
                    if (mines.get((i+1)*W+j-1).getFl()){
                        mines.get((i+1)*W+j-1).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                mines.get((i+1)*W+j-1).setPicSP();
            }
            }
            if (i!=0 && !mines.get((i-1)*W+j).getOp()){
                if (aa[i-1][j]==0) {
                opennear(mines.get((i-1)*W+j));
            }
                else if (aa[i-1][j]>0){
                    if (mines.get((i-1)*W+j).getFl()){
                        mines.get((i-1)*W+j).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                mines.get((i-1)*W+j).setPicSP();
            }
            }
            if (i!=H-1 && !mines.get((i+1)*W+j).getOp()){
                if (aa[i+1][j]==0) {
                opennear(mines.get((i+1)*W+j));
            }
                else if (aa[i+1][j]>0){
                    if (mines.get((i+1)*W+j).getFl()){
                        mines.get((i+1)*W+j).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                mines.get((i+1)*W+j).setPicSP();
            }
            }
            if (j!=0 && !mines.get((i)*W+j-1).getOp()){
                if (aa[i][j-1]==0) {
                opennear(mines.get((i)*W+j-1));
            }
                else if (aa[i][j-1]>0){
                    if (mines.get((i)*W+j-1).getFl()){
                        mines.get((i)*W+j-1).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                mines.get((i)*W+j-1).setPicSP();
            }
            }
            if (j!=W-1 && !mines.get((i) * W + j + 1).getOp()) {
                if (aa[i][j + 1] == 0) {
                    opennear(mines.get((i) * W + j + 1));
                }
                else if (aa[i][j + 1] > 0) {
                    if ( mines.get((i) * W + j + 1).getFl()){
                        mines.get((i) * W + j + 1).setFl(false);
                        minesC++;
                        minesCText.setText("Осталось мин: " + minesC);
                    }
                    mines.get((i) * W + j + 1).setPicSP();
                }
            }

        return;
    }

    public static void main(String[] args) { launch(args); }
}
