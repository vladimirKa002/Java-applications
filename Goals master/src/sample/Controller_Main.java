package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Items.Goal;

import java.io.IOException;

public class Controller_Main extends Controller {

    @FXML
    private VBox field_goals;

    @FXML
    void addItem(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader;
        Parent root = null;

        fxmlLoader =  new FXMLLoader(getClass().getResource("View_Goal_creator.fxml"));
        Controller_Goal_Creator ctc = null;
        try {
            root = fxmlLoader.load();
            ctc = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctc.setStage(stage);
        stage.setTitle("Create goal");

        Stage main = Main.primaryStage;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.getIcons().add(Main.icon);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/style_date_chooser.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize(){
        task_manager.setCm(this);
    }

    @FXML
    void openStatistics(ActionEvent event){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader;
        Parent root = null;

        fxmlLoader =  new FXMLLoader(getClass().getResource("View_Statistics.fxml"));
        Controller_Statistics cs = null;
        try {
            root = fxmlLoader.load();
            cs = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cs.setTable();
        cs.setStage(stage);
        stage.setTitle("Statistics");

        Stage main = Main.primaryStage;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.getIcons().add(Main.icon);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void update(){
        field_goals.getChildren().clear();
        for (Goal goal : task_manager.goals) {
            field_goals.getChildren().add(goal.node);
        }
    }
}
