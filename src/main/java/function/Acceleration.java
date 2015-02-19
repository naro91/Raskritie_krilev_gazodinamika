package function;

import integration.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 */
public class Acceleration implements interfaceFunction {
    private InitialData initialData;

    public Acceleration () {
        this.initialData = GeneralFunctions.initialData;
    }
    @Override
    public String getName() {
        return "Acceleration";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        double Mvn = M_vn(fi(values)), Mves = M_ves(fi(values)), Psc = GeneralFunctions.p_sc(values);
        return ( Psc*initialData.Spor+ ( (Mvn + Mves)/initialData.rsr )*A(Psc*initialData.Spor, (Mvn + Mves)/initialData.rsr) ) /
                (initialData.msht + initialData.J/(Math.pow(initialData.rsr, 2)*Math.tan(initialData.delta)));
    }

    public double A(double pressure, double external) {
        if (pressure <= external) {
            return (1- (2*initialData.ftr/Math.tan(initialData.delta)) - Math.pow(initialData.ftr, 2) ) / (Math.tan(initialData.delta) + initialData.ftr);
        } else {
            return (1+ (2*initialData.ftr/Math.tan(initialData.delta)) - Math.pow(initialData.ftr, 2) ) / (Math.tan(initialData.delta) - initialData.ftr);
        }
    }

    private double fi(HashMap<String, Double> values) {
        return values.get("X_sht")/(initialData.rsr*Math.tan(initialData.delta));
    }

    public double M_vn(double fi) {
        int index = binarySearch(initialData.M_vn, fi);
        if (fi < initialData.M_vn[0][0]) {
            return 0;
        } else if (fi > initialData.M_vn[initialData.M_vn.length-1][0]){
            return 0;
        }
        if (fi == initialData.M_vn[index][0]) {
            return initialData.M_vn[index][1];
        }else {
            return initialData.M_vn[index-1][1] + ( (fi - initialData.M_vn[index-1][0])/(initialData.M_vn[index][0] - initialData.M_vn[index-1][0]) ) *
                    (initialData.M_vn[index][1] - initialData.M_vn[index-1][1]);
        }

    }

    public double M_ves(double fi) {
        return initialData.g*(initialData.m1*initialData.r1+initialData.m2*initialData.r2)*Math.sin(fi);
    }

    private int binarySearch (double[][] mass, double value) {
        int first = 0;
        int last = mass.length;
        int mid = 0;

        if (last == 0) return 0;
        if (value < mass[0][0]) {
            return 0;
        } else if (value > mass[last-1][0]) {
            return last - 1;
        }

        while (first < last) {
            mid = first + (last-first)/2;

            if (value <= mass[mid][0]) {
                last = mid;
            } else first = mid + 1;
        }

        return last;
    }
}
