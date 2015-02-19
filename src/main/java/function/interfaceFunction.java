package function;


import java.util.HashMap;

/**
 * Created by Abovyan Narek on 14.02.15.
 */
public interface interfaceFunction {
    abstract public String getName ();
    abstract public double calculate ( double x , HashMap<String, Double> values );
}
