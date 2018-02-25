package Implement.Objective3;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

/**
 * Implementation of the Abstract Mapper using the types String and String
 */
public class PassengerCountMapper extends Mapper<String, String> {

    /**
     * Implementation of the abstract map function in Mapper
     * Maps FlightIDs to the PassengerID of an entry assuming the flight is not null
     */
    @Override
    public void map() {

        // for each of the input hash maps
        for (HashMap<String, String> anInput : input) {

            // some flight ids may be null due to the left join on the airport data in the passenger input mapper
            if(anInput.get("flight_id") != null){

                // map a flight id as the key and the passenger id as the value
                output.add(new Pair<>(anInput.get("flight_id"), anInput.get("passenger_id")));
            }
        }
    }
}
