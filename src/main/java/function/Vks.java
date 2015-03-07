package function;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 */
public class Vks implements interfaceFunction {
    private GeneralFunctions generalFunctions;
    public Vks () {
        this.generalFunctions = GeneralFunctions.instance();
    }

    @Override
    public String getName() {
        return "Vks";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return generalFunctions.U(values)*generalFunctions.S(values);
    }
}
