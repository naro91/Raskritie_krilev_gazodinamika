package function;

import integration.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 */
public class GeneralFunctions {
    public static InitialData initialData = new InitialData();

    public static double p_ks(HashMap<String, Double> values) {
        return ( ( values.get("Massa_g") - values.get("Massa_sc") ) * initialData.R * values.get("Temperature") ) / values.get("Vks");
    }

    public static double p_sc(HashMap<String, Double> values) {
        return ( values.get("Massa_sc") * initialData.R * values.get("Temperature") ) / v_sc(values) ;
    }

    public static double v_sc(HashMap<String, Double> values) {
        return initialData.V0sc + initialData.Spor * values.get("X_sht");
    }

    public static double U (HashMap<String, Double> values) {
        return initialData.K * (0.00546 + 5.36*Math.pow(10, -8)*p_ks(values));
    }

    public static double S(HashMap<String, Double> values) {
        if (values.get("X") > initialData.e) {
            return 0;
        } else return initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*values.get("X");
    }
}
