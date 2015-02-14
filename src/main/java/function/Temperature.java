package function;


import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
public class Temperature implements interfaceFunction {
    private double X0;
    public Temperature () {
        X0 = 0;
    }

    @Override
    public double calculateForInitialCondition() {
        return 0;
    }

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return Math.tan(x);
    }


    @Override
    public double getX0() {
        return 0;
    }

}
