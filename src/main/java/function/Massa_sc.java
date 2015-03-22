package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 * Класс для вычисления массы газа в силовом цилиндре
 */
public class Massa_sc implements interfaceFunction {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;
    public Massa_sc () {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
    }


    public static String getName() {
        return "Massa_sc";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        if (generalFunctions.p_ks(values) >= generalFunctions.p_sc(values) ) {
            return a_ist(x, values)*initialData.Skr*generalFunctions.p_ks(values);
        } else //return -a_ist(values)*initialData.Skr*GeneralFunctions.p_sc(values);
        return 0;
    }


    private double a_ist(double x, HashMap<String, Double> values) {
        double Pks = generalFunctions.p_ks(values), Psc = generalFunctions.p_sc(values);
        if ( Pks == Psc ) {
            return 0;
        } else if ( Pks > Psc ) {
            if ( Pks/Psc >= Math.pow( (initialData.k + 1)/2, initialData.k/(initialData.k - 1) ) ) {
                return Math.pow( 2/(initialData.k + 1), 1/(initialData.k - 1) ) * Math.sqrt(2*initialData.k / ( (initialData.k +1)*initialData.R*initialData.ksi*values.get("Temperature") ));
            } else return Math.sqrt( ( 2*initialData.k/ ( (initialData.k-1)*initialData.R*initialData.ksi*values.get("Temperature") ) )*
                    ( Math.pow(Psc/Pks,2/initialData.k) - Math.pow(Psc/Pks, (initialData.k+1)/initialData.k) ) );
        }else {
            /*if ( Psc/Pks >= Math.pow( (initialData.k + 1)/2, initialData.k/(initialData.k - 1) ) ) {
                return Math.pow( 2/(initialData.k + 1), 1/(initialData.k - 1) ) * Math.sqrt(2*initialData.k / ( (initialData.k +1)*initialData.R*initialData.ksi*values.get("Temperature") ));
            } else return Math.sqrt( ( 2*initialData.k/ ( (initialData.k-1)*initialData.R*initialData.ksi*values.get("Temperature") ) )*
                    ( Math.pow(Pks/Psc,2/initialData.k) - Math.pow(Pks/Psc, (initialData.k+1)/initialData.k) ) ); */
            return 0;
        }
    }


}
