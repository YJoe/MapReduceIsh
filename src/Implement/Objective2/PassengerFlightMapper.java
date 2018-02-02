package Implement.Objective2;

import Abstract.Mapper;
import javafx.util.Pair;

import java.util.HashMap;

public class PassengerFlightMapper extends Mapper<String, HashMap<String, String>> {
    @Override
    protected void map() {
        for (HashMap<String, String> anInput : input) {
            if(anInput.get("flight_id") != null) {
                output.add(new Pair<>(anInput.get("flight_id"), anInput));
            }
        }
    }
}
