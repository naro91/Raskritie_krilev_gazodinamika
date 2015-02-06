package integration;

/**
 * Created by Abovyan on 21.12.14.
 */
public class InitialСonditions {
    public double J, m1, m2, r1, r2, msht, Spor, delta, L, Dzar, dzar, V0sc, ksi, ftr, B, g, k, R, Tp, mzar, tokrSr, rsr, dotvKr, Skr, Vzar,
            gama, mgsc0, mgg0, cp, cv, criteria_Pks_Psc;
    public InitialСonditions () {
        J = 0.1785;
        m1 = 6.911;
        m2 = 3.297;
        r1 = 0.062;
        r2 = 0.197;
        msht = 0.197;
        Spor = 5.31;
        delta = Math.toRadians(76.5);
        L = 0.018;
        Dzar = 0.023;
        dzar = 0.011;
        V0sc = 4/1000000;
        ksi = 0.7;
        ftr = 0.095;
        B = 380;
        g = 9.8;
        k = 1.24;
        R = 361;
        Tp = 4000;
        mzar = 0.0082;
        tokrSr = 25;
        rsr = (0.005/2) + (0.01645/2);
        dotvKr = 0.002;
        Skr = Math.PI * dotvKr * dotvKr/4;
        Vzar = (Math.PI/4)*((Dzar*Dzar)-dzar*dzar)*L;
        gama = mzar/Vzar;
        cp = k*R/(k-1);
        cv = R/(k-1);
        mgsc0 = 40*Math.pow(10, 5)*V0sc/(R*2200);
        mgg0 = mgsc0 + 40*Math.pow(10, 5)*(52/1000000)/(R*2200);
        criteria_Pks_Psc = Math.pow( ((k + 1) / 2), (k/(k-1)) );
    }

}
