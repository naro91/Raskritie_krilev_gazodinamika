package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abovyan on 27.05.15.
 * Вспомогательный класс для определения коэффициента перевода из системы единиц СИ в требуемую систему измерения
 */
public class MeasureSystem {
    private Map<String, String[]> measureSystemsFromName = new HashMap<>();
    private Map<String, Double> unitСonversionСoefficient = new HashMap<>();

    public MeasureSystem () {
        // добавляем информацию о единицах измерения вычисляемых параметров
        String[] temperatureMeasureUnit = {"kelvin"};
        measureSystemsFromName.put("Temperature", temperatureMeasureUnit);
        String[] massaMeasureUnit = {"g", "kg"};
        measureSystemsFromName.put("Massa_g", massaMeasureUnit);
        measureSystemsFromName.put("Massa_sc", massaMeasureUnit);
        String[] movementMeasureUnit = {"mm", "m", "cm", "km"};
        measureSystemsFromName.put("X", movementMeasureUnit);
        measureSystemsFromName.put("X_sht", movementMeasureUnit);
        measureSystemsFromName.put("movingPusher", movementMeasureUnit);
        measureSystemsFromName.put("movingPusher_r1(fi)", movementMeasureUnit);
        measureSystemsFromName.put("movingPusher_r2(fi)", movementMeasureUnit);
        measureSystemsFromName.put("movingPusher_fi(fi)", movementMeasureUnit);
        String[] velocityMeasureUnit = {"m/s", "cm/s", "mm/s", "km/h"};
        measureSystemsFromName.put("Velocity", velocityMeasureUnit);
        measureSystemsFromName.put("U", velocityMeasureUnit);
        String[] angleMeasureUnit = {"degree", "radian"};
        measureSystemsFromName.put("angleEndPanel", angleMeasureUnit);
        measureSystemsFromName.put("angleEndPanel_fi(fi)", angleMeasureUnit);
        String[] angleMeasureUnitExperiment = {"deg", "rad"};
        measureSystemsFromName.put("fi_experimental1", angleMeasureUnitExperiment);
        measureSystemsFromName.put("fi_experimental2", angleMeasureUnitExperiment);
        measureSystemsFromName.put("fi", angleMeasureUnit);
        String[] angularVelocityUnitExperiment = {"deg/s", "rad/s"};
        measureSystemsFromName.put("w", angularVelocityUnitExperiment);
        String[] areaMeasureUnit = {"cm^2", "m^2", "mm^2"};
        measureSystemsFromName.put("S", areaMeasureUnit);
        String[] volumeMeasureUnit = {"cm^3", "m^3", "mm^3"};
        measureSystemsFromName.put("Vks", volumeMeasureUnit);
        measureSystemsFromName.put("V_sc", volumeMeasureUnit);
        String[] pressureMeasureUnit = {"at", "Pa"};
        measureSystemsFromName.put("P_ks", pressureMeasureUnit);
        measureSystemsFromName.put("P_sc", pressureMeasureUnit);
        String[] pressureMeasureUnitExperimental = {"atm", "Pascal"};
        measureSystemsFromName.put("P(t)_experimental1", pressureMeasureUnitExperimental);
        measureSystemsFromName.put("P(t)_experimental2", pressureMeasureUnitExperimental);
        String[] tanLine = {"безразмерная"};
        measureSystemsFromName.put("K_FirstWing", tanLine);
        measureSystemsFromName.put("K_SecondWing", tanLine);

        // добавление коэффициентов перевода из СИ в требуемую систему единиц
        //unitСonversionСoefficient.put("ton", -1000.0);
        unitСonversionСoefficient.put("kg", 1.0);
        unitСonversionСoefficient.put("g", 1000.0);
        unitСonversionСoefficient.put("km", -1000.0);
        unitСonversionСoefficient.put("m", 1.0);
        unitСonversionСoefficient.put("cm", 100.0);
        unitСonversionСoefficient.put("mm", 1000.0);
        unitСonversionСoefficient.put("km/h", 3.6);
        unitСonversionСoefficient.put("m/s", 1.0);
        unitСonversionСoefficient.put("cm/s", 100.0);
        unitСonversionСoefficient.put("mm/s", 1000.0);
        unitСonversionСoefficient.put("degree", (180.0/Math.PI));
        unitСonversionСoefficient.put("deg/s", (180.0/Math.PI));
        unitСonversionСoefficient.put("radian", 1.0);
        unitСonversionСoefficient.put("rad/s", 1.0);
        unitСonversionСoefficient.put("rad", -(180.0/Math.PI));
        unitСonversionСoefficient.put("deg", 1.0);
        unitСonversionСoefficient.put("m^2", 1.0);
        unitСonversionСoefficient.put("cm^2", 10000.0);
        unitСonversionСoefficient.put("mm^2", 1000000.0);
        unitСonversionСoefficient.put("m^3", 1.0);
        unitСonversionСoefficient.put("cm^3", 1000000.0);
        unitСonversionСoefficient.put("mm^3", 1000000000.0);
        unitСonversionСoefficient.put("Pa", 1.0);
        unitСonversionСoefficient.put("at", -101325.0);
        unitСonversionСoefficient.put("atm", 1.0);
        unitСonversionСoefficient.put("Pascal", 101325.0);
        unitСonversionСoefficient.put("kelvin", 1.0);
        unitСonversionСoefficient.put("безразмерная", 1.0);
    }

    // возвращает начальный список единиц измерения
    public String[] getArrayMeasureUnit() {
        return new String[]{"единица измерения"};
    }

    // возвращает список единиц измерения для функции с именем name
    public String[] getArrayMeasureUnitByName(String name) {
        String[] temp;
        return (temp = measureSystemsFromName.get(name) ) != null ? temp : new String[]{"единица измерения"};
    }

    // возвращает коэффиценты перевода из си в требуемую систему измеренеия
    public double getCoefficientConversionByName(String name) {
        Double temp;
        return (temp = unitСonversionСoefficient.get(name)) != null ? temp : 0;
    }
}
