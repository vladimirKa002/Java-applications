package sample;

import sample.Items.Goal;

import java.time.LocalDate;
import java.util.ArrayList;

public class Statistics {
    public ArrayList<Item_statistics> item_statistics = new ArrayList<>(10);
    private static Statistics statistics;

    private Statistics(){}

    public static Statistics getInstance(){
        if (statistics == null) statistics = new Statistics();
        return statistics;
    }

    public void create_new_item(String text, int compl, int amount, LocalDate deadline, LocalDate date_finished){
        new Item_statistics(text, compl, amount, deadline, date_finished);
    }

    public void create_new_item(Goal goal){
        new Item_statistics(goal.getText(), goal.getCompleted(), goal.getCount(),
                goal.getDeadline(), LocalDate.now());
    }

    public class Item_statistics{
        public Item_statistics(String text, int compl, int amount, LocalDate deadline, LocalDate date_finished){
            this.text = text;
            this.deadline = deadline;
            this.date_finished = date_finished;
            this.amount = amount;
            this.completed = compl;
            item_statistics.add(this);
        }

        public String text;
        public LocalDate deadline, date_finished;
        public int completed;
        public int amount;
    }
}
