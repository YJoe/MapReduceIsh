package Implement.Objective3;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

public class PassengerCountMapper extends Mapper<String, String> {

    @Override
    public void map() {
        for (HashMap<String, String> anInput : input) {
            if(anInput.get("flight_id") != null){
                output.add(new Pair<>(anInput.get("flight_id"), anInput.get("passenger_id")));
            }
        }
    }
}
