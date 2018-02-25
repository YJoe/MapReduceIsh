package Implement.Objective2;

import Abstract.Reducer;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of the Abstract Reducer using types String, HashMap of types String and String, String
 */
public class PassengerFlightReducer extends Reducer<String, HashMap<String, String>, String> {

    /**
     * Implementation of the Abstract reduce function of Reducer
     * Correctly formats data to match the format
     */
    @Override
    public void reduce() {

        // get data from each entry column
        long departure_seconds = Long.parseLong(input.getValue().get(0).get("departure_time"));
        long flight_seconds = Long.parseLong(input.getValue().get(0).get("flight_time")) * 60;
        long arrival_seconds = departure_seconds + flight_seconds;

        // create a date formatter and construct a comma separated string with the formatted data
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        String str = input.getValue().get(0).get("start_iata") + ", "
                + input.getValue().get(0).get("end_iata") + ", "
                + df.format(new Date(departure_seconds * 1000)) + ", "
                + df.format(new Date(arrival_seconds * 1000)) + ", "
                + df.format(new Date(flight_seconds * 1000)) + ", ";

        // add all passengers to another string separated by |
        StringBuilder passengers = new StringBuilder();
        for(int i = 0; i < input.getValue().size(); i++){
            passengers.append(input.getValue().get(i).get("passenger_id")).append("|");
        }

        // the output consists of the input FlightID and the flight data with the list of passengers
        output = new Pair<>(input.getKey(), str + passengers);
    }
}
