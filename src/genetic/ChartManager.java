package genetic;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

/**
 * Created by r.makowiecki on 18/03/2017.
 */
public class ChartManager {

    public void displayChart(double[] best, double[] avg, double[] worst, String filename, String selectionType) {

        double[] xAxisValues = new double[GeneticAlgorithmManager.GENERATIONS_COUNT];
        for (int i=0; i<GeneticAlgorithmManager.GENERATIONS_COUNT; i++) {
            xAxisValues[i] = i + 1;
        }

        XYChart chart = new XYChartBuilder()
                .width(1920)
                .height(1080)
                .title("File: " + filename + ", selection: " + selectionType)
                .xAxisTitle("Generations")
                .yAxisTitle("Fitness")
                .build();
        chart.addSeries("Best in generation", xAxisValues, best);
        chart.addSeries("Average in generation", xAxisValues, avg);
        chart.addSeries("Worst in generation", xAxisValues, worst);
        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
