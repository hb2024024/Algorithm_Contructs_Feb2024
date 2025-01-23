/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA_2;

/**
 *
 * @author Henrique
 */
public class Manager extends Person {
    private String department;

    public Manager(String name, int id, String department) {
        super(name, id);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
