package sample;

import javafx.scene.chart.XYChart;

// Euler method for plotting the graph
public class Euler extends Method{

    Euler(Function function, double x0, double y0, double X) {
        super(function, x0, y0, X);
    }

    @Override
    public XYChart.Series getSeries(int N) throws MalformedExpressionException {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>(x0, y0));

        double h = Math.abs(X - x0) / N; // Step

        double y = y0;
        for (double i = 0; i<N-1; i++){
            y = y + h * function.calculate(x0 + i * h, y);
            series.getData().add(new XYChart.Data<>(x0 + i * h + h, y));
        }

        return series;
    }
}
