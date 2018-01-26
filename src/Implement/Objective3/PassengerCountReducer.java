package Implement.Objective3;

import Abstract.Reducer;
import javafx.util.Pair;

public class PassengerCountReducer extends Reducer<String, Integer, Integer>{

    @Override
    public void reduce() {
        int count = 0;
        for(int i = 0; i < input.getValue().size(); i++){
            count += input.getValue().get(i);
        }
        output = new Pair<>(input.getKey(), count);
    }
}
