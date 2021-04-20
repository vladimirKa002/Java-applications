package sample.Items;

import javafx.scene.Node;
import sample.Controller_Goal_Block;

import java.time.LocalDate;

public class Goal extends Item{
    private int count;
    private int completed = 0;

    public Goal(String text, String text_add_in, int count, LocalDate deadline){
        this.text_additional_info = text_add_in;
        this.text = text;
        this.count = count;
        this.deadline = deadline;
    }

    public Goal(String text, String text_add_in, int count, int completed, LocalDate deadline){
        this.text_additional_info = text_add_in;
        this.text = text;
        this.count = count;
        this.completed = completed;
        this.deadline = deadline;
    }

    public boolean add_value(int val) {
        completed+=val;
        ctb.update();
        return isCompleted();
    }

    public void change(String text, String text_add_in, int count, LocalDate deadline){
        this.text_additional_info = text_add_in;
        this.text = text;
        this.count = count;
        this.deadline = deadline;
    }

    private boolean isCompleted(){
        return completed >= count;
    }

    public int getCompleted() {
        return completed;
    }

    public int getCount() {
        return count;
    }

    public Controller_Goal_Block ctb;
    public Node node;

    public void setGui(Controller_Goal_Block ctb, Node node) {
        this.ctb = ctb;
        this.node = node;
        ctb.setGoal(this);
        ctb.update();
    }
}
