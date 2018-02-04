package Mapper;
import Abstract.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataMapper<K, V> extends Mapper<K, V> {

    // using the generic types, create placeholders for the mapping job
    ArrayList<HashMap<K, V>> mappedData;
    ArrayList<ArrayList<V>> data;
    ArrayList<K> mappingKeys;

    protected void map(){
        mappedData = new ArrayList<>();

        // for every row in the data to map
        for (ArrayList<V> aData : data) {

            // assumes keys is the same size as the row of a pile
            mappedData.add(new HashMap<>());

            // for every key to map create an element in the hash map to hold the key and the data
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

    protected ArrayList<HashMap<K, V>> getValidatedData() {
        return mappedData;
    }
}
