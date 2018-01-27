package Main;

import Implement.MapReduceJob;
import Implement.Objective1.FlightCountMapper;
import Implement.Objective1.FlightCountReducer;
import Implement.Objective2.PassengerFlightMapper;
import Implement.Objective2.PassengerFlightReducer;
import Implement.Objective3.PassengerCountMapper;
import Implement.Objective3.PassengerCountReducer;
import Mapper.AirportKeyer;
import Mapper.PassengerKeyer;
import Implement.Joiner;
import Reader.CSVReader;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

// - Determine the number of flights from each airport; include a list of any airports not used.
// - Create a list of flights based on the Flight id, this output should include the passenger Id, relevant
//   IATA/FAA codes, the departure time, the arrival time (times to be converted to HH:MM:SS format),
//   and the flight times.
// - Calculate the number of passengers on each flight.

public class Main {

    public static void main(String[] args) {

        // the data we want to read in can be string
        CSVReader<String> passengerReader = new CSVReader<>("/AComp_Passenger_data.csv");
        CSVReader<String> airportReader = new CSVReader<>("/Top30_airports_LatLong.csv");

        // get x amount of groups of each data sets
        int splitCount = 4;
        ArrayList<ArrayList<ArrayList<String>>> passengerData = passengerReader.splitDataToPiles(splitCount);
        ArrayList<ArrayList<ArrayList<String>>> airportData = airportReader.splitDataToPiles(splitCount);

        // create and start mapping threads
        ArrayList<PassengerKeyer> passengerMappers = new ArrayList<>();
        ArrayList<AirportKeyer> airportMappers = new ArrayList<>();
        for (int i = 0; i < splitCount; i++) {
            passengerMappers.add(new PassengerKeyer(passengerData.get(i)));
            passengerMappers.get(passengerMappers.size() - 1).start();
            airportMappers.add(new AirportKeyer(airportData.get(i)));
            airportMappers.get(airportMappers.size() - 1).start();
        }

        // join mapper threads and get the validated data
        Joiner<String, String> joiner = new Joiner<>();
        ArrayList<ArrayList<HashMap<String, String>>> passengerMappedData = new ArrayList<>();
        for (int i = 0; i < splitCount; i++) {
            try {
                passengerMappers.get(i).join();
                airportMappers.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            passengerMappedData.add(passengerMappers.get(i).getData());
        }


        int objective = 1;

        if(objective == 1){
            MapReduceJob<String, String, Integer> m = new MapReduceJob<>();
            ArrayList<Pair<String, Integer>> results = new ArrayList<>();
            try {
                results = m.execute(passengerMappedData, FlightCountMapper.class, FlightCountReducer.class);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            for (Pair<String, Integer> result : results) {
                System.out.println(result);
            }
        }

        else if(objective == 2){
            MapReduceJob<String, HashMap<String, String>, ArrayList<HashMap<String, String>>> m = new MapReduceJob<>();
            ArrayList<Pair<String, ArrayList<HashMap<String, String>>>> results = new ArrayList<>();
            try {
                results = m.execute(passengerMappedData, PassengerFlightMapper.class, PassengerFlightReducer.class);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            for (Pair<String, ArrayList<HashMap<String, String>>> result : results) {
                System.out.println(result);
            }
        }

        else if(objective == 3){
            MapReduceJob<String, Integer, Integer> m = new MapReduceJob<>();
            ArrayList<Pair<String, Integer>> results = new ArrayList<>();
            try {
                results = m.execute(passengerMappedData, PassengerCountMapper.class, PassengerCountReducer.class);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            for (Pair<String, Integer> result : results) {
                System.out.println(result);
            }
        }
    }
}
