package DataMapping;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Mapper for the passenger data.
 * Extends Data Mapper with the types String, String
 */
public class PassengerInputMapper extends DataMapper<String, String> {
    private ArrayList<String> regexRules;
    private ArrayList<HashMap<String, String>> airportData;

    /**
     * Initialises the object with the business specific Regex Commands to be used
     *
     * @param data Passenger data as an ArrayList of ArrayList Strings to be mapped
     * @param airportData Airport data to validate passengers against for consistency
     */
    public PassengerInputMapper(ArrayList<ArrayList<String>> data, ArrayList<HashMap<String, String>> airportData){
        this.data = data;
        this.airportData = airportData;

        // define the problem specific keys for the passenger
        mappingKeys = new ArrayList<String>() {{
            add("passenger_id");
            add("flight_id");
            add("start_iata");
            add("end_iata");
            add("departure_time");
            add("flight_time");
        }};

        // define problem specific regex rules for the passenger data validation
        regexRules = new ArrayList<String>() {{
            add("[A-Z]{3}\\d{4}[A-Z]{2}\\d");
            add("[A-Z]{3}\\d{4}[A-Z]");
            add("[A-Z]{3}");
            add("[A-Z]{3}");
            add("\\d{10}");
            add("\\d{1,4}");
        }};
    }

    /**
     * @return Mapped and validated data
     */
    @Override
    public ArrayList<HashMap<String, String>> getValidatedData() {
        return mappedData;
    }

    /**
     * Child implementation of the parent DataMapper to insert extra logic for left joining data
     */
    protected void map(){

        // validate the passenger data against various regex rules
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
                    System.out.println(data.get(i) + " is not valid because the [" + mappingKeys.get(j) + "] does not meet the format [" + regexRules.get(j) + "]");
                    data.remove(i);
                    break;
                }
            }
        }

        // key the data
        super.map();

        // create a list of all of the airports for easy comparison later
        ArrayList<String> airportCodes = new ArrayList<>();
        for (HashMap<String, String> anAirportData : airportData) {
            airportCodes.add(anAirportData.get("iata"));
        }

        // validate the passenger data for consistency with the airport data
        for(int i = mappedData.size() - 1; i > -1; i--){

            // if the starting iata code of the passenger does not exist in the list of airports remove the row
            if(!airportCodes.contains(mappedData.get(i).get("start_iata"))){
                System.out.println(mappedData.get(i) + " is not valid because the from airport [" + mappedData.get(i).get("start_iata") + "] does not exist");
                mappedData.remove(i);
            }

            // if the end iata code of the passenger does not exist in the list of airports remove the row
            if(!airportCodes.contains(mappedData.get(i).get("end_iata"))){
                System.out.println(mappedData.get(i) + " is not valid because the to airport [" + mappedData.get(i).get("end_iata") + "] does not exist");
                mappedData.remove(i);
            }
        }

        // Join airport data to passenger data
        String leftKey = "iata";
        String rightKey = "start_iata";

        ArrayList<HashMap<String, String>> mergedData = new ArrayList<>();

        // check we are asking for a valid join
        if (!airportData.get(0).containsKey(leftKey) || !mappedData.get(0).containsKey(rightKey)) {
            throw new Error("One of these nodes doesn't have the keys you requested");
        }

        // create a hash map that is null to pass to rows that
        HashMap<String, String> nullHashMap = new HashMap<>();
        for (String mappingKey : mappingKeys) {
            nullHashMap.put(mappingKey, null);
        }

        for (HashMap<String, String> anAirportData : airportData) {
            boolean found = false;
            for (HashMap<String, String> aMappedData : mappedData) {
                if (anAirportData.get(leftKey).equals(aMappedData.get(rightKey))) {
                    found = true;
                    mergedData.add(new HashMap<>());
                    mergedData.get(mergedData.size() - 1).putAll(anAirportData);
                    mergedData.get(mergedData.size() - 1).putAll(aMappedData);
                }
            }
            if (!found) {
                System.out.println("airport " + anAirportData + " was not found, giving it its own row");
                mergedData.add(new HashMap<>());
                mergedData.get(mergedData.size() - 1).putAll(anAirportData);
                mergedData.get(mergedData.size() - 1).putAll(nullHashMap);
            }
        }

        mappedData.clear();
        mappedData = mergedData;
    }
}
