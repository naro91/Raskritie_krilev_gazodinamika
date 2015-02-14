package integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abovyan on 21.12.14.
 */
public class ResultIntegration {
    private static int index = 0;
    public static void clearIndex() {
        index = 0;
    }
    private Map<String, ArrayList<Double> > hashMapNameAndArraylist = new HashMap<String, ArrayList<Double>> ();

    public void addResult ( String name ) {
        hashMapNameAndArraylist.put(name, new ArrayList<Double>());
    }

    public double pullForNameArray (String name) {
        return hashMapNameAndArraylist.get(name).get(index-1);
    }

    public void setValueForName (String name, double value ) {
        hashMapNameAndArraylist.get( name ).add( index , value);
        ++index;
    }

    public void printResultForName ( String name ) {
        System.out.println(hashMapNameAndArraylist.get(name).get(index-1));
    }

}
