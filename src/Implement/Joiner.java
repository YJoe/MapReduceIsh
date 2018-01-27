package Implement;

import java.util.ArrayList;
import java.util.HashMap;

public class Joiner<K, V> {

    public ArrayList<HashMap<K, V>> joinOn(ArrayList<HashMap<K, V>> left, ArrayList<HashMap<K, V>> right, K leftKey, K rightKey){

        // check we are asking for a valid join
        if(!left.get(0).containsKey(leftKey) || !right.get(0).containsKey(rightKey)){
            throw new Error("One of these nodes doesn't have the keys you requested");
        }

        for(int i = 0; i < left.size(); i++){

            // assume we wont find it
            int foundIndex = -1;

            // for each hash map in this node
            for(int j = 0; j < right.size(); j++){

                // if the left key value of this nodes hash map equals the right key value of the other node hash map
                if (left.get(i).get(leftKey).toString().equals(left.get(j).get(rightKey).toString())) {

                    // record what index we found it on and leave
                    foundIndex = j;
                    break;
                }
            }

            // if we found it
            if (foundIndex > -1){

                // put all of the data we found into this nodes hash map
                left.get(i).putAll(right.get(foundIndex));
            }

            // if we didn't find it
            else {

                // for all of the keys in the first map of the other node (they will all be the same anyway)
                for (K key : right.get(0).keySet()) {

                    // put the key and a null value
                    left.get(i).put(key, null);
                }

                System.out.println("NULLED ON JOIN [" + leftKey + "->" + rightKey + "] " + left.get(i));
            }
        }

        return left;
    }
}
