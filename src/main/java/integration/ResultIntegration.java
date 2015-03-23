package integration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abovyan on 21.12.14.
 * Класс предназначен для организации хранения результатов вычислений
 */
public class ResultIntegration {
    private Map<String, ArrayList<Double> > hashMapNameAndArraylist = new HashMap<String, ArrayList<Double>> ();
    private Map<String, Integer> indexes = new HashMap<String, Integer>();
    private Map<String, Double> marker = new HashMap<String, Double>();

    public Map<String, ArrayList<Double>> getHashMapNameAndArraylist() {
        return hashMapNameAndArraylist;
    }

    public Map<String, Integer> getIndexes() {
        return indexes;
    }

    public ResultIntegration () {

    }

    public void clearIndexAndArray() {
        for (String temp : hashMapNameAndArraylist.keySet()){
            hashMapNameAndArraylist.get(temp).clear();
            indexes.put(temp, 0);
        }
    }

    public void addResult ( String name ) {
        hashMapNameAndArraylist.put(name, new ArrayList<Double>());
        indexes.put(name, new Integer(0));
    }

    public void addResultResultIntegration(ResultIntegration resultIntegration) {
        for (String temp : resultIntegration.getHashMapNameAndArraylist().keySet()) {
            hashMapNameAndArraylist.put(temp, resultIntegration.getHashMapNameAndArraylist().get(temp));
            indexes.put(temp, resultIntegration.getHashMapNameAndArraylist().get(temp).size());
        }
        for (String temp : resultIntegration.getMarker().keySet()) {
            marker.put(temp, resultIntegration.getMarker().get(temp));
        }
    }

    public double pullForNameArray (String name) {
        return hashMapNameAndArraylist.get(name).get(indexes.get(name)-1);
    }

    public void setValueByName (String name, double value ) {
        hashMapNameAndArraylist.get( name ).add( indexes.get(name) , value);
        indexes.put(name, indexes.get(name) + 1);
    }

    public void printResultForName ( String name ) {
        System.out.println(hashMapNameAndArraylist.get(name).toString());
    }

    public void printFile(String filename) {
        try (PrintWriter out = new PrintWriter(filename) ) {
            for (String temp : hashMapNameAndArraylist.keySet()) out.printf("%s   ", temp);
            out.print("\n\n");
            for (int i = 0; i < indexes.get("parameterIntegration"); i++) {
                for (String temp : hashMapNameAndArraylist.keySet()) {
                    out.printf("%f   ", hashMapNameAndArraylist.get(temp).get(i));
                }
                out.print("\n");
            }
        }catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public void addParametrIntegration() {
        addResult("parameterIntegration"); // добавление контейнера для переменной по которому происходит интегирование
    }

    public void addMarker(String nameMarker, double position) {
        marker.put(nameMarker, position);
    }

    public Map<String, Double> getMarker () {
        return marker;
    }
}
