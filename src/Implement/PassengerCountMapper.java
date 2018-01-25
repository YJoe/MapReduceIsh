package Implement;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class PassengerCountMapper extends Mapper<ArrayList<HashMap<String, String>>, ArrayList<Pair<String, Integer>>> {

    public PassengerCountMapper(ArrayList<HashMap<String, String>> input) {
        super(input);
        output = new ArrayList<>();
    }

    @Override
    public void map() {
        for (HashMap<String, String> anInput : input) {
            output.add(new Pair<>(anInput.get("flight_id"), 1));
        }
    }
}
