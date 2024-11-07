package ca_2;

import java.util.List;
import java.util.Scanner;

public class EmployeeUtils {
    
    public static void displayEmployeeListOptions(List<Employee> employees, Scanner scanner) {
        System.out.println("\nHow would you like to see the employee list?");
        System.out.println("1. Alphabetically by name");
        System.out.println("2. By department");
        System.out.println("3. By role");
        System.out.println("4. Filter by role");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        if (choice == 1 || choice == 2 || choice == 3) {
            quickSort(employees, 0, employees.size() - 1, choice);
            ReadFile.printEmployees(employees);
        } else if (choice == 4) {
            filterEmployeesByRole(employees, scanner);
        }
        
        
    }

    private static void filterEmployeesByRole(List<Employee> employees, Scanner scanner) {
        System.out.println("\nFilter by role:");
        System.out.println("1. Doctor");
        System.out.println("2. Nurse");
        System.out.println("3. Surgeon");
        System.out.println("4. Cleaner");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String roleFilter = null;
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
                System.out.println("Invalid filter option. Returning to main menu.");
                return;
        }

        int loopCounter = 0;
        for (Employee employee : employees) {
            if (employee.getRole().equalsIgnoreCase(roleFilter)) {
                System.out.println(employee.getName() + " - ID: " + employee.getId());
                loopCounter++;
            }
            if (loopCounter == 50) break;
        }
        
    }
    private static void quickSort(List<Employee> list, int low, int high, int choice) {
        if (low < high) {
            int partition = 0;
            switch (choice) {
                case 1:
                    partition = partitionByName(list, low, high);
                    break;
                case 2:
                    partition = partitionByDepartment(list, low, high);
                    break;
                case 3:
                    partition = partitionByRole(list, low, high);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        
            int pi = partition;
            quickSort(list, low, pi - 1, choice);
            quickSort(list, pi + 1, high, choice);
        }
    }

   
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

   private static int partitionByDepartment(List<Employee> list, int low, int high) {
        Employee pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (list.get(j).getDepartment().compareToIgnoreCase(pivot.getDepartment()) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }


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

    private static void swap(List<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    
    public static void addNewEmployee(List<Employee> employees, Scanner scanner) {
        System.out.println("\nEmployee's name:");
        String name = scanner.nextLine();
        System.out.println("Department they work:");
        System.out.println("1. Emergency Room");
        System.out.println("2. Psychiatric");
        System.out.println("3. Surgery");
        int departmentChoice = scanner.nextInt();
        System.out.println("Their role:");
        System.out.println("1. Doctor");
        System.out.println("2. Nurse");
        System.out.println("3. Cleaner");
        if(departmentChoice == 3) System.out.println("4. Surgeon");
        int roleChoice = scanner.nextInt();
        String department = "";
        switch(departmentChoice) {
            case 1:
                department = "Emergency Room";
                break;
            case 2:
                department = "Psychiatric";
                break;
            case 3: 
                department = "Surgery";
                break;
            default: 
                System.out.println("Invalid Choice.");
        }
        Employee employee = new Employee(name, 0, department, "");
        
        switch(roleChoice) {
            case 1: 
                employee = new Doctor(name, employees.size() + 1, department, "Doctor");
                break;
            case 2: 
                employee = new Nurse(name, employees.size() + 1, department, "Nurse");
                break;
            case 3:
                employee = new Cleaner(name, employees.size() + 1, department, "Cleaner");
                break;
            case 4:
                employee = new Surgeon(name, employees.size() + 1, department, "Surgeon");
                break;
            default:
                System.out.println("Invalid Choice.");
                
        }
        employees.add(employee);
        System.out.println(name + " has been registered to the " + department + " department");
    }
}
