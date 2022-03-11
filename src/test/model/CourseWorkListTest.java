package model;

import exception.NotInWorkListException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

// Representing test for CourseWorkList
public class CourseWorkListTest {
    private CourseWorkList testWorkList;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private Course course6;
    private List<Course> testViewWorkList; // for testing view method
    private List<Course> testViewRegistered;

    @BeforeEach
    void runBefore() {
        testWorkList = new CourseWorkList();
        course1 = new Course("CPSC 110", 4);
        course2 = new Course("CONS 127", 3);
        course3 = new Course("CHEM 111", 5);
        course4 = new Course("CHEM 111", 5);
        course5 = new Course("CHEM 111", 5);
        course6 = new Course("CPSC 210", 5);

    }

    // test addCourseToWorkList when adding one course
    @Test
    void testAddOneCourseToWorkList() {
        // check initial course worklist size
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
        testWorkList.addCourseToWorkList(course1);
        assertEquals(1, testWorkList.getNumberOfWorkListCourses());
        assertTrue(testWorkList.containsInWorkList(course1));


    }

    // test addCourseToWorkList when adding multiple courses
    @Test
    void testAddCourseToWorkListMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        assertEquals(2, testWorkList.getNumberOfWorkListCourses());
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course2));

        testWorkList.addCourseToWorkList(course3);
        assertEquals(3, testWorkList.getNumberOfWorkListCourses());
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course2));
        assertTrue(testWorkList.containsInWorkList(course3));

    }





    // test addCourseToWorkList when adding a already exist course
    @Test
    void testAddCourseToWorkListWhenAlreadyExist() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course1);

        assertEquals(2, testWorkList.getNumberOfWorkListCourses());
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course2));

    }

    // test viewWorkList method when there's no courses on workList
    @Test
    void testViewWorkListEmpty() {
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.dropFromWorkList(course1);
        testWorkList.dropFromWorkList(course2);

        testViewWorkList = testWorkList.viewWorkList();
        assertEquals(0, testViewWorkList.size());
        assertFalse(testViewWorkList.contains(course1));
        assertFalse(testViewWorkList.contains(course2));

    }

    // test viewWorkList method when there is only one course on workList
    @Test
    void testViewWorkListOne() {
        testWorkList.addCourseToWorkList(course1);
        testViewWorkList = testWorkList.viewWorkList();
        assertEquals(1, testViewWorkList.size());
        assertTrue(testViewWorkList.contains(course1));
    }

    // test viewWorkList method when there are multiple courses on workList
    @Test
    void testViewWorkListMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        testViewWorkList = testWorkList.viewWorkList();
        assertEquals(3, testViewWorkList.size());
        assertTrue(testViewWorkList.contains(course1));
        assertTrue(testViewWorkList.contains(course2));
        assertTrue(testViewWorkList.contains(course3));
    }

    // test getNumberOfWorkListCourses method when there is no course on workList
    @Test
    void testGetNumberOfWorkListCoursesZero() {
        assertFalse(testWorkList.containsInWorkList(course1));
        assertFalse(testWorkList.containsInWorkList(course2));
        assertFalse(testWorkList.containsInWorkList(course3));
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
    }

    // test getNumberOfWorkListCourses method when there is only one course on workList
    @Test
    void testGetNumberOfWorkListCoursesOne() {
        testWorkList.addCourseToWorkList(course1);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertFalse(testWorkList.containsInWorkList(course2));
        assertFalse(testWorkList.containsInWorkList(course3));
        assertEquals(1, testWorkList.getNumberOfWorkListCourses());


    }

    // test getNumberOfWorkListCourses method when there are multiple courses on workList
    @Test
    void testGetNumberOfWorkListCoursesMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course2));
        assertTrue(testWorkList.containsInWorkList(course3));
        assertEquals(3, testWorkList.getNumberOfWorkListCourses());
    }

    // test dropFromWorkList method when there is only one course on workList
    @Test
    void testDropFromWorkListOne() {
        testWorkList.addCourseToWorkList(course2);
        testWorkList.dropFromWorkList(course2);
        assertFalse(testWorkList.containsInWorkList(course2));
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
    }

    // test dropFromWorkList method when there are multiple courses on workList
    @Test
    void testDropFromWorkListMultiple() {
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course1);
        testWorkList.dropFromWorkList(course2);
        assertFalse(testWorkList.containsInWorkList(course2));
        assertTrue(testWorkList.containsInWorkList(course1));
        assertEquals(1, testWorkList.getNumberOfWorkListCourses());
    }

    // test register method when registering only one course
    @Test
    void testRegisterOne() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }


    }

    // test register method when registering multiple courses
    @Test
    void testRegisterMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }


    }

    // test register a course given empty workList
    @Test
    void testRegisterEmptyOption() {
        try {
            testWorkList.register(course1); // register the first course
            fail ("This course ids not founded in the workList.");

        } catch (NotInWorkListException e) {
            // expected
        }
        assertEquals(0, testWorkList.getNumberOfRegisteredCourses());

        assertEquals(0, testWorkList.getNumberOfWorkListCourses());

    }

    // test register a course given the course is not added to workList
    @Test
    void testRegisterNotContained() {

        //testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course1); // register the first course
            fail ("This course ids not founded in the workList.");

        } catch (NotInWorkListException e) {
            // expected
        }


    }

    // test dropRegisteredCourse method when dropping only one course
    @Test
    void testDropRegisteredCourseOne() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course2); // register the second course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
        testWorkList.dropRegisteredCourse(course2);
        assertFalse(testWorkList.containsInRegistered(course2));
        assertTrue(testWorkList.containsInRegistered(course3));
        assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
    }

    // test dropRegisteredCourse method when dropping multiple courses
    @Test
    void testDropRegisteredCourseMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
        testWorkList.dropRegisteredCourse(course2);
        testWorkList.dropRegisteredCourse(course3);
        assertFalse(testWorkList.containsInRegistered(course2));
        assertFalse(testWorkList.containsInRegistered(course3));
        assertTrue(testWorkList.containsInRegistered(course1));
        assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
    }

    // test viewRegistered method when there's no courses on registered course list
    @Test
    void testViewRegisteredZero() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        try {
            testWorkList.register(course2); // register the second course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        testWorkList.dropRegisteredCourse(course2);
        testViewRegistered = testWorkList.viewRegistered();
        assertEquals(0, testViewRegistered.size());
        assertFalse(testViewRegistered.contains(course1));
        assertFalse(testViewRegistered.contains(course2));

    }

    // test viewRegistered method when there is only one course on registered course list
    @Test
    void testViewRegisteredOne() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        testViewRegistered = testWorkList.viewRegistered();
        assertEquals(1, testViewRegistered.size());
        assertTrue(testViewRegistered.contains(course1));
        assertFalse(testViewRegistered.contains(course2));
        assertFalse(testViewRegistered.contains(course3));

    }

    // test viewRegistered method when there are multiple courses on registered course list
    @Test
    void testViewRegisteredMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        testViewRegistered = testWorkList.viewRegistered();
        assertEquals(3, testViewRegistered.size());
        assertTrue(testViewRegistered.contains(course1));
        assertTrue(testViewRegistered.contains(course2));
        assertTrue(testViewRegistered.contains(course3));
    }

    // test getNumberOfRegisteredCourses method when there is no course that is registered
    @Test
    void testGetNumberOfRegisteredCoursesZero() {
        assertFalse(testWorkList.containsInRegistered(course1));
        assertFalse(testWorkList.containsInRegistered(course2));
        assertFalse(testWorkList.containsInRegistered(course3));
        assertEquals(0, testWorkList.getNumberOfRegisteredCourses());
    }

    // test getNumberOfRegisteredCourses method when there is only one course that is registered
    @Test
    void testGetNumberOfRegisteredCoursesOne() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testWorkList.containsInRegistered(course1));
        assertFalse(testWorkList.containsInRegistered(course2));
        assertFalse(testWorkList.containsInRegistered(course3));
        assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
    }

    // test getNumberOfRegisteredCourses method when there are multiple courses taht are registered
    @Test
    void testGetNumberOfRegisteredCoursesMultiple() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testWorkList.containsInRegistered(course1));
        assertTrue(testWorkList.containsInRegistered(course2));
        assertTrue(testWorkList.containsInRegistered(course3));
        assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
    }

    // test getTotalRegisteredCredits method when there is zero credits
    @Test
    void testGetTotalRegisteredCreditsZero() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        testWorkList.dropRegisteredCourse(course1);
        assertEquals(0, testWorkList.getTotalRegisteredCredits());
    }

    // test getTotalRegisteredCredits method when there are nonzero credits
    @Test
    void testGetTotalRegisteredCreditsMany() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(2, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course2); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertEquals(12, testWorkList.getTotalRegisteredCredits());
        testWorkList.dropRegisteredCourse(course2);
        testWorkList.getTotalRegisteredCredits();
        testWorkList.getTotalRegisteredCredits();
        assertEquals(9, testWorkList.getTotalRegisteredCredits());
    }

    // test containsInWorkList method
    @Test
    void testContainsInWorkList() {
        assertFalse(testWorkList.containsInWorkList(course1));
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
        testWorkList.addCourseToWorkList(course1);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertEquals(1, testWorkList.getNumberOfWorkListCourses());
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course2));
        assertTrue(testWorkList.containsInWorkList(course3));
        assertEquals(3, testWorkList.getNumberOfWorkListCourses());
    }

    // test containsInWorkList method when two course names are same but they are different object
    @Test
    void testContainsInWorkListSameNameDifferentObject() {
        assertFalse(testWorkList.containsInWorkList(course1));
        assertEquals(0, testWorkList.getNumberOfWorkListCourses());
        testWorkList.addCourseToWorkList(course1);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertEquals(1, testWorkList.getNumberOfWorkListCourses());

        testWorkList.addCourseToWorkList(course3);
        assertTrue(testWorkList.containsInWorkList(course1));
        assertTrue(testWorkList.containsInWorkList(course3));
        assertEquals(2, testWorkList.getNumberOfWorkListCourses());

        assertTrue(testWorkList.containsInWorkList(course4)); // Even though we haven't add course4, it is still
                                                              // contained in workList since it has the same name with
                                                              // course3. (Check duplicate names)
        assertTrue(testWorkList.containsInWorkList(course5)); // same as above
        assertEquals(2, testWorkList.getNumberOfWorkListCourses());
    }

    // test containsInRegistered method
    @Test
    void testContainsInRegistered() {
        assertFalse(testWorkList.containsInRegistered(course1));
        assertEquals(0, testWorkList.getNumberOfRegisteredCourses());
        testWorkList.addCourseToWorkList(course1);
        try {
            testWorkList.register(course1); // register the first course
            assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course1));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course1)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testWorkList.containsInRegistered(course1));
        assertEquals(1, testWorkList.getNumberOfRegisteredCourses());
        testWorkList.addCourseToWorkList(course2);
        testWorkList.addCourseToWorkList(course3);
        try {
            testWorkList.register(course2); // register the second course
            assertEquals(2, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course2));
            assertEquals(1, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course2)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        try {
            testWorkList.register(course3); // register the second course
            assertEquals(3, testWorkList.getNumberOfRegisteredCourses());
            assertTrue(testWorkList.containsInRegistered(course3));
            assertEquals(0, testWorkList.getNumberOfWorkListCourses());
            assertFalse(testWorkList.containsInWorkList(course3)); // remove the registered from workList
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testWorkList.containsInRegistered(course1));
        assertTrue(testWorkList.containsInRegistered(course2));
        assertTrue(testWorkList.containsInRegistered(course3));
        assertEquals(3, testWorkList.getNumberOfRegisteredCourses());

    }

    // test convertToJson method
    @Test
    void testConvertToJson() {
        testWorkList.addCourseToWorkList(course1);
        testWorkList.addCourseToWorkList(course2);
        JSONObject myJ = testWorkList.convertToJson();
        JSONObject jCourse1 = course1.convertToJson();
        JSONObject jCourse2 = course2.convertToJson();
        String name1 = jCourse1.getString("Course name");
        String name2 = jCourse2.getString("Course name");
        assertEquals(name1, myJ.getJSONArray("courses").getJSONObject(0).getString("Course name"));
        assertEquals(name2, myJ.getJSONArray("courses").getJSONObject(1).getString("Course name"));

    }
}
