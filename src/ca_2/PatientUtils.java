package CA_2;

import java.util.List;
import java.util.Scanner;

public class PatientUtils {

    /**
     * This method presents the user with options for viewing and sorting the list of patients.
     * It runs in a loop, allowing the user to choose different options repeatedly until they
     * decide to return to the main menu.
     *
     * Options include sorting patients by name, admission date, or status, as well as filtering
     * by status. Based on the user's choice, the method either calls sorting methods with the
     * specified criteria or filters patients by status.
     *
     * Error handling is implemented to ensure that only valid integer inputs are accepted.
     *
     * @param patients A list of patients to display and sort.
     * @param scanner  A Scanner object for user input.
     */
    public static void displayPatientListOptions(List<Patient> patients, Scanner scanner) {
        boolean running = true;
        while (running) {

            System.out.println("\nHow would you like to see the patient list?");
            System.out.println("1. Alphabetically by name");
            System.out.println("2. By admission date");
            System.out.println("3. By status");
            System.out.println("4. Filter by status");
            System.out.println("5. Go back to main menu");

            // Validates that user input is an integer; otherwise, prompts again.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after integer input

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    quickSort(patients, 0, patients.size() - 1, choice); // Calls quickSort with different criteria
                    ReadFile.printPatients(patients); // Displays the sorted list
                    break;
                case 4:
                    filterPatientsByStatus(patients, scanner); // Filters patients by status
                    break;
                case 5:
                    running = false; // Exits the loop to return to the main menu
                    return;
                default:
                    break;
            }
        }
    }

    /**
     * Filters the patient list by status and displays patients with the selected status.
     * The user can filter by "Admitted", "Released", or "Deceased" status.
     *
     * The method validates input to ensure only integer values are accepted and only valid
     * status options are selected.
     *
     * @param patients The list of patients to filter.
     * @param scanner  Scanner object for user input.
     */
    private static void filterPatientsByStatus(List<Patient> patients, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nFilter by status:");
            System.out.println("1. Admitted");
            System.out.println("2. Released");
            System.out.println("3. Deceased");

            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after integer input

            String statusFilter = null;
            switch (choice) {
                case 1:
                    statusFilter = "Admitted";
                    break;
                case 2:
                    statusFilter = "Released";
                    break;
                case 3:
                    statusFilter = "Deceased";
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }

            for (Patient patient : patients) {
                if (patient.getStatus().equalsIgnoreCase(statusFilter)) {
                    System.out.println(patient.getName() + " - ID: " + patient.getId());
                }
            }
            if (choice >= 1 && choice <= 3) running = false;
        }
    }

    /**
     * This quickSort method provides sorting functionality for the managers list.
     * By using the QuickSort algorithm, which has an average time complexity of O(n log n),
     * I ensured efficient sorting even with larger datasets. The choice parameter allows
     * sorting by either name, admission date or status, providing flexibility and reducing code redundancy.
     * I created one unique quickSort method and different patitions so that one process of the sorting
     * could be reused independently of what is being sorted
     *
     * @param list   The list of patients to sort.
     * @param low    The starting index for the quicksort algorithm.
     * @param high   The ending index for the quicksort algorithm.
     * @param choice Sorting criterion (1 for name, 2 for admission date, 3 for status).
     */
    private static void quickSort(List<Patient> list, int low, int high, int choice) {
        if (low < high) {
            int partition = 0;
            switch (choice) {
                case 1:
                    partition = partitionByName(list, low, high); // Partition based on name
                    break;
                case 2:
                    partition = partitionByAdmissionDate(list, low, high); // Partition based on admission date
                    break;
                case 3:
                    partition = partitionByStatus(list, low, high); // Partition based on status
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }
            quickSort(list, low, partition - 1, choice); // Recursively sort the left partition
            quickSort(list, partition + 1, high, choice); // Recursively sort the right partition
        }
    }

    /**
     * This method partitions the list based on the name attribute for the QuickSort algorithm.
     * I chose this partitioning method to facilitate alphabetical sorting, as it enables a
     * simple comparison between patient names. Partitioning allows the list to be sorted
     * in-place, saving memory and improving efficiency.
     */
    private static int partitionByName(List<Patient> list, int low, int high) {
        Patient pivot = list.get(high);
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
     * This method partitions the list based on the admission date attribute for the QuickSort algorithm.
     * Sorting by admission date is helpful to check the patients that have been admitted by their date,
     * undestanding which patients have been in the hospital for longer.
     */
    private static int partitionByAdmissionDate(List<Patient> list, int low, int high) {
        Patient pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getAdmissionDate().compareTo(pivot.getAdmissionDate()) < 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * This method partitions the list based on the patient's status.
     * It is useful to use this sorting to see the full list of patients the hospital has taken,
     * get an idea maybe of how many were released/how many died, and so on. Since filtering by status has only
     * been implemented to check one at time, this is the only way for now that the user can see the lists together.
     * 
     */
    private static int partitionByStatus(List<Patient> list, int low, int high) {
        Patient pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getStatus().compareToIgnoreCase(pivot.getStatus()) < 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Patient> list, int i, int j) {
        Patient temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * Adds a new patient to the list based on user-provided information including name,
     * admission date, and department. The method validates each input for correctness,
     * such as ensuring the name has a minimum length, the date follows a specific format,
     * and the department is chosen from predefined options.
     *
     * @param patients The list to add the new patient to.
     * @param scanner  Scanner object for input.
     */
    public static void addNewPatient(List<Patient> patients, Scanner scanner) {
        System.out.println("\nEnter the patient's name:");
        String name;
        while (true) {
            name = scanner.nextLine().trim();
            if (name.length() >= 3) {
                break;
            } else {
                System.out.println("Name must be at least 3 characters. Please try again:");
            }
        }

        System.out.println("Enter the admission date (dd/mm/yyyy):");
        String admissionDate;
        while (true) {
            admissionDate = scanner.nextLine().trim();
            if (admissionDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                break;
            } else {
                System.out.println("Date must be in the format dd/mm/yyyy. Please try again:");
            }
        }

        System.out.println("Choose a department:\n1. Emergency Room\n2. Psychiatry\n3. Surgery");
        int choice;
        String department = "";
        boolean running = true;
        while (running) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next(); // Clear invalid input
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    department = "ER";
                    break;
                case 2:
                    department = "Psychiatry";
                    break;
                case 3:
                    department = "Surgery";
                    break;
                default:
                    System.out.println("Please enter a valid number (1, 2, or 3) for the department:");
            }

            if (choice >= 1 && choice <= 3) running = false;
        }

        Patient patient = new Patient(name, patients.size() + 1, admissionDate, department);
        patients.add(patient);
        System.out.println("Patient " + name + " has been added to " + department);
    }
}
