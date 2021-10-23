package sample;

import javafx.scene.chart.XYChart;

// Abstract class for different methods of plotting equations
public abstract class Method {
    protected final Function function;
    protected final double x0;
    protected final double y0;
    protected final double X;

    Method(Function function, double x0, double y0, double X){
        this.function = function;
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
    }

    // Method for series calculation. Each method has its own implementation
    public abstract XYChart.Series getSeries(int N) throws MalformedExpressionException;

    // Getting series for local error
    public XYChart.Series countLocalError(XYChart.Series exact, XYChart.Series calculated){
        XYChart.Series error = new XYChart.Series();

        for (int i = 0; i < exact.getData().size() && i < calculated.getData().size(); i++){
            XYChart.Data<Double, Double> exactPoint = (XYChart.Data<Double, Double>) exact.getData().get(i);
            XYChart.Data<Double, Double> calcPoint = (XYChart.Data<Double, Double>) calculated.getData().get(i);
            error.getData().add(new XYChart.Data<>(exactPoint.getXValue(), Math.abs(exactPoint.getYValue() - calcPoint.getYValue())));
        }

        return error;
    }

    // Getting series for global error
    public XYChart.Series countGlobalError(Analytical analytical, int n0, int N) throws MalformedExpressionException {
        XYChart.Series error = new XYChart.Series();

        for (int i = n0; i <= N; i++){
            XYChart.Series<Double, Double> exact = analytical.getSeries(i);
            XYChart.Series<Double, Double> currentSeries = getSeries(i);
            double maxLocalError = 0;
            for (int j = 0; j < exact.getData().size() && j < currentSeries.getData().size(); j++){
                double currentValue = Math.abs(exact.getData().get(j).getYValue()
                        - currentSeries.getData().get(j).getYValue());
                if (maxLocalError < currentValue && Double.POSITIVE_INFINITY != currentValue){
                    maxLocalError = currentValue;
                }
            }
            error.getData().add(new XYChart.Data<>(i, maxLocalError));
        }

        return error;
    }
}
