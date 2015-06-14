package integration;

import function.GeneralFunctions;
import function.interfaceFunction;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Abovyan on 21.12.14.
 */
public class DiffSystemSolver {

    public ResultIntegration integration( HashMap<String, Double> initialСonditions, double step,
                                         double startingValueIntegration, double finalValueIntegration,
                                         int digitRounding, HashMap<String, interfaceFunction> functions ) {

        // переменная по которому производится вычисление и переменная для временного хранения результата вычисления
        double x = startingValueIntegration, tempResult = 0;
        // HashMap для хранения значений имя функции - массив для промежуточных вычислений
        // в методе Рунге-Кутты 4-го порядка
        HashMap<String, Double[]> valuesTemp = new HashMap<String, Double[]>();
        // HashMap для хранения значений имя функции - переменные для вычисления следующего промежуточного шага
        HashMap<String, Double> valuesForNextStep = new HashMap<String, Double>();
        // объект для хранения результатов вычислений
        ResultIntegration resultIntegration = new ResultIntegration();
        resultIntegration.addParametrIntegration();
        // получение множества имен функций для которых необходимо произвести вычисления
        Set<String> nameValueIntegrationSet = initialСonditions.keySet();

        // выделяем необходимые ресурсы для данной задачи
        for (String temp : nameValueIntegrationSet ) {
            // добавляем массив в объект хранения результатов, исходя из количества вычисляемых параметров
            resultIntegration.addResult(temp);
            // добавляем массив для промежуточных вычислений исходя из количества вычисляемых параметров
            valuesTemp.put(temp, new Double[4]);
            // добавляем в объект результат вычисления для начальных значений
            resultIntegration.setValueByName(temp, initialСonditions.get(temp));
        }
        resultIntegration.setValueByName("parameterIntegration", startingValueIntegration);

        // главный цикл вычисления. Условие выхода - достижение конца интервала вычисления
        for (; r( initialСonditions.get("X_sht") , digitRounding ) < finalValueIntegration ; x += step ) {
            // цикл для расчета 1-го этапа метода Рунге-Кутты для всех функций системы
            for ( String temp : nameValueIntegrationSet ) {
                valuesTemp.get(temp)[0] = step * functions.get(temp).calculate(x , initialСonditions);
            }

            // цикл для расчета промежуточных значений необходимых для проведения 2-го этапа вычислений
            for ( String temp : nameValueIntegrationSet ) {
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[0]/2.0) );
            }

            // цикл для расчета 2-го этапа метода Рунге-Кутты для всех функций системы
            for ( String temp : nameValueIntegrationSet ) {
                valuesTemp.get(temp)[1] = step * functions.get(temp).calculate(x+step/2.0 , valuesForNextStep);
            }

            // цикл для расчета промежуточных значений необходимых для проведения 3-го этапа вычислений
            for ( String temp : nameValueIntegrationSet ) {
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[1]/2.0) );
            }

            // цикл для расчета 3-го этапа метода Рунге-Кутты для всех функций системы
            for ( String temp : nameValueIntegrationSet ) {
                valuesTemp.get(temp)[2] = step * functions.get(temp).calculate(x+step/2.0 , valuesForNextStep);
            }

            // цикл для расчета промежуточных значений необходимых для проведения 4-го этапа вычислений
            for ( String temp : nameValueIntegrationSet ) {
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[2]) );
            }

            // цикл для расчета 4-го этапа метода Рунге-Кутты для всех функций системы
            for ( String temp : nameValueIntegrationSet ) {
                valuesTemp.get(temp)[3] = step * functions.get(temp).calculate(x+step, valuesForNextStep);
            }

            // вычисление интеграла функции на соответствующем шаге
            for (String temp : nameValueIntegrationSet) {
                tempResult = resultIntegration.pullForNameArray(temp) +
                        (valuesTemp.get(temp)[0] + 2*valuesTemp.get(temp)[1] + 2*valuesTemp.get(temp)[2]
                                + valuesTemp.get(temp)[3])/6.0;
                resultIntegration.setValueByName(temp,tempResult);
                initialСonditions.put(temp, tempResult);
            }
            GeneralFunctions.instance().calculate(initialСonditions);
            resultIntegration.setValueByName("parameterIntegration", x+step);

        }

        // добавляем в объект хранения результатов интервал вычисления результатова для конкретной функции
        for (String temp : nameValueIntegrationSet ) {
            resultIntegration.addRangeOfTheFunctionsByName(temp, startingValueIntegration, x);
        }
        GeneralFunctions.instance().setRange(startingValueIntegration, x);
        return resultIntegration;
    }


    /**
     * функция для округления и отбрасывания "хвоста"
     */
    public double r(double value, int k){
        return (double)Math.round((Math.pow(10, k)*value))/Math.pow(10, k);
    }

}