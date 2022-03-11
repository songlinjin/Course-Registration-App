package ui;

import exception.NotInWorkListException;
import model.Course;
import model.CourseWorkList;
import persistence.LoaderReader;
import persistence.Writer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.*;

// GUI for course registration app
// Citation: List Demo provided by EDX Project Phase3 Instruction page
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Class represents the GUI demo of course registration application
public class CourseRegistrationAppDemo extends JPanel implements ListSelectionListener {
    private JList list;
    public DefaultListModel listModel;
    public CourseWorkList myworklist;
    private Writer jsonWriter = new Writer(ADDRESS);
    public LoaderReader jsonReader = new LoaderReader(ADDRESS);
    public static final String ADDRESS = "./data/myCourseWorkList.json";


    private static final String addToWorkListString = "Add to Course WorkList";
    private static final String saveWorkListString = "Save Course WorkList";
    private static final String displayRegisteredListString = "View/Display List of Registered Courses";
    private static final String registerString = "Register";
    private static final String loadString = "Load";
    private static final JLabel tipEnterCourseName = new JLabel("Course Name:");
    private static final JLabel tipEnterCourseCredit = new JLabel("Course Credit:");
    private JButton registerButton;
    private JButton saveWorkListButton;

    private JButton addButton;
    public JButton loadWorkListButton;
    private JButton displayRegisteredListButton;
    private JTextField courseName;
    private JTextField courseCredit;
    private AddToWorkListListener addListener;
    //private DisplayRegisteredCourses display;



    // EFFECT: construct the course registration app demo by displaying course worklist panel, process all the buttons
    //         and their associated button panels
    public CourseRegistrationAppDemo() {
        super(new BorderLayout());

        // a dialog showing info on how to start with this app
        //startWithDialog();
        displayCourseInWorkList();
        processAddToWorkListButton();
        processSaveButton();
        processLoadButton();
        //processDisplayButton();
        processRegisterButton();
        displayWorkListAndAddAndRegisterButton();
    }




    // MODIFIES: this
    // EFFECT: create the worklist and put it in a scroll panel.
    public JScrollPane displayCourseInWorkList() {
        listModel = new DefaultListModel();
        myworklist = new CourseWorkList();
        //Course example = new Course("Example", 0);
        //example.setCourseCredits();
        myworklist.addCourseToWorkList(new Course("Example",1));

        for (Course c: myworklist.viewWorkList()) {
            listModel.addElement("Course: " + c.getCourseName() + "  " + "credits: "
                    + c.getCourseCredits() + "(NOTE: the example is set to cannot be registered, if click register,"
                    + " error sound expected)");
        }




        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(16);
        JScrollPane displayListOfCoursesInWorkListPanel = new JScrollPane(list);
        return displayListOfCoursesInWorkListPanel;
    }


    // MODIFIES: this
    // EFFECT: generate and process the Add to Course WorkList button, and generate Add to Course WorkList button
    //         action listener
    public void processAddToWorkListButton() {
        addButton = new JButton(addToWorkListString);
        addListener = new AddToWorkListListener(addButton);
        addButton.setActionCommand(addToWorkListString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECT: generate and process the Save button, and generate Save button action listener
    public void processSaveButton() {


        saveWorkListButton = new JButton(saveWorkListString);
        SaveListener saveListener = new SaveListener();
        saveWorkListButton.setActionCommand(saveWorkListString);
        saveWorkListButton.addActionListener(saveListener);
        //saveWorkListButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECT: generate and process the Load button, and generate Load action listener
    public void processLoadButton() {

        ////////////////////////////////////////////////////////////////////
        loadWorkListButton = new JButton(loadString);
        LoadListener loadListener = new LoadListener();
        loadWorkListButton.setActionCommand(loadString);
        loadWorkListButton.addActionListener(loadListener);

    }




    // MODIFIES: this
    // EFFECT: generate and process the Register button, and generate Register button action listener
    public void processRegisterButton() {

        registerButton = new JButton(registerString);
        registerButton.setActionCommand(registerString);
        registerButton.addActionListener(new RegisterListener());


        courseName = new JTextField(50);


        courseName.addActionListener(addListener);
        courseName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        courseCredit = new JTextField(50);

        courseCredit.addActionListener(addListener);
        courseCredit.getDocument().addDocumentListener(addListener);
        String credit = listModel.getElementAt(
                list.getSelectedIndex()).toString();
    }


    // EFFECT: Create a panel that uses BoxLayout displaying the courses added to WorkList and register
    //         and add to WorkList button.
    public void displayWorkListAndAddAndRegisterButton() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                    BoxLayout.LINE_AXIS));

        buttonPane.add(tipEnterCourseName);
        buttonPane.add(Box.createHorizontalStrut(6));

        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(courseName);
            //////////////////////////////////////////////////
        buttonPane.add(tipEnterCourseCredit);
        buttonPane.add(courseCredit);
            /////////////////////////////////////////////////
        buttonPane.add(addButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(registerButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        add(displayCourseInWorkList(), BorderLayout.PAGE_START);
        //add(display.displayRegisteredCourses(),BorderLayout.CENTER);
        add(buttonPane, BorderLayout.CENTER);
        add(createPanelForSaveButton(), BorderLayout.PAGE_END);
    }

    // EFFECT: Create a panel for Save and Load button
    public JPanel createPanelForSaveButton() {
        //create another panel for save button
        JPanel viewButtonPanel = new JPanel();
        viewButtonPanel.add(saveWorkListButton);
        //viewButtonPanel.add(displayRegisteredListButton);
        viewButtonPanel.add(loadWorkListButton);
        return viewButtonPanel;

    }



    // Private Class that represents the action listener associated with Load button
    private class LoadListener implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECT: action to be performed when clicking Load button, including load the previously saved worklist
        //         panel; throw IOException, and catch
        //         the exception and recover the app with a message window indicating unable to load the worklist
        public void actionPerformed(ActionEvent e) {
            try {
                myworklist.viewWorkList().clear();
                myworklist = jsonReader.readCourseWorkListFromFile();

                for (int i = 1; i < myworklist.viewWorkList().size(); i++) {
                    listModel.addElement("Course: " + myworklist.viewWorkList().get(i).getCourseName() + " "
                            + "credits: "
                            + myworklist.viewWorkList().get(i).getCourseCredits());
                }
                loadWorkListButton.setEnabled(false);
                JFrame frame1 = new JFrame("Welcome to Course Registration APP");
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(frame1, "Successfully loaded your previous worklist from "
                        + ADDRESS);
            } catch (IOException e2) {

                JFrame frame2 = new JFrame("Welcome to Course Registration APP");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(frame2, "Unable to load file: " + ADDRESS
                        + "since your file is not found!");
            }


        }
    }

    // Private Class that represents the action listener associated with Save Course WorkList button
    private class SaveListener implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECT: action to be performed when clicking Save Course WorkList button, including save the worklist
        //         panel; throw FileNotFoundException if the file is not found in the provided address, and catch
        //         the exception and recover the app with a message window indicating unable to save the worklist
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(myworklist);
                jsonWriter.close();
                JFrame frame1 = new JFrame("Welcome to Course Registration APP");
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(frame1, "Successfully saved your current worklist to " + ADDRESS);
            } catch (FileNotFoundException e2) {
                JFrame frame2 = new JFrame("Welcome to Course Registration APP");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(frame2, "Unable to save file: " + ADDRESS
                        + "since your file is not found");
            }


        }
    }

    // Private Class that represents the action listener associated with Register button
    private class RegisterListener implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECT: action to be performed when clicking Register button, including remove the course from the worklist
        //         panel when clicking Register button
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index == 0) {
                Toolkit.getDefaultToolkit().beep();

                return;


            } else {

                registerButton.setEnabled(true);
                listModel.remove(index);
                try {
                    myworklist.register(myworklist.viewWorkList().get(index));
                } catch (NotInWorkListException e2) {
                    displayWindowMessage();
                }



                int size = listModel.getSize();

                if (size == 0) { //No Course is left (ie. empty panel), disable "register" function.
                    registerButton.setEnabled(false);

                } else { //Select an index.
                    if (index == listModel.getSize()) {
                        //removed item in last position
                        index--;
                    }

                    list.setSelectedIndex(index);
                    list.ensureIndexIsVisible(index);
                }
            }
        }

        private void displayWindowMessage() {
            JFrame frame2 = new JFrame("Welcome to Course Registration APP");
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JOptionPane.showMessageDialog(frame2, "The course you would like to add is not in the"
                    + " workList. Please add this course to the workList first.");

        }
    }

    // Private Class that represents the action listener associated with Add to Course WorkList button
    private class AddToWorkListListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        public Course newCourse;

        // MODIFIES: this
        // EFFECT: construct a AddToWorkListListener with button
        public AddToWorkListListener(JButton button) {
            this.button = button;
        }


        @Override
        // MODIFIES: this
        // EFFECT: action to be performed when clicking Add to Course WorkList button including add the course to
        //         worklist, playing sound when clicking button, highlight the selected course in the worklist panel,
        //         set the text fields to be empty after click the add button
        public void actionPerformed(ActionEvent e) {
            String name = courseName.getText();
            String creditNum = courseCredit.getText();


            name = formatInput(name);
            checkUserInputOfCredit(creditNum, name);

//            if (canGenerateCourse(creditNum) && !creditNum.contains("-")) {
//                int creditValue = Integer.parseInt(creditNum);
//                newCourse = new Course(name,creditValue);
//            } else {
//                doNotAddToWorkListProcedure();
//            }


            // The App does NOT proceed if:
            // case1: user type in a empty course name; or
            // case2: user didn't type in a unique course name; or
            // case3: user type in an empty associated course credit; or

            if (name.equals("") || myworklist.containsInWorkList(newCourse) || creditNum.equals("")) {
                doNotAddToWorkListProcedure();
            } else {
                int index = list.getSelectedIndex(); //get selected index
                if (index == -1) { //no selection, so insert at beginning
                    index = 0;
                } else {           //add after the selected item
                    index++;
                }

                myworklist.addCourseToWorkList(newCourse);
                listModel.addElement("Course: " + newCourse.getCourseName() + " " + "credits: "
                        + newCourse.getCourseCredits());

                resetTextFieldToEmpty();

                selectNewItemAndMakeItVisible(index);






            }

            playSoundWhenClickingAddButton("./data/buttonSound.wav");
        }



        // Citation: this method use http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
        //           as a reference and learn how to add sound to button; the author is not found in the link
        // EFFECT: play sound when clicking add to course worklist button; throw an Exception if there's error
        public void playSoundWhenClickingAddButton(String soundAddress) {
            try {
                AudioInputStream myAudio = AudioSystem.getAudioInputStream(new File(soundAddress).getAbsoluteFile());
                Clip buttonClip = AudioSystem.getClip();
                buttonClip.open(myAudio);
                buttonClip.start();
            } catch (Exception exception1) {
                JFrame frame = new JFrame("Error in playing sound");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(frame, "Error in playing sound");
                exception1.printStackTrace();
            }
        }

        // MODIFIES: this
        // EFFECT: if the user input for course credits is invalid and positive, use user input information to generate
        //         course object; otherwise do not proceed
        private void checkUserInputOfCredit(String creditNum, String name) {
            if (canGenerateCourse(creditNum) && !creditNum.contains("-")) {
                int creditValue = Integer.parseInt(creditNum);
                newCourse = new Course(name,creditValue);
            } else {
                doNotAddToWorkListProcedure();
            }
        }

        //EFFECT: Select the new item and make it visible(highlighted)
        private void selectNewItemAndMakeItVisible(int index) {

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

        }



        //EFFECTS: Reset the two text fields for user to input course name and course credits empty
        private void resetTextFieldToEmpty() {
            courseName.requestFocusInWindow();
            courseName.setText("");
            courseCredit.setText("");
        }

        // EFFECTS: format the user input of course name to upper case and remove all spaces
        private String formatInput(String s) {
            s = s.toUpperCase();
            s = s.replaceAll("\\s", "");
            return s;
        }

        // EFFECTS: return true if the user input for course credit is valid and therefore the course object can be
        //          generated, otherwise return false
        public boolean canGenerateCourse(String credit) {
            //int creditValue;
            try {
                Integer.parseInt(credit);
                //Course newCourse = new Course(nameOfCourse,creditValue);
                return true;
            } catch (NumberFormatException exception1) {
                return false;
            }
        }

        // EFFECTS: this is the procedure if user input is invalid for adding a course to workList
        public void doNotAddToWorkListProcedure() {
            Toolkit.getDefaultToolkit().beep();
            courseName.requestFocusInWindow();
            courseName.selectAll();
            return;

        }

        //EFFECT: enable the button. Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //EFFECT: handle the empty text field
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //EFFECT: if the text field is not empty, enable the button
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECT: enable the button if the button is not already enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECT: if the text field is empty, disable the button and return true; otherwise return false
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECT: If there's no selection, diabale the button; else enable the button
    // This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable register button
                registerButton.setEnabled(false);

            } else {

                registerButton.setEnabled(true);
            }
        }
    }


    // EFFECT: create and show GUI
    public static void setUpAndPresentGui() {

        JFrame frame = new JFrame("CourseRegistration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel tipEnterCourseName = new JLabel("Course Name");
        JLabel tipEnterCourseCredit = new JLabel("Course Credit");
        frame.add(tipEnterCourseName);
        frame.add(tipEnterCourseCredit);

        //Create and set up the content pane.
        JComponent newContentPane = new CourseRegistrationAppDemo();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //Display window.
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                setUpAndPresentGui();
//            }
//        });
//    }


}
