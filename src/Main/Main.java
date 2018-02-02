package Main;

import Implement.MapReduceJob;
import Implement.Objective1.FlightCountMapper;
import Implement.Objective1.FlightCountReducer;
import Implement.Objective2.PassengerFlightMapper;
import Implement.Objective2.PassengerFlightReducer;
import Implement.Objective3.PassengerCountMapper;
import Implement.Objective3.PassengerCountReducer;
import Mapper.*;
import Reader.CSVReader;
import Writer.CSVWriter;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
        int splitCount = 1;
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
            passengerMappers.add(new PassengerInputMapper(aPassengerData, airportMapper.getData()));
            passengerMappers.get(passengerMappers.size() - 1).start();
        }

        ArrayList<ArrayList<HashMap<String, String>>> passengerMappedData = new ArrayList<>();
        for(int i = 0; i < passengerMappers.size(); i++){
            try {passengerMappers.get(i).join(); }
            catch (InterruptedException e) { e.printStackTrace();}
            passengerMappedData.add(passengerMappers.get(i).getData());
        }

        MapReduceJob<String, String, Integer> m1 = new MapReduceJob<>();
        ArrayList<Pair<String, Integer>> results1 = new ArrayList<>();
        try {results1 = m1.execute(passengerMappedData, FlightCountMapper.class, FlightCountReducer.class); }
        catch (IllegalAccessException | InstantiationException e) {e.printStackTrace();}
        CSVWriter<Integer> writer1 = new CSVWriter<>();
        writer1.writeToFile("objective1.csv", "IATA, Flight Count", results1);

        MapReduceJob<String, HashMap<String, String>, String> m2 = new MapReduceJob<>();
        ArrayList<Pair<String, String>> results2 = new ArrayList<>();
        try { results2 = m2.execute(passengerMappedData, PassengerFlightMapper.class, PassengerFlightReducer.class);}
        catch (IllegalAccessException | InstantiationException e) {e.printStackTrace();}
        CSVWriter<String> writer2 = new CSVWriter<>();
        writer2.writeToFile("objective2.csv", "Flight ID, Start IATA, End IATA, Departure Time, Arrival Time, Flight Time, Passenger IDs", results2);

        MapReduceJob<String, String, Integer> m3 = new MapReduceJob<>();
        ArrayList<Pair<String, Integer>> results3 = new ArrayList<>();
        try {results3 = m3.execute(passengerMappedData, PassengerCountMapper.class, PassengerCountReducer.class);}
        catch (IllegalAccessException | InstantiationException e) { e.printStackTrace();}
        CSVWriter<Integer> writer3 = new CSVWriter<>();
        writer3.writeToFile("objective3.csv", "Flight ID, Passenger Count", results3);
    }
}
