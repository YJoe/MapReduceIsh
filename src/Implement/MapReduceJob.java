package Implement;

import Abstract.Mapper;
import Abstract.Reducer;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A controller of the process of a MapReduce job, combining the workflow in a simple user class
 * @param <K> Data key type
 * @param <V1> Value one type
 * @param <V2> Value two type
 */
public class MapReduceJob<K, V1, V2> {

    /**
     * The execution of a MapReduceJob calling each stage of the workflow and providing the output of the reducer
     * @param input ArrayList of ArrayList HashMaps, all data to be split between threads
     * @param mapper The Mapper class type to instantiate Mapper objects with
     * @param reducer The Reducer class type to instantiate Reducer objects with
     * @return The formatted CSV output of the Reducer
     * @throws IllegalAccessException Thrown if access to a variable is not granted to an operation
     * @throws InstantiationException Thrown if the Classes passed "mapper" or "reducer" cannot be instantiated
     */
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
        ArrayList<Pair<K, ArrayList<V1>>> shufflerOutput = new ArrayList<>();

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
        shufflerOutput = shuffler.shuffle(combinerOutput);

        // for all shuffled elements start a reducer thread
        for(int i = 0; i < shufflerOutput.size(); i++){
            reducers.add(reducer.newInstance());
            reducers.get(reducers.size() - 1).input = shufflerOutput.get(i);
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