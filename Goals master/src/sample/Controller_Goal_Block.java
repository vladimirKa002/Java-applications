package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Items.Goal;

import java.io.IOException;

public class Controller_Goal_Block extends Controller_Block{
    private Goal goal;

    @FXML
    private Label txt_task;

    @FXML
    private Label txt_completed;

    @FXML
    private ProgressBar progrbar;

    @FXML
    void finish(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View_Confirmation.fxml"));
        Parent root = null;
        Controller_Confirmation ctc = null;
        try {
            root = fxmlLoader.load();
            ctc = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctc.set_mode(1);
        ctc.setStage(stage);
        ctc.setItem(goal);
        Stage main = Main.primaryStage;
        stage.getIcons().add(Main.icon);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.setTitle("Finish goal");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void  add_count(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View_Goal_add_score.fxml"));
        Parent root = null;
        Controller_Goal_add_score ctc = null;
        try {
            root = fxmlLoader.load();
            ctc = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctc.setStage(stage);
        ctc.setItem(goal);
        Stage main = Main.primaryStage;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.getIcons().add(Main.icon);
        stage.setTitle("Add score");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void delete_goal(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View_Confirmation.fxml"));
        Parent root = null;
        Controller_Confirmation ctc = null;
        try {
            root = fxmlLoader.load();
            ctc = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctc.setStage(stage);
        ctc.setItem(goal);
        Stage main = Main.primaryStage;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.getIcons().add(Main.icon);
        stage.setTitle("Delete goal");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void change(MouseEvent event){
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
        ctc.set_mode(1, goal);
        ctc.set_all(goal.getText(), goal.getText_additional_info(), goal.getCount(), goal.getDeadline());
        ctc.setStage(stage);
        stage.setTitle("Change");

        Stage main = Main.primaryStage;
        stage.getIcons().add(Main.icon);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setGoal(Goal goal){
        this.goal = goal;
    }

    @Override
    public void update(){
        txt_completed.setText(goal.getCompleted()+"/"+ goal.getCount());
        txt_task.setText(goal.getText() + " До " + convert_date(goal.getDeadline()));
        progrbar.setProgress((double) goal.getCompleted()/ goal.getCount());
    }
}
