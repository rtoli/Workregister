package edu.kit.informatik;

/**
 * This class contains the start and end of a working day as well as the time worked.
 * @author Rezart Toli
 * @version 1.0
 */
public class WorkDay {
    
    //The start of the work day 
    private Date start;
    //The end of the work day
    private Date end;
    //The time worked in minutes
    private int workingTime;
    
    /**
     * The constructor sets the start and the end as well as the working time.
     * @param s
     * @param e
     * @param workTime
     */
    public WorkDay(Date s, Date e, int workTime) {
        start = s;
        end = e;
        workingTime = workTime;
    }
    
    /**
     * This method checks whether a given point in time is contained within the work day.
     * @param timePoint a certain point in time
     * @return true if the point in time is between the starting time and end time of the work day
     */
    public boolean containsTimePoint(Date timePoint) {
        if (start.sameDateAs(timePoint) && start.sameDateAs(end)) {
            if (TimeUtility.clockToMinutes(start.getHour(), start.getMinute()) 
                    <= TimeUtility.clockToMinutes(timePoint.getHour(), timePoint.getMinute())
                    && TimeUtility.clockToMinutes(end.getHour(), end.getMinute()) 
                    >= TimeUtility.clockToMinutes(timePoint.getHour(), timePoint.getMinute())) {
                return true;
            }
        } else if (start.sameDateAs(timePoint)) {
            if (TimeUtility.clockToMinutes(start.getHour(), start.getMinute()) 
                    >= TimeUtility.clockToMinutes(timePoint.getHour(), timePoint.getMinute())) {
                return true;
            }
        } else if (end.sameDateAs(timePoint)) {
            if (TimeUtility.clockToMinutes(end.getHour(), end.getMinute()) 
                    <= TimeUtility.clockToMinutes(end.getHour(), end.getMinute())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Getter method for starting date.
     * @return starting date
     */
    public Date getStart() {
        return start;
    }

    /**
     * Getter method for ending date.
     * @return ending date
     */
    public Date getEnd() {
        return end;
    }
    
    /**
     * This method returns the time worked during the day in the desired format.
     */
    @Override
    public String toString() {
        int hours = workingTime / 60;
        int minutes = workingTime % 60;
        String output = String.format("%02d:%02d", hours, minutes);
        return output;
    }
}