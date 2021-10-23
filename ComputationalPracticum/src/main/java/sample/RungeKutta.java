package sample;

import javafx.scene.chart.XYChart;

// Runge-Kutta method for plotting the graph
public class RungeKutta extends Method{

    RungeKutta(Function function, double x0, double y0, double X) {
        super(function, x0, y0, X);
    }

    @Override
    public XYChart.Series getSeries(int N) throws MalformedExpressionException{
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>(x0, y0));

        double h = Math.abs(X - x0) / N;

        double y = y0;
        for (double i = 0; i < N-1; i++){
            double k1 = function.calculate(x0 + i*h, y);
            double k2 = function.calculate(x0 + i*h + h/2, y + h/2 * k1);
            double k3 = function.calculate(x0 + i*h + h/2, y + h/2 * k2);
            double k4 = function.calculate(x0 + i*h + h, y + h * k3);
            y = y + h/6 * (k1 + 2 * k2 + 2 * k3 + k4);
            series.getData().add(new XYChart.Data<>(x0 + i*h + h, y));
        }

        return series;
    }
}
