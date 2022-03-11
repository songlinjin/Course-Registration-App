package persistence;

import model.Course;
import model.CourseWorkList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoaderReaderTest {
    private static final String INVALID_FILE = "./data/noFileFound.json";
    private static final String EMPTY_WORKLIST_FILE = "./data/testLoaderReaderEmptyFile.json";
    private static final String COURSE_WORKLIST_FILE = "./data/testLoaderReaderMultiple.json";
    private LoaderReader myReader;
    private CourseWorkList testCourseWorkList;
    private Course course1;
    private Course course2;
    private Course course3;
    private Writer myWriter;

    @BeforeEach
    void runBefore() {
        testCourseWorkList = new CourseWorkList();
        course1 = new Course("CPSC110", 4);
        course2 = new Course("MATH221", 3);
        course3 = new Course("PHIL120", 3);
        testCourseWorkList.addCourseToWorkList(course1);
        testCourseWorkList.addCourseToWorkList(course2);
        testCourseWorkList.addCourseToWorkList(course3);

    }

    // test LoaderReader when no file is found
    @Test
    void testLoaderReaderNoFoundFile() {
        myReader = new LoaderReader(INVALID_FILE);
        try {

            testCourseWorkList = myReader.readCourseWorkListFromFile();
            fail("IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testLoaderReaderEmptyWorkRoom() {
         myReader = new LoaderReader(EMPTY_WORKLIST_FILE);
        try {
            testCourseWorkList = myReader.readCourseWorkListFromFile();

            assertEquals(0, testCourseWorkList.getNumberOfWorkListCourses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testLoaderReaderWorkListWithMultipleCourses() {
        myReader = new LoaderReader(COURSE_WORKLIST_FILE);
        try {
            testCourseWorkList = myReader.readCourseWorkListFromFile();
            assertEquals(3, testCourseWorkList.getNumberOfWorkListCourses());
            assertEquals("CPSC210", testCourseWorkList.viewWorkList().get(0).getCourseName());
            assertEquals(4, testCourseWorkList.viewWorkList().get(0).getCourseCredits());
            assertEquals("ATSC113", testCourseWorkList.viewWorkList().get(1).getCourseName());
            assertEquals(3, testCourseWorkList.viewWorkList().get(1).getCourseCredits());
            assertEquals("MATH101", testCourseWorkList.viewWorkList().get(2).getCourseName());
            assertEquals(3, testCourseWorkList.viewWorkList().get(2).getCourseCredits());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }





}
