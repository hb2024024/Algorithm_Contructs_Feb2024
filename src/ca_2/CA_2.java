package CA_2;

import java.util.List;
import java.util.Scanner;

/**
 * The main class for the Hospital Pantheon application.
 * This class represents the entry point of the program, responsible for initializing resources, displaying a user interface, 
 * and executing various functionalities related to managing patients, employees, and departments within the hospital system.
 * 
 * The main method contains the core logic to handle user interactions and call utility methods in different classes.
 * It uses a menu-driven approach to navigate through various options available in the application.
 */
public class CA_2 {

    public static void main(String[] args) {
        // Initializes a Scanner object for reading user input from the console.
        // Scanner is used to capture and process user responses in the menu-driven interface, allowing the user to interact with the system.
        // This scanner is also send as arguments to other classes, to capture user interaction on them.
        Scanner scanner = new Scanner(System.in);

        // Initializes three Department objects to represent different departments within the hospital.
        // These objects are passed as arguments to other methods to assign patients and employees to specific departments.
        Department surgery = new Department("Surgery");
        Department psychiatry = new Department("Psychiatry");
        Department emergencyRoom = new Department("Emergency Room");

        // Reads a list of names from an external file using the ReadFile utility class.
        // The 'names' list provides a base of names that are randomly assigned to generated patient and employee instances.
        List<String> names = ReadFile.readNamesFile();

        // Generates a list of Patient objects based on the names and the specified departments.
        // The patients list represents all patients currently registered in the hospital and is later accessed by other methods.
        List<Patient> patients = ReadFile.generatePatients(names, surgery, psychiatry, emergencyRoom);

        // Generates a list of Employee objects based on the names and a department (surgery in this case).
        // The employees list represents all staff members working in the hospital, enabling the system to manage their details.
        List<Employee> employees = ReadFile.generateEmployees(names, surgery);

        // Generates a list of Manager objects, distinct from regular employees, to represent managerial roles in the hospital.
        // The managers list is specifically used in options where higher-level administrative actions are required.
        List<Manager> managers = ReadFile.generateManagers(names);

        // Boolean flag that controls the main program loop.
        // 'running' remains true until the user selects the option to exit, allowing the program to continually display the menu and process choices.
        boolean running = true;

        // Initial welcome message to greet the user upon starting the application.
        System.out.println("Hi! Welcome to Hospital Pantheon.");

        // Main loop of the program that presents a menu to the user and processes their selections.
        // This loop will continue until the user selects the 'Exit' option.
        while (running) {
            // Displays the main menu options to the user by iterating over the MenuOption enum values.
            // Each option is displayed with its corresponding number and description, offering a clear list of possible actions.
            System.out.println("\nPlease select an option:");
            for (MenuOption option : MenuOption.values()) {
                System.out.println(option.getChoice() + ". " + option.getDescription());
            }

            // Error handling for non-integer input to ensure the user provides a valid choice.
            // If a non-integer input is detected, the user is prompted again to enter a number, until he types in a right one.
            // Avoiding the system to break or to move on withou a proper input.
            // I got this three lines of code from the Zoo project seen in class.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            // Reads the user's menu selection and advances the scanner past the newline.
            // This choice is used to identify the corresponding action in the MenuOption enum.
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            // Maps the user's numeric input to a corresponding MenuOption.
            // If the input does not match any defined MenuOption, the selectedOption is null, indicating an invalid selection.
            // Which get's tested on the if following this line.
            MenuOption selectedOption = MenuOption.fromChoice(choice);

            // Checks if the user's selection is invalid and prompts them to try again if so.
            // This guards against invalid input and keeps the program flow consistent.
            if (selectedOption == null) {
                System.out.println("Invalid option. Please try again.");
                // Continue is used for breaking this iteration of the loop, so if the user didn't type a valid option, they don't reach the
                // switch case bellow and the loop is restarted (new iteration). 
                continue;
            }

            // Executes a different block of code depending on the user's selected menu option.
            // Each case corresponds to a specific functionality, utilizing methods from utility classes to manage patients, 
            // employees, and managers.
            switch (selectedOption) {
                case CHECK_PATIENT_LIST:
                    // Access the displayPatientListOptions method inside the PatientUtils class.
                    // Displays options for managing the patient list, , such as viewing or sorting patients by different criteria.
                    PatientUtils.displayPatientListOptions(patients, scanner);
                    break;
                case CHECK_EMPLOYEE_LIST:
                    // Access the displayEmployeeListOptions method inside the EmployeeUtils class.
                    // Displays options for managing the employee list, such as viewing or sorting employees by different criteria.
                    EmployeeUtils.displayEmployeeListOptions(employees, scanner);
                    break;
                case CHECK_MANAGERS_LIST:
                    // Access the displayManagerListOptions method inside the ManagerUtils class.
                    // Displays options specifically for managing the list of managers.
                    ManagerUtils.displayManagerListOptions(managers, scanner);
                    break;
                case REGISTER_PATIENT:
                    // Allows the user to register a new patient in the system, adding to the existing patients list.
                    PatientUtils.addNewPatient(patients, scanner);
                    break;
                case REGISTER_EMPLOYEE:
                    // Allows the user to register a new employee, adding to the existing employees list.
                    EmployeeUtils.addNewEmployee(employees, scanner);
                    break;
                case PERSONAL_ACTIVITIES:
                    // Provides access to role-specific activities for employees, enabling them to interact based on their assigned roles.
                    // The departments and other lists are passed to allow seamless interaction and data sharing across different roles.
                    PersonalActivities.personalActivities(employees, patients, managers, scanner, surgery, psychiatry, emergencyRoom);
                    break;
                case EXIT:
                    // Sets the running flag to false, exiting the main loop and allowing the program to terminate.
                    // Displays a farewell message to the user upon exiting the application.
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
            }
        }
        // Closes the scanner resource after the loop terminates, freeing any resources associated with it.
        scanner.close();
    }
}


/**
 * The MenuOption enum class is designed to encapsulate the various menu options available 
 * in the system's main menu interface. By using an enum, we can define a fixed set of 
 * constants that represent each unique menu option, ensuring type safety and improved 
 * readability throughout the code. This approach also prevents errors from using 
 * arbitrary integers or strings to represent menu choices, as each option is explicitly 
 * defined with a meaningful name and associated values.
 */
enum MenuOption {

    /**
     * Menu option for viewing the list of patients in the system. This constant is defined with 
     * an integer choice of '1' and a description "Check patient list", which can be used 
     * to display meaningful information to the user. Using constants like this 
     * helps to avoid hard-coding values throughout the application, centralizing 
     * all menu options in a single, manageable place.
     */
    CHECK_PATIENT_LIST(1, "Check patient list"),

    /**
     * Menu option for viewing the list of employees in the system. Similar to the other menu 
     * options, this option is defined with a unique integer and description. By associating 
     * each option with a distinct integer, we can easily map user input to specific 
     * menu actions without relying on fragile, error-prone input handling logic.
     */
    CHECK_EMPLOYEE_LIST(2, "Check employee list"),

    /**
     * Menu option for viewing the list of managers. This constant provides an intuitive way 
     * to access manager-related information without relying on cryptic values, enhancing 
     * code clarity and ease of maintenance. The design also allows us to add more 
     * manager-specific options in the future if necessary, by expanding this enum.
     */
    CHECK_MANAGERS_LIST(3, "Check managers list"),

    /**
     * Menu option for registering a new patient in the system. By giving this option a 
     * dedicated enum constant, we facilitate the process of handling patient registration 
     * actions based on user input, streamlining code that relies on these predefined actions.
     * This also makes it straightforward to extend or modify the registration behavior.
     */
    REGISTER_PATIENT(4, "Register a patient"),

    /**
     * Menu option for registering a new employee in the system. This option provides a distinct 
     * pathway for handling employee registration actions, which can be leveraged in the main 
     * menu's control flow. The encapsulation in an enum ensures that menu logic remains 
     * clean, with each option clearly delineated, improving the overall organization of the code.
     */
    REGISTER_EMPLOYEE(5, "Register an employee"),

    /**
     * Menu option for accessing personal activities specific to employees. This option allows 
     * employees to view or update their individual tasks, depending on their roles. Encapsulating 
     * this option as an enum constant keeps the code flexible for future enhancements, 
     * enabling us to add or modify personal activity-related actions as needed.
     */
    PERSONAL_ACTIVITIES(6, "Personal activities"),

    /**
     * Menu option for exiting the application. Assigning a dedicated constant for "Exit" 
     * helps us manage the application lifecycle in a clean and structured way. By referencing 
     * this constant, the application can unambiguously handle exit commands, improving 
     * user experience and control flow clarity.
     */
    EXIT(7, "Exit");

    // Field storing the integer value associated with the menu option
    private final int choice;

    // Field storing the description of the menu option, for user-facing display
    private final String description;

    /**
     * Constructor for each MenuOption constant. Each menu option is initialized with an integer
     * representing the user’s choice and a descriptive string that can be shown in the menu display.
     * This constructor enforces that every option is consistently defined with both a choice and 
     * description, ensuring uniformity across menu entries.
     * 
     * @param choice      An integer uniquely identifying this menu option for user input mapping.
     * @param description A short, descriptive string summarizing the purpose of this menu option.
     */
    MenuOption(int choice, String description) {
        this.choice = choice;
        this.description = description;
    }

    /**
     * Getter method to retrieve the integer choice associated with this menu option. 
     * This method allows the main menu control logic to access the numeric identifier of each 
     * menu option, which is essential for handling user input and determining the corresponding 
     * action in a structured, type-safe way.
     * 
     * @return the integer value associated with this menu option.
     */
    public int getChoice() {
        return choice;
    }

    /**
     * Getter method to retrieve the description of this menu option. This description is useful 
     * for displaying the menu options to the user in a clear and friendly way. By having 
     * this method, we abstract the exact description format from the control logic, 
     * making the code more modular and easier to modify or localize if necessary.
     * 
     * @return the description of this menu option.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Static method to map a given integer choice to a corresponding MenuOption constant.
     * This method enables conversion from user input (an integer) to the appropriate 
     * MenuOption constant, which simplifies and strengthens the control flow in the 
     * menu-handling code. Using a loop through all enum values ensures that the method 
     * can handle any valid choice and return a meaningful enum constant, while returning 
     * null for invalid inputs, which can be managed as an error case.
     * 
     * @param choice the integer representing a menu option, typically from user input.
     * @return the MenuOption corresponding to the provided integer, or null if no match is found.
     */
    public static MenuOption fromChoice(int choice) {
        for (MenuOption option : values()) {
            if (option.choice == choice) {
                return option;
            }
        }
        return null;
    }
}

