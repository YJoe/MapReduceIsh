package Main;

import Implement.MapReduceJob;
import Implement.Objective1.FlightCountMapper;
import Implement.Objective1.FlightCountReducer;
import Implement.Objective2.PassengerFlightMapper;
import Implement.Objective2.PassengerFlightReducer;
import Implement.Objective3.PassengerCountMapper;
import Implement.Objective3.PassengerCountReducer;
import DataMapping.*;
import Reader.CSVReader;
import Writer.CSVWriter;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // the data we want to read in can be string
        CSVReader<String> passengerReader = new CSVReader<>("data/AComp_Passenger_data.csv");
        CSVReader<String> airportReader = new CSVReader<>("data/Top30_airports_LatLong.csv");

        // get x amount of groups of each data sets
        int splitCount = Runtime.getRuntime().availableProcessors();
        ArrayList<ArrayList<ArrayList<String>>> passengerData = passengerReader.splitDataToPiles(splitCount);
        ArrayList<ArrayList<ArrayList<String>>> airportData = airportReader.splitDataToPiles(1);

        // validate the airport data on a single thread
        AirportInputMapper airportMapper = new AirportInputMapper(airportData.get(0));
        airportMapper.start();
        try { airportMapper.join();}
        catch (InterruptedException e) {e.printStackTrace();}

        // validate the passenger data across multiple threads passing the entire airport data to each
        ArrayList<PassengerInputMapper> passengerMappers = new ArrayList<>();
        for (ArrayList<ArrayList<String>> aPassengerData : passengerData) {
            passengerMappers.add(new PassengerInputMapper(aPassengerData, airportMapper.getValidatedData()));
            passengerMappers.get(passengerMappers.size() - 1).start();
        }

        // join the passenger mapper threads to the main thread append the return values to a common array list
        ArrayList<ArrayList<HashMap<String, String>>> passengerMappedData = new ArrayList<>();
        for(int i = 0; i < passengerMappers.size(); i++){
            try {passengerMappers.get(i).join(); }
            catch (InterruptedException e) { e.printStackTrace();}
            passengerMappedData.add(passengerMappers.get(i).getValidatedData());
        }

        // objective 1
        MapReduceJob<String, String, Integer> m1 = new MapReduceJob<>();
        ArrayList<Pair<String, Integer>> results1 = new ArrayList<>();
        try {results1 = m1.execute(passengerMappedData, FlightCountMapper.class, FlightCountReducer.class); }
        catch (IllegalAccessException | InstantiationException e) {e.printStackTrace();}
        CSVWriter<String, Integer> writer1 = new CSVWriter<>();
        writer1.writeToFile("objective_1_result.csv", "IATA, Flight Count", results1);

        // objective 2
        MapReduceJob<String, HashMap<String, String>, String> m2 = new MapReduceJob<>();
        ArrayList<Pair<String, String>> results2 = new ArrayList<>();
        try { results2 = m2.execute(passengerMappedData, PassengerFlightMapper.class, PassengerFlightReducer.class);}
        catch (IllegalAccessException | InstantiationException e) {e.printStackTrace();}
        CSVWriter<String, String> writer2 = new CSVWriter<>();
        writer2.writeToFile("objective_2_result.csv", "Flight ID, Start IATA, End IATA, Departure Time, Arrival Time, Flight Time, Passenger IDs", results2);

        // objective 3
        MapReduceJob<String, String, Integer> m3 = new MapReduceJob<>();
        ArrayList<Pair<String, Integer>> results3 = new ArrayList<>();
        try {results3 = m3.execute(passengerMappedData, PassengerCountMapper.class, PassengerCountReducer.class);}
        catch (IllegalAccessException | InstantiationException e) { e.printStackTrace();}
        CSVWriter<String, Integer> writer3 = new CSVWriter<>();
        writer3.writeToFile("objective_3_result.csv", "Flight ID, Passenger Count", results3);
    }
}
