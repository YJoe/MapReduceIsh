package Implement.Objective3;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

public class PassengerCountMapper extends Mapper<String, Integer> {

    @Override
    public void map() {
        for (HashMap<String, String> anInput : input) {
            output.add(new Pair<>(anInput.get("flight_id"), 1));
        }
    }
}
