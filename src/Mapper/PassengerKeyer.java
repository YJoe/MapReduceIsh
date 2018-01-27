package Mapper;

import java.util.ArrayList;
import java.util.HashMap;

public class PassengerKeyer extends Keyer<String, String> {
    private ArrayList<String> regexRules;

    public PassengerKeyer(ArrayList<ArrayList<String>> data){
        this.data = data;
        mappingKeys = new ArrayList<String>() {{
            add("passenger_id");
            add("flight_id");
            add("start_iata");
            add("end_iata");
            add("departure_time");
            add("flight_time");
        }};
        regexRules = new ArrayList<String>() {{
            add("[A-Z]{3}\\d{4}[A-Z]{2}\\d");
            add("[A-Z]{3}\\d{4}[A-Z]");
            add("[A-Z]{3}");
            add("[A-Z]{3}");
            add("\\d{10}");
            add("\\d{1,4}");
        }};
    }

    @Override
    public ArrayList<HashMap<String, String>> getData() {
        return mappedData;
    }

    @Override
    public void run() {
        validate();
        super.run();
    }

    private void validate(){
        for(int i = data.size() - 1; i > -1; i--) {
            if (data.get(i).size() != regexRules.size()) {
                data.remove(i);
                continue;
            }
            for (int j = 0; j < regexRules.size(); j++) {
                if (!data.get(i).get(j).matches(regexRules.get(j))) {
                    data.remove(i);
                    break;
                }
            }
        }
    }
}
