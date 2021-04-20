package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Items.Goal;

import java.time.LocalDate;

public class Controller_Goal_Creator extends Controller {

    @FXML
    private TextField input_text;

    @FXML
    private TextArea input_text_add_i;

    @FXML
    private TextField input_count;

    @FXML
    private DatePicker input_deadline;

    private int mode = 0;
    private Goal goal;
    public void set_mode(int mode, Goal goal){
        this.mode = mode;
        this.goal = goal;
    }

    public void set_all(String text, String text_add_in, int count, LocalDate deadline){
        input_text_add_i.setText(text_add_in.replaceAll("__", "\n"));
        input_text.setText(text);
        input_count.setText(count+"");
        input_deadline.setValue(deadline);
    }

    @FXML
    void do_changes(ActionEvent event){
        if (mode == 0) { // Create new
            create_task();
        }
        else { // Change
            save_changes();
        }
    }

    private void create_task(){
        try{
            Integer.parseInt(input_count.getText());
        }
        catch (NumberFormatException e){
            input_count.setText(input_count.getText()+" Ошибка!");
            return;
        }
        if (input_deadline.getValue() == null) return;
        Goal goal = new Goal(input_text.getText(), input_text_add_i.getText().replaceAll("\n", "__"),
                Integer.parseInt(input_count.getText()), input_deadline.getValue());
        task_manager.add_goal(goal);
        stage.close();
    }

    private void save_changes(){
        try{
            Integer.parseInt(input_count.getText());
        }
        catch (NumberFormatException e){
            input_count.setText(input_count.getText()+" Ошибка!");
            return;
        }
        if (input_deadline.getValue() == null) return;
        goal.change(input_text.getText(), input_text_add_i.getText().replaceAll("\n", "__"), Integer.parseInt(input_count.getText()),
                input_deadline.getValue());
        stage.close();
    }
}
