package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import static java.lang.Thread.*;

public class LabelTime extends Label {
    private double time = 60.0;

    LabelTime(){
        setText("Time: "+time);
        setStyle(String.format("-fx-font: 22 arial"));

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                while (time>0){
                    String timeStr = Double.toString(time).substring(0, Double.toString(time).indexOf('.')+2);
                    Platform.runLater(() -> setText("Time: "+timeStr));
                    time-=0.1;
                    try{
                        sleep(100);
                    }catch (InterruptedException e){}
                }
                Platform.runLater(() -> {
                    Main.setSceneGameOver();
                    Main.controllerGameOver.setText();
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    public void resetTime(int a){
        time+=a;
    }
}
