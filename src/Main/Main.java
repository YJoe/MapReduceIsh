package Main;

import Reader.CSVReader;
import java.util.ArrayList;
import Node.Node;

// - Determine the number of flights from each airport; include a list of any airports not used.
// - Create a list of flights based on the Flight id, this output should include the passenger Id, relevant
//   IATA/FAA codes, the departure time, the arrival time (times to be converted to HH:MM:SS format),
//   and the flight times.
// - Calculate the number of passengers on each flight.

public class Main {
    public static void main(String[] args){

        // define the files to use
        ArrayList<String> fileDirectories = new ArrayList<String>() {{
            add("/AComp_Passenger_data.csv");
            add("/Top30_airports_LatLong.csv");
        }};

        // define map keys
        ArrayList<String> keys = new ArrayList<String>() {{
            add("passenger_id");
            add("flight_id");
            add("start_iata");
            add("end_iata");
            add("departure_time");
            add("flight_time");
        }};

        // open the files into a shiny object
        CSVReader passengerData = new CSVReader(fileDirectories.get(0));
        CSVReader airportData = new CSVReader(fileDirectories.get(1));

        int pileCount = 20;
        passengerData.splitDataToPiles(pileCount);
        passengerData.printData();

        System.out.println("Creating nodes");
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<String> passengerValidation = new ArrayList<>();

        passengerValidation.add("[A-Z]{3}\\d{4}[A-Z]{2}\\d"); // passenger id
        passengerValidation.add("[A-Z]{3}\\d{4}[A-Z]"); // flight id
        passengerValidation.add("[A-Z]{3}"); // IATA
        passengerValidation.add("[A-Z]{3}"); // IATA
        passengerValidation.add("\\d{10}"); // departure time
        passengerValidation.add("\\d{1,4}"); // flight time

        // create a new node for each pile of data passing the pile and the validation for that data
        for(int i = 0; i < pileCount; i++){
            nodes.add(new Node("NODE" + i, keys, passengerData.splitData.get(i), passengerValidation));
        }

        // start all node threads, starting the validation process
        for(int i = 0; i < nodes.size(); i++){
            nodes.get(i).start();
        }

        // halt the main thread until all nodes have been joined
        for(int i = 0; i < nodes.size(); i++){
            try {
                nodes.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
