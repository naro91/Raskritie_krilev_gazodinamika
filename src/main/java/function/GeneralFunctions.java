package function;

import initialDataForTask.InitialData;
import integration.ResultIntegration;
import resources.ResourceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abovyan Narek on 17.02.15.
 * Данный класс содержит методы которые используются для вычислений в
 * методах других классов. С целью исключения повторения кода эти методы
 * определены в данном классе, и доступны для использования в других классах
 */
public class GeneralFunctions {
    private static InitialData initialData = new InitialData();  // объект содержащий исходные данные для решения задачи
    private ResultIntegration resultIntegration;  // объект предоставляющий возможность структурированного хранения результатов вычислений
    private static GeneralFunctions generalFunctions = null;  // для реализации паттерна Singleton
    private volatile double P_ks, P_sc, V_sc, U, S, fi;  // вспомогательные переменные
    private String[] valueList;
    private Map<String, Double> valueHashmap;

    private GeneralFunctions() {
        valueList = new String[]{"P_ks", "P_sc", "V_sc", "U", "S", "fi"};
        valueHashmap = new HashMap<>();
        initialization();
    }

    // для реализации паттерна Singleton
    public static GeneralFunctions instance() {
        if (generalFunctions == null) {
            return generalFunctions = new GeneralFunctions();
        } else return generalFunctions;
    }

    // метод для расчета давления в камере сгорания на текущем шаге интегрирования
    public double p_ks(HashMap<String, Double> values) {
        return ( ( values.get("Massa_g") - values.get("Massa_sc") ) * initialData.R * values.get("Temperature") ) / values.get("Vks");
    }

    // метод для расчета давления в силовом цилиндре на текущем шаге интегрирования
    public double p_sc(HashMap<String, Double> values) {
        return ( values.get("Massa_sc") * initialData.R * values.get("Temperature") ) / v_sc(values) ;
    }

    // метод для расчета объема силового цилиндра на текущем шаге интегрирования
    public double v_sc(HashMap<String, Double> values) {
        return initialData.V0sc + initialData.Spor * values.get("X_sht");
    }

    // метод для расчета скорости горения на текущем шаге интегрирования
    public double U(HashMap<String, Double> values) {
        if (values.get("X") > initialData.eps) {
            return 0;
        } else return initialData.K * (0.00546 + 5.36*Math.pow(10, -8)*p_ks(values));
    }

    // метод для расчета площади горения заряда на текущем шаге интегрирования
    public double S(HashMap<String, Double> values) {
        if (values.get("X") > initialData.eps) {
            return 0;
        } else return initialData.S0zar - 4*Math.PI*(initialData.Dzar+initialData.dzar)*values.get("X");
    }

    // вычисляет угол корневой панели по перемещению штока
    public double fi(HashMap<String, Double> values) {
        return values.get("X_sht")/(initialData.r_vint*Math.tan(initialData.delta));
    }

    //метод для расчета и сохранения всех параметров на каждом шаге интегирования в resultIntegration
    public void calculate(HashMap<String, Double> values) {
        valueHashmap.put("P_ks", p_ks(values));
        valueHashmap.put("P_sc", p_sc(values));
        valueHashmap.put("V_sc", v_sc(values));
        valueHashmap.put("U", U(values) );
        valueHashmap.put("S", S(values));
        valueHashmap.put("fi", fi(values));

        for (String temp : valueList) {
            resultIntegration.setValueByName(temp, valueHashmap.get(temp));
        }
    }

    // расчет и сохранение начальных значений параметров определенных в GeneralFunctions в resultIntegretion
    private void initialization () {
        resultIntegration = new ResultIntegration();

        // добавление параметров необходимых для сохранения в resultIntegration
        for (String temp : valueList) {
            resultIntegration.addResult(temp);
        }

        valueHashmap.put("P_ks", initialData.p_ks0);
        valueHashmap.put("P_sc", initialData.p_sc0);
        valueHashmap.put("V_sc", initialData.V0sc);
        valueHashmap.put("U", (initialData.K * (0.00546 + 5.36* Math.pow(10, -8)*P_ks)) );
        valueHashmap.put("S", initialData.S0zar);
        valueHashmap.put("fi", 0.0);

        for (String temp : valueList) {
            resultIntegration.setValueByName(temp, valueHashmap.get(temp));
        }
    }

    // добавление интервалов вычислений для функций
    public void setRange(double startRange, double finishRange) {
        for (String temp : valueList) {
            resultIntegration.addRangeOfTheFunctionsByName(temp, startRange, finishRange);
        }
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

}
