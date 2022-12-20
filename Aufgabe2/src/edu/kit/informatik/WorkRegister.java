package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * The work register keeps track of different employees and their work calendars.
 * @author Rezart Toli
 * @version 1.0
 */
public class WorkRegister {
    //A list that stores all the holidays available.
    private List<Date> holidays = new ArrayList<Date>();
    //A list of employees.
    private List<Employee> employees = new ArrayList<Employee>();
    
    /**
     * The constructor reads the file which contains the holidays and checks if they're all given
     * in the correct format. If not they aren't added and a notification about it is given.
     */
    public WorkRegister() {
        String[] hDays = Terminal.readFile("WordList.txt");
        if (!correctHolidays(hDays)) {
            holidays.clear();
            Terminal.printError("holidays weren't added.");
        }
    }
    
    /**
     * This method adds a new employee.
     * @param role the working role of an employee
     * @param firstName the first name of an employee
     * @param lastName the last name of an employee
     * @param birthday the birthday of an employee
     */
    public void addEmployee(String role, String firstName, String lastName, String birthday) {
        if (role.matches("A|N|P|NP")) {
            Employee newEmployee = new Employee(role, firstName, lastName, birthday);
            employees.add(newEmployee);
            Terminal.printLine("Added new employee with identifier " + newEmployee.getId() + ".");
        } else {
            Terminal.printError("incorrect employee type.");
        }
    }
    
    /**
     * This method is used to assign work to a certain employee.
     * @param id the id of the employee
     * @param startA the starting time of the work
     * @param endA the ending time of the work
     * @param startP the starting time of the pause
     * @param endP the ending time of the pause
     */
    public void workTime(String id, String startA, String endA, String startP, String endP) {
        int iD = 0;
        try {
            iD = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            Terminal.printError("incorrect id.");
            return;
        }
        
        if (!DateFormat.correctDateAndTimeFormat(startA) || !DateFormat.correctDateAndTimeFormat(endA)
                || !DateFormat.correctDateAndTimeFormat(startP) || !DateFormat.correctDateAndTimeFormat(endP)) {
            Terminal.printError("date/dates not entered correctly.");
            return;
        }
        
        if (employees.isEmpty() || iD > employees.size() || iD <= 0) {
            Terminal.printError("employee doesn't exist.");
            return;
        }
        String output = employees.get(iD - 1).getCalendar().checkWorkTime(employees.get(iD - 1),
                startA, endA, startP, endP, holidays);
        Terminal.printLine(output);
    }
    
    /**
     * This method is used to assign work to a certain employee.
     * @param id the id of the employee
     * @param startA the starting time of the work
     * @param endA the ending time of the work
     */
    public void workTime(String id, String startA, String endA) {
        int iD = 0;
        try {
            iD = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            Terminal.printError("incorrect id.");
            return;
        }
        
        if (!DateFormat.correctDateAndTimeFormat(startA) || !DateFormat.correctDateAndTimeFormat(endA)) {
            Terminal.printError("date/dates not entered correctly.");
            return;
        }
        
        if (employees.isEmpty() || iD > employees.size() || iD <= 0) {
            Terminal.printError("employee doesn't exist.");
            return;
        }
        String output 
            = employees.get(iD - 1).getCalendar().checkWorkTime(employees.get(iD - 1), startA, endA, holidays);
        Terminal.printLine(output);
    }
    
    /**
     * This method prints all the days an employee has worked as long as the time he has worked for.
     * @param id the id of the employee
     */
    public void listEmployeeWork(int id) {
        if (employees.isEmpty() || id > employees.size() || id <= 0) {
            Terminal.printError("employee doesn't exist.");
            return;
        }
        String output = employees.get(id - 1).getCalendar().listWork();
        if (output.length() == 0) {
            Terminal.printLine("EMPTY");
        } else {
            Terminal.printLine(output);
        }
    }
    
    /**
     * This method prints all the employees that have worked at a certain point of time.
     * @param timePoint the point in time we're looking for
     */
    public void listEmployee(String timePoint) {
        String output = "";
        if (!DateFormat.correctDateAndTimeFormat(timePoint)) {
            Terminal.printError("incorrect time format.");
            return;
        }
        if (employees.isEmpty()) {
            return;
        }
        for (Employee e : employees) {
            if (e.getCalendar().containsTimePoint(timePoint)) {
                output += e.toString() + "\n";
            }
        }
        if (!(output.length() == 0)) {
            Terminal.printLine(output.substring(0, output.length() - 1));
        } else {
            Terminal.printLine("EMPTY");
        }
    }
    
    /**
     * This method checks if the holidays give are correct.
     * @param hDays an array of holidays
     * @return true if there is no problem with the holidays, false otherwise
     */
    private boolean correctHolidays(String[] hDays) {
        String[] dates;
        for (String h : hDays) {
            if (!DateFormat.correctDateFormat(h)) {
                return false;
            }
            dates = h.split("-");
            Date newDate = new Date(dates[0], dates[1], dates[2]);
            if (!newDate.isValidDate()) {
                return false;
            } else {
                holidays.add(newDate);
            }
        }
        return true;
    }
}