package persistence;

import org.json.JSONObject;

//This is the higher level abstraction of JsonReader and JsonWriter
public interface Writable {
    //EFFECTS: returns this as a JSONObject
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    JSONObject toJson();
}
