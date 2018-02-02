package Implement;

import Abstract.Mapper;
import Abstract.Reducer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class MapReduceJob<K, V1, V2> {

    public MapReduceJob() {}

    public ArrayList<Pair<K, V2>> execute(ArrayList<ArrayList<HashMap<K, K>>> input,
                                          Class<? extends Mapper<K, V1>> mapper,
                                          Class<? extends Reducer<K, V1, V2>> reducer)
            throws IllegalAccessException, InstantiationException {

        // define containers for threads and output values
        ArrayList<Mapper<K, V1>> mappers = new ArrayList<>();
        ArrayList<Combiner<K, V1>> combiners = new ArrayList<>();
        ArrayList<Reducer<K, V1, V2>> reducers = new ArrayList<>();
        ArrayList<Pair<K, ArrayList<V1>>> combinerOutput = new ArrayList<>();
        ArrayList<Pair<K, V2>> reducedValues = new ArrayList<>();

        // create and start mapping threads
        for (ArrayList<HashMap<K, K>> anInput : input) {
            mappers.add(mapper.newInstance());
            mappers.get(mappers.size() - 1).input = anInput;
            mappers.get(mappers.size() - 1).start();
        }

        // join passenger count mapper threads
        for (int i = 0; i < mappers.size(); i++) {
            try { mappers.get(i).join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            combiners.add(new Combiner<>(mappers.get(i).getData()));
            combiners.get(combiners.size() - 1).start();
        }

        // join combiner threads
        for (Combiner<K, V1> combiner : combiners) {
            try { combiner.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            combinerOutput.addAll(combiner.getOutput());
        }

        // shuffle outputs
        Shuffler<K, V1> shuffler = new Shuffler<>();
        shuffler.shuffle(combinerOutput);

        // for all shuffled elements start a reducer thread
        for(int i = 0; i < shuffler.output.size(); i++){
            reducers.add(reducer.newInstance());
            reducers.get(reducers.size() - 1).input = shuffler.output.get(i);
            reducers.get(reducers.size() - 1).start();
        }

        // collect results from each reducer thread
        for(int i = 0; i < reducers.size(); i++){
            try { reducers.get(i).join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            reducedValues.add(reducers.get(i).getOutput());
        }

        return reducedValues;
    }
}