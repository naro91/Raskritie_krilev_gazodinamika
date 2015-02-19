package function;


import integration.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
public class Temperature implements interfaceFunction {
    public InitialData initialData;
    public Massa_sc massa_sc = new Massa_sc();
    public Temperature () {
        this.initialData = GeneralFunctions.initialData;
    }


    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return (values.get("Temperature")/(values.get("Vks")*GeneralFunctions.p_ks(values)))*
                (GeneralFunctions.U(values)*GeneralFunctions.S(values)*initialData.gama*initialData.R/initialData.cv *
                        (initialData.ksi*initialData.cp*initialData.Tp - initialData.cv*values.get("Temperature")-
                                (initialData.k-1)*initialData.R*values.get("Temperature")*massa_sc.calculate(x,values)));
    }

}
