package Implement;

import javafx.util.Pair;
import java.util.ArrayList;

public class Combiner<K, V> extends Thread {
    ArrayList<Pair<K, V>> input;
    ArrayList<Pair<K, ArrayList<V>>> output;

    public Combiner(ArrayList<Pair<K, V>> input){
        this.input = input;
    }

    @Override
    public void run(){
        super.run();
        combine();
    }

    private void combine(){

        // define the output arraylist and means of recording seen keys
        output = new ArrayList<>();
        ArrayList<K> seenKeys = new ArrayList<>();

        // for all of the input elements from the mapper
        for(int i = 0; i < input.size(); i++){

            // assume we haven't found this key before
            boolean found = false;

            // search the current list of seen keys
            for(int j = 0; j < seenKeys.size(); j++){

                // if we have seen this key before
                if(seenKeys.get(j).equals(input.get(i).getKey())){

                    // record that we found it and add the entry to the output value arraylist
                    found = true;
                    output.get(j).getValue().add(input.get(i).getValue());

                    // break because we have already found the key and we want to move to the next input
                    break;
                }
            }

            // if we didn't find the input ket in the list of sene keys
            if(!found){

                // record that we have seen this key
                seenKeys.add(input.get(i).getKey());

                // create a new entry in the output and add the input value to it
                ArrayList<V> temp = new ArrayList<>();
                temp.add(input.get(i).getValue());
                output.add(new Pair<>(input.get(i).getKey(), temp));
            }
        }
    }

    public ArrayList<Pair<K, ArrayList<V>>> getOutput(){
        return output;
    }
}
