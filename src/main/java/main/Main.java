package main;

import function.Temperature;
import function.interfaceFunction;
import integration.DiffSystemSolver;
import integration.ResultIntegration;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
public class Main {
    public static void main (String args[]){
        DiffSystemSolver diffSystemSolver = new DiffSystemSolver();
        HashMap<String, Double> initialConditions = new HashMap<String, Double>();
        HashMap<String, interfaceFunction> functionHashMap = new HashMap<String, interfaceFunction>();
        initialConditions.put("Temperature", 0.0);
        functionHashMap.put("Temperature", new Temperature());
        ResultIntegration resultIntegration = diffSystemSolver.integration(initialConditions, 0.01, 1, 2, functionHashMap);
        resultIntegration.printResultForName("Temperature");
    }
}
