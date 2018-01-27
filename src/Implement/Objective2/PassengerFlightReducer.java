package Implement.Objective2;

import Abstract.Reducer;

import java.util.ArrayList;
import java.util.HashMap;

public class PassengerFlightReducer extends Reducer<String, HashMap<String, String>, ArrayList<HashMap<String, String>>> {
    @Override
    public void reduce() {
        System.out.println("Reducing");
    }
}
