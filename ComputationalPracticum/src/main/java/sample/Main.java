package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// The class that runs application
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Initializing equations input form
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View_FunctionsInput.fxml"));
        primaryStage.setTitle("Differential equations analyzer");
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((Controller_FunctionsInput) fxmlLoader.getController()).setPrimaryStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
