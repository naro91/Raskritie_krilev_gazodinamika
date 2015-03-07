package plotter;
import integration.ResultIntegration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by Abovyan Narek on 07.03.15.
 */
public class Plotter {

    public static void plot(double x0, double xFinish, String nameGraphic, ResultIntegration resultIntegration, boolean outputImage) {
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
        if (outputImage) {
            try {
                // например в файл
                OutputStream stream = new FileOutputStream("./graphicsImage/".concat(nameGraphic.concat(".png")));
                ChartUtilities.writeChartAsPNG(stream, chart, 700, 500);
            } catch (IOException e) {
                System.err.println("Failed to render chart as png: " + e.getMessage());
                e.printStackTrace();
            }
        }

        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(600,400);
        frame.setVisible(true);
    }

    public static void plotAllGraphics (double x0, double xFinish, ResultIntegration resultIntegration, boolean outputImage) {
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            if (temp.equals("parameterIntegration")) continue;
            plot(x0, xFinish, temp, resultIntegration, outputImage);
        }
    }

}
