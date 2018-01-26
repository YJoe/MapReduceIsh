package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Reducer<K, V> extends Thread{
    public Pair<K, ArrayList<V>> input;
    protected Pair<K, V> output;

    public Reducer(){}

    @Override
    public void run(){
        reduce();
    }

    public abstract void reduce();

    public Pair<K, V> getOutput(){
        return output;
    }
}
