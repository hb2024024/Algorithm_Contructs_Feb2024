/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Henrique
 */
public class PersonalActivities {
    public static void personalActivities(List<Employee> employees, List<Patient> patients, Scanner scanner) {
    System.out.println("Log in with:");
    System.out.println("1. Name");
    System.out.println("2. ID");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    Employee employee = null;

    if (choice == 1) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        // !!!!!!!!!!!!!!!!!!!1 ask the professor if i can use stream
        employee = employees.stream()
                            .filter(e -> e.getName().equalsIgnoreCase(name))
                            .findFirst().orElse(null);
    } else if (choice == 2) {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        employee = employees.stream()
                            .filter(e -> e.getId() == id)
                            .findFirst().orElse(null);
    }

    if (employee != null) {
        System.out.println("Welcome " + employee.getName() + ". Here are your activities.");
    } else {
        System.out.println("Employee not found.");
    }
}

}
