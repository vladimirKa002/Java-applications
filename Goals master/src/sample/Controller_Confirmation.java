package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller_Confirmation extends Controller{
    private int mode = 0; // 0 - delete, 1- finish

    @FXML
    void do_something(ActionEvent event){
        if (mode == 0){
            delete_task();
        }
        else if (mode == 1){
            finish_task();
        }
    }

    private void delete_task() {
        task_manager.delete_item(item);
        stage.close();
    }

    private void finish_task() {
        task_manager.finish_item(item);
        stage.close();
    }

    public void set_mode(int mode){
        this.mode = mode;
    }
}
