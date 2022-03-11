package persistence;

import exception.NotInWorkListException;
import model.Course;
import model.CourseWorkList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    private static final String INVALID_ADDRESS = "./data/my\123/abc/invalidFileName.json";
    private static final String VALID_ADDRESS_EMPTY_FILE = "./data/testWriterWithEmptyCourseWorkList.json";
    private static final String VALID_ADDRESS_SINGLE_COURSE_FILE = "./data/testWriterOneCourseInCourseWorkList.json";
    private static final String VALID_ADDRESS_MANY_COURSES = "./data/testWriterMultipleCoursesInCourseWorkList.json";
    private static final String VALID_ADDRESS_COURSES_REMAINING = "./data/testWriterManyCoursesInCourseWorkList.json";

    private CourseWorkList cw;
    private Writer myWriter;
    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeEach
    void runBefore() {
        cw = new CourseWorkList();
        course1 = new Course("CPSC210", 4);
        course2 = new Course("MATH 101", 3);
        course3 = new Course("ECON666", 3);



    }
    // test Writer when file is invalid
    @Test
    void testWriterInvalid() {
        try {

            Writer myWriter = new Writer(INVALID_ADDRESS);
            myWriter.open();
            fail("There's IOException exists");
        } catch (IOException e) {
            // expected
        }
    }

    // test Writer when CourseWorkList is empty (i.e. have nothing in the workList)
    @Test
    void testWriterEmptyWorkroom() {
        try {

            myWriter = new Writer(VALID_ADDRESS_EMPTY_FILE);
            myWriter.open();
            myWriter.write(cw);
            myWriter.close();

            LoaderReader testReader = new LoaderReader(VALID_ADDRESS_EMPTY_FILE);
            cw = testReader.readCourseWorkListFromFile();


        } catch (IOException e1) {
            fail("No Exception");
        }
        assertEquals(0, cw.getNumberOfRegisteredCourses());
        assertEquals(0, cw.getNumberOfWorkListCourses());
    }

    // test Writer when CourseWorkList has only ONE course in worklist
    @Test
    void testWriterOneCourseInWorkList() {
        try {
            cw.addCourseToWorkList(course1);
            myWriter = new Writer(VALID_ADDRESS_SINGLE_COURSE_FILE);
            myWriter.open();
            myWriter.write(cw);
            myWriter.close();

            LoaderReader testReader = new LoaderReader(VALID_ADDRESS_SINGLE_COURSE_FILE);
            cw = testReader.readCourseWorkListFromFile();


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(0, cw.getNumberOfRegisteredCourses()); // important to check this since we just added
                                                                    // to CourseWorkList and NOT registered any yet!
        assertEquals(1, cw.getNumberOfWorkListCourses());
        assertTrue(cw.containsInWorkList(course1));

        assertEquals("CPSC210", cw.viewWorkList().get(0).getCourseName());
        assertEquals(4, cw.viewWorkList().get(0).getCourseCredits());

    }

    // test Writer when CourseWorkList has multiple courses in worklist
    @Test
    void testWriterMultipleCoursesInWorkList() {
        try {
            cw.addCourseToWorkList(course1);
            cw.addCourseToWorkList(course2);
            cw.addCourseToWorkList(course3);
            myWriter = new Writer(VALID_ADDRESS_MANY_COURSES);
            myWriter.open();
            myWriter.write(cw);
            myWriter.close();

            LoaderReader testReader = new LoaderReader(VALID_ADDRESS_MANY_COURSES);
            cw = testReader.readCourseWorkListFromFile();


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(0, cw.getNumberOfRegisteredCourses()); // important to check this since we just added
                                                                    // to CourseWorkList and NOT registered any yet!
        assertEquals(3, cw.getNumberOfWorkListCourses());
        assertTrue(cw.containsInWorkList(course1));
        assertTrue(cw.containsInWorkList(course2));
        assertTrue(cw.containsInWorkList(course3));

        assertEquals("CPSC210", cw.viewWorkList().get(0).getCourseName());
        assertEquals(4, cw.viewWorkList().get(0).getCourseCredits());
        assertEquals("MATH 101", cw.viewWorkList().get(1).getCourseName());
        assertEquals(3, cw.viewWorkList().get(1).getCourseCredits());
        assertEquals("ECON666", cw.viewWorkList().get(2).getCourseName());
        assertEquals(3, cw.viewWorkList().get(2).getCourseCredits());
    }

    // test Writer when CourseWorkList has multiple courses in workList AFTER register some courses
    @Test
    void testWriterMultipleCoursesRemainingInWorkListAfterRegisterSomeCourses() {
        try {
            cw.addCourseToWorkList(course1);
            cw.addCourseToWorkList(course2);
            cw.addCourseToWorkList(course3);
            try {
                cw.register(course1);
            } catch (NotInWorkListException e) {
                fail("No exception expected.");
            }

            try {
                cw.register(course2);

            } catch (NotInWorkListException e) {
                fail("No exception expected.");
            }
            //cw.register(course1); // note that course1 is now removed from workList since it is registered!
            //cw.register(course2); // note that course2 is now removed from workList since it is registered!
            assertEquals(2, cw.getNumberOfRegisteredCourses());
            myWriter = new Writer(VALID_ADDRESS_COURSES_REMAINING);
            myWriter.open();
            myWriter.write(cw);
            myWriter.close();

            LoaderReader testReader = new LoaderReader(VALID_ADDRESS_COURSES_REMAINING);
            cw = testReader.readCourseWorkListFromFile();


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        //assertEquals(2, cw.getNumberOfRegisteredCourses());
        assertEquals(1, cw.getNumberOfWorkListCourses());
        assertFalse(cw.containsInWorkList(course1));
        assertFalse(cw.containsInWorkList(course2));
        assertTrue(cw.containsInWorkList(course3));

        assertEquals("ECON666", cw.viewWorkList().get(0).getCourseName());
        assertEquals(3, cw.viewWorkList().get(0).getCourseCredits());
    }
}
