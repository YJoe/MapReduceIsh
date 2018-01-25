import java.util.ArrayList;
import java.util.HashMap;

public class Reducer <K, V> extends Thread {
    ArrayList<HashMap<K, V>> data;

    public Reducer(ArrayList<HashMap<K, V>> data){
        this.data = data;
    }


}
