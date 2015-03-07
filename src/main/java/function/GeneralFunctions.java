package function;

import initialDataForTask.InitialData;
import integration.ResultIntegration;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 */
public class GeneralFunctions {
    private static InitialData initialData = new InitialData();
    private ResultIntegration resultIntegration = new ResultIntegration();
    private static GeneralFunctions generalFunctions = null;
    private volatile double P_ks, P_sc, V_sc, U, S, x;
    private GeneralFunctions() {
        resultIntegration.addResult("P_ks");
        resultIntegration.addResult("P_sc");
        resultIntegration.addResult("V_sc");
        resultIntegration.addResult("U");
        resultIntegration.addResult("S");
        initialization();
    }
    public static GeneralFunctions instance() {
        if (generalFunctions == null) {
            return generalFunctions = new GeneralFunctions();
        } else return generalFunctions;
    }

    public double p_ks(HashMap<String, Double> values) {
        return ( ( values.get("Massa_g") - values.get("Massa_sc") ) * initialData.R * values.get("Temperature") ) / values.get("Vks");
    }

    public double p_sc(HashMap<String, Double> values) {
        return ( values.get("Massa_sc") * initialData.R * values.get("Temperature") ) / v_sc(values) ;
    }

    public double v_sc(HashMap<String, Double> values) {
        return initialData.V0sc + initialData.Spor * values.get("X_sht");
    }

    public double U (HashMap<String, Double> values) {
        if (values.get("X") > initialData.e) {
            return 0;
        } else return initialData.K * (0.00546 + 5.36*Math.pow(10, -8)*p_ks(values));
    }

    public double S(HashMap<String, Double> values) {
        if (values.get("X") > initialData.e) {
            return 0;
        } else return initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*values.get("X");
    }

    public void calculate(HashMap<String, Double> values) {
        P_ks = p_ks(values);
        V_sc = v_sc(values);
        P_sc = p_sc(values);
        U = U(values);
        S = S(values);
        resultIntegration.setValueByName("P_ks", P_ks);
        resultIntegration.setValueByName("P_sc", P_sc);
        resultIntegration.setValueByName("V_sc", V_sc);
        resultIntegration.setValueByName("U", U);
        resultIntegration.setValueByName("S", S);
    }

    private void initialization () {
        P_ks = ( ( initialData.mgg0 - initialData.mgsc0 ) * initialData.R * initialData.Tks0 ) / initialData.Vks0;
        V_sc = initialData.V0sc + initialData.Spor * 0.0;
        P_sc = (initialData.mgsc0  * initialData.R * initialData.Tks0) / V_sc;
        U = initialData.K * (0.00546 + 5.36* Math.pow(10, -8)*P_ks);
        S = initialData.S0zar - 4 * Math.PI*(initialData.Dzar+initialData.dzar)*0.0;
        resultIntegration.setValueByName("P_ks", P_ks);
        resultIntegration.setValueByName("P_sc", P_sc);
        resultIntegration.setValueByName("V_sc", V_sc);
        resultIntegration.setValueByName("U", U);
        resultIntegration.setValueByName("S", S);
    }

    public ResultIntegration getResultIntegration() {
        return resultIntegration;
    }

    public static InitialData getInitialData() {
        return initialData;
    }
}
