package Implement;

import javafx.util.Pair;
import java.util.ArrayList;

public class Shuffler<K, V>{
    public ArrayList<Pair<K, ArrayList<V>>> output;

    public void shuffle(ArrayList<Pair<K, ArrayList<V>>> input) {
        output = new ArrayList<>();
        ArrayList<K> seenKeys = new ArrayList<>();

        for(int i = 0; i < input.size(); i++){
            int foundIndex = -1;
            for(int j = 0; j < seenKeys.size(); j++){
                if(seenKeys.get(j).equals(input.get(i).getKey())){
                    foundIndex = j;
                    output.get(j).getValue().addAll(input.get(i).getValue());
                    break;
                }
            }

            if(foundIndex < 0){
                seenKeys.add(input.get(i).getKey());
                output.add(new Pair<>(input.get(i).getKey(), input.get(i).getValue()));
            }
        }
    }
}
