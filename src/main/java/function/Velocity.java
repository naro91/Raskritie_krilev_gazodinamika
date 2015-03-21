package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 */
public class Velocity implements interfaceFunction {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;

    public Velocity() {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
    }

    public static String getName() {
        return "Velocity";
    }

    @Override
    public double calculate(double x, HashMap<String, Double> values) {
        double Mvn = M_vn(fi(values)), Mves = M_ves(fi(values)), Psc = generalFunctions.p_sc(values); System.out.println(Math.toDegrees(fi(values)));
        return ( Psc*initialData.Spor+ ( (Mvn + Mves)/initialData.rsr )*A(Psc*initialData.Spor, (Mvn + Mves)/initialData.rsr) ) /
                (initialData.msht + initialData.J/(Math.pow(initialData.rsr, 2)*Math.tan(initialData.delta)));
    }

    public double A(double pressureForce, double externalForce) {
        if(pressureForce*externalForce < 0) {
            if (pressureForce * Math.sin(initialData.delta) <= externalForce * Math.cos(initialData.delta)) {
                //return (1 - (2 * initialData.ftr / Math.tan(initialData.delta)) - Math.pow(initialData.ftr, 2)) / (Math.tan(initialData.delta) + initialData.ftr);
                return (1 - Math.tan(initialData.delta) * initialData.ftr) / (Math.tan(initialData.delta + initialData.ftr));
            } else {
                //return (1 + (2 * initialData.ftr / Math.tan(initialData.delta)) - Math.pow(initialData.ftr, 2)) / (Math.tan(initialData.delta) - initialData.ftr);
                return (1 + Math.tan(initialData.delta) * initialData.ftr) / (Math.tan(initialData.delta - initialData.ftr));
            }
        } else if (pressureForce * Math.sin(initialData.delta) == externalForce * Math.cos(initialData.delta)) { // если движения резьбы нет то не учитывается
            return 1.0/Math.tan(initialData.delta);
        } else return (1 - Math.tan(initialData.delta) * initialData.ftr) / (Math.tan(initialData.delta + initialData.ftr));
    }

    private double fi(HashMap<String, Double> values) {
        return values.get("X_sht")/(initialData.rsr*Math.tan(initialData.delta));
    }

    public double M_vn(double fi) { // метод проводит линейную интерполяцию по заданным табличным значениям
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
    // метод находит индекс элемента наиболее близкого по значение к заданному. Поиск проводится в заданном массиве значений.
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
