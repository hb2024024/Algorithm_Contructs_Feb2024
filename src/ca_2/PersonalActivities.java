package CA_2;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PersonalActivities {
    
    // The purpose of this class is to handle activities specific to employees and managers within the hospital system.
    // I designed this class as a central point for managing "personal activities," such as logging (which is a search algorithm) in and accessing specific tasks.
    // This separation of logic into a dedicated class improves modularity,
    // making it easier to maintain and update the code related to these user activities in one location.

    public static void personalActivities(
            List<Employee> employees,
            List<Patient> patients,
            List<Manager> managers,
            Scanner scanner,
            Department surgery,
            Department psychiatry,
            Department emergencyRoom
            ) {
        // This method serves as the entry point for employees and managers to access their respective personal activities.
        // I chose to make this method static to avoid the need to instantiate PersonalActivities, as it operates on passed parameters. 
        // The method's purpose is to guide users through a login process and then direct them
        // to relevant options based on their role (Manager or general Employee).

        boolean running = true;
        while (running) {
            
            // Displaying login options allows users to identify themselves either by name or ID.
            // By providing multiple options, we enhance usability, catering to different user preferences or situations
            // (e.g. if an employee remembers their ID but not their name).
            System.out.println("Log in with:");
            System.out.println("1. Name");
            System.out.println("2. ID");
            // It is important to always give the user an exit route.
            System.out.println("3. Go back to main menu");

            // Error handling for non-integer input to ensure the user provides a valid choice.
            while (!scanner.hasNextInt()) {
                System.out.println("Please make sure to select an option using a number");
                scanner.next();
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Employee employee = null;
            Manager manager = null;

            // For the searches it was used a linear search algorithm instead of a binary. 
            // Although binary has a better time complexity, linear doesn't require the list to be 
            // pre-sorted, so for the system right now, it suited better to use the one with a simpler
            // implementation. 
            switch (choice) {
                case 1:
                    // Case 1: Login by name
                    // Here I use a stream filter to locate the employee or manager with the specified name.
                    // The use of streams helps readability and provides a declarative approach to finding the matching entry.
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    employee = employees.stream()
                            .filter(e -> e.getName().equalsIgnoreCase(name))
                            .findFirst().orElse(null);
                    if(employee == null) {
                        manager = managers.stream()
                                .filter(e -> e.getName().equalsIgnoreCase(name))
                                .findFirst().orElse(null);
                    }   
                    break;
                case 2:
                    // Case 2: Login by ID
                    // Similar to login by name, I apply a stream filter to identify the employee or manager with the given ID.
                    // Using ID-based login offers an alternative for users who may not remember exact name spelling.
                    System.out.print("Enter ID: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Please make sure to select an option using a number");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    employee = employees.stream()
                            .filter(e -> e.getId() == id)
                            .findFirst().orElse(null);
                    if(employee == null) {
                        manager = managers.stream()
                                .filter(e -> e.getId() == id)
                                .findFirst().orElse(null);
                    }   
                    break;
                case 3:
                    // Case 3: Exit option to return to the main menu
                    running = false;
                    return;
                default:
                    System.out.println("Choose a valid option"); 
            }

            // If the user does not exist in either employees or managers lists, display an error message.
            // By checking both employee and manager lists, we ensure that any user with access rights is recognized.
            if (employee == null && manager == null && choice >= 1 && choice <= 2 ) {
                System.out.println("Employee not found.");
                return;
            }

            // Redirecting to either manager or employee activities based on their role.
            // This ensures that users only access options relevant to their job responsibilities.
            if (manager != null && choice >= 1 && choice <= 2) {
                System.out.println("Welcome " + manager.getName() + ". Here are your activities.");
                handleManagerActivities(manager, employees, scanner, surgery, psychiatry, emergencyRoom);
            } else if (choice >= 1 && choice <= 2) {
                System.out.println("Welcome " + employee.getName() + ". Here are your activities.");
                handleEmployeeActivities(employee, patients);
            }
        }
    }


    // This method allows managers to perform specific tasks within their assigned departments.
    // Each manager has access only to the activities related to their department, ensuring role-based access control.
    // The while loop keeps the manager within this menu until they choose to exit, improving usability.
    private static void handleManagerActivities(
           Manager manager,
           List<Employee> employees,
           Scanner scanner,
           Department surgery,
           Department psychiatry,
           Department emergencyRoom) {

       boolean running = true;
       while (running) {

           // Displaying options for manager-specific actions.
           // These options include viewing patients and employees in the department,
           // as well as assigning unassigned employees, offering control over the department's staffing.
           System.out.println("1. View admitted patients in your department");
           System.out.println("2. View employees in your department");
           System.out.println("3. Assign employees without a department to your department");
           System.out.println("4. Go back to main menu");

           // Error handling for non-integer input, ensuring robustness and better user experience.
           while (!scanner.hasNextInt()) {
               System.out.println("Please make sure to select an option using a number");
               scanner.next();
           }
           int choice = scanner.nextInt();
           scanner.nextLine(); // Consume newline

           // Obtain the department based on the manager's assigned department field.
           // Using the helper method getDepartment encapsulates the logic of department selection,
           // making the code modular and easily modifiable if department structures change.
           Department department = getDepartment(manager, surgery, psychiatry, emergencyRoom);

           switch (choice) {
               case 1:
                   // View all patients admitted to the manager's department.
                   System.out.println("Admitted patients in " + department.getName() + ":");
                   for (Patient patient : department.getPatients()) {
                       System.out.println(patient.getName());
                   }
                   break;
               case 2:
                   // View all employees working in the manager's department.
                   System.out.println("Employees in " + department.getName() + ":");
                   for (Employee emp : department.getEmployees()) {
                       System.out.println(emp.getName() + " - " + emp.getRole());
                   }
                   break;
               case 3:
                   // Assign employees without a department to the manager's department.
                   assignEmployeesToDepartment(department, employees, scanner);
                   break;
               case 4:
                   // Option to exit the manager activities menu.
                   running = false;
                   return;
               default:
                   break;
           }
       }
    }

    private static void assignEmployeesToDepartment(
            Department department,
            List<Employee> employees, 
            Scanner scanner) {

        // This method facilitates assigning employees without a department to a specific department.
        // Only employees of specific roles (Doctor, Nurse, Cleaner) can be assigned, as per the system requirements.

        System.out.println("Select employee type to assign:");
        System.out.println("1. Doctor");
        System.out.println("2. Nurse");
        System.out.println("3. Cleaner");

        // Error handling for non-integer input to ensure user provides valid input.
        while (!scanner.hasNextInt()) {
            System.out.println("Please make sure to select an option using a number");
            scanner.next();
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Determining the role type based on user selection. This limits assignment to specific roles,
        // preventing incorrect assignments and ensuring that employees are appropriately categorized.
        String roleType = "";
        switch (typeChoice) {
            case 1:
                roleType = "Doctor";
                break;
            case 2:
                roleType = "Nurse";
                break;
            case 3:
                roleType = "Cleaner";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Retrieve the list of unassigned employees for the selected role type.
        // The helper method getUnassignedEmployees encapsulates the filtering logic, enhancing code reusability.
        List<Employee> unassignedEmployees = getUnassignedEmployees(employees, roleType);

        // Limit the number of employees that can be assigned to 1/4 of the total available unassigned employees.
        // This number was chosen because there are four departments in the hospital, this would increase based on
        // department sizes. A good future implementation would be to use a list of departments to divide by that.
        // This limit ensures that the department is not overloaded and provides a realistic approach to staffing.
        int maxAssignable = unassignedEmployees.size() / 4;
        System.out.println("How many " + roleType + "(s) do you want to assign?");
        while (!scanner.hasNextInt()) {
            System.out.println("Please make sure to select an option using a number");
            scanner.next();
        }
        int count = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // If the requested number exceeds the allowable limit, adjust it to the maximum assignable.
        if (count > maxAssignable) {
            System.out.println("We can only assign " + maxAssignable + " " + roleType + "(s) today. These are the " + roleType + "(s) for today:");
            count = maxAssignable;
        } else {
            System.out.println("These are the " + roleType + "(s) for today:");
        }

        // Assign the selected employees to the department and update their department field.
        for (int i = 0; i < count; i++) {
            Employee employee = unassignedEmployees.get(i);
            employee.setDepartment(department.getName());
            department.addEmployee(employee);
            System.out.println(employee.getName());
        }
    }

    private static void handleEmployeeActivities(Employee employee, List<Patient> patients) {
        // This method manages the activities available to general employees (Doctors, Nurses).
        // The tasks are limited to viewing department-specific information to avoid unauthorized access to other departments.

        if (employee.getDepartment() == null) {
            // Employees without an assigned department are advised to contact a manager.
            System.out.println("You haven't been assigned a department today. Contact a manager.");
            return;
        }

        System.out.println("Your department: " + employee.getDepartment());

        if (employee instanceof Doctor || employee instanceof Nurse || employee instanceof Surgeon) {
            // Only Doctors, Nurses and Surgeons can view patients within their department.
            System.out.println("Patients in your department:");
            for (Patient patient : patients) {
                if("Admitted".equals(patient.getStatus())) {
                    if (((Admitted) patient).getDepartmentAssigned().equalsIgnoreCase(employee.getDepartment())) {
                        System.out.println(patient.getName());
                    }
                }
         
            }
        }
    }

    // Helper method to retrieve unassigned employees of a specified role.
    private static List<Employee> getUnassignedEmployees(List<Employee> employees, String roleType) {
        // This method filters employees based on role and department assignment.
        // It utilizes streams for readability and efficiency, allowing us to easily identify employees without a department.
        return employees.stream()
            .filter(emp -> emp.getRole().equalsIgnoreCase(roleType) && emp.getDepartment() == null)
            .collect(Collectors.toList());
    }

    // Helper method to map a manager's department name to the corresponding Department object.
    private static Department getDepartment(Manager manager, Department surgery, Department psychiatry, Department emergencyRoom) {
        // This method returns the correct Department object based on the manager's assigned department name.
        // Using a switch statement here provides flexibility for future department additions or changes.
        Department department = new Department("");
        switch(manager.getDepartment()) {
            case "Surgery":
                return surgery;
            case "Psychiatry":
                return psychiatry;
            case "Emergency Room":
                return emergencyRoom;
            default:
                return department;
        } 
    }
}
