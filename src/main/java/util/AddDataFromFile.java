package util;

import integration.ResultIntegration;

/**
 * Created by Abovyan on 08.06.15.
 */
public class AddDataFromFile {
    public double p_diff_dir[][];
    public double fi_diff_dir[][];
    public void addDataForComparisonFromFile(ResultIntegration resultIntegration) {
        resultIntegration.addResult("P(t)_diff_dir_exp");
        resultIntegration.addResult("fi_diff_dir");
        resultIntegration.addRangeOfTheFunctionsByName("P(t)_diff_dir_exp", p_diff_dir[0][0], p_diff_dir[p_diff_dir.length-1][0]);
        resultIntegration.addRangeOfTheFunctionsByName("fi_diff_dir", fi_diff_dir[0][0], fi_diff_dir[fi_diff_dir.length-1][0]);
        for (double[] temp : p_diff_dir) {
            resultIntegration.setValueByName("P(t)_diff_dir_exp", temp[1]);
        }
        for (double[] temp : fi_diff_dir) {
            resultIntegration.setValueByName("fi_diff_dir", temp[1]);
        }
    }
}
