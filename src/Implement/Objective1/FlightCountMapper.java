package Implement.Objective1;

import Abstract.Mapper;
import javafx.util.Pair;
import java.util.HashMap;

public class FlightCountMapper extends Mapper<String, String> {

    @Override
    public void map() {
        for (HashMap<String, String> anInput : input) {
            output.add(new Pair<>(anInput.get("iata"), anInput.get("flight_id")));
        }
    }}
