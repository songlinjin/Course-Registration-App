package persistence;

import org.json.JSONObject;

// an interface that provide method to convert Course and CourseWorkList(i.e. list of course) to the json object
// and json array

// Cited source: the sample provided on EDX course page regarding project phase 2 with the following GitHub link:
//              https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface WriteToJSon {
    // EFFECTS: returns this as JSON object
    JSONObject convertToJson();
}
