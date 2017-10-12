package Node;

import java.util.ArrayList;
import java.util.Map;

public class Node extends Thread{
    private ArrayList<ArrayList<String>> pile;
    private ArrayList<String> validationList;
    private String tag;

    public Node(String tag, ArrayList<ArrayList<String>> pile, ArrayList<String> validationList) {
        this.pile = pile;
        this.validationList = validationList;
        this.tag = tag;
    }

    private void validateData(){
        for(int i = 0; i < pile.size(); i++){
            String message = "[" + tag + "] Validating set\n";
            message += "\t" + pile.get(i) + "\n\t";
            for(int j = 0; j < validationList.size(); j++){
                if(pile.get(i).get(j).matches(validationList.get(j))){
                    message += "-";
                } else{
                    message += "_";
                }
            }
            System.out.println(message);
        }
    }

    @Override
    public void run() {
        validateData();
    }
}
