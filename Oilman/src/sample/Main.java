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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Main extends Application {
    int wellAm = 10;
    int H = 9;
    int W = 9;
    int wellC=10;
    Text wellCText;
    static int turn = 0;
    VBox wellFieldVer = new VBox(0);
    ArrayList<well> wellar = new ArrayList<well>(81);
    ArrayList<Integer> integer = new ArrayList<Integer>(81);
    VBox cont1;
    Class<?> rrr = this.getClass();
    InputStream input1;
    InputStream inputOilm = rrr.getResourceAsStream("images/oilman.png");
    Image im = new Image (inputOilm);
    ImageView imgvOilm = new ImageView(im);
    Button toMenu = new Button("В контору");
    Stage st;
    Scene main;
    boolean win = false;
    boolean loss = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        toMenu.setStyle(String.format("-fx-font: 18 arial;"));
        st = primaryStage;
        InputStream inputIC = rrr.getResourceAsStream("images/icon.png");
        Image imIC = new Image (inputIC);
        primaryStage.getIcons().add(imIC);
        wellFieldVer.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Нефтяник");
        Button btnstart = new Button("Разработать месторождение");
        btnstart.setStyle("-fx-font: 18 arial;");
        HBox fieldSt = new HBox(10, btnstart); fieldSt.setAlignment(Pos.CENTER);
        VBox mainfield = new VBox(30, imgvOilm, fieldSt); mainfield.setAlignment(Pos.CENTER);

        RadioButton easy = new RadioButton("Малое месторождение");
        easy.setStyle(String.format("-fx-font: 18 arial;"));
        RadioButton medium = new RadioButton("Среднее месторождение");
        medium.setStyle(String.format("-fx-font: 18 arial;"));
        RadioButton hard = new RadioButton("Крупное месторождение");
        hard.setStyle(String.format("-fx-font: 18 arial;"));

        ToggleGroup group = new ToggleGroup();

        easy.fire();
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);

        easy.setOnAction(event -> {
            H=9; W = 9; wellAm = 10;
            wellC = wellAm;
        });
        medium.setOnAction(event -> {
            H=16; W = 16; wellAm = 40;
            wellC = wellAm;
        });
        hard.setOnAction(event -> {
            H=16; W = 30; wellAm = 99;
            wellC = wellAm;
        });
        HBox choice = new HBox(10, easy, medium, hard);
        choice.setAlignment(Pos.CENTER);
        mainfield.getChildren().add(choice);
        main = new Scene(mainfield, 800, 500);
        primaryStage.setScene(main);
        primaryStage.show();

        wellCText = new Text("Доступных скважин: " + wellC);
        wellCText.setStyle("-fx-font: 24 arial;");
        cont1 = new VBox(10, wellFieldVer, wellCText);
        cont1.setAlignment(Pos.CENTER);
        Scene gamescn = new Scene(cont1, 1200, 700);
        btnstart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                createACarcas();
                startgame();
                turn = 1;
                primaryStage.setScene(gamescn);
            }
        });
    }

    public void startgame (){
        input1 = rrr.getResourceAsStream("images/inpro.png");
        Image img = new Image(input1);
        ImageView imgv = new ImageView(img);
        cont1.getChildren().add(0, imgv);

        wellar.forEach(new Consumer<well>() {
            @Override
            public void accept(well mi) {
                mi.getPic().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if (Main.turn == 1 & !loss & !win & event.getButton() == MouseButton.PRIMARY){
                            turn =0;
                            fillField (mi.getIJ());
                            opennear(mi);
                        }
                        else if (turn!=1 & !loss & !win & !mi.getOp() & event.getButton() == MouseButton.SECONDARY) {
                            if (mi.getFl()) {
                                mi.setFl(false);
                                wellC++;
                            }
                            else {
                                mi.setFl(true);
                                wellC--;
                                if (well.openedCount==H*W-wellAm & wellC==0) {
                                    input1 = rrr.getResourceAsStream("images/win.png");
                                    Image img = new Image(input1);
                                    ImageView imgv = new ImageView(img);
                                    cont1.getChildren().remove(0);
                                    cont1.getChildren().remove(1);
                                    cont1.getChildren().add(0, imgv);
                                    cont1.getChildren().add(2, toMenu);
                                    win = true;
                                }
                            }
                            wellCText.setText("Доступных скважин: " + wellC);
                        }
                        else if (!loss & !win & !mi.getOp() & !mi.getFl() & event.getButton() == MouseButton.PRIMARY) {
                            if ( mi.getMine() ){
                                for (int i = 0; i<H*W;i++){
                                    wellar.get(i).setPicSP();
                                }
                                loss = true;
                                input1 = rrr.getResourceAsStream("images/loss.png");
                                Image img = new Image(input1);
                                ImageView imgv = new ImageView(img);
                                cont1.getChildren().remove(0);
                                cont1.getChildren().remove(1);
                                cont1.getChildren().add(0, imgv);
                                cont1.getChildren().add(2, toMenu);
                            }
                            else if (!mi.getCh()) {
                                opennear(mi);
                                if (well.openedCount==H*W-wellAm & wellC==0) {
                                    input1 = rrr.getResourceAsStream("images/win.png");
                                    Image img = new Image(input1);
                                    ImageView imgv = new ImageView(img);
                                    cont1.getChildren().remove(0);
                                    cont1.getChildren().remove(1);
                                    cont1.getChildren().add(0, imgv);
                                    cont1.getChildren().add(2, toMenu);
                                    win = true;
                                }
                            }
                            else {
                                mi.setPicSP();
                                if (well.openedCount==H*W-wellAm & wellC==0) {
                                    input1 = rrr.getResourceAsStream("images/win.png");
                                    Image img = new Image(input1);
                                    ImageView imgv = new ImageView(img);
                                    cont1.getChildren().remove(0);
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

    public void createACarcas (){
        wellC=wellAm;
        wellCText.setText("Доступных скважин: " + wellC);
        win = false;
        loss = false;
        if (wellFieldVer.getChildren().size()>0) {
            wellFieldVer.getChildren().remove(0,wellFieldVer.getChildren().size());
            cont1.getChildren().remove(0);
            cont1.getChildren().remove(1);
            cont1.getChildren().add(1, wellCText);
        }
        well.openedCount=0;
        wellar.clear();
        integer.clear();
        for (int i = 0; i<H*W;i++){
            wellar.add(new well());
        }
        int y = 0;
        for (int i =0;i<H;i++){
            HBox line = new HBox(0);
            line.setAlignment(Pos.CENTER);
            for (int j =0;j<W;j++){
                line.getChildren().add(wellar.get(y).getPic());
                wellar.get(y).setIJ(i, j);
                y++;
            }
            wellFieldVer.getChildren().add(line);
        }
    }

    public void fillField (int [] ij){
        int num = ij[0] * W + ij[1];
        Random rnd = new Random();
        for (int i = 0; i<H*W;i++){
            integer.add(i);
        }
        if (ij[1]+1<W & ij[0]+1<H) integer.remove(num+W+1);
        if (ij[0]+1<H) integer.remove(num+W);
        if (ij[1]-1>=0 & ij[0]+1<H) integer.remove(num+W-1);
        if (ij[1]+1<W) integer.remove(num+1);
        integer.remove(num);
        if (ij[1]-1>=0) integer.remove(num-1);
        if (ij[1]+1<W & ij[0]-1>=0) integer.remove(num-W+1);
        if (ij[0]-1>=0) integer.remove(num-W);
        if (ij[0]-1>=0 & ij[1]-1>=0) integer.remove(num-W-1);
        for (int i = 0; i<wellAm;i++){
            int u = rnd.nextInt(integer.size());
            int y = integer.get(u);
            wellar.get(y).setOil();
            integer.remove(u);
        }
        int [][] u = new int[H][W];
        aa = new int[H][W];
        int p =0;
        for (int i =0;i<H;i++){
            for (int j =0;j<W;j++){
                if (wellar.get(p).getMine()) u[i][j]=1;
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
                    wellar.get(p).setImages(aa[i][j]);
                }
                p++;
            }
        }
    }

    public void opennear (well mi){
        if (mi.getFl()){
            mi.setFl(false);
            wellC++;
            wellCText.setText("Доступных скважин: " + wellC);
        }
        mi.setPicSP();
        int i = mi.getIJ()[0]; int j = mi.getIJ()[1];
        if (i!=H-1 && j!=W-1 && !wellar.get((i+1)*W+1+j).getOp()){
            if (aa[i+1][j+1]==0) {
                opennear(wellar.get((i+1)*W+1+j));
            }
            else if (aa[i+1][j+1]>0){
                if (wellar.get((i+1)*W+1+j).getFl()){
                    wellar.get((i+1)*W+1+j).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i+1)*W+1+j).setPicSP();
            }
        }
        if (i!=0 && j!=0 && !wellar.get((i-1)*W+j-1).getOp()){
            if (aa[i-1][j-1]==0) {
                opennear(wellar.get((i-1)*W+j-1));
            }
            else if (aa[i-1][j-1]>0){
                if (wellar.get((i-1)*W+j-1).getFl()){
                    wellar.get((i-1)*W+j-1).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i-1)*W+j-1).setPicSP();
            }
        }
        if (j!=W-1 && i!=0 && !wellar.get((i-1)*W+j+1).getOp()){
            if (aa[i-1][j+1]==0) {
                opennear(wellar.get((i-1)*W+j+1));
            }
            else if (aa[i-1][j+1]>0){
                if (wellar.get((i-1)*W+j+1).getFl()){
                    wellar.get((i-1)*W+j+1).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i-1)*W+j+1).setPicSP();
            }
        }
        if (j!=0 && i!=H-1 && !wellar.get((i+1)*W+j-1).getOp()){
            if (aa[i+1][j-1]==0) {
                opennear(wellar.get((i+1)*W+j-1));
            }
            else if (aa[i+1][j-1]>0){
                if (wellar.get((i+1)*W+j-1).getFl()){
                    wellar.get((i+1)*W+j-1).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i+1)*W+j-1).setPicSP();
            }
        }
        if (i!=0 && !wellar.get((i-1)*W+j).getOp()){
            if (aa[i-1][j]==0) {
                opennear(wellar.get((i-1)*W+j));
            }
            else if (aa[i-1][j]>0){
                if (wellar.get((i-1)*W+j).getFl()){
                    wellar.get((i-1)*W+j).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i-1)*W+j).setPicSP();
            }
        }
        if (i!=H-1 && !wellar.get((i+1)*W+j).getOp()){
            if (aa[i+1][j]==0) {
                opennear(wellar.get((i+1)*W+j));
            }
            else if (aa[i+1][j]>0){
                if (wellar.get((i+1)*W+j).getFl()){
                    wellar.get((i+1)*W+j).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i+1)*W+j).setPicSP();
            }
        }
        if (j!=0 && !wellar.get((i)*W+j-1).getOp()){
            if (aa[i][j-1]==0) {
                opennear(wellar.get((i)*W+j-1));
            }
            else if (aa[i][j-1]>0){
                if (wellar.get((i)*W+j-1).getFl()){
                    wellar.get((i)*W+j-1).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i)*W+j-1).setPicSP();
            }
        }
        if (j!=W-1 && !wellar.get((i) * W + j + 1).getOp()) {
            if (aa[i][j + 1] == 0) {
                opennear(wellar.get((i) * W + j + 1));
            }
            else if (aa[i][j + 1] > 0) {
                if ( wellar.get((i) * W + j + 1).getFl()){
                    wellar.get((i) * W + j + 1).setFl(false);
                    wellC++;
                    wellCText.setText("Доступных скважин: " + wellC);
                }
                wellar.get((i) * W + j + 1).setPicSP();
            }
        }

        return;
    }

    public static void main(String[] args) { launch(args); }
}
