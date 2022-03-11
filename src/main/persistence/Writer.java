package persistence;

import model.CourseWorkList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of CourseWorkList to file
// Cited source: the sample provided on EDX course page regarding project phase 2 with the following GitHub link:
//               https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class Writer {
    private static final int TAB = 6;
    private PrintWriter myPwriter;
    private String address;

    // MODIFIES: this
    // EFFECTS: constructs a writer to write to file in the storage address
    public Writer(String storageAddress) {

        this.address = storageAddress;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if file in the storage address cannot
    //          be opened to write on it
    public void open() throws FileNotFoundException {
        myPwriter = new PrintWriter(new File(address));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CourseWorkList to file
    public void write(CourseWorkList cw) {
        JSONObject json = cw.convertToJson();
        writeStringToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the Writer
    public void close() {

        myPwriter.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the string to file
    private void writeStringToFile(String json) {

        myPwriter.print(json);
    }
}
