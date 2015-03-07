package plotter;
import integration.ResultIntegration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;



/**
 * Created by Abovyan Narek on 07.03.15.
 */
public class Plotter {

    public static void plot(double x0, double xFinish, String nameGraphic, ResultIntegration resultIntegration) {
        XYSeries series = new XYSeries(nameGraphic);
        double step = (xFinish - x0) / resultIntegration.getHashMapNameAndArraylist().get(nameGraphic).size();
        for(double temp : resultIntegration.getHashMapNameAndArraylist().get(nameGraphic)){
            series.add(x0, temp);
            x0 += step;
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(nameGraphic, "t", nameGraphic,
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.setVisible(true);
    }

    public static void plotAllGraphics (double x0, double xFinish, ResultIntegration resultIntegration) {
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            plot(x0, xFinish, temp, resultIntegration);
        }
    }

}
