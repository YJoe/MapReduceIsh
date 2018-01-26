package Abstract;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Mapper<I1, I2, O1, O2> extends Thread{
    public ArrayList<HashMap<I1, I2>> input;
    protected ArrayList<Pair<O1, O2>> output;

    public Mapper(){
         output = new ArrayList<>();
    }

    @Override
    public void run(){
        super.run();
        map();
    }

    protected abstract void map();

    public ArrayList<Pair<O1, O2>> getData(){
        return output;
    }
}
