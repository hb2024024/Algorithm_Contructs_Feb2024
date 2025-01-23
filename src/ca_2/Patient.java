package CA_2;

/**
 * The Patient class represents a patient in the hospital, with attributes
 * for admission date and status. It serves as the base class for different
 * patient states (e.g., admitted, released, deceased).
 */
public class Patient extends Person {
    private String admissionDate; // Date of admission to the hospital
    private String status; // Current status of the patient

    /**
     * Constructor for Patient.
     * Sets the name, ID, admission date, and status of the patient.
     *
     * @param name - the name of the patient
     * @param id - the ID of the patient
     * @param admissionDate - the date of admission
     * @param status - the current status of the patient
     */
    public Patient(String name, int id, String admissionDate, String status) {
        super(name, id);
        this.admissionDate = admissionDate;
        this.status = status;
    }

    // Getter for the patient's admission date
    public String getAdmissionDate() {
        return admissionDate;
    }

    // Getter for the patient's status
    public String getStatus() {
        return status;
    }

    // Method to get department assignment, overridden in specific subclasses if applicable
    public String getDepartment() {
        return null; // Default for patients without department assignment such as deceased and released
    }
}

/**
 * The Deceased class extends Patient to represent patients who have passed away.
 * It includes an additional field for the date of death, as this is relevant only
 * for patients who are deceased.
 */
class Deceased extends Patient {
    private String dateOfDeath; // Date of death

    public Deceased(String name, int id, String admissionDate, String dateOfDeath) {
        super(name, id, admissionDate, "Deceased");
        this.dateOfDeath = dateOfDeath;
    }

    // Getter for the date of death
    public String getDateOfDeath() {
        return dateOfDeath;
    }
}

/**
 * The Released class extends Patient to represent patients who have been released.
 * It includes a release date, using this information specifically for patients
 * who are no longer admitted.
 */
class Released extends Patient {
    private String releaseDate; // Date of release

    public Released(String name, int id, String admissionDate, String releaseDate) {
        super(name, id, admissionDate, "Released");
        this.releaseDate = releaseDate;
    }

    // Getter for the release date
    public String getReleaseDate() {
        return releaseDate;
    }
}

/**
 * The Admitted class extends Patient to represent patients still in the hospital.
 * It includes a department assignment to facilitate department-specific activities
 * for admitted patients.
 */
class Admitted extends Patient {
    private String departmentAssigned; // Department assigned to the admitted patient

    public Admitted(String name, int id, String admissionDate, String departmentAssigned) {
        super(name, id, admissionDate, "Admitted");
        this.departmentAssigned = departmentAssigned;
    }

    public String getDepartmentAssigned() {
        return departmentAssigned;
    }
}
