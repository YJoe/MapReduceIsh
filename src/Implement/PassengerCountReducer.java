package Implement;

import Abstract.Reducer;
import javafx.util.Pair;

import java.util.ArrayList;

public class PassengerCountReducer extends Reducer<ArrayList<Pair<String, Integer>>, Pair<String, Integer>>{

    public PassengerCountReducer(ArrayList<Pair<String, Integer>> input) {
        super(input);
    }

    @Override
    public void reduce() {

        int count = 0;
        for(int i = 0; i < input.size(); i++){
            count += input.get(i).getValue();
        }
        output = new Pair<>(input.get(0).getKey(), count);
    }
}
