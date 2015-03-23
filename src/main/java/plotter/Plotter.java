package plotter;

import integration.ResultIntegration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * Created by Abovyan Narek on 07.03.15.
 * Класс инкапсулирующий действия связанные с отображением графиков
 */

public class Plotter {
    private static ArrayList<JFrame> arrayListJFrame = new ArrayList<>();
    private static ChartPanel tempChartPanel = null;
    private static XYSeriesCollection xyDataset;
    private static StringBuilder nameGraphics = new StringBuilder();
    private static JFreeChart chart;
    public static void plot(double x0, double xFinish, String nameGraphic, ResultIntegration resultIntegration, JFrame frame, boolean addToOld) {
        // метод для отображения соответствущего графика исходя из переданного значения nameGraphic
        XYSeries series = new XYSeries(nameGraphic);
        double step = (xFinish - x0) / resultIntegration.getHashMapNameAndArraylist().get(nameGraphic).size();
        for(double temp : resultIntegration.getHashMapNameAndArraylist().get(nameGraphic)){
            series.add(x0, temp);
            x0 += step;
        }

        if (addToOld && xyDataset != null) {
            xyDataset.addSeries(series);
            nameGraphics.append(nameGraphic).append(", ");
            chart = ChartFactory
                    .createXYLineChart(nameGraphics.toString(), "t", nameGraphics.toString(),
                            xyDataset,
                            PlotOrientation.VERTICAL,
                            true, true, true);
        } else {
            nameGraphics.delete(0, nameGraphics.length() == 0 ? 0 : nameGraphics.length() - 1);
            nameGraphics.append(nameGraphic).append(", ");
            xyDataset = new XYSeriesCollection(series);
            chart = ChartFactory
                    .createXYLineChart(nameGraphic, "t", nameGraphic,
                            xyDataset,
                            PlotOrientation.VERTICAL,
                            true, true, true);
        }
        plotMarker(chart, resultIntegration);
        if (frame == null) {
            frame = new JFrame("Program for NPO");
            // Помещаем график на фрейм
            frame.getContentPane().add(new ChartPanel(chart));
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            arrayListJFrame.add(frame);
        }else {
            ChartPanel graphic = new ChartPanel(chart);
            if (tempChartPanel != null) {
                frame.remove(tempChartPanel);
                tempChartPanel = null;
            }
            tempChartPanel = graphic;
            frame.add(graphic);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public static void saveAllChart (double x0, double xFinish, ResultIntegration resultIntegration) {
        // метод для отображения всех графиков
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            if (temp.equals("parameterIntegration")) continue;
            saveChart(x0, xFinish, temp, resultIntegration, false);
        }
    }

    public static void saveChart (double x0, double xFinish, String nameGraphic, ResultIntegration resultIntegration, boolean currentChart) {
        JFreeChart chartTemp;
        if (!currentChart) {
            // метод для отображения соответствущего графика исходя из переданного значения nameGraphic
            XYSeries series = new XYSeries(nameGraphic);
            double step = (xFinish - x0) / resultIntegration.getHashMapNameAndArraylist().get(nameGraphic).size();
            for(double temp : resultIntegration.getHashMapNameAndArraylist().get(nameGraphic)){
                series.add(x0, temp);
                x0 += step;
            }
            XYSeriesCollection xyDatasetTemp = new XYSeriesCollection(series);
            chartTemp = ChartFactory
                    .createXYLineChart(nameGraphic, "t", nameGraphic,
                            xyDatasetTemp,
                            PlotOrientation.VERTICAL,
                            true, true, true);
        } else chartTemp = chart;

        try {
            // сохранение в файл
            OutputStream stream = new FileOutputStream("./graphicsImage/".concat(nameGraphic.concat(".png")));
            ChartUtilities.writeChartAsPNG(stream, chartTemp, 700, 500);
        } catch (IOException e) {
            System.err.println("Failed to render chart as png: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void plotMarker(JFreeChart chart, ResultIntegration resultIntegration) {
        XYPlot plot = (XYPlot) chart.getPlot();
        for (String temp : resultIntegration.getMarker().keySet()) {
            ValueMarker marker = new ValueMarker(resultIntegration.getMarker().get(temp));  // position is the value on the axis
            marker.setPaint(Color.black);
            marker.setLabel(temp); // see JavaDoc for labels, colors, strokes
            plot.addDomainMarker(marker);
        }
    }
}
