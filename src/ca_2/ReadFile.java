package CA_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

// Class for handling file reading and data generation for Employees, Patients, and Managers in the hospital system.
// This class also keeps track of used names to avoid duplicates and supports department-specific allocations.
public class ReadFile {

    // Path to the file containing names data for generating entities.
    // Keeping the file path as a constant improves maintainability, allowing easy updates if the file path changes.
    private static final String NAMES_FILE = "NAMES_DATA.txt";

    // Maximum limit for printed output, helping to manage large data and prevent overly verbose output.
    private static final int MAX_PRINT_LIMIT = 50;

    // A Set to store used names, ensuring that each name is only assigned once.
    // Using a HashSet provides O(1) average time complexity for insertions and lookups, which is efficient.
    private static final Set<String> usedNames = new HashSet<>();

    // Reads names from the file into a List, which can then be used to generate employees, patients, and managers.
    // By using a List, we retain the order of names as they appear in the file, which can be useful for data consistency.
    public static List<String> readNamesFile() {
        List<String> names = new ArrayList<>();
        File userFile = new File(NAMES_FILE);

        try {
            Scanner fileScanner = new Scanner(userFile);
            
            // Read each line in the file and add it to the list, providing a clean, accessible structure for names.
            while (fileScanner.hasNextLine()) {
                String name = fileScanner.nextLine();
                names.add(name);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        return names;
    }

    // Generates a list of Employee objects from the provided list of names.
    // Randomly assigns roles to each employee, and some are given a specific department, such as Surgery.
    // The Random boolean decision and maxLoops limit help control the number of employees generated and maintain diversity.
    public static List<Employee> generateEmployees(List<String> names, Department surgery) {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();
        int maxLoops = 0;

        for (String name : names) {
            // Randomly choose to create an employee, ensuring diversity in generated data by limiting the loop.
            if (random.nextBoolean() && maxLoops <= 50 && !usedNames.contains(name)) {
                int id = random.nextInt(names.size()); // Generate a unique ID within the names size limit.
                Employee employee = generateRandomEmployee(name, id);
                employees.add(employee);
                usedNames.add(name); // Track the name to prevent re-use.

                // If the generated employee belongs to Surgery, add them to the surgery department.
                if (employee.getDepartment() == "Surgery") {
                    surgery.addEmployee(employee);
                }
                maxLoops++;
            }
        }
        return employees;
    }

    // Generates a list of Patient objects from the provided list of names.
    // Patients are randomly assigned to different departments, and their admission statuses vary.
    public static List<Patient> generatePatients(List<String> names, Department surgery, Department psychiatry, Department emergencyRoom) {
        List<Patient> patients = new ArrayList<>();
        Random random = new Random();
        int maxLoops = 0;

        for (String name : names) {
            // Ensure uniqueness of names and randomly assign patients to departments with various statuses.
            if (!random.nextBoolean() && maxLoops <= 50 && !usedNames.contains(name)) {
                int id = random.nextInt(names.size()); // Generate a random ID
                Patient patient = generateRandomPatient(name, id);
                patients.add(patient);
                usedNames.add(name); // Mark name as used for tracking
                
                // Add patient to their respective department if assigned one.
                if("Admitted".equals(patient.getStatus())){
                    switch (((Admitted) patient).getDepartmentAssigned()) {
                        case "Surgery":
                            surgery.addPatient(patient);
                            break;
                        case "Psychiatry":
                            psychiatry.addPatient(patient);
                            break;
                        case "Emergency Room":
                            emergencyRoom.addPatient(patient);
                            break;
                    }
                }
            }
        }
        return patients;
    }

    // Generates a list of Manager objects for each department.
    // Each department receives two managers for balanced oversight.
    public static List<Manager> generateManagers(List<String> names) {
        List<Manager> managers = new ArrayList<>();
        String[] departments = {"Psychiatry", "Emergency Room", "Surgery"};
        Random random = new Random();

        // Iterate through departments to assign managers to each.
        for (String department : departments) {
            int managersGenerated = 0;
            while (managersGenerated < 2) { // Limit to two managers per department
                String name = names.get(random.nextInt(names.size()));
                if (!usedNames.contains(name)) { // Ensure name uniqueness
                    int id = random.nextInt(names.size()); // Generate a unique ID
                    Manager manager = new Manager(name, id, department);
                    managers.add(manager);
                    usedNames.add(name); // Track the name to avoid reuse
                    managersGenerated++;
                }
            }
        }
        return managers;
    }

    // Helper method to generate a random employee, assigning them a role and, optionally, a department.
    // Each role has specific attributes, and only certain roles (e.g., Surgeon) are assigned to a department.
    private static Employee generateRandomEmployee(String name, int id) {
        Random random = new Random();
        String role;
        String department = null; // Not all roles require a department assignment.

        switch (random.nextInt(5)) {
            case 0:
                role = "Doctor";
                break;
            case 1:
                role = "Nurse";
                break;
            case 2:
                role = "Surgeon";
                department = "Surgery";
                break;
            default:
                role = "Cleaner";
                break;
        }

        // Instantiate specific employee subclass based on the assigned role.
        switch (role) {
            case "Doctor":
                return new Doctor(name, id, null);
            case "Nurse":
                return new Nurse(name, id, null);
            case "Surgeon":
                return new Surgeon(name, id, department);
            case "Cleaner":
                return new Cleaner(name, id, null);
            default:
                return null;
        }
    }

    // Helper method to create a random Patient, assigning status, admission date, and department if applicable.
    private static Patient generateRandomPatient(String name, int id) {
        Random random = new Random();
        String admissionDate = "2023-" + (random.nextInt(12) + 1) + "-" + (random.nextInt(28) + 1);
        String status;
        String department = null;

        // Randomly assign status, and if admitted, assign a department.
        switch (random.nextInt(3)) {
            case 0:
                status = "Deceased";
                return new Deceased(name, id, admissionDate, "2024-" + (random.nextInt(12) + 1) + "-" + (random.nextInt(28) + 1));
            case 1:
                status = "Released";
                return new Released(name, id, admissionDate, "2024-" + (random.nextInt(12) + 1) + "-" + (random.nextInt(28) + 1));
            default:
                status = "Admitted";
                department = getRandomDepartment();
                return new Admitted(name, id, admissionDate, department);
        }
    }

    // Randomly assigns a department to an admitted patient for diversity in department allocations.
    private static String getRandomDepartment() {
        String[] departments = {"Emergency Room", "Psychiatry", "Surgery"};
        Random random = new Random();
        return departments[random.nextInt(departments.length)];
    }

    // Prints information on employees up to a limit for readability.
    public static void printEmployees(List<Employee> employees) {
        System.out.println("Employees:");
        int loopCounter = 0;
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId() + ", Name: " + employee.getName() +
                    ", Role: " + employee.getRole() + ", Department: " + (employee.getDepartment() != null ? employee.getDepartment() : "None"));
            loopCounter++;
            if (loopCounter == MAX_PRINT_LIMIT) break;
        }
    }

    // Prints information on patients up to a specified limit, displaying key attributes like admission date and status.
    public static void printPatients(List<Patient> patients) {
        int loopCounter = 0;
        System.out.println("\nPatients:");
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getId() + ", Name: " + patient.getName() +
                    ", Admission Date: " + patient.getAdmissionDate() + ", Status: " + patient.getStatus() +
                    ", Department: " + (patient instanceof Admitted ? ((Admitted) patient).getDepartmentAssigned() : "None"));
            loopCounter++;
            if (loopCounter == MAX_PRINT_LIMIT) break;
        }
    }

    // Prints the list of managers, each associated with their department.
    public static void printManagers(List<Manager> managers) {
        System.out.println("\nManagers:");
        for (Manager manager : managers) {
            System.out.println("ID: " + manager.getId() + ", Name: " + manager.getName() + ", Department: " + manager.getDepartment());
        }
    }
}
