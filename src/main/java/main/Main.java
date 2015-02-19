package main;

import function.*;
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

        // system equation
        functionHashMap.put("Temperature", new Temperature());
        functionHashMap.put("X_sht", new X_sht());
        functionHashMap.put("X", new X());
        functionHashMap.put("Vks", new Vks());
        functionHashMap.put("Massa_sc", new Massa_sc());
        functionHashMap.put("Massa_g", new Massa_g());
        functionHashMap.put("Acceleration", new Acceleration());

        // initial conditions
        initialConditions.put("Temperature", GeneralFunctions.initialData.Tks0);
        initialConditions.put("X_sht", 0.0);
        initialConditions.put("X", 0.0);
        initialConditions.put("Massa_sc", GeneralFunctions.initialData.mgsc0);
        initialConditions.put("Massa_g", GeneralFunctions.initialData.mgg0);
        initialConditions.put("Vks", GeneralFunctions.initialData.Vks0);
        initialConditions.put("Acceleration", 0.0);

        ResultIntegration resultIntegration = diffSystemSolver.integration(initialConditions, 0.001, 0, 1, 2, functionHashMap);
        resultIntegration.printResultForName("parameterIntegration");
        resultIntegration.printResultForName("X_sht");
    }
}
