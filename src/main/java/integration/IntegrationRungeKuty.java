package integration;

/**
 * Created by Abovyan on 21.12.14.
 */
public class IntegrationRungeKuty {

    public InitialСonditions initialСonditions = new InitialСonditions();
    public Splayn Mvn = new Splayn();
    public ResultIntegration resultIntegration;

    public ResultIntegration integration() {
        int k = 2;
        double to, lo, ln, vo, vn, h;
        double kmass[][] = new double[2][4];
                /*
                 *Начальные условия
                 */
        to = 0;
        lo = 0;
        vo = 2;

        h = 0.001; // шаг

        System.out.println("\tX\t\tY\t\tZ");
        for(; r(to,3) < 1.0; to += h){

            kmass[0][1] = h * vo;
            q1 = h * g(Xo, Yo, Zo);

            k2 = h * f(Xo + h/2.0, Yo + q1/2.0, Zo + k1/2.0);
            q2 = h * g(Xo + h / 2.0, Yo + q1/2.0, Zo + k1/2.0);

            k3 = h * f(Xo + h/2.0, Yo + q2/2.0, Zo + k2/2.0);
            q3 = h * g(Xo + h/2.0, Yo + q2/2.0, Zo + k2/2.0);

            k4 = h * f(Xo + h, Yo + q3, Zo + k3);
            q4 = h * g(Xo + h, Yo + q3, Zo + k3);

            Z1 = Zo + (k1 + 2.0*k2 + 2.0*k3 + k4)/6.0;
            Y1 = Yo + (q1 + 2.0*q2 + 2.0*q3 + q4)/6.0;
            //System.out.println("\t" + r(Xo + h, k) + "\t\t" + r(Y1 ,k) + "\t\t" + r(Z1 ,k));
            Yo = Y1;
            Zo = Z1;

        }
        return null;

    }
    /**
     * функция для округления и отбрасывания "хвоста"
     */
    public double r(double value, int k){
        return (double)Math.round((Math.pow(10, k)*value))/Math.pow(10, k);
    }
    /**
     * функции, которые получаются из системы
     */
    public double Vsc(double l){
        return (initialСonditions.V0sc+initialСonditions.Spor*l);
    }

    public double mgks(double mgg, double mgsc){
        return (mgg - mgsc);
    }
    public double Pks(double mgks, double T, double Vks){
        return (mgks*initialСonditions.R*T/Vks);
    }

    public double Psc(double mgsc, double T, double Vsc){
        return (mgsc*initialСonditions.R*T/Vsc);
    }

    public double Sgor(double xgor){
        return ((Math.PI*(initialСonditions.Dzar+initialСonditions.dzar)/2)*(initialСonditions.Dzar-initialСonditions.dzar+2*initialСonditions.L)
        -4*Math.PI*(initialСonditions.Dzar+initialСonditions.dzar)*xgor);
    }

    public double fi(double l){
        return (l/(initialСonditions.rsr*Math.tan(initialСonditions.delta)));
    }

    public double U(double xgor, double pks){
        if (xgor <= (initialСonditions.Dzar - initialСonditions.dzar)/4) {
            return (initialСonditions.B / (initialСonditions.B - (initialСonditions.tokrSr - 20))) * (0.00546 + (5.36 / (Math.pow(10, 8))) * pks);
        }else return 0;
    }

    public double Aist(double Pks_delit_Psc, double T){
        double k = initialСonditions.k;
        if (Math.abs(Pks_delit_Psc - 1) <= 0.1) {
            return 0;
        } else {
            if (Pks_delit_Psc >= initialСonditions.criteria_Pks_Psc) {
                return (Math.pow((2 / (k + 1)), 2) * Math.sqrt((2 * k) / ((k + 1) * initialСonditions.R * initialСonditions.ksi * T)));
            } else {
                return Math.sqrt((2 * k / ((k - 1) * initialСonditions.R * initialСonditions.ksi * T)) * (Math.pow(Pks_delit_Psc, 2 / k) - Math.pow(Pks_delit_Psc, (k + 1) / k)));
            }
        }
    }

    public double A(double Mvn_plus_Mves){
        if(Mvn_plus_Mves <= 0){
            return (1+(2*initialСonditions.ftr/Math.tan(initialСonditions.delta))-Math.pow(initialСonditions.ftr, 2))/(Math.tan(initialСonditions.delta)-initialСonditions.ftr);
        }else {
            return (1-(2*initialСonditions.ftr/Math.tan(initialСonditions.delta))-Math.pow(initialСonditions.ftr, 2))/(Math.tan(initialСonditions.delta)+initialСonditions.ftr);
        }
    }

    public double Mves(double fi){
        return initialСonditions.g*(initialСonditions.m1*initialСonditions.r1+initialСonditions.m2*initialСonditions.r2)*Math.sin(fi);
    }

    // Основные функции для систем уравнений
    public double equationForVelocity(double Psc, double Mvn, double Mves) {
        return (Psc*initialСonditions.Spor+(Mvn+Mves)*A( (Mvn+Mves) ))/
                (initialСonditions.msht+ ( initialСonditions.J/( Math.pow(initialСonditions.rsr, 2)*Math.tan(initialСonditions.delta) ) ));
    }

    public double equation_mgsc(double Pks_delit_Psc, double mgks, double T, double Vks){
        return Aist(Pks_delit_Psc, T)*initialСonditions.Skr*Pks(mgks, T, Vks);
    }

    public double equation_mgg(double xgor, double pks){
        return U(xgor, pks)*Sgor(xgor)*initialСonditions.gama;
    }

    public double equation_Vks(double xgor, double pks){
        return U(xgor, pks)*Sgor(xgor);
    }

    public double equation_T(double T, double mgg, double xgor, double pks, double v){
        return ( U(xgor, pks)*Sgor(xgor)*initialСonditions.gama/(mgg*initialСonditions.cv) )*(initialСonditions.ksi*initialСonditions.Tp
                -initialСonditions.cv*T)-(pks*initialСonditions.Spor*v)/(mgg*initialСonditions.cv);
    }

}