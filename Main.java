/**
 * Entry point of the Masroofy application.
 *
 * <p>This class is responsible for launching the graphical user interface (GUI)
 * of the system. It delegates execution to the {@code MainGUI} class located
 * in the {@code view} package.</p>
 *
 * <p>The purpose of separating this class is to keep a clear starting point
 * for the application while maintaining the MVC structure, where the view
 * handles all UI-related logic.</p>
 */
public class Main {

    /**
     * Main method that starts the application.
     *
     * @param args command-line arguments passed during program execution
     *
     * <p>This method calls the main method of {@code MainGUI} to initialize
     * and display the application's user interface.</p>
     */
    public static void main(String[] args) {
        view.MainGUI.main(args);
    }
}