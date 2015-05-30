package util;

import function.*;
import initialDataForTask.InitialData;
import integration.DiffSystemSolver;
import integration.ResultIntegration;
import resources.ResourceFactory;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Abovyan Narek on 09.03.15.
 * класс инкапсулирует алгоритм решения задачи в целом
 */
public class GeneralAlgorithms {
    private DiffSystemSolver diffSystemSolver = new DiffSystemSolver();
    private HashMap<String, Double> initialConditions = new HashMap<String, Double>();
    private HashMap<String, interfaceFunction> functionHashMap = new HashMap<String, interfaceFunction>();
    private Kinematic kinematic = new Kinematic();
    private ResultIntegration resultIntegration;
    private GeneralFunctions generalFunctions = GeneralFunctions.instance();
    public static double step = 0.00001, theBeginningOfTheInterval = 0, endOfTheInterval = 0.03;
    public static int round = 5;

    public void startCalculating () {
        generalFunctions.getResultIntegration().clearIndexAndArray();
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
        // добавляем начальные условия в хэшмэп для передачи методу решения дифференциальных уравнений
        initialConditions.put(Temperature.getName(), GeneralFunctions.getInitialData().Tks0);
        initialConditions.put(X_sht.getName(), GeneralFunctions.getInitialData().X_sht0);
        initialConditions.put(X.getName(), GeneralFunctions.getInitialData().X0);
        initialConditions.put(Massa_sc.getName(), GeneralFunctions.getInitialData().mgsc0);
        initialConditions.put(Massa_g.getName(), GeneralFunctions.getInitialData().mgg0);
        initialConditions.put(Vks.getName(), GeneralFunctions.getInitialData().Vks0);
        initialConditions.put(Velocity.getName(), GeneralFunctions.getInitialData().velocity0);
        // получаем решение дифференциальных уравнений путем вызова метода integration
        resultIntegration = diffSystemSolver.integration(initialConditions, step, theBeginningOfTheInterval, endOfTheInterval, round, functionHashMap);
        startCalculatKinematics(resultIntegration);
        resultIntegration.addResultResultIntegration(GeneralFunctions.instance().getResultIntegration());
        //System.out.println( resultIntegration.getHashMapNameAndArraylist().get("S").size() == resultIntegration.getHashMapNameAndArraylist().get("parameterIntegration").size());
        //resultIntegration.printFile("resultSolve.txt");
    }

    public void startCalculatKinematics (ResultIntegration resultIntegration) {
        kinematic.calculate(resultIntegration);
        //kinematic.calculateSplit(resultIntegration);
    }

    public void enterInitialDataFromFile(String path){
        generalFunctions.enterInitialDataFromFile(path);
    }

    public String[] getFilesNamesOfDirectory(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String[] filesNames = new String[listOfFiles.length - 1];
        for (int i = 1; i < listOfFiles.length; i++) {
            filesNames[i - 1] = listOfFiles[i].getName();
//            if (listOfFiles[i].isFile()) {
//                System.out.println("File " + listOfFiles[i].getName());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
        }
        return filesNames;
    }

    public ResultIntegration getResultIntegration( ) {
        return resultIntegration;
    }

}
