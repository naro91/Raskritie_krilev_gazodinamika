package function;

import integration.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 */
public class X implements interfaceFunction {
    InitialData initialData;

    public X() {
        this.initialData = GeneralFunctions.initialData;
    }

    @Override
    public String getName() {
        return "X";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        if (values.get("X") <= initialData.e) {
            return GeneralFunctions.U(values);
        } else return 0;
    }
}
