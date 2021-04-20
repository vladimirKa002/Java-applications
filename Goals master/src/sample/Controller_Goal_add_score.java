package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Items.Goal;

public class Controller_Goal_add_score extends Controller {
    @FXML
    private TextField input_count;

    @FXML
    void save_task(ActionEvent event) {
        try{
            Integer.parseInt(input_count.getText());
        }
        catch (NumberFormatException e){
            input_count.setText(input_count.getText()+" Ошибка!");
            return;
        }
        task_manager.add_goal_count((Goal) item, Integer.parseInt(input_count.getText()));
        stage.close();
    }
}

