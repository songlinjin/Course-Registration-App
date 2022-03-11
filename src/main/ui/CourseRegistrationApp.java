package ui;

import exception.NotInWorkListException;
import model.Course;
import model.CourseWorkList;
import model.Terms;
import persistence.LoaderReader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.util.List;


// Representing the course registration application and its usage
public class CourseRegistrationApp {
    public Scanner userInput;
    private CourseWorkList term;
    private Terms termsList;

    private static final String ADDRESS = "./data/myCourseWorkList.json";
    private Writer jsonWriter;
    private LoaderReader jsonReader;

    // EFFECTS: run the course registration application
    public CourseRegistrationApp() {
        beginRegistration();
    }

    // MODIFIES: this
    // EFFECTS: process the user input
    public void beginRegistration() {
        boolean valid = true;

        setup();
        menuForAddToWorkList();

        while (valid) {

            String button = userInput.next();
            button = formatInput(button);
            if (button.equals("Q")) {
                System.out.println("\nWould you like to save your worklist before logging out?");
                System.out.println("\nY -> Yes, I forgot to save that!");
                System.out.println("\nN -> No, I have already saved that and thank you for reminder!");
                String button2 = userInput.next();
                button2 = formatInput(button2);
                if (button2.equals("Y")) {
                    saveCourseWorkList();
                    //System.out.println("\nSaved!");

                } else {
                    valid = false;

                }
            } else {
                commandProcessor(button);
            }
        }
        System.out.println("\nYou are logged out. Have a nice day!");



    }

    // EFFECTS: print the menu for selecting courses for a term
    private void menuForAddToWorkList() {
        System.out.println("\nWelcome! This is menu for establish course schedule for ONE term");
        System.out.println("\nPlease select from:");
        System.out.println("\tA -> add a course to workList (this is different from register)");
        System.out.println("\tR -> register a course to the registered list");
        System.out.println("\tV -> view record of total register credits and registered courses of the current term");
        System.out.println("\tDR -> drop a registered course");
        System.out.println("\tS -> add the current workList you work on to list of terms and view the degree progress");
        System.out.println("\t ------------------------------------NOTE: Below are new updated functions for our App!"
                + "Wow Let's try them!"
                + "--------------------------------------------");
        System.out.println("\tSA -> SAVE the current workList you work on");
        System.out.println("\tP -> PRINT course worklist");
        System.out.println("\tL -> LOAD the previous workList you work on");
        System.out.println("\tQ -> quit");

    }

    private void commandProcessor(String command) {
        if (command.equals("A")) {
            doAddToWorkList();

        } else if (command.equals("R")) {
            doRegister();

        } else if (command.equals("V")) {
            doViewCredits();

        } else if (command.equals("DR")) {
            doDropRegistered();

        } else if (command.equals("S")) {
            doAddTerm();

        } else if (command.equals("SA")) {
            saveCourseWorkList();
        } else if (command.equals("L")) {
            loadCourseWorkList();

        } else if (command.equals("P")) {
            printWorkList();
        } else {
            processInvalid();
        }

    }

    // MODIFIES: this
    // EFFECTS: initialize the workList for a term and terms which consists of list of workLists
    private void setup() {

        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n"); // keep the string that is after the space

        term = new CourseWorkList();
        termsList = new Terms();

        jsonWriter = new Writer(ADDRESS);
        jsonReader = new LoaderReader(ADDRESS);




    }

    // EFFECTS: saves the CourseWorkList to file
    private void saveCourseWorkList() {
        try {
            jsonWriter.open();
            jsonWriter.write(term);
            jsonWriter.close();
            System.out.println("Successfully saved your current worklist to " + ADDRESS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + ADDRESS + "since your file is not found!");
        }
        printBack();
    }

    // MODIFIES: this
    // EFFECTS: loads CourseWorkList from file
    private void loadCourseWorkList() {
        try {
            term = jsonReader.readCourseWorkListFromFile();
            System.out.println("Loaded your previous worklist from " + ADDRESS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + ADDRESS + "since your file is not found!");
        }
        printBack();
    }

    private void printWorkList() {
        System.out.println("\nThese are the courses in your workList:");
        for (Course c : term.viewWorkList()) {
            String optionName = c.getCourseName();
            System.out.println(optionName);
        }
        printBack();
    }

    // MODIFIES: this
    // EFFECTS: process adding a course to workList
    private void doAddToWorkList() {
        System.out.println("\nPlease enter the name of course you would like to add to workList:");
        String courseName1 = userInput.next();
        courseName1 = courseName1.toUpperCase();
        courseName1 = courseName1.replaceAll("\\s", ""); //remove all space in user input to make sure
                                                                         // for example
                                                                         //"econ 210" & "econ210" represent same course

        System.out.println("\nPlease enter the credits of course you would like to add to workList:");
        int credit1 = userInput.nextInt();

        Course course1 = new Course(courseName1, credit1);
        System.out.println(courseName1);

        if (! term.containsInWorkList(course1)) {
            term.addCourseToWorkList(course1);
            System.out.printf("%s Successfully added!%n", courseName1);
        } else {
            System.out.println("\nThis course has been already added to workList. Please add a different course.");
        }
        printBack();

    }

    // MODIFIES: this
    // EFFECTS: do the registering a course from workList process
    private void doRegister() {
        System.out.println("\nThese are the courses in your workList and please select one to register:");
        for (Course c : term.viewWorkList()) {
            String optionName = c.getCourseName();
            System.out.println(optionName);
        }
        System.out.println("\nPlease enter the name of course you would like to register:");
        String registerName1input = userInput.next();
        registerName1input = formatInput(registerName1input);
//        registerName1input = registerName1input.toUpperCase();
//        registerName1input = registerName1input.replaceAll("\\s", "");
        //boolean found = false;

        for (int i = 0; i < term.viewWorkList().size(); i++) {
            String registeredName = term.viewWorkList().get(i).getCourseName();

            if (registerName1input.equals(registeredName)) {
                try {
                    term.register(term.viewWorkList().get(i));
                    printRegisteredList();
                    //found = true;
                } catch (NotInWorkListException e) {
                    System.out.println("\nThe course is not added to workList, please add it first");
                }

            }
        }

        //if (!found) {
            //System.out.println("\nThe course is not added to workList, please add it first");
        //}
        printBack();
    }





    // EFFECTS: view record of total register credits and registered courses for a term
    private void doViewCredits() {
        printRegisteredList();
        term.getTotalRegisteredCredits();
        printTotalCredits();
        printBack();
    }

    // MODIFIES: this
    // EFFECTS: do the dropping a registered course process
    private void doDropRegistered() {
        printRegisteredList();
        List<Course> l = term.viewRegistered();
        if (l.size() == 0) {
            printBack();

        } else {
            System.out.println("\nPlease enter the name of course you would like to drop:");
            String dropName1input = userInput.next();
            dropName1input = formatInput(dropName1input);
            boolean found = false;

            for (int i = 0; i < term.viewRegistered().size(); i++) {
                String registeredName = term.viewRegistered().get(i).getCourseName();

                if (dropName1input.equals(registeredName)) {
                    term.dropRegisteredCourse(term.viewRegistered().get(i));
                    System.out.printf("%s successfully dropped!%n", registeredName);//////////////////
                    printRegisteredList();
                    found = true;
                }
            }
            if (!found) {
                System.out.println("\nThe course is not registered, please register it first");
            }
            printBack();
        }
        //System.out.println("\nPlease enter the name of course you would like to drop:");
        //String dropName1input = userInput.next();
        //dropName1input = dropName1input.toUpperCase();
        //boolean found = false;

        //for (int i = 0; i < term.viewRegistered().size(); i++) {
            //String registeredName = term.viewRegistered().get(i).getCourseName();

            //if (dropName1input.equals(registeredName)) {
                //term.dropRegisteredCourse(term.viewRegistered().get(i));
                //System.out.printf("%s successfully dropped!%n", registeredName);//////////////////
                //printRegisteredList();
                //found = true;
            //}
        //}
        //if (!found) {
            //System.out.println("\nThe course is not registered, please register it first");
        //}
        //printBack();
        //menuForAddToWorkList();
    }

    // MODIFIES: this
    // EFFECTS: add the worklist(term) to terms list and print the degree progress
    private void doAddTerm() {
        termsList.addTerm(term);
        System.out.println("\nTotal number of terms added: " + termsList.getNumberOfTerms());
        System.out.println("\nTotal credits for ALL terms: " + termsList.sumCredits());
        System.out.println("\nRemaining credits to graduate: " + termsList.remainingCreditsToGraduate());
        System.out.println("\nBonus credits(more than graduation requirements): " + termsList.getBonus());


        term = new CourseWorkList(); // construct a new workList for next term you would like to add
        printBack();
    }

    // MODIFIES: this
    // EFFECTS: formatting the user input to make sure that lower cases to upper cases and spaces are omitted
    private String formatInput(String s) {
        s = s.toUpperCase();
        s = s.replaceAll("\\s", "");
        return s;
    }


    // EFFECTS: print the registered courses and there associated credits
    private void printRegisteredList() {

        List<Course> l = term.viewRegistered();
        if (l.size() == 0) {
            System.out.println("\nThese are the courses already registered in this term: None course registered");
            //printBack();
            //menuForAddToWorkList();
        } else {
            System.out.println("\nThese are the courses already registered in this term and there associated credits:");
            for (Course c: term.viewRegistered()) {
                String registeredName = c.getCourseName();
                int registeredCredits = c.getCourseCredits();

                System.out.println("Course name: " + registeredName + " " + "Credits: " + registeredCredits);
            }
        }
    }


    // EFFECTS: print back to menu and direct back to menu
    private void printBack() {
        System.out.println("\nBack to main menu...What would you like to do next?");
        menuForAddToWorkList();
    }


    // EFFECTS: print the total registered credits for a term
    private void printTotalCredits() {
        System.out.printf("%s Total registered credits!%n", term.getTotalRegisteredCredits());
    }

    // EFFECTS: process invalid user inputs
    public void processInvalid() {
        System.out.println("\nYour selection is invalid. Please select again.");
        menuForAddToWorkList();

    }



}
