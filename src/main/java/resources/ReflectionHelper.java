package resources;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class ReflectionHelper {
    private static ArrayList<Double[]> tempArray = new ArrayList<>();
    public static boolean indicateArray = false;
    public static Object createIntance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, String value) {

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(double.class)){
                field.set(object, Double.valueOf(value));
            } else if (field.getType().equals(double[][].class)){
                indicateArray = true;
                readArray(object, field, value);
            }

            field.setAccessible(false);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readArray(Object object, Field field, String value) {
        Double[] tempDoubleMass;
        String[] massStr = value.split("\n");
        for (String temp1 : massStr) {
            temp1 = temp1.replaceAll("\\s|\\{|\\}", "");
            if (temp1.isEmpty()) continue;
            String[] tempStringMass = temp1.split(",");
            tempDoubleMass = new Double[2];
            tempDoubleMass[0] = Double.valueOf(tempStringMass[0]);
            tempDoubleMass[1] = Double.valueOf(tempStringMass[1]);
            tempArray.add(tempDoubleMass);
        }
        indicateArray = true;
    }

    private static double[][] arrayListToArray() {
        double[][] mass = new double[tempArray.size()][2];
        int i = 0;
        for (Double[] temp : tempArray) {
            mass[i][0] = temp[0];
            mass[i][1] = temp[1];
            ++i;
        }
        return mass;
    }

    public static void setArrayField(Object object, String fieldName) {
        if (indicateArray) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                double[][] temp = arrayListToArray();
                field.set(object, temp);
                field.setAccessible(false);
                indicateArray = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
