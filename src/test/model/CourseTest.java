package model;

import exception.InvalidCreditsException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Representing test for Course
class CourseTest {
    private Course testCourse1;
    private Course testCourse2;
    private Course testCourse3;

    @BeforeEach
    void runBefore() {
        testCourse1 = new Course("MATH 101", 3);
        testCourse2 = new Course("CPSC 110", 4);
        testCourse3 = new Course("CONS 127", 3);

    }

    // test constructor
    @Test
    void testCourseConstructor() {
        assertEquals("MATH 101", testCourse1.getCourseName());
        assertEquals("CPSC 110", testCourse2.getCourseName());
        assertEquals("CONS 127", testCourse3.getCourseName());
        assertEquals(3, testCourse1.getCourseCredits());
        assertEquals(4, testCourse2.getCourseCredits());
        assertEquals(3, testCourse3.getCourseCredits());

    }

    // test getCourseName method
    @Test
    void testGetCourseName() {
        assertEquals("MATH 101", testCourse1.getCourseName());
        assertEquals("CPSC 110", testCourse2.getCourseName());
        assertEquals("CONS 127", testCourse3.getCourseName());
    }

    // test getCourseCredits method
    @Test
    void testGetCourseCredits() {
        assertEquals(3, testCourse1.getCourseCredits());
        assertEquals(4, testCourse2.getCourseCredits());
        assertEquals(3, testCourse3.getCourseCredits());
    }

    // test setCourseName method
    @Test
    void testSetCourseName() {
        assertEquals("MATH 101", testCourse1.getCourseName());
        testCourse1.setCourseName("cpsc");
        assertEquals("cpsc", testCourse1.getCourseName());
        testCourse1.setCourseName("123456");
        assertEquals("123456", testCourse1.getCourseName());
        assertEquals("CPSC 110", testCourse2.getCourseName());
        testCourse2.setCourseName("cpsc111");
        assertEquals("cpsc111", testCourse2.getCourseName());
    }

    // test setCourseCredits method
    @Test
    void testSetCourseCredits() {
        assertEquals(3, testCourse1.getCourseCredits());
        try {
            testCourse1.setCourseCredits(100);
            assertEquals(100, testCourse1.getCourseCredits());

        } catch (InvalidCreditsException e) {
            fail("No exception expected.");
        }

        try {
            testCourse1.setCourseCredits(0);
            assertEquals(0, testCourse1.getCourseCredits());

        } catch (InvalidCreditsException e) {
            fail("No exception expected.");
        }

        try {
            testCourse1.setCourseCredits(-1);
            fail("Credits cannot be negative.");

        } catch (InvalidCreditsException e) {
            // expected
        }
    }

    // test convertToJson method
    @Test
    void testConvertToJson() {
        JSONObject myJ = testCourse1.convertToJson();
        assertEquals("MATH 101", myJ.getString("Course name"));
        assertEquals(3, myJ.getInt("Course credits"));
    }






}