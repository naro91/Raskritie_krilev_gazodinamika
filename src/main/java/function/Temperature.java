package function;


import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 * Класс для вычисления температуры
 */
public class Temperature implements interfaceFunction {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;
    public Massa_sc massa_sc = new Massa_sc();
    public Temperature () {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
    }



    public static String getName() {
        return "Temperature";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        return ( values.get("Temperature") /  ( values.get("Vks") * generalFunctions.p_ks(values) )  )*
                ((generalFunctions.U(values)*generalFunctions.S(values)*initialData.gama*initialData.R/initialData.cv) *
                        (initialData.ksi*initialData.cp*initialData.Tp - initialData.cv*values.get("Temperature")) -
                                (initialData.k-1)*initialData.R*values.get("Temperature")*massa_sc.calculate(x,values));
    }

}
