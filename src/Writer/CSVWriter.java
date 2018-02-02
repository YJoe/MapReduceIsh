package Writer;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CSVWriter<T> {

    public void writeToFile(String fileName, String headings, ArrayList<Pair<String, T>> data){
        PrintWriter writer = null;
        try { writer = new PrintWriter(fileName, "UTF-8"); }
        catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace();}
        assert writer != null;
        writer.println(headings);
        for(int i = 0; i < data.size(); i++){
            writer.println(data.get(i).getKey() + ", " + data.get(i).getValue().toString());
        }
        writer.close();
    }

}
