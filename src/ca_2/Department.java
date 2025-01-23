package CA_2;

import java.util.ArrayList;
import java.util.List;

/**
 * The Department class represents a department within the hospital.
 * Each department has a name, a list of employees, and a list of patients.
 * This class is essential for organizing the hospital's structure, allowing
 * employees and patients to be grouped by department.
 */
public class Department {
    private String name; // The name of the department
    private List<Employee> employees; // List of employees in the department
    private List<Patient> patients; // List of patients assigned to the department

    /**
     * Constructor for Department.
     * Initializes a department with the specified name and initializes empty lists
     * for employees and patients to be added as needed.
     * 
     * @param name - the name of the department
     */
    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    // Getter for the department name
    public String getName() {
        return name;
    }

    // Getter for the list of employees in this department
    public List<Employee> getEmployees() {
        return employees;
    }

    // Getter for the list of patients in this department
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Adds an employee to this department.
     * This method is used to associate employees with their specific departments.
     *
     * @param employee - the employee to add to the department
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Adds a patient to this department.
     * This method allows the assignment of patients to a specific department.
     *
     * @param patient - the patient to add to the department
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }
}
