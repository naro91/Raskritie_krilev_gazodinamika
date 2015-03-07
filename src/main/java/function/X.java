package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 */

// Класс для представления функции изменения скорости горения
    // интеграл дает величину сгоревшей части

public class X implements interfaceFunction {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;

    public X() {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
    }

    @Override
    public String getName() {
        return "X";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        if (values.get("X") <= initialData.e) {
            double result = generalFunctions.U(values);
            return result;
        } else return 0;
    }
}
