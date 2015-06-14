package function;


import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
// интерфейс обобщающий способ обращения к моделируемым функциям
public interface interfaceFunction {
    abstract public double calculate ( double x , HashMap<String, Double> values );
}
