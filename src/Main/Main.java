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

//        String IATAreg = "^\\w{3}\\b";
//        if ("erhf".matches(IATAreg)){
//            System.out.println("Yep");
//        } else {
//            System.out.println("narp");
//        }

        // define the files to use
        ArrayList<String> fileDirectories = new ArrayList<String>() {{
            add("/AComp_Passenger_data.csv");
            add("/Top30_airports_LatLong.csv");
        }};

        // open the files into a shiny object
        CSVReader passengerData = new CSVReader(fileDirectories.get(0));
        CSVReader airportData = new CSVReader(fileDirectories.get(1));

        int pileCount = 2;
        passengerData.splitDataToPiles(pileCount);
        passengerData.printData();

        System.out.println("Creating nodes");

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<String> passengerValidation = new ArrayList<>();

        passengerValidation.add("somoe regex0"); // passenger id
        passengerValidation.add("somoe regex1"); // flight id
        passengerValidation.add("^\\w{3}\\b"); // IATA, does this need to enforce cAsE?
        passengerValidation.add("^\\w{3}\\b"); // IATA
        passengerValidation.add("somoe regex4"); // departure time
        passengerValidation.add("somoe regex5"); // flight time

        // create a new node for each pile of data passing the pile and the validation for that data
        for(int i = 0; i < pileCount; i++){
            nodes.add(new Node("NODE" + i, passengerData.splitData.get(i), passengerValidation));
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

        System.out.println("Validation across piles complete");

//        SomeLoop sl1 = new SomeLoop("tag1", 4, 6);
//        SomeLoop sl2 = new SomeLoop("tag2", 2, 5);
//        sl1.start();
//        sl2.start();
//
//        try {
//            sl1.join();
//            sl2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(sl1.getState());
//        System.out.println(sl1.getResult());
//
//        System.out.println(sl2.getState());
//        System.out.println(sl2.getResult());
    }
}
