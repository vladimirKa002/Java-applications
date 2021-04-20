package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Canvas canvas = new Canvas(500,500);
    private GraphicsContext gc;
    private Snake snake;
    private Point point;
    private double time =0;
    private StackPane root;

    @Override
    public void start(Stage primaryStage) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        root = new StackPane(canvas);

        canvas.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    snake.set_dir(8);
                    break;
                case DOWN:
                    snake.set_dir(2);
                     break;
                case LEFT:
                    snake.set_dir(4);
                    break;
                case RIGHT:
                    snake.set_dir(6);
                    break;
            }
        });

        primaryStage.setTitle("SnakeGame");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        startNewGame();
    }

    private double time_move = 0.5;
    private final double time_move_add = 0.06;
    private void startNewGame(){
        double tempNanoTIme = System.nanoTime();
        snake = new Snake(gc);
        point = new Point(gc);
        time_move = 0.5;
        new AnimationTimer() {
            public void handle(long presentNanoTime) {
                time = (presentNanoTime - tempNanoTIme) / 1000000000;
                if (time_move-0.1<=time & time_move<=time+0.1){
                    time_move+=time_move_add;
                    if (snake.is_intersected_point(point)){
                        snake.grow();
                        point = new Point(gc);
                    }
                    if (snake.is_intersected() || snake.is_out_of_bounds()){
                        game_over();
                        this.stop();
                        return;
                    }
                    snake.move();
                }
                draw();
            }
        }.start();
    }

    private void game_over(){
        Label text_game_over = new Label("GAME OVER!");
        text_game_over.setAlignment(Pos.CENTER);
        text_game_over.setStyle("-fx-font: 25 System");
        Button btn_new_game = new Button("Play again");
        btn_new_game.setAlignment(Pos.CENTER);
        btn_new_game.setStyle("-fx-font: 18 System");

        VBox vBox = new VBox(text_game_over, btn_new_game);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);

        btn_new_game.setOnAction(ev-> {
            root.getChildren().remove(vBox);
            startNewGame();
        });
    }

    private void draw(){
        gc.clearRect(0,0,500,500);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 500, 500);
        gc.setFill(Color.WHITE);
        gc.fillRect(75, 75, 350, 350);
        snake.render();
        point.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
