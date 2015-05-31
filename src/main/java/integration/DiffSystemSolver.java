package integration;

import function.GeneralFunctions;
import function.interfaceFunction;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Abovyan on 21.12.14.
 */
public class DiffSystemSolver {

    public ResultIntegration integration(HashMap<String, Double> initialСonditions, double step, double startingValueIntegration, double finalValueIntegration,
                                         int digitRounding, HashMap<String, interfaceFunction> functions) {

        double x = startingValueIntegration, tempResult = 0;  // переменная по которому производится вычисление и переменная для временного хранения результата вычисления
        HashMap<String, Double[]> valuesTemp = new HashMap<String, Double[]>(); // HashMap для хранения значений имя функции - массив для промежуточных вычислений для метода Рунге-Кутты 4-го порядка
        HashMap<String, Double> valuesForNextStep = new HashMap<String, Double>(); // HashMap для хранения значений имя функции - переменные для вычисления следующего промежуточного шага
        ResultIntegration resultIntegration = new ResultIntegration();  // объект для хранения результатов вычислений
        resultIntegration.addParametrIntegration();
        Set<String> nameValueIntegrationSet = initialСonditions.keySet();  // получение множества имен функций для которых необходимо произвести вычисления
        GeneralFunctions.instance().setRange(startingValueIntegration, finalValueIntegration);
        for (String temp : nameValueIntegrationSet ) { // выделяем необходимые ресурсы для данной задачи
            resultIntegration.addResult(temp);   // добавляем массив в объект хранения результатов, исходя из количества вычисляемых параметров
            resultIntegration.addRangeOfTheFunctionsByName(temp, startingValueIntegration, finalValueIntegration); // добавляем в объект хранения результатов интервал вычисления результатова для конкретной функции
            valuesTemp.put(temp, new Double[4]);  // добавляем массив для промежуточных вычислений исходя из количества вычисляемых параметров
            resultIntegration.setValueByName(temp, initialСonditions.get(temp));  // добавляем в объект результат вычисления для начальных значений
        }
        resultIntegration.setValueByName("parameterIntegration", startingValueIntegration);

        for (; r( x , digitRounding ) < finalValueIntegration; x += step ) {  // главный цикл вычисления. Условие выхода - достижение конца интервала вычисления
            for ( String temp : nameValueIntegrationSet ) {  // цикл для расчета 1-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[0] = step * functions.get(temp).calculate(x , initialСonditions);
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета промежуточных значений необходимых для проведения 2-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[0]/2.0) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 2-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[1] = step * functions.get(temp).calculate(x+step/2.0 , valuesForNextStep);
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета промежуточных значений необходимых для проведения 3-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[1]/2.0) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 3-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[2] = step * functions.get(temp).calculate(x+step/2.0 , valuesForNextStep);
            }

            for ( String temp : nameValueIntegrationSet ) {  // цикл для расчета промежуточных значений необходимых для проведения 4-го этапа вычислений
                valuesForNextStep.put(temp , (initialСonditions.get(temp) + valuesTemp.get(temp)[2]) );
            }

            for ( String temp : nameValueIntegrationSet ) { // цикл для расчета 4-го этапа метода Рунге-Кутты для всех функций системы
                valuesTemp.get(temp)[3] = step * functions.get(temp).calculate(x+step, valuesForNextStep);
            }

            for (String temp : nameValueIntegrationSet) { // вычисление интеграла функции на соответствующем шаге
                tempResult = resultIntegration.pullForNameArray(temp) +
                        (valuesTemp.get(temp)[0] + 2*valuesTemp.get(temp)[1] + 2*valuesTemp.get(temp)[2] + valuesTemp.get(temp)[3])/6.0;
                resultIntegration.setValueByName(temp,tempResult);
                initialСonditions.put(temp, tempResult);
            }
            GeneralFunctions.instance().calculate(initialСonditions);
            resultIntegration.setValueByName("parameterIntegration", x+step);

        }

        return resultIntegration;
    }


    /**
     * функция для округления и отбрасывания "хвоста"
     */
    public double r(double value, int k){
        return (double)Math.round((Math.pow(10, k)*value))/Math.pow(10, k);
    }

}