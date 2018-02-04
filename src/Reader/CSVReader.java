package Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader <T>{
    private ArrayList<ArrayList<T>> data;
    private ArrayList<ArrayList<String>> stringData;
    private ArrayList<ArrayList<ArrayList<T>>> splitData;
    private boolean dataHasBeenSplit;

    public CSVReader(String directory){
        data = new ArrayList<>();
        stringData = new ArrayList<>();
        splitData = new ArrayList<>();
        dataHasBeenSplit = false;
        readCSVLSVAsArrayList(directory);
    }

    private void readCSVLSVAsArrayList(String fileDirectory){
        System.out.println("Reading data from [" + fileDirectory + "]");

        // read the file as a scanner
        Scanner s = new Scanner(CSVReader.class.getResourceAsStream(fileDirectory));

        // while there is a line to read
        while (s.hasNext()){

            // split the line on "," to get a list of elements
            stringData.add(new ArrayList<>(Arrays.asList(s.nextLine().split(","))));
            data.add(new ArrayList<>());

            // add each element of the result to the data list
            for(int i = 0; i < stringData.get(stringData.size() - 1).size(); i++){
                data.get(data.size() - 1).add((T)stringData.get(stringData.size() - 1).get(i));
            }
        }
    }

    public ArrayList<ArrayList<ArrayList<T>>> splitDataToPiles(int pileCount){

        // make sure we aren't being silly and splitting again
        if (dataHasBeenSplit){
            throw new Error("Data has already been split");
        }

        // if there isn't enough data to spread across the threads just reduce the number of threads
        if(pileCount > data.size()){
            pileCount = data.size();
         }

        int dataIndex = 0;

        // for however many piles we want, add a new pile array list and add a chunk of the data to that list
        for(int i = 0; i < pileCount; i++){
            splitData.add(new ArrayList<>());
            for(int j = 0; j < data.size() / pileCount; j++){
                splitData.get(splitData.size() - 1).add(data.get(dataIndex++));
            }
        }

        // for the remaining data elements that weren't added to a thread, add them to the first n threads
        for(int i = 0; i < data.size() % pileCount; i++){
            splitData.get(i).add(data.get(dataIndex++));
        }

        dataHasBeenSplit = true;
        return splitData;
    }
}
