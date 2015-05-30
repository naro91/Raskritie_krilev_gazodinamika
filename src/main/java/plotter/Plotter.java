package plotter;

import gui.MainGUI;
import initialDataForTask.InitialData;
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


    public static void plot(String nameGraphic, ResultIntegration resultIntegration, MainGUI mainGUI, boolean addToOld, double coeffConverUnitsOY, double coeffConverUnitsOX) {
        coeffConverUnitsOX = getCoefficientMultiplication(coeffConverUnitsOX);
        coeffConverUnitsOY = getCoefficientMultiplication(coeffConverUnitsOY);
        // метод для отображения соответствущего графика исходя из переданного значения nameGraphic
        XYSeries series = new XYSeries(nameGraphic);
        double step = resultIntegration.getStepFunctionByName(nameGraphic);
        double x0 = resultIntegration.getRangeStartByName(nameGraphic);
        for(double temp : resultIntegration.getHashMapNameAndArraylist().get(nameGraphic)){
            series.add(x0 * coeffConverUnitsOX, temp * coeffConverUnitsOY);
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
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.black);
            plot.setRangeGridlinePaint(Color.black);
        } else {
            nameGraphics.delete(0, nameGraphics.length() == 0 ? 0 : nameGraphics.length() - 1);
            nameGraphics.append(nameGraphic).append(", ");
            xyDataset = new XYSeriesCollection(series);
            chart = ChartFactory
                    .createXYLineChart(nameGraphic, "t", nameGraphic,
                            xyDataset,
                            PlotOrientation.VERTICAL,
                            true, true, true);
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.black);
            plot.setRangeGridlinePaint(Color.black);
        }
        //plotMarker(chart, resultIntegration);
        if (mainGUI.getFrame() == null) {
            mainGUI.setFrame(new JFrame("Program for NPO"));
            ChartPanel graphic = new ChartPanel(chart);
            // Помещаем график на фрейм
            mainGUI.getFrame().getContentPane().add(graphic);
            mainGUI.getFrame().setSize(1000, 600);
            mainGUI.getFrame().setLocationRelativeTo(null);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainGUI.getFrame().setVisible(true);
            arrayListJFrame.add(MainGUI.getFrame());
        }else {
            ChartPanel graphic = new ChartPanel(chart);
            if (tempChartPanel != null) {
                mainGUI.getPanel().remove(tempChartPanel);
                tempChartPanel = null;
            }
            tempChartPanel = graphic;
            graphic.setDomainZoomable(true);
            mainGUI.getPanel().setLayout(new BorderLayout());
            mainGUI.getPanel().add(graphic, BorderLayout.CENTER);
            mainGUI.getFrame().pack();
            mainGUI.getFrame().setVisible(true);
        }
    }

    public static void saveAllChart (ResultIntegration resultIntegration) {
        // метод для сохранения всех графиков
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            if (temp.equals("parameterIntegration")) continue;
            saveChart(temp, resultIntegration, false);
        }
    }

    public static void saveChart (String nameGraphic, ResultIntegration resultIntegration, boolean currentChart) {
        JFreeChart chartTemp;
        if (!currentChart) {
            // метод для сохранения соответствущего графика исходя из переданного значения nameGraphic
            XYSeries series = new XYSeries(nameGraphic);
            double step = resultIntegration.getStepFunctionByName(nameGraphic);
            double x0 = resultIntegration.getRangeStartByName(nameGraphic);
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

    private static double getCoefficientMultiplication (double coefficient) {
        if (coefficient < 0) {
            return (1.0/Math.abs(coefficient));
        } else return coefficient;
    }
}
