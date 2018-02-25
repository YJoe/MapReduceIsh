package Implement.Objective1;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

/**
 * Implementation of the Abstract Mapper using the types String and String
 */
public class FlightCountMapper extends Mapper<String, String> {

    /**
     * Implementation of the abstract map function in Mapper
     * Maps IATA to the FlightID of an entry
     */
    @Override
    public void map() {

        // for each of the inputs
        for (HashMap<String, String> anInput : input) {

            // map the iata code to the flight id
            output.add(new Pair<>(anInput.get("iata"), anInput.get("flight_id")));
        }
    }}
