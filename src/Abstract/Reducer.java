package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Reducer<K, V1, V2> extends Thread{
    public Pair<K, ArrayList<V1>> input;
    protected Pair<K, V2> output;

    public Reducer(){}

    @Override
    public void run(){
        reduce();
    }

    public abstract void reduce();

    public Pair<K, V2> getOutput(){
        return output;
    }
}
