package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Controller_Statistics extends Controller {
    @FXML
    private GridPane table;

    public void setTable(){
        Statistics statistics = task_manager.getStatistics();
        ArrayList<Statistics.Item_statistics> items = statistics.item_statistics;
        int i = 1;
        for (Statistics.Item_statistics item_statistics : items) {
            table.addRow(i);
            int col = 0;
            table.add(wrapText(item_statistics.text), col++, i);
            table.add(wrapText(item_statistics.completed+"/"+item_statistics.amount), col++, i);
            table.add(wrapText(convert_date(item_statistics.deadline)), col++, i);
            table.add(wrapText(convert_date(item_statistics.date_finished)), col, i);
            i++;
        }
    }

    public VBox wrapText(String text){
        VBox vb = new VBox(new Label(text));
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(5));
        vb.setStyle("-fx-font: 22 Arial");
        return vb;
    }
}
