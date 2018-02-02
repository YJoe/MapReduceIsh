package Implement.Objective2;

import Abstract.Reducer;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class PassengerFlightReducer extends Reducer<String, HashMap<String, String>, String> {

    @Override
    public void reduce() {

        long departure_seconds = Long.parseLong(input.getValue().get(0).get("departure_time"));
        long flight_seconds = Long.parseLong(input.getValue().get(0).get("flight_time")) * 60;
        long arrival_seconds = departure_seconds + flight_seconds;

        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        String str = input.getValue().get(0).get("start_iata") + ", "
                + input.getValue().get(0).get("end_iata") + ", "
                + df.format(new Date(departure_seconds * 1000)) + ", "
                + df.format(new Date(arrival_seconds * 1000)) + ", "
                + df.format(new Date(flight_seconds * 1000)) + ", ";

        StringBuilder passengers = new StringBuilder();
        for(int i = 0; i < input.getValue().size(); i++){
            passengers.append(input.getValue().get(i).get("passenger_id")).append(", ");
        }

        output = new Pair<>(input.getKey(), str + passengers);
    }
}
