package Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    public ArrayList<ArrayList<String>> data;
    public ArrayList<ArrayList<ArrayList<String>>> splitData;
    public boolean dataHasBeenSplit;

    public CSVReader(String directory){
        data = new ArrayList<>();
        splitData = new ArrayList<>();
        dataHasBeenSplit = false;
        readCSVLSVAsArrayList(directory);
    }

    public void readCSVLSVAsArrayList(String fileDirectory){
        System.out.println("Reading data from [" + fileDirectory + "]");

        // read the file as a scanner
        Scanner s = new Scanner(CSVReader.class.getResourceAsStream(fileDirectory));

        // while there is a line to read
        while (s.hasNext()){
            data.add(new ArrayList<>(Arrays.asList(s.nextLine().split(","))));
        }
    }

    public void splitDataToPiles(int pileCount){
        System.out.println("Splitting data");
        if (dataHasBeenSplit){
            System.out.println("\tData has already been split");
            return;
        }
        System.out.println("\tsplitting data into [" + pileCount + "] piles");
        System.out.println("\tdata size [" + data.size() + "]");

        if(pileCount > data.size()){
            pileCount = data.size();
            System.out.println("\tthat's just stupid, you want more piles than you have data, pile count " +
                    "has been reduced to the data size to avoid empty array lists");
        }

        System.out.println("\tsplitting data into [" + pileCount + "] piles");
        System.out.println("\tpile sizes will be at least [" + data.size() / pileCount + "] elements big");
        System.out.println("\tthe remaining [" + data.size() % pileCount + "] data elements will be split onto the first few piles");
        int dataIndex = 0;

        // for however many piles we want, add a new pile array list and add a chunk of the data to that list
        for(int i = 0; i < pileCount; i++){
            splitData.add(new ArrayList<>());
            for(int j = 0; j < data.size() / pileCount; j++){
                splitData.get(splitData.size() - 1).add(data.get(dataIndex++));
            }
        }

        for(int i = 0; i < data.size() % pileCount; i++){
            splitData.get(i).add(data.get(dataIndex++));
        }

        //System.out.println("\tdata has been split into [" + splitData.size() + "] piles");
        //for(int i = 0; i < splitData.size(); i++){
        //    System.out.println("\tpile [" + i + "] has [" + splitData.get(i).size() + "] elements in it");
        //}

        dataHasBeenSplit = true;
    }

    public void printData(){
        if(!dataHasBeenSplit) {
            for (int i = 0; i < data.size(); i++) {
                System.out.println("data element = " + data.get(i));
            }
        } else {
            for(int i = 0; i < splitData.size(); i++){
                System.out.println("pile [" + i + "]");
                for(int j = 0; j < splitData.get(i).size(); j++){
                    System.out.println("\tdata element = " + splitData.get(i).get(j));
                }
            }
        }
    }
}
