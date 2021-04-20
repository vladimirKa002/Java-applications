package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import static java.lang.Thread.sleep;

public class GameSceneController {
    private LabelTime labelTime = null;
    private Game gameCl;
    private boolean blocked = false;

    @FXML
    private HBox TimeField;

    @FXML
    private TextField input;

    @FXML
    private Label num1;

    @FXML
    private Label scoreText;

    @FXML
    private Label operator;

    @FXML
    private Label num2;

    @FXML
    void initialize() {
        input.setOnKeyTyped(ke -> {
            if (ke.getCharacter().equals("\r")){
                try{
                    if (Integer.parseInt(input.getText()) == gameCl.getCorrect()) {
                        gameCl.addScore();
                        labelTime.resetTime(2);
                        gameCl.count();
                        resetTexts();
                        Task task = new Task<Void>() {
                            @Override
                            public Void call() {
                                if (blocked) return null;
                                setStyleCorrect();
                                return null;
                            }
                        };
                        new Thread(task).start();
                    }
                    else {
                        labelTime.resetTime(-2);
                        Task task = new Task<Void>() {
                            @Override
                            public Void call() {
                                if (blocked) return null;
                                setStyleUnCorrect();
                                return null;
                            }
                        };
                        new Thread(task).start();
                    }
                }catch(RuntimeException e){
                    labelTime.resetTime(-2);
                    Task task = new Task<Void>() {
                        @Override
                        public Void call() {
                            if (blocked) return null;
                            setStyleUnCorrect();
                            return null;
                        }
                    };
                    new Thread(task).start();
                }
            }
        });
    }

    public void resetTexts() {
        int a[] = gameCl.getNums();
        num1.setText(Integer.toString(a[0]));
        num2.setText(Integer.toString(a[1]));
        input.setText("");
        scoreText.setText("Score: "+ gameCl.getScore());
    }

    public void addTimerAndOperat(String mode){
        if (TimeField.getChildren().size()>0) TimeField.getChildren().clear();
        labelTime = new LabelTime();
        TimeField.getChildren().add(labelTime);
        operator.setText(mode);
        resetTexts();
    }

    public void setGame(Game gameCl){
        this.gameCl = gameCl;
    }

    public void stop(){
        if (labelTime!= null) labelTime.resetTime(-90);
    }

    void setStyleUnCorrect(){
        blocked = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                input.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-border-color: black;");
            }
        });
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                input.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
            }
        });
        blocked = false;
    }

    void setStyleCorrect(){
        blocked = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                input.setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-border-color: black;");
            }
        });
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                input.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
            }
        });
        blocked = false;

    }
}
