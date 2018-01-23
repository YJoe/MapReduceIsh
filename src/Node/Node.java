package Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Node<K, V> {
    private ArrayList<HashMap<K, V>> data;

    public Node(ArrayList<HashMap<K, V>> data){
        this.data = data;
    }

    public void joinOn(Node<K, V> otherNode, K leftKey, K rightKey){

        // check we are asking for a valid join
        if(!data.get(0).containsKey(leftKey) || !otherNode.data.get(0).containsKey(rightKey)){
            throw new Error("One of these nodes doesn't have the keys you requested");
        }

        for(int i = 0; i < data.size(); i++){

            // assume we wont find it
            int foundIndex = -1;

            // for each hash map in this node
            for(int j = 0; j < otherNode.data.size(); j++){

                // if the left key value of this nodes hash map equals the right key value of the other node hash map
                if (data.get(i).get(leftKey).toString().equals(otherNode.data.get(j).get(rightKey).toString())) {

                    // record what index we found it on and leave
                    foundIndex = j;
                    break;
                }
            }

            // if we found it
            if (foundIndex > -1){

                // put all of the data we found into this nodes hash map
                data.get(i).putAll(otherNode.data.get(foundIndex));
            }

            // if we didn't find it
            else {

                // for all of the keys in the first map of the other node (they will all be the same anyway)
                for (K key : otherNode.data.get(0).keySet()) {

                    // put the key and a null value
                    data.get(i).put(key, null);
                }

                System.out.println("NULLED ON JOIN [" + leftKey + "->" + rightKey + "] " + data.get(i));
            }
        }
    }

    public ArrayList<Node<K, V>> splitData(int splitCount){

        // define a list of nodes to send back and the starting point to iterate through the existing data
        ArrayList<Node<K, V>> splitNodes = new ArrayList<>();
        int dataIndex = 0;

        // for how many nodes we want to split into
        for(int i = 0; i < splitCount; i++){

            // create a temp array list to populate the node with
            ArrayList<HashMap<K, V>> temp = new ArrayList<>();

            // for how ever many data elements we can fit into one node
            for(int j = 0; j < data.size() / splitCount; j++){

                // add the data to the list and increment the index for next time
                temp.add(data.get(dataIndex++));
            }

            // create a node from the array list
            splitNodes.add(new Node<>(temp));
        }

        // add the remaining elements to the first few nodes
        for(int i = 0; i < data.size() % splitCount; i++){
            splitNodes.get(i).data.add(data.get(dataIndex++));
        }

        // print for fun
        for(int i = 0; i < splitNodes.size(); i++){
            System.out.println(splitNodes.get(i).data.size());
        }

        return splitNodes;
    }
}
