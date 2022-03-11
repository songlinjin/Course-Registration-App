package model;

import exception.InvalidCreditsException;
import org.json.JSONObject;
import persistence.WriteToJSon;

// Representing a course with course name and course credits
public class Course implements WriteToJSon {
    public int courseCredits; // course credits for each course

    public String courseName; // course name

    // REQUIRES: name has no zero length and credits >= 0
    // MODIFIES: this
    // EFFECTS: set courseName to name and set courseCredits to credits
    public Course(String name, int credits) {
        courseName = name;
        courseCredits = credits;
    }

    // EFFECTS: return course name
    public String getCourseName() {
        return courseName;
    }

    // EFFECTS: return course credits
    public int getCourseCredits() {
        return courseCredits;
    }

    // MODIFIES: this
    // EFFECTS: set courseName to String n
    public void setCourseName(String n) {
        courseName = n;

    }

    // MODIFIES: this
    // EFFECTS: set courseCredits to int n if c>=0, otherwise throws InvalidCreditsException
    public void setCourseCredits(int c) throws InvalidCreditsException {
        if (c >= 0) {
            courseCredits = c;
        } else {
            throw new InvalidCreditsException();
        }



    }


    // EFFECTS: Convert Course to JSON object
    @Override
    public JSONObject convertToJson() {
        JSONObject j = new JSONObject();
        j.put("Course name", courseName);
        j.put("Course credits", courseCredits);
        return j;
    }






}
