/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

/**
 *
 * @author Henrique
 */
public class Department {
    String name;
    int id;

    Department(String name, int id) {
        this.name = name;
        this.id =  id;
    }
}

class SurgeryDepartment extends Department {
    int teamSize;
    
    SurgeryDepartment(String name, int id, int teamSize) {
        super(name, id);
        this.teamSize = teamSize;
    }
}

class PsychiatryDepartment extends Department {
    int teamSize;
    
    PsychiatryDepartment(String name, int id, int teamSize) {
        super(name, id);
        this.teamSize = teamSize;
    }
}

class EmergencyRoom extends Department {
    
    EmergencyRoom(String name, int id) {
        super(name, id);
    }
}
