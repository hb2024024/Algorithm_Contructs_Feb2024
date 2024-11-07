/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

import java.util.List;

/**
 *
 * @author Henrique
 */

public class Patient {
    private String name;
    private int id;
    private String admissionDate;
    private String status;

    public Patient(String name, int id, String admissionDate, String status) {
        this.name = name;
        this.id = id;
        this.admissionDate = admissionDate;
        this.status = status;
    }
    
    // Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getStatus() {
        return status;
    }
}

class Deceased extends Patient {
    private String dateOfDeath;

    public Deceased(String name, int id, String admissionDate, String dateOfDeath) {
        super(name, id, admissionDate, "Deceased");
        this.dateOfDeath = dateOfDeath;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

}

class Released extends Patient {
    private String releaseDate;
    
    public Released(String name, int id, String admissionDate, String releaseDate) {
        super(name, id, admissionDate, "Released");
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    
}

class Admitted extends Patient {
    String departmentAssigned;

    public Admitted(String name, int id, String admissionDate, String departmentAssigned) {
        super(name, id, admissionDate, "Admitted");
        this.departmentAssigned = departmentAssigned;
    }

    public String getDepartmentAssigned() {
        return departmentAssigned;
    }

}
