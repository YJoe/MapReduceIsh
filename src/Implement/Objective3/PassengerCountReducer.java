package Implement.Objective3;

import Abstract.Reducer;
import javafx.util.Pair;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the Abstract Reducer using types String, String, Integer
 */
public class PassengerCountReducer extends Reducer<String, String, Integer> {

    /**
     * Implementation of the Abstract reduce function of Reducer
     * Creates a unique list of passengers, avoiding counting duplicate entries
     */
    @Override
    public void reduce() {

        // create a unique set of passenger ids to remove the duplicates
        Set<String> s = new HashSet<>(input.getValue());

        // key the flight id to the count of the unique passenger ids
        output = new Pair<>(input.getKey(), s.size());
    }
}
