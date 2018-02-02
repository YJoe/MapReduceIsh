package Implement.Objective2;

import Abstract.Reducer;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PassengerFlightReducer extends Reducer<String, HashMap<String, String>, ArrayList<HashMap<String, String>>> {

    @Override
    public void reduce() {
        output = new Pair<>(input.getKey(), new ArrayList<HashMap<String, String>>());
        for(int i = 0; i < input.getValue().size(); i++){
            output.getValue().add(input.getValue().get(i));
        }
    }
}
