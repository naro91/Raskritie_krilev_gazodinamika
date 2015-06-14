package function;

import initialDataForTask.InitialData;
import util.GeneralAlgorithms;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 15.02.15.
 */
public class X_sht implements interfaceFunction {
    private InitialData initialData;
    private double endOfInterval = 0;

    public X_sht(double endOfInterval) {
        this.endOfInterval = endOfInterval;
        this.initialData = GeneralFunctions.getInitialData();
    }


    public static String getName() {
        return "X_sht";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) { // возвращает скорость движения штока
        if (values.get("X_sht") < endOfInterval) {
            return values.get("Velocity");
        } else return 0;

    }

}
