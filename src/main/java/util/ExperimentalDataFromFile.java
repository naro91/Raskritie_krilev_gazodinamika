package util;

import integration.ResultIntegration;

/**
 * Created by Abovyan on 08.06.15.
 * Класс для хранения экспериментальных данных
 */
public class ExperimentalDataFromFile {
    public double p_experimental1[][];
    public double p_experimental2[][];
    public double fi_experimental1[][];
    public double fi_experimental2[][];
    public void addDataForComparisonFromFile(ResultIntegration resultIntegration) {
        resultIntegration.addResult("P(t)_experimental1");
        resultIntegration.addResult("P(t)_experimental2");
        resultIntegration.addResult("fi_experimental1");
        resultIntegration.addResult("fi_experimental2");
        resultIntegration.addRangeOfTheFunctionsByName("P(t)_experimental1",
                p_experimental1[0][0], p_experimental1[p_experimental1.length-1][0]);
        resultIntegration.addRangeOfTheFunctionsByName("P(t)_experimental2",
                p_experimental2[0][0], p_experimental2[p_experimental2.length-1][0]);
        resultIntegration.addRangeOfTheFunctionsByName("fi_experimental1",
                fi_experimental1[0][0], fi_experimental1[fi_experimental1.length-1][0]);
        resultIntegration.addRangeOfTheFunctionsByName("fi_experimental2",
                fi_experimental2[0][0], fi_experimental2[fi_experimental2.length-1][0]);
        for (double[] temp : p_experimental1) {
            resultIntegration.setValueByName("P(t)_experimental1", temp[1]);
        }
        for (double[] temp : p_experimental2) {
            resultIntegration.setValueByName("P(t)_experimental2", temp[1]);
        }
        for (double[] temp : fi_experimental1) {
            resultIntegration.setValueByName("fi_experimental1", temp[1]);
        }
        for (double[] temp : fi_experimental2) {
            resultIntegration.setValueByName("fi_experimental2", temp[1]);
        }
    }
}
