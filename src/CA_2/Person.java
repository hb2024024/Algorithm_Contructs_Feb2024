package CA_2;

/**
 * The Person class serves as a base class for both employees, manager and patients.
 * It provides basic attributes, name and ID, that are shared among all 
 * individuals in the hospital system.
 */
public class Person {
    protected String name; // Name of the person
    protected int id; // Unique identifier for the person

    /**
     * Constructor for Person.
     * Initializes the basic attributes for a person, used by its subclasses.
     *
     * @param name - the name of the person
     * @param id - the ID of the person
     */
    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Getter for the name of the person
    public String getName() {
        return name;
    }

    // Getter for the ID of the person
    public int getId() {
        return id;
    }
}
