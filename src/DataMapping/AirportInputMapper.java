package DataMapping;

import java.util.ArrayList;
import java.util.HashMap;

public class AirportInputMapper extends DataMapper<String, String> {
    private ArrayList<String> regexRules;

    public AirportInputMapper(ArrayList<ArrayList<String>> data) {
        this.data = data;

        // define the problem specific keys for the airport
        mappingKeys = new ArrayList<String>() {{
            add("airpot_name");
            add("iata");
            add("latitude");
            add("lonitude");
        }};

        // define problem specific regex rules for the airport data validation
        regexRules = new ArrayList<String>(){{
            add("[A-Z| |/]{3,20}");
            add("[A-Z]{3}");
            add("(-|)\\d{1,2}.\\d{0,6}");
            add("(-|)\\d{1,3}.\\d{0,6}");
        }};
    }

    @Override
    public ArrayList<HashMap<String, String>> getValidatedData() {
        return mappedData;
    }

    @Override
    public void run() {

        // call the validator
        validate();

        // call the super functions (mapping)
        super.run();
    }

    private void validate(){

        // cycle through the data backwards so that we can remove elements without disturbing the order
        for(int i = data.size() - 1; i > -1; i--) {

            // if there aren't enough elements in the line, throw out the data and carry on
            if (data.get(i).size() != regexRules.size()) {
                System.out.println(data.get(i) + " is not valid because it does not have the correct number of fields");
                data.remove(i);
                continue;
            }

            // for all of the regex rules we need to validate against
            for (int j = 0; j < regexRules.size(); j++) {

                // if the element does not match its regex rule throw it out and break because we don't care if the other elements of the row fail
                if (!data.get(i).get(j).matches(regexRules.get(j))) {
                    System.out.println("Removing [" + data.get(i) + "] failed on validation [" + mappingKeys.get(j) + " (" + regexRules.get(j) + ")]");
                    data.remove(i);
                    break;
                }
            }
        }
    }
}
