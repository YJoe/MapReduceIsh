package Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    public ArrayList<ArrayList<String>> data;

    public CSVReader(String directory){
        data = new ArrayList<>();
        readCSVLSVAsArrayList(directory);
    }

    public void readCSVLSVAsArrayList(String fileDirectory){
        // read the file as a scanner
        Scanner s = new Scanner(CSVReader.class.getResourceAsStream(fileDirectory));

        // while there is a line to read
        while (s.hasNext()){
            data.add(new ArrayList<>(Arrays.asList(s.nextLine().split(","))));
        }
    }

    public void printData(){
        for(int i = 0; i < data.size(); i++){
            System.out.print("DATA = ");
            for(int j = 0; j < data.get(i).size(); j++){
                System.out.print(data.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}
