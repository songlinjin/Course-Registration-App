package model;

import exception.NotInWorkListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Representing test for Terms
public class TermsTest {
    private Terms testTermsList;
    private CourseWorkList term1;
    private CourseWorkList term2;
    private CourseWorkList term3;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;

    @BeforeEach
    void runBefore() {
        testTermsList = new Terms();
        term1 = new CourseWorkList();
        term2 = new CourseWorkList();
        term3 = new CourseWorkList();
        course1 = new Course("CPSC 110", 4);
        course2 = new Course("CONS 127", 3);
        course3 = new Course("CHEM 111", 5);
        course4 = new Course("ECON 000", 108);
        course5 = new Course("ECON 001", 30);


    }

    // test constructor
    @Test
    void testConstructor() {
        assertEquals(0, testTermsList.getNumberOfTerms());
        assertFalse(testTermsList.containInTermsList(term1));
        assertFalse(testTermsList.containInTermsList(term2));
        assertFalse(testTermsList.containInTermsList(term3));
    }

    // test containInTermsList method when there is no term in term list
    @Test
    void testContainInTermsListZero() {
        assertFalse(testTermsList.containInTermsList(term1));
        assertFalse(testTermsList.containInTermsList(term2));
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        term1.addCourseToWorkList(course3);
        //term1.register(course1);
        //term1.register(course2);
        assertFalse(testTermsList.containInTermsList(term1)); // even though we add and register courses to term1,
                                                              // term1 is still not in the termsList

    }

    // test containInTermsList method when there is only one term in term list
    @Test
    void testContainInTermsListOne() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        term1.addCourseToWorkList(course3);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }



        try {
            term1.register(course2);
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }


        testTermsList.addTerm(term1);
        assertEquals(1, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
    }

    // test containInTermsList method when there are multiple terms in term list
    @Test
    void testContainInTermsListMultiple() {
        term1.addCourseToWorkList(course1);
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }



        try {
            term2.register(course2);
        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }

        testTermsList.addTerm(term1);
        testTermsList.addTerm(term2);
        testTermsList.addTerm(term3);
        assertEquals(3, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertTrue(testTermsList.containInTermsList(term3));

    }

    // test addTerm method when add term to an empty list
    @Test
    void testAddTermToEmpty() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertEquals(1, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
    }

    // test addTerm method when add a term that is already there
    @Test
    void testAddTermAlreadyThere() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        try {
            term1.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(2, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertFalse(testTermsList.addTerm(term1));
        assertEquals(2, testTermsList.getNumberOfTerms()); // size doesn't change since term1 is already added
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
    }

    // test addTerm method when add multiple terms
    @Test
    void testAddTermMultiple() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        try {
            term1.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term3.addCourseToWorkList(course1);
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertTrue(testTermsList.addTerm(term3));
        assertEquals(3, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertTrue(testTermsList.containInTermsList(term3));

    }

    // test getNumberOfTerms method when there's no term
    @Test
    void testGetNumberOfTermsZero() {
        assertEquals(0, testTermsList.getNumberOfTerms());

    }

    // test getNumberOfTerms method then there's only one term
    @Test
    void testGetNumberOfTermsOne() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertFalse(testTermsList.addTerm(term1));
        assertEquals(1, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
    }

    // test getNumberOfTerms method then there are multiple terms
    @Test
    void testGetNumberOfTermsMultiple() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(2, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));

    }

    // test sumCredits method when there's sum of zero registered credits
    @Test
    void testSumCreditsZero() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term1.dropRegisteredCourse(course1); // then drop
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3); // no registered credits for term 2
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(2, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertEquals(0, testTermsList.sumCredits());

    }

    // test sumCredits method when there's sum of nonzero registered credits
    @Test
    void testSumCreditsMany() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(2, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertEquals(12, testTermsList.sumCredits());
        term2.dropRegisteredCourse(course2); // then we drop course 2 from registered courses
        assertEquals(9, testTermsList.sumCredits()); // result should be 12-3

    }

    // test sumCredits method to make sure that no negative value would be presented
    @Test
    void testSumCreditsNonNegative() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(2, testTermsList.getNumberOfTerms());
        assertTrue(testTermsList.containInTermsList(term1));
        assertTrue(testTermsList.containInTermsList(term2));
        assertEquals(12, testTermsList.sumCredits());
        term2.dropRegisteredCourse(course2); // then we drop course 2 from registered courses
        term2.dropRegisteredCourse(course3);
        term1.dropRegisteredCourse(course1);
        assertEquals(0, testTermsList.sumCredits()); // result should be 12-3-5-4 = 0
        term1.dropRegisteredCourse(course1);
        assertEquals(0, testTermsList.sumCredits());

    }

    // test remainingCreditsToGraduate method when remaining credits = GRADUATION_REQUIREMENT
    @Test
    void testRemainingCreditsToGraduateGraduationRequirement() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term1.dropRegisteredCourse(course1);
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3); // no registered
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(0, testTermsList.sumCredits());
        assertEquals(120, testTermsList.remainingCreditsToGraduate());


    }

    // test remainingCreditsToGraduate method when  0 < remaining credits < GRADUATION_REQUIREMENT
    @Test
    void testRemainingCreditsToGraduateSome() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(12, testTermsList.sumCredits());
        assertEquals(120-12, testTermsList.remainingCreditsToGraduate());


    }

    // test remainingCreditsToGraduate method when remaining credits = 0
    @Test
    void testRemainingCreditsToGraduateNone() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        term2.addCourseToWorkList(course4);


        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course4);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(120, testTermsList.sumCredits());
        assertEquals(0, testTermsList.remainingCreditsToGraduate());
    }

    // test remainingCreditsToGraduate method when total earned credits exceed graduation requirement
    @Test
    void testRemainingCreditsToGraduateExceed() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        term1.addCourseToWorkList(course5);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term1.register(course5);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        term2.addCourseToWorkList(course4);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course4);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(150, testTermsList.sumCredits());
        assertEquals(0, testTermsList.remainingCreditsToGraduate());
    }

    // test getBonus method
    @Test
    void testGetBonus() {
        term1.addCourseToWorkList(course1);
        term1.addCourseToWorkList(course2);
        term1.addCourseToWorkList(course5);
        try {
            term1.register(course1);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term1.register(course5);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        term2.addCourseToWorkList(course2);
        term2.addCourseToWorkList(course3);
        term2.addCourseToWorkList(course4);
        try {
            term2.register(course2);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course3);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        try {
            term2.register(course4);

        } catch (NotInWorkListException e) {
            fail ("No exception expected.");
        }
        assertTrue(testTermsList.addTerm(term1));
        assertTrue(testTermsList.addTerm(term2));
        assertEquals(150, testTermsList.sumCredits());
        assertEquals(0, testTermsList.remainingCreditsToGraduate());
        assertEquals(30, testTermsList.getBonus());
    }




}
