package CA_2;

import java.util.List;
import java.util.Scanner;

public class ManagerUtils {

    /**
     * This method provides a user interface for displaying a list of managers.
     * The method utilizes a while loop to continuously prompt the user for input
     * until they choose to exit. I chose to encapsulate this logic within a method
     * to provide a modular and reusable way to manage the display options for
     * managers. This approach allows for the flexibility of adding more sorting
     * or filtering options in the future without modifying the core application logic.
     * 
     * @param managers A list of managers to display and sort.
     * @param scanner  A Scanner object for user input.
     */
    public static void displayManagerListOptions(List<Manager> managers, Scanner scanner) {
        boolean running = true;
        while(running) {
            System.out.println("\nHow would you like to see the manager list?");
            System.out.println("1. Alphabetically by name");
            System.out.println("2. By department");
            System.out.println("3. Filter by department");
            System.out.println("4. Go back to main menu");

            // Error handling to ensure the user enters a valid integer option.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // This switch structure provides various options based on user input.
            // Each case calls a specific method to perform the sorting/filtering logic,
            // allowing the application to respond dynamically to user choices.
            switch (choice) {
                case 1:
                case 2:
                    quickSort(managers, 0, managers.size() - 1, choice);
                    printManagers(managers);
                    break;
                case 3:
                    filterManagersByDepartment(managers, scanner);
                    break;
                case 4:
                    running = false;
                    return;
                default:
                    break;
            }
        }
    }

    /**
     * This method filters the list of managers by department based on user input.
     * The design decision to separate this functionality into its own method provides
     * MORE modularity and code reuse, which is something I always look out for.
     * It also allows the department filter to be expanded to other filter types in the future.
     * 
     */
    private static void filterManagersByDepartment(List<Manager> managers, Scanner scanner) {
        boolean running = true;
        while(running) {
            System.out.println("\nFilter by department:");
            System.out.println("1. Psychiatry");
            System.out.println("2. Emergency Room");
            System.out.println("3. Surgery");

            // Error handling for non-integer input to ensure valid user choice.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String departmentFilter = "";
            switch (choice) {
                case 1:
                    departmentFilter = "Psychiatry";
                    break;
                case 2:
                    departmentFilter = "Emergency Room";
                    break;
                case 3:
                    departmentFilter = "Surgery";
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }

            // Filtering managers by department and printing the relevant ones.
            // This loop checks each manager's department, ensuring only relevant results are displayed.
            for (Manager manager : managers) {
                if (manager.getDepartment().equalsIgnoreCase(departmentFilter)) {
                    System.out.println(manager.getName() + " - ID: " + manager.getId() + ", Department: " + manager.getDepartment());
                }
            }

            if(choice >= 1 && choice <= 3) running = false;
        }
    }

    /**
     * This quickSort method provides sorting functionality for the managers list.
     * By using the QuickSort algorithm, which has an average time complexity of O(n log n),
     * I ensured efficient sorting even with larger datasets. The choice parameter allows
     * sorting by either name or department, providing flexibility and reducing code redundancy.
     * I created one unique quickSort method and different patitions so that one process of the sorting
     * could be reused independently of what is being sorted
     */
    private static void quickSort(List<Manager> list, int low, int high, int choice) {
        if (low < high) {
            int partitionIndex = (choice == 1) ? partitionByName(list, low, high) : partitionByDepartment(list, low, high);
            quickSort(list, low, partitionIndex - 1, choice);
            quickSort(list, partitionIndex + 1, high, choice);
        }
    }

    /**
     * This method partitions the list based on the name attribute for the QuickSort algorithm.
     * I chose this partitioning method to facilitate alphabetical sorting, as it enables a
     * simple comparison between manager names. Partitioning allows the list to be sorted
     * in-place, saving memory and improving efficiency.
     */
    private static int partitionByName(List<Manager> list, int low, int high) {
        Manager pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getName().compareToIgnoreCase(pivot.getName()) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * This method partitions the list based on the department attribute for the QuickSort algorithm.
     * Sorting by department allows users to view managers grouped by their roles, which can aid in
     * analyzing departmental structure and finding specific managers quickly.
     */
    private static int partitionByDepartment(List<Manager> list, int low, int high) {
        Manager pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getDepartment().compareToIgnoreCase(pivot.getDepartment()) < 0) {
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
    private static void swap(List<Manager> list, int i, int j) {
        Manager temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * This method prints out a formatted list of managers, including their ID, name, and department.
     * Encapsulating this print logic allows for easy modifications to the display format, enhancing
     * readability and providing a centralized location for output formatting.
     * 
     * @param managers A list of managers to display.
     */
    public static void printManagers(List<Manager> managers) {
        System.out.println("\nManagers:");
        for (Manager manager : managers) {
            System.out.println("ID: " + manager.getId() + ", Name: " + manager.getName() + ", Department: " + manager.getDepartment());
        }
    }
}
