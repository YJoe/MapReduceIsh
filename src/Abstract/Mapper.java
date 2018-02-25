package Abstract;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Class Mapper that extends Thread
 * @param <K> Data Key
 * @param <V> Data Value
 */
public abstract class Mapper<K, V> extends Thread{
    public ArrayList<HashMap<K, K>> input;
    protected ArrayList<Pair<K, V>> output;

    /**
     * Constructor for the base class Mapper
     */
    public Mapper(){
         output = new ArrayList<>();
    }

    /**
     * Override of Thread - run() used to call the map function
     */
    @Override
    public void run(){
        super.run();
        map();
    }

    /**
     * Abstract method to be implemented with problem specific logic
     */
    protected abstract void map();

    /**
     * @return The mapped and validated data of the job
     */
    public ArrayList<Pair<K, V>> getData(){
        return output;
    }
}
