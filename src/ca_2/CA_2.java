package ca_2;

import java.util.List;
import java.util.Scanner;

public class CA_2 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Patient> patients = ReadFile.readPatientFile();
        List<Employee> employees = ReadFile.readEmployeeFile();
        boolean running = true;
        
        System.out.println("Hi! Welcome to Hospital Pantheon.");

        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Check patient list");
            System.out.println("2. Check employee list");
            System.out.println("3. Register a patient");
            System.out.println("4. Register an employee");
            System.out.println("5. Personal activities (to be implemented)");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            
            switch (choice) {
                case 1:
                    PatientUtils.displayPatientListOptions(patients, scanner);
                    break;
                case 2:
                    EmployeeUtils.displayEmployeeListOptions(employees, scanner);
                    break;
                case 3:
                    PatientUtils.addNewPatient(patients, scanner);
                    break;
                case 4:
                    EmployeeUtils.addNewEmployee(employees, scanner);
                    break;
                case 5:
                    System.out.println("Personal activities feature is under construction.");
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
