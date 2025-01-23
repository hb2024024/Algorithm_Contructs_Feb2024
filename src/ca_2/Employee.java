/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA_2;

/**
 * The Employee class represents a hospital employee with a name, ID, role,
 * and department. It serves as the base class for specific employee types
 * such as doctors, nurses, surgeons, cleaners, and managers.
 */
public class Employee extends Person {
    protected String role; // Role of the employee, e.g., Doctor, Nurse
    protected String department; // Department where the employee is assigned

    /**
     * Constructor for Employee.
     * Sets the basic attributes for an employee: name, ID, and role.
     *
     * @param name the name of the employee
     * @param id the ID of the employee
     * @param role the role of the employee
     */
    public Employee(String name, int id, String role) {
        super(name, id);
        this.role = role;
    }

    // Getter for the employee's role
    public String getRole() {
        return role;
    }

    // Getter for the employee's department
    public String getDepartment() {
        return department;
    }

    // Setter for the employee's department
    public void setDepartment(String department) {
        this.department = department;
    }
}

/**
 * The Doctor class extends Employee to represent a specific type of employee
 * with the role "Doctor". It includes department assignment, facilitating 
 * department-specific activities for doctors.
 */
class Doctor extends Employee {
    public Doctor(String name, int id, String department) {
        super(name, id, "Doctor");
        this.department = department;
    }
}

// Similar comments apply for Nurse, Surgeon, Cleaner, and Manager classes.
// Each subclass specifies the role and optionally includes department-related
// responsibilities as needed.


// Nurse subclass with department
class Nurse extends Employee {

    public Nurse(String name, int id, String department) {
        super(name, id, "Nurse");
        this.department = department;
    }

}

// Surgeon subclass with department
class Surgeon extends Employee {

    public Surgeon(String name, int id, String department) {
        super(name, id, "Surgeon");
        this.department = department;
    }

}

// Cleaner subclass with department
class Cleaner extends Employee {

    public Cleaner(String name, int id, String department) {
        super(name, id, "Cleaner");
        this.department = department;
    }

}

// Manager subclass with department
class Manager extends Employee {

    public Manager(String name, int id, String department) {
        super(name, id, "Manager");
        this.department = department;
    }

}
