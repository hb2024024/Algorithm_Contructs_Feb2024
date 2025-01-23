package CA_2;

import java.util.List;
import java.util.Scanner;

public class EmployeeUtils {

    /**
     * This method is designed to provide a menu for displaying and filtering employee lists. 
     * It allows users to choose how to view the list (sorted by name, by role, or filtered by a specific role).
     * The while loop ensures that the menu keeps running until the user chooses to exit.
     * Using a loop here makes the program interactive and user-friendly, allowing multiple actions without restarting.
     * 
     * Although a lot of this code is very similar to the classes ManagerUtils and PatientUtils, I didn't know how to
     * take advantage of the same code for all of them, since the type of the parameters and sometimes returns would be
     * different. I imagine java has a way around this, but in all honesty, I couldn't figure it out. If in my feedback
     * I could get an explanation as how to do it, it would be very appreciated!
     * 
     * @param employees A list of employees to display and sort.
     * @param scanner  A Scanner object for user input.
    */
    public static void displayEmployeeListOptions(List<Employee> employees, Scanner scanner) {
        boolean running = true;
        while (running) {

            System.out.println("\nHow would you like to see the employee list?");
            System.out.println("1. Alphabetically by name");
            System.out.println("2. By role");
            System.out.println("3. Filter by role");
            System.out.println("4. Go back to main menu");
            
            // Error handling ensures the user provides valid integer input.
            // This prevents the program from crashing on invalid input, making it robust and user-friendly.
            // This error handling approach was adapted from the Zoo project.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            
            // The switch statement processes user choices based on integer input.
            // Different options provide sorting and filtering functionalities, enhancing usability.
            // The sorting uses quickSort to efficiently order employees by name or role, showcasing modularity and reusability.
            switch (choice) {
                case 1:
                case 2:
                    quickSort(employees, 0, employees.size() - 1, choice);
                    ReadFile.printEmployees(employees);
                    break;
                case 3:
                    filterEmployeesByRole(employees, scanner);
                    break;
                case 4:
                    running = false;
                    return;
                default:
                    System.out.println("Choose a valid option");
                    break;
            }
        }
    }

    // This method allows filtering employees by role, making the application more interactive and allowing for targeted data retrieval.
    // Using a loop and switch structure here allows the user to filter employees by specific roles dynamically.
    private static void filterEmployeesByRole(List<Employee> employees, Scanner scanner) {
        boolean running = true;
        while (running) {

            System.out.println("\nFilter by role:");
            System.out.println("1. Doctor");
            System.out.println("2. Nurse");
            System.out.println("3. Surgeon");
            System.out.println("4. Cleaner");

            // Error handling for non-integer input ensures a smooth user experience by prompting valid input.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String roleFilter = "";
            // Assigns the selected role to the filter, allowing precise employee role retrieval.
            switch (choice) {
                case 1:
                    roleFilter = "Doctor";
                    break;
                case 2:
                    roleFilter = "Nurse";
                    break;
                case 3:
                    roleFilter = "Surgeon";
                    break;
                case 4:
                    roleFilter = "Cleaner";
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }

            // This loop iterates through employees to display those with the specified role.
            // Using a role filter enhances search precision, improving the program’s flexibility.
            for (Employee employee : employees) {
                if (employee.getRole().equalsIgnoreCase(roleFilter)) {
                    System.out.println(employee.getName() + " - ID: " + employee.getId());
                }
            }

            if (choice >= 1 && choice <= 4) running = false;
        }
    }

    /**
     * This quickSort method provides sorting functionality for the employees list.
     * By using the QuickSort algorithm, which has an average time complexity of O(n log n),
     * I ensured efficient sorting even with larger datasets. The choice parameter allows
     * sorting by either name or role, providing flexibility and reducing code redundancy.
     * I created one unique quickSort method and different patitions so that one process of the sorting
     * could be reused independently of what is being sorted
     */
    private static void quickSort(List<Employee> list, int low, int high, int choice) {
        if (low < high) {
            int partition = 0;
            // The partition is determined based on the sorting criterion (name or role).
            switch (choice) {
                case 1:
                    partition = partitionByName(list, low, high);
                    break;
                case 2:
                    partition = partitionByRole(list, low, high);
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }
            quickSort(list, low, partition - 1, choice);
            quickSort(list, partition + 1, high, choice);
        }
    }

    // partitionByName is a helper method that organizes elements around a pivot by comparing names.
    // This is used by the quickSort method for sorting alphabetically by name, enhancing readability and modularity.
    private static int partitionByName(List<Employee> list, int low, int high) {
        Employee pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (list.get(j).getName().compareToIgnoreCase(pivot.getName()) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    // partitionByRole organizes elements based on roles for quickSort when sorting by role.
    // It follows the same structure as partitionByName, demonstrating the code's adaptability.
    private static int partitionByRole(List<Employee> list, int low, int high) {
        Employee pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (list.get(j).getRole().compareToIgnoreCase(pivot.getRole()) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * This method swaps two elements in the list, a fundamental operation within sorting algorithms.
     * By isolating the swap logic, this approach enhances code readability and modularity, enabling
     * code reuse and potential debugging ease.
     */
    private static void swap(List<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // This method allows adding new employees with a specified name and role.
    // It interacts with the user for input and validates data to ensure meaningful entries.
    // Adding employees is essential for expanding the dataset dynamically during runtime.
    public static void addNewEmployee(List<Employee> employees, Scanner scanner) {
        System.out.println("\nEnter the employee's name:");
        String name;
        while (true) {
            name = scanner.nextLine().trim();
            if (name.length() >= 3) {
                break; // Name is valid
            } else {
                System.out.println("Name must be at least 3 characters. Please try again:");
            }
        }

        // The role selection enhances flexibility, allowing the user to assign appropriate roles to employees.
        System.out.println("Choose a role:\n1. Doctor\n2. Nurse\n3. Surgeon\n4. Cleaner");
        int choice;
        String role = "";
        boolean running = true;
        while (running) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next(); // Clear the invalid input
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    role = "Doctor";
                    break;
                case 2:
                    role = "Nurse";
                    break;
                case 3:
                    role = "Surgeon";
                    break;
                case 4:
                    role = "Cleaner";
                    break;
                default:
                    System.out.println("Invalid role choice.");
            }

            // Adds the new employee with a unique ID, making the program have the ability to handle dynamic data.
            if (choice >= 1 && choice <= 4) running = false;

            Employee employee = new Employee(name, employees.size() + 1, role);
            employees.add(employee);
            System.out.println("Employee " + name + " has been added as a " + role);
        }
    }
}
