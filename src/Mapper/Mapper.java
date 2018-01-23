package Mapper;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Mapper<K, V> extends Thread{
    ArrayList<HashMap<K, V>> mappedData;
    ArrayList<ArrayList<V>> data;
    ArrayList<K> mappingKeys;

    void map(){
        mappedData = new ArrayList<>();
        for (ArrayList<V> aData : data) {

            // assumes keys is the same size as the row of a pile
            mappedData.add(new HashMap<>());
            for (int j = 0; j < mappingKeys.size(); j++) {
                mappedData.get(mappedData.size() - 1).put(mappingKeys.get(j), aData.get(j));
            }
        }
    }

    @Override
    public void run() {
        super.run();
        map();
    }

    protected ArrayList<HashMap<K, V>> getData() {
        return mappedData;
    }
}
