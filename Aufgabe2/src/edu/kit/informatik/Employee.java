package edu.kit.informatik;

/**
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public class Employee {
    private static int counter;
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String employeeRole;
    private Calendar calendar = new Calendar();
    
    /**
     * The constructor assigns the employees variables such as name, birthday, role and id. 
     * @param role the employees role
     * @param fName the employees first name
     * @param lName the employees last name
     * @param bday the employees birthday
     */
    public Employee(String role, String fName, String lName, String bday) {
        counter++;
        id = counter;
        employeeRole = role;
        firstName = fName;
        lastName = lName;
        birthday = bday;
    }

    /**
     * Getter method for the id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for the calendar.
     * @return calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Getter method for the employee role.
     * @return employee role
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /**
     * This method returns information about the employee.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + birthday + " " + employeeRole; 
    }
}