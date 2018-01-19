package Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node extends Thread{
    private ArrayList<ArrayList<String>> pile;
    private ArrayList<ArrayList<Map<String, String>>> mappedPile;
    private ArrayList<String> keys;
    private ArrayList<String> validationList;
    private String tag;

    public Node(String tag, ArrayList<String> keys, ArrayList<ArrayList<String>> pile, ArrayList<String> validationList) {
        this.pile = pile;
        this.validationList = validationList;
        this.tag = tag;
        this.keys = keys;
    }

    private void validateData(){
        for(int i = pile.size() - 1; i > -1; i--){
            StringBuilder str = new StringBuilder();
            str.append("\n[").append(tag).append("] Validating set\n\t").append(pile.get(i));
            for(int j = 0; j < validationList.size(); j++) {
                if (!pile.get(i).get(j).matches(validationList.get(j))) {
                    str.append("\n\tInvalid on regex [").append(j).append(" - ").append(validationList.get(j)).append("] this row will be removed");
                    pile.remove(i);
                    break;
                }
            }
            System.out.println(str);
        }
    }

    private void mapData(){
        mappedPile = new ArrayList<>();
        for(int i = 0; i < pile.size(); i++){

            // assumes keys is the same size as the row of a pile
            mappedPile.add(new ArrayList<>());
            for(int j = 0; j < keys.size(); j++){
                HashMap<String, String> tempMap = new HashMap<>();
                tempMap.put(keys.get(j), pile.get(i).get(j));
                mappedPile.get(mappedPile.size() - 1).add(tempMap);
            }
        }
    }

    @Override
    public void run() {
        validateData();
    }
}
