package function;


import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
public interface interfaceFunction { // интерфейс обобщающий способ обращения к моделируемым функциям
    abstract public double calculate ( double x , HashMap<String, Double> values );
}
