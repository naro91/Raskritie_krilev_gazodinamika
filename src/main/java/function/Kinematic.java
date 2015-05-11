package function;

import initialDataForTask.InitialData;
import integration.ResultIntegration;


/**
 * Created by Abovyan Narek on 28.03.15.
 */
public class Kinematic {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;
    private double angleChangeRadius;
    private boolean changeRadius = false;
    private double moveToAngle;
    private double movingPusher0 = 0;
    public Kinematic() {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
        angleChangeRadius = initialData.angleChangeRadius; // угол при котором изменяется радиус
        moveToAngle = (initialData.rsr*Math.tan(initialData.delta));
    }


    public void calculate(ResultIntegration resultIntegration) {
        int i = 0;
        resultIntegration.addResult("movingPusher");
        resultIntegration.addResult("angleEndPanel");
        resultIntegration.addRangeOfTheFunctionsByName("movingPusher", resultIntegration.getRangeStartByName("X_sht"), resultIntegration.getRangeFinishByName("X_sht"));
        resultIntegration.addRangeOfTheFunctionsByName("angleEndPanel", resultIntegration.getRangeStartByName("X_sht"), resultIntegration.getRangeFinishByName("X_sht"));
        calculateMuvingPusher0();
        for (double fi : resultIntegration.getHashMapNameAndArraylist().get("X_sht")) {
            fi = fi / moveToAngle;
            resultIntegration.setValueByName("movingPusher", movingPusher(fi, resultIntegration, i));
            resultIntegration.setValueByName("angleEndPanel", angleEndPanel(fi, resultIntegration, i));
            i++;
        }
        resultIntegration.printResultForName("movingPusher");
        resultIntegration.printResultForName("angleEndPanel");
    }

    public double movingPusher (double fi, ResultIntegration resultIntegration, int i) {
        Double currentTime = resultIntegration.getHashMapNameAndArraylist().get("parameterIntegration").get(i);
        double psi = fi - initialData.fi0;
        if (psi <= angleChangeRadius) {
            if (changeRadius) {
                //resultIntegration.addMarker("ChangeR ".concat((currentTime).toString()), currentTime);
                changeRadius = false;}
            return movingPusher_r1(psi);
        } else {
            //if (!changeRadius) { resultIntegration.addMarker("ChangeR ".concat((currentTime).toString()), currentTime); changeRadius = true;}
            return movingPusher_r2(psi);
        }
    }

    public double angleEndPanel (double fi, ResultIntegration resultIntegration, int i) {
        return ( Math.acos( (initialData.r5 - ( initialData.r60 - movingPusher(fi, resultIntegration, i) )*Math.sin(initialData.betta_c)) / initialData.r4 )
                - initialData.alfa_c - initialData.betta_c ) ;
    }

    public void calculateMuvingPusher0() {
        double temp = movingPusher_r1(0);
        movingPusher0 = temp;
    }

    public double movingPusher_r1(double psi) {
        return ( Math.sqrt( Math.pow(initialData.r13, 2) - Math.pow(initialData.r1, 2) + Math.pow(initialData.r1, 2)*Math.pow(Math.sin(initialData.psi_r1 - psi),2)
                + 2*initialData.r1*initialData.e*Math.cos(initialData.psi_r1-psi)) - initialData.r1*Math.sin(initialData.psi_r1-psi) ) - movingPusher0;
    }

    public double movingPusher_r2(double psi) {
        return (Math.sqrt(Math.pow(initialData.r23, 2) - Math.pow(initialData.r2, 2) + Math.pow(initialData.r2, 2) * Math.pow(Math.sin(initialData.psi_r2 - psi),2)
                + 2 * initialData.r2 * initialData.e * Math.cos(initialData.psi_r2 - psi)) - initialData.r2 * Math.sin(initialData.psi_r2 - psi)) - movingPusher0;
    }

    public double movingPusher_fi (double fi) {
        double psi = fi - initialData.fi0;
        if (psi <= angleChangeRadius) {
            return movingPusher_r1(psi);
        } else {
            return movingPusher_r2(psi);
        }
    }
    public double angleEndPanel_fi (double fi) {
        return ( Math.acos( (initialData.r5 - ( initialData.r60 - movingPusher_fi(fi) )*Math.sin(initialData.betta_c)) / initialData.r4 )
                - initialData.alfa_c - initialData.betta_c ) ;
    }

    public void calculateSplit(ResultIntegration resultIntegration) {
        calculateMuvingPusher0();
        resultIntegration.addResult("movingPusher_r1");
        resultIntegration.addResult("movingPusher_r2");
        resultIntegration.addResult("movingPusher_fi");
        resultIntegration.addResult("angleEndPanel_fi");
        resultIntegration.addResult("parameterIntegration");
        for (double fi = 0; fi < Math.toRadians(109); fi += 0.01) {
            resultIntegration.setValueByName("movingPusher_r1", movingPusher_r1(fi));
            resultIntegration.setValueByName("movingPusher_r2", movingPusher_r2(fi));
            resultIntegration.setValueByName("movingPusher_fi", movingPusher_fi (fi));
            resultIntegration.setValueByName("angleEndPanel_fi", angleEndPanel_fi (fi));
            resultIntegration.setValueByName("parameterIntegration", fi);
        }
    }
}
