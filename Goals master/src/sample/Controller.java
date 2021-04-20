package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Items.Item;

import java.time.LocalDate;

public abstract class Controller {
    protected static Task_manager task_manager;
    protected Item item;
    protected Stage stage;

    @FXML
    protected void set_hover(MouseEvent event) {
        Node button = (Node) event.getSource();
        String current_st = button.getStyle();
        current_st = current_st.replaceAll(" ", "");
        if (current_st.contains("-fx-background-color:#00a705")) {
            current_st = current_st.replace("-fx-background-color:#00a705", "-fx-background-color:white");
            current_st = current_st.replace("-fx-text-fill:white", "-fx-text-fill:black");
        }
        else {
            current_st = current_st.replace("-fx-background-color:white", "-fx-background-color:#00a705");
            current_st = current_st.replace("-fx-text-fill:black", "-fx-text-fill:white");
        }
        button.setStyle(current_st);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public static void setTask_manager(Task_manager task_manager) {
        Controller.task_manager = task_manager;
    }

    public String convert_date(LocalDate date){
        return date.toString().substring(8, 10) + "." + date.toString().substring(5, 7) + "." + date.toString().substring(0, 4);
    }
}
