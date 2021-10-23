package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// Controller class for plotting the graphs
public class Controller_Graphs {

    @FXML
    private GridPane ChartBox_Graphs;

    @FXML
    private GridPane ChartBox_LocalErr;

    @FXML
    private GridPane ChartBox_GlobalErr;

    private LineChart[] charts;

    @FXML
    private TextField Input_x0;

    @FXML
    private TextField Input_y0;

    @FXML
    private TextField Input_X;

    @FXML
    private TextField Input_N;

    @FXML
    private TextField Input_n0;

    @FXML
    private TextField Input_NGlob;

    @FXML
    private CheckBox CheckBox_Analyt;

    @FXML
    private CheckBox CheckBox_Euler;

    @FXML
    private CheckBox CheckBox_ImprEuler;

    @FXML
    private CheckBox CheckBox_RungeKutta;

    @FXML
    private Label Label_Function;

    private Function exactSolution;
    private Function fxyFunction;
    private Function cFunction;

    private XYChart.Series Chart_analyticalSeries;
    private XYChart.Series Chart_EulerSeries;
    private XYChart.Series Chart_ImprEulerSeries;
    private XYChart.Series Chart_RungeKuttaSeries;

    private XYChart.Series Chart_LocalErr_Euler;
    private XYChart.Series Chart_LocalErr_ImprEuler;
    private XYChart.Series Chart_LocalErr_RungeKutta;

    private XYChart.Series Chart_GlobErr_Euler;
    private XYChart.Series Chart_GlobErr_ImprEuler;
    private XYChart.Series Chart_GlobErr_RungeKutta;

    private static final String ANALYTICAL_COLOR = "00eb3f";
    private static final String EULER_COLOR = "ffaf00";
    private static final String IMPREULER_COLOR = "3000ff";
    private static final String RUNGEKUTTA_COLOR = "ff0000";

    // Plotting the graphs
    @FXML
    public void Plot() throws MalformedExpressionException{
        for (LineChart lineChart: charts){
            lineChart.getData().clear();
        }

        double x0;
        double y0;
        double X;
        int N;
        int n0;
        int NGlob;

        try {
            x0 = new Function(Input_x0.getText()).calculate(0, 0);
            y0 = new Function(Input_y0.getText()).calculate(0, 0);
            X = new Function(Input_X.getText()).calculate(0, 0);
            N = Integer.parseInt(Input_N.getText());
            n0 = Integer.parseInt(Input_n0.getText());
            NGlob = Integer.parseInt(Input_NGlob.getText());
        }
        catch (MalformedExpressionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getClass().getSimpleName());
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }

        exactSolution.setC(cFunction.calculate(x0, y0));

        Analytical analytical = new Analytical(exactSolution, x0, y0, X);
        Chart_analyticalSeries = analytical.getSeries(N);
        ShowAnalytical();

        Euler euler = new Euler(fxyFunction, x0, y0, X);
        Chart_EulerSeries = euler.getSeries(N);
        Chart_LocalErr_Euler = euler.countLocalError(Chart_analyticalSeries, Chart_EulerSeries);
        Chart_GlobErr_Euler = euler.countGlobalError(analytical, n0, NGlob);
        ShowEuler();

        ImprovedEuler improvedEuler = new ImprovedEuler(fxyFunction, x0, y0, X);
        Chart_ImprEulerSeries = improvedEuler.getSeries(N);
        Chart_LocalErr_ImprEuler = improvedEuler.countLocalError(Chart_analyticalSeries, Chart_ImprEulerSeries);
        Chart_GlobErr_ImprEuler = improvedEuler.countGlobalError(analytical, n0, NGlob);
        ShowImprEuler();

        RungeKutta rungeKutta = new RungeKutta(fxyFunction, x0, y0, X);
        Chart_RungeKuttaSeries = rungeKutta.getSeries(N);
        Chart_LocalErr_RungeKutta = rungeKutta.countLocalError(Chart_analyticalSeries, Chart_RungeKuttaSeries);
        Chart_GlobErr_RungeKutta = rungeKutta.countGlobalError(analytical, n0, NGlob);
        ShowRungeKutta();
    }

    // Functions for enabling/disabling graphs
    @FXML
    private void ShowAnalytical(){
        if (!CheckBox_Analyt.isSelected()){
            charts[0].getData().remove(Chart_analyticalSeries);
        }
        else {
            charts[0].getData().add(Chart_analyticalSeries);
            ((Node) Chart_analyticalSeries.nodeProperty().get()).setStyle("-fx-stroke: #" + ANALYTICAL_COLOR + ";");
        }
    }

    @FXML
    private void ShowEuler(){
        if (!CheckBox_Euler.isSelected()){
            charts[0].getData().remove(Chart_EulerSeries);
            charts[1].getData().remove(Chart_LocalErr_Euler);
            charts[2].getData().remove(Chart_GlobErr_Euler);
        }
        else {
            charts[0].getData().add(Chart_EulerSeries);
            charts[1].getData().add(Chart_LocalErr_Euler);
            charts[2].getData().add(Chart_GlobErr_Euler);

            ((Node) Chart_EulerSeries.nodeProperty().get()).setStyle("-fx-stroke: #" + EULER_COLOR + ";");
            ((Node) Chart_LocalErr_Euler.nodeProperty().get()).setStyle("-fx-stroke: #" + EULER_COLOR + ";");
            ((Node) Chart_GlobErr_Euler.nodeProperty().get()).setStyle("-fx-stroke: #" + EULER_COLOR + ";");
        }
    }

    @FXML
    private void ShowImprEuler(){
        if (!CheckBox_ImprEuler.isSelected()){
            charts[0].getData().remove(Chart_ImprEulerSeries);
            charts[1].getData().remove(Chart_LocalErr_ImprEuler);
            charts[2].getData().remove(Chart_GlobErr_ImprEuler);
        }
        else {
            charts[0].getData().add(Chart_ImprEulerSeries);
            charts[1].getData().add(Chart_LocalErr_ImprEuler);
            charts[2].getData().add(Chart_GlobErr_ImprEuler);

            ((Node) Chart_ImprEulerSeries.nodeProperty().get()).setStyle("-fx-stroke: #" + IMPREULER_COLOR + ";");
            ((Node) Chart_LocalErr_ImprEuler.nodeProperty().get()).setStyle("-fx-stroke: #" + IMPREULER_COLOR + ";");
            ((Node) Chart_GlobErr_ImprEuler.nodeProperty().get()).setStyle("-fx-stroke: #" + IMPREULER_COLOR + ";");
        }
    }

    @FXML
    private void ShowRungeKutta(){
        if (!CheckBox_RungeKutta.isSelected()){
            charts[0].getData().remove(Chart_RungeKuttaSeries);
            charts[1].getData().remove(Chart_LocalErr_RungeKutta);
            charts[2].getData().remove(Chart_GlobErr_RungeKutta);
        }
        else {
            charts[0].getData().add(Chart_RungeKuttaSeries);
            charts[1].getData().add(Chart_LocalErr_RungeKutta);
            charts[2].getData().add(Chart_GlobErr_RungeKutta);

            ((Node) Chart_RungeKuttaSeries.nodeProperty().get()).setStyle("-fx-stroke: #" + RUNGEKUTTA_COLOR + ";");
            ((Node) Chart_LocalErr_RungeKutta.nodeProperty().get()).setStyle("-fx-stroke: #" + RUNGEKUTTA_COLOR + ";");
            ((Node) Chart_GlobErr_RungeKutta.nodeProperty().get()).setStyle("-fx-stroke: #" + RUNGEKUTTA_COLOR + ";");
        }
    }

    // Initializing graphical elements
    @FXML
    public void initialize() {
        GridPane[] chartBoxes = {ChartBox_Graphs, ChartBox_LocalErr, ChartBox_GlobalErr};
        charts = new LineChart[3];

        for(int i = 0; i<chartBoxes.length;i++){
            chartBoxes[i].getChildren().clear();
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            final LineChart<Number,Number> lineChart =
                    new LineChart<>(xAxis,yAxis);
            lineChart.setCreateSymbols(false);
            lineChart.setAnimated(false);
            lineChart.setLegendVisible(false);
            chartBoxes[i].getChildren().add(lineChart);
            charts[i] = lineChart;
        }
    }

    // Initialization of a new graph plotting window by setting functions
    public void setFunctions(Function fxyFunction, Function exactSolution, Function cFunction){
        this.fxyFunction = fxyFunction;
        this.exactSolution = exactSolution;
        this.cFunction = cFunction;

        Label_Function.setText("Function:\ndy/dx = " + fxyFunction.getExpression());
    }
}