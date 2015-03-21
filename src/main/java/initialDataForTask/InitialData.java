package initialDataForTask;

/**
 * Created by Abovyan on 21.12.14.
 */
public class InitialData {
    public double J, m1, m2, r1, r2, msht, Spor, delta, L, Dzar, dzar, V0sc, ksi, ftr, B, g, k, R, Tp, mzar, tokrSr, rsr, dotvKr, Skr, Vzar,
            gama, mgsc0, mgg0, cp, cv, Vks0, X0, e, S0zar, K, dp, Tks0, X_sht0, velocity0, t;
    public double[][] M_vn = {
            {Math.toRadians(0), -4.71},
            {Math.toRadians(10), -58.17},
            {Math.toRadians(21), -77.50},
            {Math.toRadians(32), -98.49},
            {Math.toRadians(42), -120.76},
            {Math.toRadians(54), -119.78},
            {Math.toRadians(64), -115.17},
            {Math.toRadians(75), -106.93},
            {Math.toRadians(85), -80.93},
            {Math.toRadians(96), -53.37},
            {Math.toRadians(107), -24.53},
            {Math.toRadians(110), -20.0}
    };
    public InitialData() {
        J = 0.1785;
        m1 = 6.911;
        m2 = 3.297;
        r1 = 0.062;
        r2 = 0.197;
        msht = 0.197;
        dp = 0.026;
        Spor = 5.31/10000.0;//Math.PI*dp*dp/4;
        delta = Math.toRadians(76.5);
        L = 0.018;
        Dzar = 0.023;
        dzar = 0.011;
        V0sc = 4*Math.pow(10, -6);
        ksi = 0.7;
        ftr = 0.095;
        B = 380;
        g = 9.8;
        k = 1.24;
        R = 361;
        Tp = 4000;
        mzar = 0.0082;
        tokrSr = 20;
        Tks0 = 2200;
        rsr = (0.005/2.0) + (0.01645/2.0);
        dotvKr = 0.002;
        Skr = Math.PI * dotvKr * dotvKr/4.0;
        Vzar = (Math.PI/4.0)*((Dzar*Dzar)-dzar*dzar)*L;
        gama = mzar/Vzar;
        cp = k*R/(k-1);
        cv = R/(k-1);
        Vks0 = (52.0/1000000.0);
        mgsc0 = 40*Math.pow(10, 5)*V0sc/(R*Tks0);
        mgg0 = mgsc0 + 40*Math.pow(10, 5)*Vks0/(R*Tks0);
        X0 = 0;
        X_sht0 = 0;
        velocity0 = 0;
        e = (Dzar - dzar)/4;
        S0zar = (Math.PI*(Dzar+dzar)/2.0) * (Dzar-dzar+2.0*L);
        t = 20;
        K = B / (B - (t - 20));
    }

}
