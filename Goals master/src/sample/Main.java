package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {
    private Task_manager task_manager;
    public static Stage primaryStage;
    public static Image icon;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Task_manager task_manager = Task_manager.generateTask_manager();
        this.task_manager = task_manager;
        Controller.setTask_manager(task_manager);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View_Main.fxml"));
        Main.primaryStage = primaryStage;
        Parent root = fxmlLoader.load();
        Controller_Main cm = fxmlLoader.getController();
        primaryStage.setTitle("Goals master");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        setIcon();
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    private void setIcon(){
        Class<?> rrr = this.getClass();
        InputStream inputIC = rrr.getResourceAsStream("icon.png");
        icon = new Image (inputIC);
    }

    @Override
    public void stop() throws Exception {
        task_manager.save_items();
        task_manager.save_statistics();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
