package Writer;

import javafx.util.Pair;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CSVWriter<K, V> {

    public void writeToFile(String fileName, String headings, ArrayList<Pair<K, V>> data){

        // create a print writer
        PrintWriter writer = null;
        try { writer = new PrintWriter(fileName, "UTF-8"); }
        catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace();}

        // make sure that the writer has been created, if not, throw an error
        assert writer != null;

        // print a confirmation message
        System.out.println("Writing data to [" + fileName + "]");

        // write the headings to the file
        writer.println(headings);

        // for each element of the result, write to a new line the key and the value
        for(int i = 0; i < data.size(); i++){
            writer.println(data.get(i).getKey() + ", " + data.get(i).getValue().toString());
        }

        // close the file so that everything is flushed
        writer.close();
    }

}
