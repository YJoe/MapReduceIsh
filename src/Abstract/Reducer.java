package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Abstract Class Reducer that extends Thread
 * @param <K> Key
 * @param <V1> Value One
 * @param <V2> Value One
 */
public abstract class Reducer<K, V1, V2> extends Thread{
    public Pair<K, ArrayList<V1>> input;
    protected Pair<K, V2> output;

    /**
     * Override of Thread - run() used to call the reduce function
     */
    @Override
    public void run(){
        reduce();
    }

    /**
     * Abstract method to be implemented with problem specific logic
     */
    public abstract void reduce();

    /**
     * @return The reduced data of the job
     */
    public Pair<K, V2> getOutput(){
        return output;
    }
}
