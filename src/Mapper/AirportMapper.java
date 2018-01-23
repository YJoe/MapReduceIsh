package Mapper;

import java.util.ArrayList;
import java.util.HashMap;

public class AirportMapper extends Mapper<String, String> {
    private ArrayList<String> regexRules;

    public AirportMapper(ArrayList<ArrayList<String>> data) {
        this.data = data;
        mappingKeys = new ArrayList<String>() {{
            add("airpot_name");
            add("iata");
            add("latitude");
            add("logitude");
        }};
        regexRules = new ArrayList<String>(){{
            add("[A-Z| ]{1,20}");
            add("[A-Z]{3}");
            add("(-|)\\d{1,3}.\\d{1,13}");
            add("(-|)\\d{1,3}.\\d{1,13}");
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
