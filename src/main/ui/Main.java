package ui;

// main class that generates an object of course registration app demo(GUI)
// and course registration application(console-based UI)
public class Main {
    public static void main(String[] args) {
        //new CourseRegistrationApp();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CourseRegistrationAppDemo demo1 = new CourseRegistrationAppDemo();
                demo1.setUpAndPresentGui();
            }
        });

        new CourseRegistrationApp();

    }
}
