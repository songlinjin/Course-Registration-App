package persistence;

import model.Course;
import model.CourseWorkList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads CourseWorkList from JSON data stored in file
//Cited source: the sample provided on EDX course page regarding project phase 2 with the following GitHub link:
//              https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class LoaderReader {
    private String filename;

    // MODIFIES: this
    // EFFECTS: constructor - construct a LoaderReader to read the content of the the file at address with filename
    public LoaderReader(String userFilename) {
        filename = userFilename;

    }

    // EFFECTS: reads file as string and returns it; throws IOException if an error occurs reading data from file
    private String readFileAsString(String source) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s));
        }

        return content.toString();
    }

    // EFFECTS: reads CourseWorkLis from file and returns it; throws IOException if an error occurs reading data
    //          from file
    public CourseWorkList readCourseWorkListFromFile() throws IOException {
        String courseDataContent = readFileAsString(filename);
        JSONObject courseJsonObject = new JSONObject(courseDataContent);
        return extractCourseWorkList(courseJsonObject);

    }

    // EFFECTS: extract CourseWorkList from JSON object and returns it
    private CourseWorkList extractCourseWorkList(JSONObject j) {

        CourseWorkList cw = new CourseWorkList();
        addMultipleCourses(cw, j);
        return cw;
    }

    // MODIFIES: cw
    // EFFECTS: extract Courses from JSON object and adds them to worklist
    private void addMultipleCourses(CourseWorkList cw, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(cw, nextCourse);
        }
    }

    // MODIFIES: cw
    // EFFECTS: extract COURSE from JSON object and adds it to worklist
    private void addCourse(CourseWorkList cw, JSONObject jsonObject) {
        String name = jsonObject.getString("Course name");
        int credits = jsonObject.getInt("Course credits");
        Course course = new Course(name, credits);
        cw.addCourseToWorkList(course);
    }
}

