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

        output = new ArrayList<>();
        ArrayList<K> seenKeys = new ArrayList<>();

        for(int i = 0; i < input.size(); i++){
            int foundIndex = -1;
            for(int j = 0; j < seenKeys.size(); j++){
                if(seenKeys.get(j).equals(input.get(i).getKey())){
                    foundIndex = j;
                    output.get(j).getValue().add(input.get(i).getValue());
                    break;
                }
            }

            if(foundIndex < 0){
                seenKeys.add(input.get(i).getKey());
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
