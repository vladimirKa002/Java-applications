package sample;

import javafx.scene.chart.XYChart;

// Analytical method for plotting the graph
public class Analytical extends Method{

    Analytical(Function function, double x0, double y0, double X) {
        super(function, x0, y0, X);
    }

    @Override
    public XYChart.Series getSeries(int N) throws MalformedExpressionException {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>(x0, y0));

        double h = (X - x0) / N;

        for (double i = 0; i<N-1; i++){
            series.getData().add(new XYChart.Data<>(x0 + (i + 1)*h, function.calculate(x0 + (i + 1)*h, 0)));
        }

        return series;
    }
}
