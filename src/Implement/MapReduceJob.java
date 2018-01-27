package Implement;

import Abstract.Mapper;
import Abstract.Reducer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class MapReduceJob<X, Y, Z> {

    public MapReduceJob() {}

    public ArrayList<Pair<X, Z>> execute(ArrayList<ArrayList<HashMap<X, X>>> input,
                        Class<? extends Mapper<X, Y>> mapper,
                        Class<? extends Reducer<X, Y, Z>> reducer) // was ist das?
            throws IllegalAccessException, InstantiationException {

        // define containers for threads and output values
        ArrayList<Mapper<X, Y>> mappers = new ArrayList<>();
        ArrayList<Combiner<X, Y>> combiners = new ArrayList<>();
        ArrayList<Reducer<X, Y, Z>> reducers = new ArrayList<>();
        ArrayList<Pair<X, ArrayList<Y>>> combinerOutput = new ArrayList<>();
        ArrayList<Pair<X, Z>> reducedValues = new ArrayList<>();

        // create and start mapping threads
        for (ArrayList<HashMap<X, X>> anInput : input) {
            mappers.add(mapper.newInstance());
            mappers.get(mappers.size() - 1).input = anInput;
            mappers.get(mappers.size() - 1).start();
        }

        // join passenger count mapper threads
        for (int i = 0; i < mappers.size(); i++) {
            try { mappers.get(i).join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
//            System.out.println(mappers.get(i).getData());
//            throw new Error();
            combiners.add(new Combiner<>(mappers.get(i).getData()));
            combiners.get(combiners.size() - 1).start();
        }

        // join combiner threads
        for (Combiner<X, Y> combiner : combiners) {
            try { combiner.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            combinerOutput.addAll(combiner.getOutput());
        }

        // shuffle outputs
        Shuffler<X, Y> shuffler = new Shuffler<>();
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