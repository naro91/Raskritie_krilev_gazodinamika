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
    public static void plot(double x0, double xFinish, String nameGraphic, ResultIntegration resultIntegration, boolean outputImage) {
        // метод для отображения соответствущего графика исходя из переданного значения nameGraphic
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
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        arrayListJFrame.add(frame);
    }

    public static void plotAllGraphics (double x0, double xFinish, ResultIntegration resultIntegration, boolean outputImage) {
        // метод для отображения всех графиков
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            if (temp.equals("parameterIntegration")) continue;
            plot(x0, xFinish, temp, resultIntegration, outputImage);
        }
    }

    public static void closeAllWindow () {  // метод предназначенный для закрытия всех окон графиков
        for (JFrame temp : arrayListJFrame) {
            temp.setVisible(false);
            temp.dispose();
        }
    }

}
