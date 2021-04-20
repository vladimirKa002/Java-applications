package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sample.Items.Goal;
import sample.Items.Item;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Task_manager {
    private static Task_manager task_manager;
    public ArrayList<Goal> goals = new ArrayList<>(10);
    private Controller_Main cm;
    private Statistics statistics = Statistics.getInstance();

    public Statistics getStatistics(){
        return statistics;
    }

    private Task_manager(){
        try {
            load_items();
            load_statistics();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Task_manager generateTask_manager(){
        if (task_manager==null) task_manager = new Task_manager();
        return task_manager;
    }

    public void set_goal(Goal goal) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View_Goal_Block.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_Goal_Block ctb = fxmlLoader.getController();
        ctb.setGoal(goal);
        goal.setGui(ctb, root);
    }

    public void add_goal(Goal goal){
        goals.add(goal);
        set_goal(goal);
        if (cm!=null) cm.update();
    }

    public void add_goal_count(Goal goal, int val){
        if (goal.add_value(val)){
            goals.remove(goal);
            statistics.create_new_item(goal);
        }
        if (cm!=null) cm.update();
    }

    public void delete_item(Item item){
        if (goals.contains(item)) goals.remove(item);
        if (cm!=null) cm.update();
    }

    public void finish_item(Item item){
        if (goals.contains(item)) {
            statistics.create_new_item((Goal) item);
            goals.remove(item);
        }
        if (cm!=null) cm.update();
    }

    public void setCm(Controller_Main cm){
        this.cm = cm;
        cm.update();
    }

    private void load_items() throws FileNotFoundException {
        Scanner in = new Scanner(new File(new File("").getAbsolutePath()+"/data/Items.txt"),
                "UTF-8");
        while (in.hasNextLine()){
            // [{name=Goal}{text=lalala};{count=0};{completed=0};{deadline=0.0.0};
            // {additional_info=blablabla};{points=0}]
            String data = in.nextLine();
            if (!data.contains("[")) continue;
            String name = data.substring(data.indexOf("=")+1, data.indexOf("}"));
            data = data.substring(data.indexOf(";")+1);
            if (name.equals("Goal")){
                String text = data.substring(data.indexOf("=")+1, data.indexOf("}"));
                data = data.substring(data.indexOf(";")+1);
                int count = Integer.parseInt(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                int completed = Integer.parseInt(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                LocalDate deadline = LocalDate.parse(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                String text_add_i = data.substring(data.indexOf("=")+1, data.indexOf("}"));
                data = data.substring(data.indexOf(";")+1);
                Goal goal = new Goal(text, text_add_i, count, completed, deadline);
                add_goal(goal);
            }
        }
    }

    public void save_items() throws FileNotFoundException, UnsupportedEncodingException {
        Formatter formatter = new Formatter(new File(new File("").getAbsolutePath()+"/data/Items.txt"),
                "UTF-8");
        for (Goal goal : goals) {
            formatter.format("[{name=Goal};{text=" + goal.getText()+"};{count="+ goal.getCount()+"};{completed="+
                    goal.getCompleted()+"};{deadline="+ goal.getDeadline().toString()
                    +"};{additional_info="+ goal.getText_additional_info()+"}]\n");
        }
        formatter.close();
    }

    private void load_statistics() throws FileNotFoundException {
        Scanner in = new Scanner(new File(new File("")
                .getAbsolutePath()+"/data/Statistics.txt"), "UTF-8");
        while (in.hasNextLine()){
            // [{name=Goal};{completed=0};{amount=0};{text=lalala};{deadline=0.0.0};{date_finished=0.0.0};{points=0}]
            String data = in.nextLine();
            if (!data.contains("[")) continue;
            String name = data.substring(data.indexOf("=")+1, data.indexOf("}"));
            data = data.substring(data.indexOf(";")+1);
            if (name.equals("Goal")){
                int compl = Integer.parseInt(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                int amount = Integer.parseInt(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                String text = data.substring(data.indexOf("=")+1, data.indexOf("}"));
                data = data.substring(data.indexOf(";")+1);
                LocalDate deadline = LocalDate.parse(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                LocalDate date_finished = LocalDate.parse(data.substring(data.indexOf("=")+1, data.indexOf("}")));
                data = data.substring(data.indexOf(";")+1);
                statistics.create_new_item(text, compl, amount, deadline, date_finished);
            }
        }
    }

    public void save_statistics() throws FileNotFoundException, UnsupportedEncodingException {
        Formatter formatter = new Formatter(new File(new File("").getAbsolutePath()+"/data/Statistics.txt"),
                "UTF-8");
        for (Statistics.Item_statistics item : statistics.item_statistics) {
            formatter.format("[{name=Goal};"+"{completed=" + item.completed+"};"+ "{amount=" + item.amount+
                    "};"+"{text=" + item.text+"};{deadline="+ item.deadline.toString()
                    +"};{date_finished="+ item.date_finished+"}]\n");
        }
        formatter.close();
    }
}
