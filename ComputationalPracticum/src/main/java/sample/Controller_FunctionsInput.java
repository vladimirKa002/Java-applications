package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*
Controller class for inputting equations

User is able to input differential equation and solution to it (f(x, y), y, and solution for C)
or input only differential equation and obtain solution by running WolframAlfa.

There is also an information button, that can show requirements for inputting equations.
*/
public class Controller_FunctionsInput {
    @FXML
    private TextArea Input_fxy;

    @FXML
    private TextArea Input_const;

    @FXML
    private TextArea Input_sol;

    @FXML
    private RadioButton Radio_Wolf;

    @FXML
    private RadioButton Radio_MySol;

    @FXML
    private GridPane Pane_MySolution;

    // When user presses the "Plot" button, the program will build graphs in a new window
    @FXML
    private void Plot() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View_Graphs.fxml"));
        stage.setTitle("Differential equations analyzer");
        stage.setScene(new Scene(fxmlLoader.load()));
        Controller_Graphs cg = fxmlLoader.getController();

        try{
            if (Radio_MySol.isSelected()){
                cg.setFunctions(new Function(Input_fxy.getText()),
                        new Function(Input_sol.getText()),
                        new Function(Input_const.getText()));
            }
            else {
                WolframAlphaCalculator wolframAlphaCalculator = new WolframAlphaCalculator();
                wolframAlphaCalculator.ODESolution(Input_fxy.getText(), cg);
            }

            cg.Plot();

            stage.show();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getClass().getSimpleName());
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    @FXML
    private void setPaneMySolActive(){
        Pane_MySolution.setDisable(!Radio_MySol.isSelected());
    }

    private Stage primaryStage;

    // Showing information box
    @FXML
    private void ShowInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View_InputInfo.fxml"));
        stage.setTitle("Differential equations analyzer");
        stage.setScene(new Scene((Parent) fxmlLoader.load()));

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.show();
    }

    // Custom design for "Information" button
    @FXML
    private void set_hover(MouseEvent event) {
        Node button = (Node) event.getSource();
        String current_st = button.getStyle();
        current_st = current_st.replaceAll(" ", "");
        if (current_st.contains("-fx-background-color:white")) {
            current_st = current_st.replace("-fx-background-color:white", "-fx-background-color:lightgrey");
        }
        else {
            current_st = current_st.replace("-fx-background-color:lightgrey", "-fx-background-color:white");
        }
        button.setStyle(current_st);
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
}
