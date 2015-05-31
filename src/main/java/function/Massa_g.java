package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 * Класс для вычисления общей массы газа
 */
public class Massa_g implements interfaceFunction {
    InitialData initialData;
    GeneralFunctions generalFunctions;

    public Massa_g() {
        this.initialData = GeneralFunctions.getInitialData();
        generalFunctions = GeneralFunctions.instance();
    }


    public static String getName() {
        return "Massa_g";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return generalFunctions.U(values) * generalFunctions.S(values)*initialData.gama;
    }

}
