package Implement.Objective3;

import Abstract.Reducer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PassengerCountReducer extends Reducer<String, String, Integer> {

    @Override
    public void reduce() {

        // create a unique set of passenger ids to remove the duplicates
        Set<String> s = new HashSet<>(input.getValue());

        // key the flight id to the count of the unique passenger ids
        output = new Pair<>(input.getKey(), s.size());
    }
}
