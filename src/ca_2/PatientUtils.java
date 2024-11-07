package ca_2;

import java.util.List;
import java.util.Scanner;

public class PatientUtils {

    public static void displayPatientListOptions(List<Patient> patients, Scanner scanner) {
        System.out.println("\nHow would you like to see the patient list?");
        System.out.println("1. Alphabetically by name");
        System.out.println("2. By admission date");
        System.out.println("3. By status");
        System.out.println("4. Filter by status");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        if (choice == 1 || choice == 2 || choice == 3) {
            quickSort(patients, 0, patients.size() - 1, choice);
            ReadFile.printPatients(patients);
        } else if (choice == 4) {
            filterPatientsByStatus(patients, scanner);
        } 
    }

    private static void filterPatientsByStatus(List<Patient> patients, Scanner scanner) {
        System.out.println("\nFilter by status:");
        System.out.println("1. Admitted");
        System.out.println("2. Released");
        System.out.println("3. Deceased");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

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
                System.out.println("Invalid filter option. Returning to main menu.");
                return;
        }
        
        int loopCounter = 0;
        for (Patient patient : patients) {
            if (patient.getStatus().equalsIgnoreCase(statusFilter)) {
                System.out.println(patient.getName() + " - ID: " + patient.getId());
                loopCounter++;
            }
            if (loopCounter == 50) break;
        }
    }
    
    private static void quickSort(List<Patient> list, int low, int high, int choice) {
        if (low < high) {
            int partition = 0;
            switch (choice) {
                case 1:
                    partition = partitionByName(list, low, high);
                    break;
                case 2:
                    partition = partitionByAdmissionDate(list, low, high);
                    break;
                case 3:
                    partition = partitionByStatus(list, low, high);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        
            int pi = partition;
            quickSort(list, low, pi - 1, choice);
            quickSort(list, pi + 1, high, choice);
        }
    }

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
    
    public static void addNewPatient(List<Patient> patients, Scanner scanner) {
        System.out.println("\nWhat's the patient's name?");
        String name = scanner.nextLine();
        System.out.println("When was the patient admitted? (dd/mm/yyyy)");
        String admissionDate = scanner.nextLine();
        System.out.println("To what ward was he assigned?");
        System.out.println("1. Emergency Room");
        System.out.println("2. Psychiatric");
        System.out.println("3. Surgery");
        int userChoice = scanner.nextInt();
        String department = "";
        switch(userChoice) {
            case 1:
                department = "Emergency Room";
                break;
            case 2:
                department = "Psychiatric";
                break;
            case 3: 
                department = "Surgery";
                break;
            case 4: 
                System.out.println("Invalid Choice.");
        }
        Patient patient;
        patient = new Admitted(name, patients.size() + 1, admissionDate, department);
        patients.add(patient);
        System.out.println(name + " has been added to " + department + " successfully!");
    }
    
    
    
}
