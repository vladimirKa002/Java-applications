package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {
    private StackPane root;
    private Canvas Canvas = new Canvas(600,600);
    private GraphicsContext gc;
    private double time =0;
    static int action=0;
    private Class<?> rrr= this.getClass();
    private InputStream inputIC = rrr.getResourceAsStream("images/background.png");
    private Image backgroundImage = new Image(inputIC);
    private ArrayList<String> weaponNames = new ArrayList<>();
    private Random rnd = new Random();
    private Button newGame = new Button("New game");
    private int Score = 0;
    private double tempNanoTIme;

    @Override
    public void init() {
        newGame.setStyle("-fx-background-color: white; -fx-text-fill: black;" +
                " -fx-border-color: white; -fx-border-radius: 15; -fx-background-radius: 15;-fx-font: 22 arial;");
        Canvas.setOnKeyPressed(event ->{
                if (knight==null) return;
                KeyCode key=event.getCode();
                if(!key.equals(KeyCode.UP)) return;
                knight.move();
                action=1;
        });
        Canvas.setOnKeyReleased(e -> action=0);
        Canvas.setOnMouseMoved(e -> {
            if (knight==null) return;
            double X = knight.getPos()[0];
            double Y = knight.getPos()[1];
            double w = e.getX() - X;
            double h = e.getY() - Y;
            double angle = Math.toDegrees(Math.atan(h/w));
            if (w<0) angle-=180;
            knight.setAngle(angle);
        });
        Canvas.setOnMousePressed(e -> {
            if (knight==null) return;
            if(e.isPrimaryButtonDown()){
                knight.setAttack();
            }
            else if(e.isSecondaryButtonDown()){
                knight.changeWeapon();
            }
        });

        gc = Canvas.getGraphicsContext2D();
        Canvas.setFocusTraversable(true);
        root = new StackPane(Canvas);

        try {
            Scanner sc = new Scanner(new File(new File(".").getAbsolutePath() + "/conf"));
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                String name = str.substring(0, str.indexOf(" "));
                weaponNames.add(name);
                str = str.substring(str.indexOf(" ")+1);
                int[] a = new int[2];
                a[0]= Integer.parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ")+1);
                a[1]= Integer.parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ")+1);
                int b= Integer.parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ")+1);
                double d = Double.parseDouble(str);
                Sprite.setArmory(a, d, b);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sprite.setArmoryNames(weaponNames);
    }

    private Knight knight;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private final double enemyTime = 1.0;
    private double enemyTimeSpawn = enemyTime;
    private Enemy winner;

    @Override
    public void start(Stage primaryStage){
        InputStream inputIC = rrr.getResourceAsStream("images/icon.png");
        Image imIC = new Image (inputIC);
        primaryStage.getIcons().add(imIC);
        Scene game = new Scene(root, 590, 590);
        primaryStage.setTitle("Knights");
        primaryStage.setScene(game);
        primaryStage.show();
        primaryStage.setResizable(false);

        knight = new Knight(gc);
        enemies.add(new Enemy(gc, knight, weaponNames.get(rnd.nextInt(weaponNames.size()))));

        tempNanoTIme = System.nanoTime();
        startNewGame();
        newGame.setOnAction((ActionEvent event) ->{
            tempNanoTIme = System.nanoTime();
            Score = 0;
            enemyTimeSpawn=enemyTime;
            action=0;
            time = 0;
            knight = new Knight(gc);
            enemies.clear();
            winner=null;
            Platform.runLater(() -> root.getChildren().remove(newGame));
            startNewGame();
        });
    }

    private void startNewGame(){
        new AnimationTimer() {
            public void handle(long presentNanoTime) {
                if (knight==null & winner!=null && winner.getAttack()) this.stop();
                time = (presentNanoTime - tempNanoTIme) / 1000000000;
                if (knight != null && enemyTimeSpawn-0.1<=time & time<=enemyTimeSpawn+0.1) {
                    enemyTimeSpawn+=enemyTime;
                    enemies.add(new Enemy(gc, knight, weaponNames.get(rnd.nextInt(weaponNames.size()))));
                }
                for (Enemy value : enemies) {
                    if (knight == null) break;
                    value.setAngle();
                    value.move();
                }
                for (int i =0;i<enemies.size();i++) {
                    if (enemies.get(i).intersects() | knight == null) {
                        winner = enemies.get(i);
                        gc.clearRect(0,0,430,610);
                        gc.drawImage(backgroundImage, 0,0);
                        for (Enemy enemy : enemies) {
                            enemy.render();
                        }
                        Platform.runLater(() -> {
                            if (knight!=null) root.getChildren().add(newGame);
                            knight = null;
                            drawText();
                        });
                        return;
                    }
                    else if (knight.intersects(enemies.get(i))) {
                        enemies.remove(enemies.get(i));
                        Score++;
                    }
                }
                draw();
            }
        }.start();
    }

    private void draw(){
        gc.clearRect(0,0,610,610);
        gc.drawImage(backgroundImage, 0,0);
        knight.render();
        for (Enemy enemy : enemies) {
            enemy.render();
        }
        drawText();
    }

    private void drawText(){
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("arial",  24));
        gc.fillText(Integer.toString(Score), 15, 30);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
