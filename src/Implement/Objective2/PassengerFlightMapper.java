package Implement.Objective2;

import Abstract.Mapper;

import java.util.HashMap;

public class PassengerFlightMapper extends Mapper<String, HashMap<String, String>> {
    @Override
    protected void map() {
        System.out.println("Mapping");
    }
}
