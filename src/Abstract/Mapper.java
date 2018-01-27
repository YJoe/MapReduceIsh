package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Mapper<K, V> extends Thread{
    public ArrayList<HashMap<K, K>> input;
    protected ArrayList<Pair<K, V>> output;

    public Mapper(){
         output = new ArrayList<>();
    }

    @Override
    public void run(){
        super.run();
        map();
    }

    protected abstract void map();

    public ArrayList<Pair<K, V>> getData(){
        return output;
    }
}
