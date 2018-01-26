package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Mapper<K, V1, V2> extends Thread{
    public ArrayList<HashMap<K, V1>> input;
    protected ArrayList<Pair<V1, V2>> output;

    public Mapper(){

    }

    @Override
    public void run(){
        super.run();
        map();
    }

    protected abstract void map();

    public ArrayList<Pair<V1, V2>> getData(){
        return output;
    }
}
