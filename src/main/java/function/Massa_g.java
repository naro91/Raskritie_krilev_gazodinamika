package function;

import integration.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 */
public class Massa_g implements interfaceFunction {
    InitialData initialData;

    public Massa_g() {
        this.initialData = GeneralFunctions.initialData;
    }

    @Override
    public String getName() {
        return "Massa_g";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return GeneralFunctions.U(values)*GeneralFunctions.S(values)*initialData.gama;
    }




}
