package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 18.02.15.
 */
public class Massa_g implements interfaceFunction {
    InitialData initialData;
    GeneralFunctions generalFunctions;

    public Massa_g() {
        this.initialData = GeneralFunctions.getInitialData();
        generalFunctions = GeneralFunctions.instance();
    }

    @Override
    public String getName() {
        return "Massa_g";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        //calculateOtherParametrs(values);
        return generalFunctions.U(values) * generalFunctions.S(values)*initialData.gama;
    }

    //public void calculateOtherParametrs(HashMap<String, Double> values) {
    //    generalFunctions.getResultIntegration().setValueByName("U", U);
    //    generalFunctions.getResultIntegration().setValueByName("S", S);
    //    generalFunctions.getResultIntegration().setValueByName("P_ks", Pks);
    //    generalFunctions.getResultIntegration().setValueByName("P_sc", Psc);
    //}


}
