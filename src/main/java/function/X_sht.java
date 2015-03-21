package function;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 15.02.15.
 */
public class X_sht implements interfaceFunction {

    public X_sht() {

    }


    public static String getName() {
        return "X_sht";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return values.get("Velocity");
    }

}
