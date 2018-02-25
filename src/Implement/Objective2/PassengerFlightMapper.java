package Implement.Objective2;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

/**
 * Implementation of the Abstract Mapper using the types String and String
 */
public class PassengerFlightMapper extends Mapper<String, HashMap<String, String>> {

    /**
     * Implementation of the abstract map function in Mapper
     * Maps FlightID to an entire data entry
     */
    @Override
    protected void map() {
        for (HashMap<String, String> anInput : input) {
            if(anInput.get("flight_id") != null) {
                output.add(new Pair<>(anInput.get("flight_id"), anInput));
            }
        }
    }
}
