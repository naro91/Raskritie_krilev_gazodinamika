package integration;

import function.interfaceFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Abovyan on 21.12.14.
 */
public class DiffSystemSolver {

    public InitialСonditions initialСonditions = new InitialСonditions();

    public ResultIntegration integration(HashMap<String, Double> initialСonditions, double step, double finalValueIntegration,
                                         int digitRounding, HashMap<String, interfaceFunction> functions) {

        double x = 0, tempResult = 0;  // переменная по которому производится вычисление и переменная для временного хранения результата вычисления
        //int sizeOfEquation = initialСonditions.size();
        HashMap<String, Double[]> valuesTemp = new HashMap<String, Double[]>(); // HashMap для хранения значений имя функции - массив для промежуточных вычислений для метода Рунге-Кутты 4-го порядка
        HashMap<String, Double> valuesForNextStep = new HashMap<String, Double>(); // HashMap для хранеия значений имя функции - переменные для вычисления следующего промежуточного шага
        //double valuesTemp[][] = new double[sizeOfEquation][4];
        ResultIntegration resultIntegration = new ResultIntegration();  // объект для хранения результатов вычислений
        Set<String> nameValueIntegrationSet = initialСonditions.keySet();  // получение множества имен функций для которых необходимо провести вычисления
        for (String temp : nameValueIntegrationSet ) { // выделяем необходимые ресурсы для данной задачи
            resultIntegration.addResult(temp);   // добавляем массив в объект хранения результатов, исходя из количества вычисляемых параметров
            valuesTemp.put(temp, new Double[4]);  // добавляем массив для промежуточных вычислений исходя из количества вычисляемых параметров
            resultIntegration.setValueForName(temp, functions.get(temp).calculateForInitialCondition());  // добавляем в объект результатов вычисления для начальных значений
        }

        for (; r( x , digitRounding ) < finalValueIntegration; x += step ) {  // главный цикл вычисления. Условие выхода - достижение конца интервала вычисления
            for ( String temp : nameValueIntegrationSet ) {  // цикл для расчета 1-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[0] = step * functions.get(temp).calculate(x , initialСonditions);
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета промежуточных значений необходимых для проведения 2-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[0]/2) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 2-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[1] = step * functions.get(temp).calculate(x+step/2 , valuesForNextStep);
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета промежуточных значений необходимых для проведения 3-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[1]/2) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 3-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[2] = step * functions.get(temp).calculate(x+step/2 , valuesForNextStep);
            }

            for ( String temp : nameValueIntegrationSet ) {  // цикл для расчета промежуточных значений необходимых для проведения 4-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[2]) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 4-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[3] = step * functions.get(temp).calculate(x+step, valuesForNextStep);
            }

            for (String temp : nameValueIntegrationSet) {
                tempResult = resultIntegration.pullForNameArray(temp) +
                        (valuesTemp.get(temp)[0] + 2*valuesTemp.get(temp)[1] + 2*valuesTemp.get(temp)[2] + valuesTemp.get(temp)[3])/6;
                resultIntegration.setValueForName(temp,tempResult);
                initialСonditions.put(temp, tempResult);
            }

        }

        return resultIntegration;
    }


    /**
     * функция для округления и отбрасывания "хвоста"
     */
    public double r(double value, int k){
        return (double)Math.round((Math.pow(10, k)*value))/Math.pow(10, k);
    }
    /**
     * функции, которые получаются из системы
     */
    public double Vsc(double l){
        return (initialСonditions.V0sc+initialСonditions.Spor*l);
    }

    public double mgks(double mgg, double mgsc){
        return (mgg - mgsc);
    }
    public double Pks(double mgks, double T, double Vks){
        return (mgks*initialСonditions.R*T/Vks);
    }

    public double Psc(double mgsc, double T, double Vsc){
        return (mgsc*initialСonditions.R*T/Vsc);
    }

    public double Sgor(double xgor){
        return ((Math.PI*(initialСonditions.Dzar+initialСonditions.dzar)/2)*(initialСonditions.Dzar-initialСonditions.dzar+2*initialСonditions.L)
        -4*Math.PI*(initialСonditions.Dzar+initialСonditions.dzar)*xgor);
    }

    public double fi(double l){
        return (l/(initialСonditions.rsr*Math.tan(initialСonditions.delta)));
    }

    public double U(double xgor, double pks){
        if (xgor <= (initialСonditions.Dzar - initialСonditions.dzar)/4) {
            return (initialСonditions.B / (initialСonditions.B - (initialСonditions.tokrSr - 20))) * (0.00546 + (5.36 / (Math.pow(10, 8))) * pks);
        }else return 0;
    }

    public double Aist(double Pks_delit_Psc, double T){
        double k = initialСonditions.k;
        if (Math.abs(Pks_delit_Psc - 1) <= 0.1) {
            return 0;
        } else {
            if (Pks_delit_Psc >= initialСonditions.criteria_Pks_Psc) {
                return (Math.pow((2 / (k + 1)), 2) * Math.sqrt((2 * k) / ((k + 1) * initialСonditions.R * initialСonditions.ksi * T)));
            } else {
                return Math.sqrt((2 * k / ((k - 1) * initialСonditions.R * initialСonditions.ksi * T)) * (Math.pow(Pks_delit_Psc, 2 / k) - Math.pow(Pks_delit_Psc, (k + 1) / k)));
            }
        }
    }

    public double A(double Mvn_plus_Mves){
        if(Mvn_plus_Mves <= 0){
            return (1+(2*initialСonditions.ftr/Math.tan(initialСonditions.delta))-Math.pow(initialСonditions.ftr, 2))/(Math.tan(initialСonditions.delta)-initialСonditions.ftr);
        }else {
            return (1-(2*initialСonditions.ftr/Math.tan(initialСonditions.delta))-Math.pow(initialСonditions.ftr, 2))/(Math.tan(initialСonditions.delta)+initialСonditions.ftr);
        }
    }

    public double Mves(double fi){
        return initialСonditions.g*(initialСonditions.m1*initialСonditions.r1+initialСonditions.m2*initialСonditions.r2)*Math.sin(fi);
    }

    // Основные функции для систем уравнений
    public double equationForVelocity(double Psc, double Mvn, double Mves) {
        return (Psc*initialСonditions.Spor+(Mvn+Mves)*A( (Mvn+Mves) ))/
                (initialСonditions.msht+ ( initialСonditions.J/( Math.pow(initialСonditions.rsr, 2)*Math.tan(initialСonditions.delta) ) ));
    }

    public double equation_mgsc(double Pks_delit_Psc, double mgks, double T, double Vks){
        return Aist(Pks_delit_Psc, T)*initialСonditions.Skr*Pks(mgks, T, Vks);
    }

    public double equation_mgg(double xgor, double pks){
        return U(xgor, pks)*Sgor(xgor)*initialСonditions.gama;
    }

    public double equation_Vks(double xgor, double pks){
        return U(xgor, pks)*Sgor(xgor);
    }

    public double equation_T(double T, double mgg, double xgor, double pks, double v){
        return ( U(xgor, pks)*Sgor(xgor)*initialСonditions.gama/(mgg*initialСonditions.cv) )*(initialСonditions.ksi*initialСonditions.Tp
                -initialСonditions.cv*T)-(pks*initialСonditions.Spor*v)/(mgg*initialСonditions.cv);
    }

}