package Implement.Objective1;

import Abstract.Reducer;
import javafx.util.Pair;
import java.util.ArrayList;

public class FlightCountReducer extends Reducer<String, String, Integer> {

    @Override
    public void reduce() {
        ArrayList<String> seenFlights = new ArrayList<>();

        for(int i = 0; i < input.getValue().size(); i++){
            boolean seen = false;
            for(int j = 0; j < seenFlights.size(); j++){
                if(seenFlights.get(j).equals(input.getValue().get(i))){
                    seen = true;
                    break;
                }
            }

            if(!seen && input.getValue().get(i) != null){
                seenFlights.add(input.getValue().get(i));
            }
        }

        output = new Pair<>(input.getKey(), seenFlights.size());
    }
}
