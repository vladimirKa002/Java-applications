package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.String;
import java.io.InputStream;

public class Main extends Application {
    GetData gd = new GetData();
    public String result[];
    public Class<?> rrr = this.getClass();
    public InputStream input = rrr.getResourceAsStream("images/123.png");
    public Image image = new Image(input);
    public ImageView imageView = new ImageView(image);;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Car encyclopedia");
        Label out = new Label("");

        TreeItem<String> choice = new TreeItem<String>("Sport cars");
        porsche: {
            TreeItem<String> porsche = new TreeItem<String>("Porsche");
            choice.getChildren().add(porsche);
            TreeItem<String> porsche911 = new TreeItem<String>("911");
            porsche.getChildren().add(porsche911);
            porsche911.getChildren().addAll(
                    new TreeItem<String>("Carrera S"),
                    new TreeItem<String>("Carrera S Cabriolet"),
                    new TreeItem<String>("Carrera 4S"),
                    new TreeItem<String>("Carrera 4S Cabriolet"),
                    new TreeItem<String>("Speedster"),
                    new TreeItem<String>("GT2 RS"),
                    new TreeItem<String>("GT3 RS"));
            TreeItem<String> porsche918 = new TreeItem<String>("918");
            porsche.getChildren().add(porsche918);
            porsche918.getChildren().addAll(new TreeItem<String>("Spyder"));
        }
        TreeView<String> langsTreeView = new TreeView<String>(choice);
        langsTreeView.setPrefSize(200, 300);

        Button btnChoose = new Button("Choose a car");

        HBox cont1 = new HBox(20, langsTreeView);
        HBox cont3 = new HBox(20, imageView, out);
        VBox cont2 = new VBox(30, cont1, cont3);
        cont1.setAlignment(Pos.TOP_CENTER);
        cont2.setAlignment(Pos.CENTER);
        cont3.setAlignment(Pos.CENTER);
        Scene scn = new Scene(cont2, 1000, 600);
        primaryStage.setScene(scn);

        MultipleSelectionModel<TreeItem<String>> slModel = langsTreeView.getSelectionModel();

        slModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

            public void changed(ObservableValue<? extends TreeItem<String>> changed,
                                TreeItem<String> oldValue, TreeItem<String> newValue){

                if (newValue.getChildren().isEmpty() == false) { return; }

                String name = new String(newValue.getValue());
                TreeItem<String> parent = newValue.getParent();
                while(parent != choice){
                    name = parent.getValue() + " " + name;
                    parent = parent.getParent();
                }
                result = gd.getCar(name);
                out.setText(result[0]);
                if (!result[1].equals("ha")) {
                    input = rrr.getResourceAsStream(result[1]);
                    image = new Image(input);
                    imageView.setImage(image);
                    cont2.getChildren().remove(0);
                    cont2.getChildren().add(0, btnChoose);
                }
                else {
                    input = rrr.getResourceAsStream("images/123.png");
                    image = new Image(input);
                    imageView.setImage(image);
                }
            }
        });

        btnChoose.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent ae) {
                input = rrr.getResourceAsStream("images/123.png");
                image = new Image(input);
                imageView.setImage(image);
                out.setText("");
                cont2.getChildren().remove(0);
                cont2.getChildren().add(0, cont1);
            }
        });

        primaryStage.show();
    }
}

