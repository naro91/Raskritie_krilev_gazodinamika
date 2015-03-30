package util;

import function.*;
import integration.DiffSystemSolver;
import integration.ResultIntegration;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 09.03.15.
 * класс инкапсулирует алгоритм решения задачи в целом
 */
public class GeneralAlgorithms {
    private DiffSystemSolver diffSystemSolver = new DiffSystemSolver();
    private HashMap<String, Double> initialConditions = new HashMap<String, Double>();
    private HashMap<String, interfaceFunction> functionHashMap = new HashMap<String, interfaceFunction>();
    Kinematics kinematics = new Kinematics();
    private ResultIntegration resultIntegration;
    public static double step = 0.00001, theBeginningOfTheInterval = 0, endOfTheInterval = Math.toRadians(108);//0.03;
    public static int round = 5;

    public void startCalculating () {
        GeneralFunctions.instance().getResultIntegration().clearIndexAndArray();
        // system equation
        // добавляем уравнения в хэшмэп для передачи в метод решения дифф. ур.
        functionHashMap.put(Temperature.getName(), new Temperature());
        functionHashMap.put(X_sht.getName(), new X_sht());
        functionHashMap.put(X.getName(), new X());
        functionHashMap.put(Vks.getName(), new Vks());
        functionHashMap.put(Massa_sc.getName(), new Massa_sc());
        functionHashMap.put(Massa_g.getName(), new Massa_g());
        functionHashMap.put(Velocity.getName(), new Velocity());

        // initial conditions
        // добавляем начальные условия в хэшмэп для передачи методу для решения дифференциальных уравнений
        initialConditions.put(Temperature.getName(), GeneralFunctions.getInitialData().Tks0);
        initialConditions.put(X_sht.getName(), GeneralFunctions.getInitialData().X_sht0);
        initialConditions.put(X.getName(), GeneralFunctions.getInitialData().X0);
        initialConditions.put(Massa_sc.getName(), GeneralFunctions.getInitialData().mgsc0);
        initialConditions.put(Massa_g.getName(), GeneralFunctions.getInitialData().mgg0);
        initialConditions.put(Vks.getName(), GeneralFunctions.getInitialData().Vks0);
        initialConditions.put(Velocity.getName(), GeneralFunctions.getInitialData().velocity0);

        resultIntegration = diffSystemSolver.integration(initialConditions, step, theBeginningOfTheInterval, endOfTheInterval, round, functionHashMap);
        resultIntegration.addResultResultIntegration(GeneralFunctions.instance().getResultIntegration());
        //System.out.println( resultIntegration.getHashMapNameAndArraylist().get("S").size() == resultIntegration.getHashMapNameAndArraylist().get("parameterIntegration").size());
        //resultIntegration.printFile("resultSolve.txt");
    }

    public void startCalculatKinematics () {
        kinematics.calculate();
        resultIntegration = GeneralFunctions.instance().getResultIntegration();
    }

    public ResultIntegration getResultIntegration( ) {
        return resultIntegration;
    }

}
