package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the calendar of an employee which is used to organize the work to be done by him as well as 
 * keep track of it and get back information about the work he has performed.
 * @author USERONE
 * @version 1.0
 */
public class Calendar {
    private CalendarLogic cLogic = new CalendarLogic();
    private int workTime;
    private int pauseTime;
    private List<WorkDay> workDay = new ArrayList<WorkDay>();
    private Map<WorkDay, ArrayList<String>> workTimeAndDay = new HashMap<WorkDay, ArrayList<String>>();
    //private Map<Integer, Map<Integer, Integer>> monthlyWork = new HashMap<Integer, Map<Integer, Integer>>();
    private Map<Integer, Map<Integer, Map<Integer, WorkDay>>> dailyWork 
            = new HashMap<Integer, Map<Integer, Map<Integer, WorkDay>>>();
    
    /**
     * This method checks if the employee is eligible to be given the specified work.
     * @param employee the employee whose calendar we're working with
     * @param startA the starting time of the work
     * @param endA the ending time of the work
     * @param startP the starting time of the pause
     * @param endP the ending time of the pause
     * @param holidays the list of holidays if there are any
     * @return how many hours of work the employee has done or an error message if work couldn't be assigned
     */
    public String checkWorkTime(Employee employee, String startA, String endA, String startP, String endP,
            List<Date> holidays) {
        String[] sA = startA.split("T");
        String[] eA = endA.split("T");
        String[] sP = startP.split("T");
        String[] eP = endP.split("T");
        String[] date1 = sA[0].split("-");
        String[] time1 = sA[1].split(":");
        String[] date2 = eA[0].split("-");
        String[] time2 = eA[1].split(":");
        String[] date3 = sP[0].split("-");
        String[] time3 = sP[1].split(":");
        String[] date4 = eP[0].split("-");
        String[] time4 = eP[1].split(":");
        Date workStart;
        Date workEnd;
        Date pauseStart;
        Date pauseEnd;
        try {
            workStart = new Date(date1[0], date1[1], date1[2], time1[0], time1[1]);
            workEnd = new Date(date2[0], date2[1], date2[2], time2[0], time2[1]);
            pauseStart = new Date(date3[0], date3[1], date3[2], time3[0], time3[1]);
            pauseEnd = new Date(date4[0], date4[1], date4[2], time4[0], time4[1]);
        } catch (NumberFormatException e) {
            return "Error, date/dates not entered correctly.";
        }
        if (!cLogic.validTimeFrame(workStart, workEnd)) {
            return "Error, invalid time frame given.";
        }
        if (!cLogic.validWorkPauseCycle(workStart, workEnd, pauseStart, pauseEnd)) {
            return "Error, invalid pause time given.";
        }
        setWorkAndPauseTime(workStart, workEnd, pauseStart, pauseEnd);
        WorkDay wDay = new WorkDay(workStart, workEnd, workTime);
        if (workTime > 360 && workTime <= 540 && pauseTime < 30) {
            return "Error, pause time must be 30 minutes or more.";
        } else if (workTime > 540 && workTime <= 600 && pauseTime < 45) {
            return "Error, pause time must be 45 minutes or more.";
        } else if (workTime > 600) {
            return "Error, employees can't work for more than 10 hours";
        } else if (!cLogic.pauseInTime(workStart, pauseStart)) {
            return "Error, can't work 6 consecutive hours without pause";
        }
        if (cLogic.isNightWork(workStart, workEnd, cLogic.convertToMinutes(pauseStart, pauseEnd)) && employee.getEmployeeRole().matches("A|P")) {
            return "Error, only night workers can work during the night";
        }
        if ((cLogic.isSundayWork(workStart, workEnd) || cLogic.isHolidayWork(workStart, workEnd, holidays)) 
                && employee.getEmployeeRole().matches("A|N")) {
            return "Error, this work can only be assigned to production workers.";
        }
        if (!cLogic.enoughRestTime(workStart, workEnd, dailyWork)) {
            return "Error, not enough rest between working times";
        } else {
            Map<Integer, WorkDay> map1 = new HashMap<Integer, WorkDay>();
            Map<Integer, Map<Integer, WorkDay>> map2 = new HashMap<Integer, Map<Integer, WorkDay>>();
            map1.put(workStart.getDay(), wDay);
            map2.put(workStart.getMonth(), map1);
            dailyWork.put(workStart.getYear(), map2);
        }
        addDetail(wDay, startA, endA, startP, endP);
        workDay.add(wDay);
        return "Recorded working time " + wDay.toString() + " for employee " + employee.getId() + ".";
    }
    
    /**
     * This method checks if the employee is eligible to be given the specified work.
     * @param employee the employee whose calendar we're working with
     * @param startA the starting time of the work
     * @param endA the ending time of the work
     * @param holidays the list of holidays if there are any
     * @return how many hours of work the employee has done or an error message if work couldn't be assigned
     */
    public String checkWorkTime(Employee employee, String startA, String endA, List<Date> holidays) {
        String[] sA = startA.split("T");
        String[] eA = endA.split("T");
        String[] date1 = sA[0].split("-");
        String[] time1 = sA[1].split(":");
        String[] date2 = eA[0].split("-");
        String[] time2 = eA[1].split(":");
        Date workStart;
        Date workEnd;
        try {
            workStart = new Date(date1[0], date1[1], date1[2], time1[0], time1[1]);
            workEnd = new Date(date2[0], date2[1], date2[2], time2[0], time2[1]);
        } catch (NumberFormatException e) {
            return "Error, date/dates not entered correctly.";
        }
        if (!cLogic.validTimeFrame(workStart, workEnd)) {
            return "Error, invalid time frame given.";
        }
        setWorkTime(workStart, workEnd);
        WorkDay wDay = new WorkDay(workStart, workEnd, workTime);
        
        if (workTime > 360 && workTime <= 540 && pauseTime < 30) {
            return "Error, pause time must be 30 minutes or more.";
        } else if (workTime > 540 && workTime <= 600 && pauseTime < 45) {
            return "Error, pause time must be 45 minutes or more.";
        } else if (workTime > 600) {
            return "Error, employees can't work for more than 10 hours";
        }
        if (cLogic.isNightWork(workStart, workEnd, 0) && employee.getEmployeeRole().matches("A|P")) {
            return "Error, only night workers can work during the night";
        }
        if ((cLogic.isSundayWork(workStart, workEnd) || cLogic.isHolidayWork(workStart, workEnd, holidays)) 
                && employee.getEmployeeRole().matches("A|N")) {
            return "Error, this work can only be assigned to production workers.";
        }
        if (!cLogic.enoughRestTime(workStart, workEnd, dailyWork)) {
            return "Error, not enough rest between working times";
        } else {
            Map<Integer, WorkDay> map1 = new HashMap<Integer, WorkDay>();
            Map<Integer, Map<Integer, WorkDay>> map2 = new HashMap<Integer, Map<Integer, WorkDay>>();
            map1.put(workStart.getDay(), wDay);
            map2.put(workStart.getMonth(), map1);
            dailyWork.put(workStart.getYear(), map2);
        }
        addDetail(wDay, startA, endA);
        workDay.add(new WorkDay(workStart, workEnd, workTime));
        return "Recorded working time " + wDay.toString() + " for employee " + employee.getId() + ".";
    }
    
    /**
     * This method lists all the work an employee has done.
     * @return a list with all the times the employee has worked along with how many hours he worked for
     */
    public String listWork() {
        String output = "";
        Set<Map.Entry<WorkDay, ArrayList<String>>> entries = workTimeAndDay.entrySet();
        Comparator<Map.Entry<WorkDay, ArrayList<String>>> valueComparator 
            = new Comparator<Map.Entry<WorkDay, ArrayList<String>>>() {
                @Override
                public int compare(Map.Entry<WorkDay, ArrayList<String>> e, Map.Entry<WorkDay, ArrayList<String>> e2) {
                    ArrayList<String> v1 = e.getValue();
                    ArrayList<String> v2 = e2.getValue();
                    return v1.get(0).compareTo(v2.get(0));
                }
            };
        List<Map.Entry<WorkDay, ArrayList<String>>> listOfEntries 
            = new ArrayList<Map.Entry<WorkDay, ArrayList<String>>>(entries);
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<WorkDay, ArrayList<String>> sortedByValue 
            = new LinkedHashMap<WorkDay, ArrayList<String>>(listOfEntries.size());
        for (Map.Entry<WorkDay, ArrayList<String>> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        Set<Map.Entry<WorkDay, ArrayList<String>>> entrySetSortedByValue = sortedByValue.entrySet();
        for (Map.Entry<WorkDay, ArrayList<String>> mapping : entrySetSortedByValue) {
            output += 
                  mapping.getKey().toString() + " " + mapping.getValue().toString().replaceAll("[\\[\\],]", "") + "\n";
        }
        if (output.length() == 0) {
            return output;
        }
        return output.substring(0, output.length() - 1);
    }
    
    /**
     * This method checks if the employee was working during a given point in time.
     * @param timePoint the time that we want to check
     * @return if the employee was working at this point in time
     */
    public boolean containsTimePoint(String timePoint) {
        boolean output = false;
        String[] tP = timePoint.split("T");
        String[] date = tP[0].split("-");
        String[] time = tP[1].split(":");
        Date tPoint = new Date(date[0], date[1], date[2], time[0], time[1]);
        for (WorkDay wd : workDay) {
            if (wd.containsTimePoint(tPoint)) {
                output = true;
            }
        }
        return output;
    }
    
    /**
     * This method sets the work and pause time for a given day.
     * @param starta the time when work starts
     * @param enda the time when work ends
     * @param startp the time when the pause starts
     * @param endp the time when the pause ends
     */
    private void setWorkAndPauseTime(Date starta, Date enda, Date startp, Date endp) {
        int work = cLogic.convertToMinutes(starta, enda);
        int pause = cLogic.convertToMinutes(startp, endp);
        pauseTime = pause;
        workTime = work - pause;
    }
    
    /**
     * This method sets the work and pause time for a given day.
     * @param starta the time when work starts
     * @param enda the time when work ends
     */
    private void setWorkTime(Date starta, Date enda) {
        workTime = cLogic.convertToMinutes(starta, enda);
    }
    
    /**
     * This method adds the time frames for the working and pause time to a list as strings and then stores this list 
     * to a map with the amount of worked hours as key.
     * @param wDay class that stores the beginning and end of the work day as well as the working time for that day
     * @param startA the start of the working time
     * @param endA the end of the working time
     * @param startP the start of the pause time
     * @param endP the end of the pause time
     */
    private void addDetail(WorkDay wDay, String startA, String endA, String startP, String endP) {
        ArrayList<String> details = new ArrayList<String>();
        details.add(startA);
        details.add(endA);
        details.add(startP);
        details.add(endP);
        workTimeAndDay.put(wDay, details);
    }
    
    /**
     * This method adds the time frames for the working time to a list as strings and then stores this list 
     * to a map with the amount of worked hours as key.
     * @param wDay
     * @param startA
     * @param endA
     */
    private void addDetail(WorkDay wDay, String startA, String endA) {
        ArrayList<String> details = new ArrayList<String>();
        details.add(startA);
        details.add(endA);
        workTimeAndDay.put(wDay, details);
    }
}