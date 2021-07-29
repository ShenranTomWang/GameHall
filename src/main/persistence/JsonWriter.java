package persistence;

import model.User;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//This class represents a tool to write and save data to a JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    //EFFECTS: constructs JsonWriter to write to destination.
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //         be opened for writing
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of user to file
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void write(User user) {
        JSONObject json = user.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private void saveToFile(String json) {
        writer.print(json);
    }
}
