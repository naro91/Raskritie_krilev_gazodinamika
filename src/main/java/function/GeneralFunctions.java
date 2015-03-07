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
        initialization();
        resultIntegration.addResult("P_ks");
        resultIntegration.addResult("P_sc");
        resultIntegration.addResult("V_sc");
        resultIntegration.addResult("U");
        resultIntegration.addResult("S");
    }
    public static GeneralFunctions instance() {
        if (generalFunctions == null) {
            return new GeneralFunctions();
        } else return generalFunctions;
    }

    public double p_ks(double x, HashMap<String, Double> values) {
        if (this.x == x) {
            return P_ks;
        } else {
            calculate(x, values);
            return P_ks;
        }
    }

    public double p_sc(double x, HashMap<String, Double> values) {
        if (this.x == x) {
            return P_sc;
        } else {
            calculate(x, values);
            return P_sc;
        }
    }

    public double v_sc(double x, HashMap<String, Double> values) {
        if (this.x == x) {
            return V_sc;
        } else {
            calculate(x, values);
            return V_sc;
        }
    }

    public double U (double x, HashMap<String, Double> values) {
        if (this.x == x) {
            return U;
        } else {
            calculate(x, values);
            return U;
        }
    }

    public double S(double x, HashMap<String, Double> values) {
        if (this.x == x) {
            return S;
        } else {
            calculate(x, values);
            return S;
        }
    }

    private void calculate(double x, HashMap<String, Double> values) {
        P_ks = ( ( values.get("Massa_g") - values.get("Massa_sc") ) * initialData.R * values.get("Temperature") ) / values.get("Vks");
        V_sc = initialData.V0sc + initialData.Spor * values.get("X_sht");
        P_sc = ( values.get("Massa_sc") * initialData.R * values.get("Temperature") ) / V_sc;
        U = initialData.K * (0.00546 + 5.36*Math.pow(10, -8)*P_ks);
        S = ( values.get("X") > initialData.e ) ? 0 : initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*values.get("X");
        this.x = x;
    }

    private void initialization () {
        P_ks = ( ( initialData.mgg0 - initialData.mgsc0 ) * initialData.R * initialData.Tks0 ) / initialData.Vks0;
        V_sc = initialData.V0sc + initialData.Spor * 0.0;
        P_sc = ( initialData.mgsc0  * initialData.R * initialData.Tks0 ) / V_sc;
        U = initialData.K * (0.00546 + 5.36*Math.pow(10, -8)*P_ks);
        S = initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*0.0;
        this.x = 0.0;
    }

    public ResultIntegration getResultIntegration() {
        return resultIntegration;
    }

    public static InitialData getInitialData() {
        return initialData;
    }
}
