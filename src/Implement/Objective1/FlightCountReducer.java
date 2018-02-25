package Implement.Objective1;

import Abstract.Reducer;
import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Implementation of the Abstract Reducer using types String, String, Integer
 */
public class FlightCountReducer extends Reducer<String, String, Integer> {

    /**
     * Implementation of the Abstract reduce function of Reducer
     * Counts flights found for a given Airport IATA
     */
    @Override
    public void reduce() {
        ArrayList<String> seenFlights = new ArrayList<>();

        // for each input
        for(int i = 0; i < input.getValue().size(); i++){

            // assume we haven't seen the flight id before
            boolean seen = false;

            // for all of the flights we have already seen
            for(int j = 0; j < seenFlights.size(); j++){

                // check if we have found it
                if(seenFlights.get(j).equals(input.getValue().get(i))){
                    seen = true;
                    break;
                }
            }

            // if we never saw it add to the list fo flights
            if(!seen && input.getValue().get(i) != null){
                seenFlights.add(input.getValue().get(i));
            }
        }

        // set the output to the airport code and the size of the seen flights from this airport
        output = new Pair<>(input.getKey(), seenFlights.size());
    }
}
