package Implement;

import javafx.util.Pair;
import java.util.ArrayList;

public class Shuffler<K, V>{
    public ArrayList<ArrayList<Pair<K, V>>> output;

    public ArrayList<ArrayList<Pair<K, V>>> shuffle(ArrayList<Pair<K, V>> input) {
        output = new ArrayList<>();
        ArrayList<K> seenKeys = new ArrayList<>();

        for(int i = 0; i < input.size(); i++){
            int foundIndex = -1;
            for(int j = 0; j < seenKeys.size(); j++){
                if(seenKeys.get(j).equals(input.get(i).getKey())){
                    foundIndex = j;
                    output.get(j).add(input.get(i));
                    break;
                }
            }

            if(foundIndex < 0){
                seenKeys.add(input.get(i).getKey());
                output.add(new ArrayList<>());
                output.get(output.size() - 1).add(input.get(i));
            }
        }

        return output;
    }
}
