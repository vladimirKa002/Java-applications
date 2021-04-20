package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Window;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static ArrayList<Domino> yourDominos = new ArrayList<Domino>(7);
    static ArrayList<Domino> opponDominos = new ArrayList<Domino>(7);
    static ArrayList<Domino> bazar = new ArrayList<Domino>(28);
    static boolean passOpp=false;

    static boolean end = false;
    static int YoAm = 7;
    static int leri = 0;
    static int OpAm = 7;
    static String Turn = "opp";

    static boolean pass = false;
    static Label opponAmount; static Label turnOppTXT;
    static Label yourAmount; static Label turnYourTXT;
    static Label bazarAmmount; static HBox yourDomino;
    static HBox gameField; static VBox bazarField;
    static Domino bord1; static Domino bord2;
    static Domino choice;
    Scene game;
    static Scene EndOfGame;
    static VBox rooteog;
    Scene start;
    Button btnStart;
    Button btnMenu = new Button("Меню");
    Random rnd = new Random();
    static Button btnTake;
    static Class<?> rrr;
    static boolean first = true;
    static Stage st;

    public void mix (){
        int n = 0;
        bazar.clear();
        yourDominos.clear();
        opponDominos.clear();
        Domino d66 = new Domino(6,6,yourDominos, yourDomino);
        for (int i = 0;i<7;i++){
            for (int y = i;y<7;y++){
                bazar.add(new Domino(i,y,yourDominos, yourDomino));
                n++;
            }
        }
        bazar.remove(bazar.size()-1);
        ArrayList<Domino> dom1 = new ArrayList<Domino>(28);;
        int j =0;
        for (int i = 0;i<27;i++){
            j=rnd.nextInt(bazar.size());
            dom1.add(bazar.get(j));
            bazar.remove(j);
        }
        bazar = dom1;
        opponDominos.add(d66);
        for (int i = 0;i<6;i++){
            j=rnd.nextInt(bazar.size());
            opponDominos.add(bazar.get(j));
            bazar.remove(j);
        }
        for (int i = 0;i<7;i++){
            j=rnd.nextInt(bazar.size());
            yourDominos.add(bazar.get(j));
            bazar.remove(j);
        }
    }

    static public void setTexts(){
        yourAmount.setText("" + YoAm);
        opponAmount.setText("" + OpAm);
        bazarAmmount.setText("" + bazar.size());
    }

    static public void checkYOURDominos (){
        boolean b = false;
        for (int i =0;i<yourDominos.size();i++){
            if (yourDominos.get(i).getUse())
            if (yourDominos.get(i).getVal()[0]== bord1.getVal()[0] ||
                    yourDominos.get(i).getVal()[1]==bord1.getVal()[0]
                    || (gameField.getChildren().size()>1 &&
                    (yourDominos.get(i).getVal()[0]==bord2.getVal()[1] ||
                    yourDominos.get(i).getVal()[1]==bord2.getVal()[1]))){
                b = true;
                pass=false;
                bazarField.getChildren().remove(btnTake);
                break;
            }
        }
        if (bazar.size()!=0 & !b & !bazarField.getChildren().contains(btnTake)) {
            bazarField.getChildren().add(btnTake);
        }
        if (bazar.size()==0 & !b) {
            pass=true;
            if (Main.passOpp){
                Main.Winner(-1);
                return;
            }
            if (!passOpp) {
                oppTurn();
                if (Main.OpAm ==0)  {
                    Main.Winner(1);
                    return;
                }
                Main.setTexts();
                checkYOURDominos();
            }
        }
    }

    public void youTakeFromBazar(){
        int j =rnd.nextInt(bazar.size());
        yourDominos.add(bazar.get(j));
        bazar.remove(j);
        YoAm++;
        setTexts();
        yourDomino.getChildren().add(yourDominos.get(yourDominos.size()-1).getPic());
    }

    @Override
    public void init() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(1400);
        VBox root = new VBox(50);
        scrollPane.setContent(root);
        scrollPane.setPannable(true);
        scrollPane.setPadding(new Insets(50,50,0, 50));
        VBox rootMain = new VBox(50, scrollPane);
        root.setAlignment(Pos.CENTER);
        rootMain.setAlignment(Pos.CENTER);

        game = new Scene(rootMain, 1400,600);
        rooteog = new VBox(5);
        rooteog.setAlignment(Pos.CENTER);
        rooteog.getChildren().add(btnMenu);
        EndOfGame = new Scene(rooteog, 1400,800);

        VBox toppart = new VBox();
        opponAmount = new Label();
        HBox oppon = new HBox(150, new Label ("Соперник"), new HBox(10,
                new Label("Количество домино: "), opponAmount));
        oppon.setStyle(String.format("-fx-font: 20 arial;"));
        oppon.setAlignment(Pos.CENTER);
        turnOppTXT = new Label("Ход соперника...");
        HBox turnOpp = new HBox (10, turnOppTXT);
        turnOpp.setStyle(String.format("-fx-font: 20 arial;"));
        toppart.getChildren().addAll(oppon,turnOpp,turnOppTXT);
        toppart.setAlignment(Pos.TOP_CENTER);

        gameField = new HBox (20);
        gameField.setAlignment(Pos.CENTER);

        VBox yourPart = new VBox();
        turnYourTXT = new Label("Ваш ход...");
        HBox turnYour = new HBox (10, turnYourTXT);
        turnYour.setStyle(String.format("-fx-font: 20 arial;"));
        yourAmount = new Label();
        HBox your = new HBox(50, new Label ("Вы"), new HBox(10,
                new Label("Количество домино: "), yourAmount));
        your.setStyle(String.format("-fx-font: 20 arial;"));
        your.setAlignment(Pos.CENTER);
        yourDomino = new HBox (25);
        yourDomino.setAlignment(Pos.CENTER);
        yourPart.getChildren().addAll(turnYour, your,yourDomino);

        bazarAmmount = new Label();
        btnTake = new Button("Взять");
        HBox bazarAm = new HBox (10, new Label("Количество: "), bazarAmmount);
        bazarAm.setStyle(String.format("-fx-font: 20 arial;"));
        bazarField = new VBox(10, new Label("Базар"), bazarAm);
        bazarField.setStyle(String.format("-fx-font: 20 arial;"));

        HBox bottomPart = new HBox(60,yourPart, bazarField);
        bottomPart.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().addAll(toppart, gameField, bottomPart);

        btnStart = new Button("Начать!");
        HBox root1 = new HBox(10, btnStart);
        root1.setAlignment(Pos.CENTER);
        start = new Scene(root1, 1400,800);

        btnStart.setStyle(String.format("-fx-font: 20 arial;"));
        btnMenu.setStyle(String.format("-fx-font: 20 arial;"));
        btnTake.setStyle(String.format("-fx-font: 20 arial;"));
    }

    public static void Winner(int b){
        pass=false;
        bord2=null;
        choice=null;
        first = true;
        passOpp=false;
        gameField.getChildren().remove(0, gameField.getChildren().size());
        if (yourDomino.getChildren().size()>=1) yourDomino.getChildren().remove(0,
                yourDomino.getChildren().size());
        int your=0;
        int opp=0;
        for (int i = 0;i<yourDominos.size();i++){
            if (yourDominos.get(i).getUse()) your = yourDominos.get(i).getVal()[0]
                    + yourDominos.get(i).getVal()[1];
        }
        for (int i = 0;i<opponDominos.size();i++){
            if (opponDominos.get(i).getUse()) opp = opponDominos.get(i).getVal()[0]
                    + opponDominos.get(i).getVal()[1];
        }
        if (your < opp | b==0) {
            if (rooteog.getChildren().size()>1) rooteog.getChildren().remove(0);
            InputStream input = rrr.getResourceAsStream("images/YouWin.png");;
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            rooteog.getChildren().add(0, imageView);
            st.setScene(EndOfGame);
        }
        else if (your>opp | b==1) {
            if (rooteog.getChildren().size()>1) rooteog.getChildren().remove(0);
            InputStream input = rrr.getResourceAsStream("images/OppWin.png");;
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            rooteog.getChildren().add(0, imageView);
            st.setScene(EndOfGame);
        }
        else{
            rooteog.getChildren().removeAll();
            if (rooteog.getChildren().size()>1) rooteog.getChildren().remove(0);
            InputStream input = rrr.getResourceAsStream("images/Draw.png");;
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            rooteog.getChildren().add(0, imageView);
            st.setScene(EndOfGame);
        }
        end=true;
    }

    static public boolean opponTakesFromBazar(){
        if (Main.bazar.size()==0) return false;
        Main.opponDominos.add(Main.bazar.get(0));
        Main.bazar.remove(0);
        Main.OpAm++;
        setTexts();
        return true;
    }

    public static void oppTurn (){
        int dn = -1;
            int lr = 0;
            int max=0;
            while (dn==-1){
                for (int i=0;i<Main.opponDominos.size();i++){
                    if (Main.opponDominos.get(i).getUse()) {
                        if (Main.opponDominos.get(i).getVal()[0] == Main.bord1.getVal()[0] |
                                Main.opponDominos.get(i).getVal()[1] == Main.bord1.getVal()[0]) {
                            int y = Main.opponDominos.get(i).getVal()[0] + Main.opponDominos.get(i).getVal()[1];
                            if (y > max) {
                                dn = i;
                                max = y;
                                lr = 0;
                            }
                        } else if (Main.opponDominos.get(i).getVal()[0] == Main.bord2.getVal()[1] |
                                Main.opponDominos.get(i).getVal()[1] == Main.bord2.getVal()[1]){
                            int y = Main.opponDominos.get(i).getVal()[0] + Main.opponDominos.get(i).getVal()[1];
                            if (y > max) {
                                dn = i;
                                max = y;
                                lr = 1;
                            }
                        }
                    }
                }
                if (dn==-1) {
                    if (!Main.opponTakesFromBazar()) break;
                }
            }
            if (dn==-1){
                Main.passOpp=true;
                return;
            }
            else Main.passOpp=false;
            if (lr==0) {
                if (Main.opponDominos.get(dn).getVal()[1] != Main.bord1.getVal()[0]) Main.opponDominos.get(dn).rotate();
                Main.bord1 = Main.opponDominos.get(dn);
                Main.gameField.getChildren().add(0, Main.opponDominos.get(dn).getPic());
            }
            else {
                if (Main.opponDominos.get(dn).getVal()[0] != Main.bord2.getVal()[1]) Main.opponDominos.get(dn).rotate();
                Main.bord2 = Main.opponDominos.get(dn);
                Main.gameField.getChildren().add(Main.opponDominos.get(dn).getPic());
            }
            Main.opponDominos.get(dn).setUsed();
            Main.OpAm--;
            Main.leri = lr;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        end = false;
        rrr = this.getClass();
        st=primaryStage;
        primaryStage.setTitle("Домино");
        primaryStage.setScene(start);
        primaryStage.show();

        btnStart.setOnAction(event -> {
            mix();
            gameField.getChildren().removeAll();
            yourDomino.getChildren().removeAll();
            bazarField.getChildren().remove(btnTake);
            YoAm=7;
            OpAm=7;
            for (int i = 0;i<7;i++) yourDomino.getChildren().add(yourDominos.get(i).getPic());
            primaryStage.setScene(game);
            {
                int dn=0;
                int max=0;
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

            setTexts();
            turnOppTXT.setText("");
            turnYourTXT.setText("Ваш ход...");

            checkYOURDominos();

            btnMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent eventtt) {
                primaryStage.setScene(start);
            }
            });
            btnTake.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent eventtt) {
                youTakeFromBazar();
                checkYOURDominos();
            }
            });
    });
    }
}
