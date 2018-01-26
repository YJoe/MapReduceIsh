package Implement.Objective1;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class PassengerCountMapper extends Mapper<String, String, Integer> {

    public PassengerCountMapper() {
        output = new ArrayList<>();
    }

    @Override
    public void map() {
        for (HashMap<String, String> anInput : input) {
            output.add(new Pair<>(anInput.get("flight_id"), 1));
        }
    }
}
