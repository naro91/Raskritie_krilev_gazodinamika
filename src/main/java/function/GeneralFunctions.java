package function;

import initialDataForTask.InitialData;
import integration.ResultIntegration;
import resources.ResourceFactory;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 * Данный класс содержит методы которые используются для вычислений в
 * методах других классов. С целью исклчения повторения кода эти методы
 * определены в данном классе, и доступны для использования в других классах
 */
public class GeneralFunctions {
    private static InitialData initialData = new InitialData();  // объект содержащий исходные данные для решения задачи
    private ResultIntegration resultIntegration;  // объект предоставляющий возможность структурированного хранения результатов вычислений
    private static GeneralFunctions generalFunctions = null;  // для реализации патерна Singleton
    private volatile double P_ks, P_sc, V_sc, U, S;  // вспомогательные переменные
    private GeneralFunctions() {
        initialization();
    }

    // для реализации патерна Singleton
    public static GeneralFunctions instance() {
        if (generalFunctions == null) {
            return generalFunctions = new GeneralFunctions();
        } else return generalFunctions;
    }

    public double p_ks(HashMap<String, Double> values) {  // метод для расчета давления в камере сгорания на текущем шаге интегрирования
        return ( ( values.get("Massa_g") - values.get("Massa_sc") ) * initialData.R * values.get("Temperature") ) / values.get("Vks");
    }

    public double p_sc(HashMap<String, Double> values) { // метод для расчета давления в силовом цилиндре на текущем шаге интегрирования
        return ( values.get("Massa_sc") * initialData.R * values.get("Temperature") ) / v_sc(values) ;
    }

    public double v_sc(HashMap<String, Double> values) { // метод для расчета объема силового цилиндра на текущем шаге интегрирования
        return initialData.V0sc + initialData.Spor * values.get("X_sht");
    }

    public double U(HashMap<String, Double> values) { // метод для расчета скорости горения на текущем шаге интегрирования
        if (values.get("X") > initialData.eps) {
            return 0;
        } else return initialData.K * (0.00546 + 5.36*Math.pow(10, -9.3)*p_ks(values));
    }

    public double S(HashMap<String, Double> values) { // метод для расчета площади горения заряда на текущем шаге интегрирования
        if (values.get("X") > initialData.eps) {
            return 0;
        } else return initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*values.get("X");
    }

    //метод для расчета и сохранения всех параметоров на каждом шаге интегирования в resultIntegration
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

    // расчет и сохранение начальных значений параметров определенных в GeneralFunctions в resultIntegretion
    private void initialization () {
        resultIntegration = new ResultIntegration();

        // добавление параметров необходимых для сохранения в resultIntegration
        resultIntegration.addResult("P_ks");
        resultIntegration.addResult("P_sc");
        resultIntegration.addResult("V_sc");
        resultIntegration.addResult("U");
        resultIntegration.addResult("S");

        P_ks = ( ( initialData.mgg0 - initialData.mgsc0 ) * initialData.R * initialData.Tks0 ) / (initialData.Vks0);
        V_sc = initialData.V0sc;
        P_sc = (initialData.mgsc0  * initialData.R * initialData.Tks0) / (V_sc);
        U = initialData.K * (0.00546 + 5.36* Math.pow(10, -8)*P_ks);
        S = initialData.S0zar;

        resultIntegration.setValueByName("P_ks", P_ks);
        resultIntegration.setValueByName("P_sc", P_sc);
        resultIntegration.setValueByName("V_sc", V_sc);
        resultIntegration.setValueByName("U", U);
        resultIntegration.setValueByName("S", S);
    }

    // добавление интервалов вычислений для функций
    public void setRange(double startRange, double finishRange) {
        resultIntegration.addRangeOfTheFunctionsByName("P_ks", startRange, finishRange);
        resultIntegration.addRangeOfTheFunctionsByName("P_sc", startRange, finishRange);
        resultIntegration.addRangeOfTheFunctionsByName("V_sc", startRange, finishRange);
        resultIntegration.addRangeOfTheFunctionsByName("U", startRange, finishRange);
        resultIntegration.addRangeOfTheFunctionsByName("S", startRange, finishRange);
    }

    // считывание начальных данных из xml файла по заданному пути path (например path = "./initialData/initialData.xml")
    public void enterInitialDataFromFile(String path){
        initialData = ResourceFactory.instance().getResource(path);
        initialization();
        //System.out.println(initialData1.J);
    }

    public ResultIntegration getResultIntegration() {
        return resultIntegration;
    }

    public static InitialData getInitialData() {
        return initialData;
    }

//    public static String[] getListFunctionsNames() {
//        String[] listFunctionsNames = {"P_ks", "P_sc", "V_sc", "U", "S", Massa_g.getName(), Massa_sc.getName(), Temperature.getName(), Velocity.getName(),
//        Vks.getName(), X.getName(), X_sht.getName(), "movingPusher", "angleEndPanel", "movingPusher_r1", "movingPusher_r2"};
//        return listFunctionsNames;
//    }
}
