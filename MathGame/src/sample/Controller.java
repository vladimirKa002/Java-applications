package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Label recPlus;

    @FXML
    private Label recDiv;

    @FXML
    private Label recMin;

    @FXML
    private Label recMult;

    @FXML
    private Button startGame;

    @FXML
    private ArrayList<ToggleButton> modeBtn = new ArrayList<ToggleButton>();

    @FXML
    private ToggleButton modePlus;

    @FXML
    private ToggleButton modeMinus;

    @FXML
    private ToggleButton modeDivis;

    @FXML
    private ToggleButton modeMultipl;


    @FXML
    void initialize() {
        startGame.setOnMouseEntered(event -> {
            startGame.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-border-color: green; -fx-border-radius: 5; -fx-background-radius: 5;");
        });
        startGame.setOnMouseExited(event -> {
            startGame.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-border-color: green; -fx-border-radius: 5; -fx-background-radius: 5;");
        });
        modeBtn.add(modePlus); modeBtn.add(modeMinus); modeBtn.add(modeMultipl); modeBtn.add(modeDivis);
        modeBtn.get(0).setSelected(true);
        setStyleToToogles();
        for (ToggleButton tg : modeBtn) {
            tg.setOnAction(event ->{
                if (!tg.isSelected()) return;
                Main.mode = tg.getText();
                for (ToggleButton tg1 : modeBtn) {
                    if (tg!=tg1) tg1.setSelected(false);
                }
                setStyleToToogles();
            });
        }
    }

    private void setStyleToToogles(){
        for (ToggleButton tg : modeBtn) {
            if (tg.isSelected())
                tg.setStyle("-fx-text-fill: white;  -fx-border-color: green; -fx-border-radius: 15;-fx-background-color: green; -fx-background-radius: 15;");
            else
                tg.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-border-color: green; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
    }

    public Button getStartGame(){
        return startGame;
    }

    public void setRecordsTexts(int [] records){
        recPlus.setText("Record: " + records[0]);
        recMin.setText("Record: " + records[1]);
        recMult.setText("Record: " + records[2]);
        recDiv.setText("Record: " + records[3]);
    }
}
