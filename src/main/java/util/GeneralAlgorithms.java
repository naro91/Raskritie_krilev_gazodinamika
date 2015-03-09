package util;

import function.*;
import integration.DiffSystemSolver;
import integration.ResultIntegration;
import plotter.Plotter;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 09.03.15.
 */
public class GeneralAlgorithms {
    private DiffSystemSolver diffSystemSolver = new DiffSystemSolver();
    private HashMap<String, Double> initialConditions = new HashMap<String, Double>();
    private HashMap<String, interfaceFunction> functionHashMap = new HashMap<String, interfaceFunction>();
    private ResultIntegration resultIntegration;

    public void startCalculating () {
        // system equation
        functionHashMap.put("Temperature", new Temperature());
        functionHashMap.put("X_sht", new X_sht());
        functionHashMap.put("X", new X());
        functionHashMap.put("Vks", new Vks());
        functionHashMap.put("Massa_sc", new Massa_sc());
        functionHashMap.put("Massa_g", new Massa_g());
        functionHashMap.put("Acceleration", new Acceleration());

        // initial conditions
        initialConditions.put("Temperature", GeneralFunctions.getInitialData().Tks0);
        initialConditions.put("X_sht", 0.0);
        initialConditions.put("X", 0.0);
        initialConditions.put("Massa_sc", GeneralFunctions.getInitialData().mgsc0);
        initialConditions.put("Massa_g", GeneralFunctions.getInitialData().mgg0);
        initialConditions.put("Vks", GeneralFunctions.getInitialData().Vks0);
        initialConditions.put("Acceleration", 0.0);

        resultIntegration = diffSystemSolver.integration(initialConditions, 0.00001, 0, 0.01, 5, functionHashMap);
        resultIntegration.addResultResultIntegration(GeneralFunctions.instance().getResultIntegration());
        //System.out.println( resultIntegration.getHashMapNameAndArraylist().get("S").size() == resultIntegration.getHashMapNameAndArraylist().get("parameterIntegration").size());
        //resultIntegration.printFile("resultSolve.txt");
    }

    public ResultIntegration getResultIntegration( ) {
        return resultIntegration;
    }


}
