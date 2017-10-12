package Main;

import Reader.CSVReader;
import java.util.ArrayList;

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

        // open the files into a shiny object
        CSVReader passengerData = new CSVReader(fileDirectories.get(0));
        CSVReader airportData = new CSVReader(fileDirectories.get(1));

        int pileCount = 4;
        passengerData.splitDataToPiles(pileCount);

        passengerData.printData();

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
