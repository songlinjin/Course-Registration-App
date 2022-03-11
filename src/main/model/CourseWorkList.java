package model;

import exception.NotInWorkListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WriteToJSon;

import java.util.ArrayList;


import java.util.List;


// Representing the course workList consists of list of courses, list of registered courses, total registered courses
// for one term
public class CourseWorkList implements WriteToJSon {
    public List<Course> workList; // declare a workList (i.e. a list of courses that one would like to register)
    public List<Course> registeredCourseList; // a list of registered courses
    public int numCredits; // credits for each registered course
    public int totalTermCredits; // initial total credits for all registered courses







    // EFFECTS: the course workList is empty
    public CourseWorkList() {
        workList = new ArrayList<Course>();
        registeredCourseList = new ArrayList<Course>();
    }

    // MODIFIES: this
    // EFFECTS: add a course to the course workList if it's not already there
    public void addCourseToWorkList(Course myCourse) {
        if (! containsInWorkList(myCourse)) {
            workList.add(myCourse);


        }
    }

    // EFFECTS: return the course workList
    public List<Course> viewWorkList() {
        return workList;
    }

    // EFFECTS: Returns the number of courses in the course workList
    public int getNumberOfWorkListCourses() {
        return workList.size();

    }



    // REQUIRES: Course dropCourse is an element of the course worklist the workList is not empty
    // MODIFIES: this
    // EFFECTS: Course dropCourse is removed from the course worklist
    public void dropFromWorkList(Course dropCourse) {
        workList.remove(dropCourse);


    }


    // MODIFIES: this
    // EFFECTS: if the course is contained in the workList register a course and store that course into
    //          registeredCourseList;remove that course from the current course workList;
    //          if the workList is empty, throw an NotInWorkListException
    //          if the course is not contained in the workList, throw an NotInWorkListException
    public void register(Course registerCourse) throws NotInWorkListException {
        if (viewWorkList().size() == 0) {
            throw new NotInWorkListException();
        }
        if (!containsInWorkList(registerCourse)) {
            throw new NotInWorkListException();
        } else {
            workList.remove(registerCourse);
            registeredCourseList.add(registerCourse);
        }


    }

    // REQUIRES: Course dropRegistered is an element of the registeredCourseList and the registeredCourseList is not
    //           empty
    // MODIFIES: this
    // EFFECTS: Course dropCourse is removed from the registeredCourseList
    public void dropRegisteredCourse(Course dropRegistered) {
        registeredCourseList.remove(dropRegistered);

    }




    // EFFECTS: return the list of registered courses
    public List<Course> viewRegistered() {
        return registeredCourseList;
    }

    // EFFECTS: Returns the number of courses in the list of registered courses
    public int getNumberOfRegisteredCourses() {
        return registeredCourseList.size();

    }

    // MODIFIES: this
    //EFFECTS: calculate and return the sum of registered courses credits
    public int getTotalRegisteredCredits() {
        totalTermCredits = 0;
        for (Course c: registeredCourseList) {
            numCredits = c.getCourseCredits();
            totalTermCredits += numCredits;
        }
        return totalTermCredits;
    }



    // EFFECTS: Returns true if Course c is in the WorkList and false otherwise
    public boolean containsInWorkList(Course c) {
        for (int i = 0; i < viewWorkList().size(); i++) {
            String registeredName = viewWorkList().get(i).getCourseName();

            if (c.getCourseName().equals(registeredName)) {
                return true;
            }

        }
        return false;
    }


    // EFFECTS: Returns true if Course c is in the list of registered courses and false otherwise
    public boolean containsInRegistered(Course c) {
        return registeredCourseList.contains(c);
    }

    @Override
    //EFFECT: convert to jsonObject
    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();

        json.put("courses", coursesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArrayWorkList = new JSONArray();

        for (Course c : workList) {
            jsonArrayWorkList.put(c.convertToJson());
        }

        return jsonArrayWorkList;
    }


}
