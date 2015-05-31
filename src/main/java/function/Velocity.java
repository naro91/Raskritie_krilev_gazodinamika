package function;

import initialDataForTask.InitialData;

import java.util.HashMap;

/**
 * Created by Abovyan Narek on 17.02.15.
 */
public class Velocity implements interfaceFunction {
    private InitialData initialData;
    private GeneralFunctions generalFunctions;
    private double diffirentDirectionCoefficient, collinearDirectionCoefficient;

    public Velocity() {
        this.initialData = GeneralFunctions.getInitialData();
        this.generalFunctions = GeneralFunctions.instance();
        // коэффициент в случае разнонаправленности силы трения и внешнего момента
        diffirentDirectionCoefficient =  (1 - Math.tan(initialData.delta) * initialData.ftr) /
                (Math.tan(initialData.delta) + initialData.ftr);
        // коэффициент в случае сонаправленности силы трения и внешнего момента
        collinearDirectionCoefficient = (1 + Math.tan(initialData.delta)*initialData.ftr) /
                (Math.tan(initialData.delta) - initialData.ftr);
        System.out.printf("%f  %f ", diffirentDirectionCoefficient, collinearDirectionCoefficient);
    }

    public static String getName() {
        return "Velocity";
    }

    //метод возвращает ускорение штока
    @Override
    public double calculate(double x, HashMap<String, Double> values) {

        double Mvn = M_vn(fi(values)), Mves = M_ves(fi(values)), Psc = generalFunctions.p_sc(values);
        //System.out.println("fi = " + Math.toDegrees(fi(values)));
        double K = A(values, (Mvn + Mves)/initialData.rsr);

        return ( Psc*initialData.Spor+ ( (Mvn + Mves)/initialData.rsr ) * K ) /
                (initialData.msht + (initialData.J ) / (Math.pow(initialData.rsr, 2)*Math.tan(initialData.delta)));
    }

    //метод возвращает коэффициент для расчета силы в зависимости от момента на винтовой передаче
    public double A(HashMap<String, Double> values, double externalForce) {
        if(values.get("Velocity") * externalForce > 0) { // если момент направлен против силы трения
            return diffirentDirectionCoefficient;
        } else {
            if ( values.get("Velocity") * externalForce < 0 ) { // если момент сонаправлен с силой трения
                return collinearDirectionCoefficient;
            } else {
                if ( values.get("Velocity") == 0.0 ) {
                    return diffirentDirectionCoefficient;
                } else return 0;
            }
        }
    }

    // метод возвращает угол поворота корневой панели в зависимости от перемещения штока
    private double fi(HashMap<String, Double> values) {
        return values.get("X_sht")/(initialData.rsr*Math.tan(initialData.delta));
    }

    // метод проводит линейную интерполяцию по заданным табличным значениям
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
        return initialData.g*(initialData.m1*initialData.r1m +initialData.m2*initialData.r2m)*Math.sin(fi);
    }

    // метод находит индекс элемента наиболее близкого по значениб к заданному.
    // Поиск проводится в заданном массиве значений.
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
