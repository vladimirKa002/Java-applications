package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {
    public int[] records = new int[4];
    public static Stage primaryStage = null;
    private Controller controller;
    private GameSceneController controllerGame;
    static public GameoverController controllerGameOver;
    private Scene menu = null;
    private Scene game = null;
    static private Scene gameOver = null;
    static String mode="+";
    private Game gameCl=null;

    @Override
    public void init() throws Exception{
        readRecords();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        loader = new FXMLLoader(getClass().getResource("gameScene.fxml"));
        Parent root2 = loader.load();
        controller.setRecordsTexts(records);
        controllerGame = loader.getController();
        loader = new FXMLLoader(getClass().getResource("gameover.fxml"));
        Parent root3 = loader.load();
        controllerGameOver = loader.getController();
        menu = new Scene(root, 400, 600);
        game = new Scene(root2, 400, 600);
        gameOver = new Scene(root3, 400, 600);
    }

    @Override
    public void start(Stage primaryStage){
        Class<?> rrr = this.getClass();
        InputStream inputIC = rrr.getResourceAsStream("images/icon.png");
        Image imIC = new Image (inputIC);
        primaryStage.getIcons().add(imIC);
        primaryStage.setResizable(false);
        controller.getStartGame().setOnAction(event -> {
            int y=0;
            switch (mode) {
                case "+":
                    y = 0;
                    break;
                case "-":
                    y = 1;
                    break;
                case "*":
                    y = 2;
                    break;
                case "/":
                    y=3;
                    break;
            }
            primaryStage.setScene(game);
            gameCl = new Game(mode, y, records);
            controllerGameOver.setGame(gameCl);
            controllerGame.setGame(gameCl);
            controllerGame.addTimerAndOperat(mode);
        });
        controllerGameOver.getToMenuBtn().setOnAction(event -> {
            readRecords();
            controller.setRecordsTexts(records);
            primaryStage.setScene(menu);
        });
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MathGame");
        primaryStage.setScene(menu);
        primaryStage.show();
    }

    public static void setSceneGameOver(){
        primaryStage.setScene(gameOver);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        controllerGame.stop();
        super.stop();
    }

    public void readRecords(){
        File fl = new File(new File(".").getAbsolutePath() + "/records");
        Scanner sc = null;
        try {
            sc = new Scanner(fl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i=0;i<4;i++) records[i] = sc.nextInt();
    }
}
