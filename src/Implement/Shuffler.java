package Implement;

import javafx.util.Pair;
import java.util.ArrayList;

/**
 * A single threaded operation to combine common keys from each Combiner
 * @param <K> Key type
 * @param <V> Value type
 */
public class Shuffler<K, V>{

    /**
     * Generic shuffle function used by all objectives
     * @param input The collection of data from Combiners to shuffle into common piles
     * @return Sorted data piles
     */
    public ArrayList<Pair<K, ArrayList<V>>> shuffle(ArrayList<Pair<K, ArrayList<V>>> input) {
        ArrayList<Pair<K, ArrayList<V>>> output = new ArrayList<>();
        ArrayList<K> seenKeys = new ArrayList<>();

        for(int i = 0; i < input.size(); i++){
            boolean found = false;
            for(int j = 0; j < seenKeys.size(); j++){
                if(seenKeys.get(j).equals(input.get(i).getKey())){
                    found = true;
                    output.get(j).getValue().addAll(input.get(i).getValue());
                    break;
                }
            }

            if(!found){
                seenKeys.add(input.get(i).getKey());
                output.add(new Pair<>(input.get(i).getKey(), input.get(i).getValue()));
            }
        }

        return output;
    }
}
