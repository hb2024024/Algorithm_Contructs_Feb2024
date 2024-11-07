package ca_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Henrique
 */

public class ReadFile {
    
    private static final String PATIENT_DATA_FILE = "PATIENT_DATA.txt";
    private static final String EMPLOYEE_DATA_FILE = "EMPLOYEE_DATA.txt";
    private static final int MAX_PRINT_LIMIT = 50;
    
    public static List<Patient> readPatientFile() {
        List<Patient> patients = new ArrayList<>();
        File userFile = new File(PATIENT_DATA_FILE);
        
        try {
            Scanner fileScanner = new Scanner(userFile);
            System.out.println("File read successfully!");
            String fileLine;
            
            // Read each line of the file
            while (fileScanner.hasNextLine()) {
                fileLine = fileScanner.nextLine();
                
                List<String> listData = Arrays.asList(fileLine.split(","));
                
                int id = Integer.parseInt(listData.get(0));
                String name = listData.get(1);
                String admissionDate = listData.get(2);
                String status = listData.get(3);
                String department = listData.get(4);
                String finalDate = listData.size() > 5 ? listData.get(5) : "";


                Patient patient;

                switch(status.toLowerCase()) {
                    case "deceased":
                        patient = new Deceased(name, id, admissionDate, finalDate);
                        break;
                    case "released":
                        patient = new Released(name, id, admissionDate, finalDate);
                        break;
                    case "admitted":
                        patient = new Admitted(name, id, admissionDate, department);
                        break;
                    default:
                        // If status is unknown, default to a generic Patient
                        patient = new Patient(name, id, admissionDate, status);
                        break;
                }

                patients.add(patient);

                
            }
            fileScanner.close();

            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        
        return patients;
        
    }
    
    public static List<Employee> readEmployeeFile() {
        List<Employee> employees = new ArrayList<>();
        File userFile = new File(EMPLOYEE_DATA_FILE);
        
        try {
            Scanner fileScanner = new Scanner(userFile);
            System.out.println("File read successfully!");
            String fileLine;
            
            // Read each line of the file
            while (fileScanner.hasNextLine()) {
                fileLine = fileScanner.nextLine();
                
                List<String> listData = Arrays.asList(fileLine.split(","));
                
                    int id = Integer.parseInt(listData.get(0));
                    String name = listData.get(1);
                    String department = listData.get(2);
                    String role = listData.get(3);
                    
                    Employee employee = new Employee(name, id, department, role);
                    employees.add(employee);
                
                
            }
            fileScanner.close();

            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        
        return employees;
        
    }
    
    public static void printPatients(List<Patient> patients) {
        int loopCounter = 0;
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Admission Date: " + patient.getAdmissionDate());
            System.out.println("Status: " + patient.getStatus());

            if (patient instanceof Deceased) {
                System.out.println("Date of Death: " + ((Deceased) patient).getDateOfDeath());
            } else if (patient instanceof Released) {
                System.out.println("Release Date: " + ((Released) patient).getReleaseDate());
            } else if (patient instanceof Admitted) {
                System.out.println("Department Assigned: " + ((Admitted) patient).getDepartmentAssigned());
            }
            System.out.println();
            loopCounter++;
            if(loopCounter == MAX_PRINT_LIMIT) break;
        }
    }
    
    public static void printEmployees(List<Employee> employees) {
        int loopCounter = 0;
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Role: " + employee.getRole());

            System.out.println();
            loopCounter++;
            if(loopCounter == MAX_PRINT_LIMIT) break;
        }
    }
}
