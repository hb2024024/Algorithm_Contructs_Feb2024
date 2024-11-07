/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

/**
 *
 * @author Henrique
 */
public class Employee {
    String name;
    int id;
    String department;
    String role;
    
    Employee(String name, int id, String department, String role) {
        this.name = name;
        this.id = id;
        this.department = department;
        this.role = role;
    }
    
    // Gettters
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public String getRole() {
        return role;
    }
}

class Doctor extends Employee {
    public Doctor(String name, int id, String department, String role) {
        super(name, id, department, role);
    }
}

class Nurse extends Employee {
    public Nurse(String name, int id, String department, String role) {
        super(name, id, department, role);
    }
}

class Surgeon extends Employee {
    public Surgeon(String name, int id, String department, String role) {
        super(name, id, department, role);
    }
}

class Cleaner extends Employee {
    public Cleaner(String name, int id, String department, String role) {
        super(name, id, department, role);
    }
}
