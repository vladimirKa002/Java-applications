package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Insets;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    BuildMatrix bm = new BuildMatrix();

    @Override
    public void start(Stage stage) throws Exception{

        TextField wid = new TextField();
        TextField hei = new TextField();
        HBox width = new HBox(10, new Label("Width: "), wid);
        HBox height = new HBox(10, new Label("Height: "), hei);

        RadioButton num = new RadioButton("Number");
        RadioButton cha = new RadioButton("Symbol");
        ToggleGroup elemToggle = new ToggleGroup();
        num.setToggleGroup(elemToggle);
        cha.setToggleGroup(elemToggle);

        ToggleGroup formToggle = new ToggleGroup();
        RadioButton hor = new RadioButton("Horizontal"); hor.setToggleGroup(formToggle);
        RadioButton vert = new RadioButton("Vertical"); vert.setToggleGroup(formToggle);
        RadioButton diag = new RadioButton("Diagonal"); diag.setToggleGroup(formToggle);
        RadioButton spIN = new RadioButton("Spiral inside"); spIN.setToggleGroup(formToggle);
        RadioButton spOUT = new RadioButton("Spiral outside"); spOUT.setToggleGroup(formToggle);
        RadioButton sph = new RadioButton("Sphere"); sph.setToggleGroup(formToggle);

        Button btnShow = new Button("Show");
        VBox cont1 = new VBox(10, new Label("Size: "), width, height,
                new Label("Elements: "), num, cha,
                new Label("Form: "), hor, vert, diag, spIN, spOUT, sph);
        cont1.setAlignment(Pos.TOP_LEFT);
        VBox cont2 = new VBox(10, btnShow);
        cont2.setAlignment(Pos.TOP_CENTER);

        VBox contM = new VBox(10, cont1, cont2);
        contM.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(cont1, new Insets(5,0,0,5));

        btnShow.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String widthSTR = wid.getText();
                String heightSTR = hei.getText();
                int width = 0;
                int height = 0;
                check :
                {
                    for (int i = 0; i < widthSTR.length(); i++) {
                        if (widthSTR.charAt(i) - 48 < 0 | widthSTR.charAt(i) - 48 > 9) break check;
                        width = width * 10 + (widthSTR.charAt(i) - 48);
                    }
                    for (int i = 0; i < heightSTR.length(); i++) height = height * 10 + (heightSTR.charAt(i) - 48);
                    boolean symb = false;
                    if (elemToggle.getSelectedToggle() == cha) symb = true;
                    RadioButton choiceForm = (RadioButton) formToggle.getSelectedToggle();
                    try {
                        String form = choiceForm.getText();
                        stage.setScene(bm.build(width, height, symb, form));
                    } catch (RuntimeException exc) {

                    }
                }
            }
        });

        stage.setTitle("Matrix");
        Scene scnMain = new Scene(contM, 250, 430);
        stage.setScene(scnMain);

        bm.btnOk.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(scnMain);
            }
        });

        stage.show();
    }

}
