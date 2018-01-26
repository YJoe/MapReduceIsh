package Implement;

import Abstract.Mapper;
import Abstract.Reducer;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class MapReduceJob<I1, I2, O1, O2, O3> {

    public MapReduceJob() {
    }

    public ArrayList<Pair<O1, O3>> execute(ArrayList<ArrayList<HashMap<I1, I2>>> input,
                        Class<? extends Mapper<I1, I2, O1, O2>> mapper,
                        Class<? extends Reducer<O1, O2, O3>> reducer)
            throws IllegalAccessException, InstantiationException {

        // define containers for threads and output values
        ArrayList<Mapper<I1, I2, O1, O2>> mappers = new ArrayList<>();
        ArrayList<Combiner<O1, O2>> combiners = new ArrayList<>();
        ArrayList<Reducer<O1, O2, O3>> reducers = new ArrayList<>();
        ArrayList<Pair<O1, ArrayList<O2>>> combinerOutput = new ArrayList<>();
        ArrayList<Pair<O1, O3>> reducedValues = new ArrayList<>();

        // create and start mapping threads
        for (ArrayList<HashMap<I1, I2>> anInput : input) {
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
        for (Combiner<O1, O2> combiner : combiners) {
            try { combiner.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            combinerOutput.addAll(combiner.getOutput());
        }

        // shuffle outputs
        Shuffler<O1, O2> shuffler = new Shuffler<>();
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