package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameoverController {
    private Game gameCl;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label recordText;

    @FXML
    private Button toMenu;

    @FXML
    private Label scoreText;

    @FXML
    void initialize() {
        toMenu.setOnMouseEntered(event -> {
            toMenu.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-border-color: green; -fx-border-radius: 5; -fx-background-radius: 5;");
        });
        toMenu.setOnMouseExited(event -> {
            toMenu.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-border-color: green; -fx-border-radius: 5; -fx-background-radius: 5;");
        });
    }

    public void setText(){
        scoreText.setText("Score: "+gameCl.getScore());
        if (gameCl.getScore()>gameCl.getRec()) {
            gameCl.setRec(gameCl.getScore());
            try {
                Formatter frm = new Formatter(new File(".").getAbsolutePath() + "/records");
                for (int i = 0;i<4;i++) frm.format(gameCl.getRecords()[i]+"\n");
                frm.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        recordText.setText("Record: "+gameCl.getRec());
    }

    public void setGame(Game gameCl){
        this.gameCl = gameCl;
    }

    public Button getToMenuBtn(){
        return toMenu;
    }
}