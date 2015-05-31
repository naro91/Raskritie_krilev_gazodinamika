package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abovyan on 27.05.15.
 * Вспомогательный класс для определения коэффициента перевода из системы единиц СИ в требуемую единицу измерения
 */
public class MeasureSystem {
    private Map<String, String[]> measureSystemsFromName = new HashMap<>();
    private Map<String, Double> unitСonversionСoefficient = new HashMap<>();

    public MeasureSystem () {
        // добавляем информацию о единицах измерения вычисляемых параметров
        String[] temperatureMeasureUnit = {"kelvin"};
        measureSystemsFromName.put("Temperature", temperatureMeasureUnit);
        String[] massaMeasureUnit = {"kg", "g"};
        measureSystemsFromName.put("Massa_g", massaMeasureUnit);
        measureSystemsFromName.put("Massa_sc", massaMeasureUnit);
        String[] movementMeasureUnit = {"m", "cm", "mm", "km"};
        measureSystemsFromName.put("X", movementMeasureUnit);
        measureSystemsFromName.put("X_sht", movementMeasureUnit);
        measureSystemsFromName.put("movingPusher", movementMeasureUnit);
        String[] velocityMeasureUnit = {"m/s", "cm/s", "mm/s", "km/h"};
        measureSystemsFromName.put("Velocity", velocityMeasureUnit);
        measureSystemsFromName.put("U", velocityMeasureUnit);
        String[] angleMeasureUnit = {"radian", "degree"};
        measureSystemsFromName.put("angleEndPanel", angleMeasureUnit);
        String[] areaMeasureUnit = {"m^2", "cm^2", "mm^2"};
        measureSystemsFromName.put("S", areaMeasureUnit);
        String[] volumeMeasureUnit = {"m^3", "cm^3", "mm^3"};
        measureSystemsFromName.put("Vks", volumeMeasureUnit);
        measureSystemsFromName.put("V_sc", volumeMeasureUnit);
        String[] pressureMeasureUnit = {"Pa", "at"};
        measureSystemsFromName.put("P_ks", pressureMeasureUnit);
        measureSystemsFromName.put("P_sc", pressureMeasureUnit);

        // добавление коэффицентов перевода из СИ в требуемую систему единиц
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
        unitСonversionСoefficient.put("radian", 1.0);
        unitСonversionСoefficient.put("m^2", 1.0);
        unitСonversionСoefficient.put("cm^2", 10000.0);
        unitСonversionСoefficient.put("mm^2", 1000000.0);
        unitСonversionСoefficient.put("m^3", 1.0);
        unitСonversionСoefficient.put("cm^3", 1000000.0);
        unitСonversionСoefficient.put("mm^3", 1000000000.0);
        unitСonversionСoefficient.put("Pa", 1.0);
        unitСonversionСoefficient.put("at", -101325.0);
        unitСonversionСoefficient.put("kelvin", 1.0);
    }
    public String[] getArrayMeasureUnit() {
        return new String[]{"единица измерения"};
    }

    public String[] getArrayMeasureUnitByName(String name) {
        String[] temp;
        return (temp = measureSystemsFromName.get(name) ) != null ? temp : new String[]{"единица измерения"};
    }

    public double getCoefficientConversionByName(String name) {
        Double temp;
        return (temp = unitСonversionСoefficient.get(name)) != null ? temp : 0;
    }
}
