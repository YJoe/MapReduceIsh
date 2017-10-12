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
        for(int i = 0; i < validationList.size(); i++){
            for(int j = 0; j < pile.size(); j++){
                System.out.println("[" + tag + "] is this valid against regex? [" + pile.get(j).get(i) + "] [" + validationList.get(i) + "]");
            }
        }
    }

    @Override
    public void run() {
        validateData();
    }
}
