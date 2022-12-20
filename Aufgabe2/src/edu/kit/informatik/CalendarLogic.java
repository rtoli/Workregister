package edu.kit.informatik;

import java.util.List;
import java.util.Map;

/**
 * This class contains the logical operations and the different checks required for the work calendar.
 * @author Rezart Toli
 * @version 1.0
 */
public class CalendarLogic {
    
    /**
     * This method checks if the time frame given is valid.
     * @param start starting time
     * @param end ending time
     * @return whether the time frame of the work is valid
     */
    public boolean validTimeFrame(Date start, Date end) {
        if (start.equalDateAndTime(end) || !validDayDifference(start, end)) {
            return false;
        }
        if (start.sameDateAs(end)) {
            if (start.getHour() > end.getHour() || start.getHour() == end.getHour() 
                    && start.getMinute() > end.getMinute()) {
                return false;
            }
        }
        if (!start.isValidDate() || !end.isValidDate()) {
            return false;
        }
        return true;
    }
    
    /**
     * This method checks if there is a reasonable day difference during the working time.
     * @param start starting time
     * @param end ending time
     * @return if there is a valid day difference for the given work time
     */
    private boolean validDayDifference(Date start, Date end) {
        if (start.getYear() + 1 == end.getYear() && start.getMonth() == 12 && end.getMonth() == 1
                && start.getDay() == 31 && end.getDay() == 1) {
            return true;
        }
        if (start.getMonth() + 1 == end.getMonth() && end.getDay() == 1) {
            if (start.getMonth() == 2) {
                if (TimeUtility.leapYear(start.getYear()) && start.getDay() == 29) {
                    return true;
                } else if (!TimeUtility.leapYear(start.getYear()) && start.getDay() == 28) {
                    return true;
                }
            } else if (start.getMonth() <= 7 && start.getMonth() % 2 == 1 
                    || start.getMonth() > 7 && start.getMonth() % 2 == 0 && start.getDay() == 31) {
                return true;
            } else if (start.getDay() == 30) {
                return true;
            }
        } else if (start.getDay() + 1 == end.getDay()) {
            return true;
        } else if (start.sameDateAs(end)) {
            return true;
        }
        return false;
    }
    
    /**
     * This method checks if the pause time is given during the working time frame.
     * @param startA the start of the working time
     * @param endA the end of the working time
     * @param startP the start of the pause time
     * @param endP the end of the pause time
     * @return true if the pause time resides within the working time
     */
    public boolean validWorkPauseCycle(Date startA, Date endA, Date startP, Date endP) {
        if (startA.sameDateAs(endA)) {
            if (startP.sameDateAs(startA) && endP.sameDateAs(startA)) {
                if (TimeUtility.clockToMinutes(startP.getHour(), startP.getMinute()) 
                        >= TimeUtility.clockToMinutes(startA.getHour(), startA.getMinute())
                        && TimeUtility.clockToMinutes(endP.getHour(), endP.getMinute()) 
                        <= TimeUtility.clockToMinutes(endA.getHour(), endA.getMinute())) {
                    return true;
                }
            }
        } else if (startP.sameDateAs(startA) && endP.sameDateAs(startA)) {
            if (TimeUtility.clockToMinutes(startP.getHour(), startP.getMinute()) 
                    >= TimeUtility.clockToMinutes(startA.getHour(), startA.getMinute())) {
                return true;
            }
        } else if (startP.sameDateAs(startA) && endP.sameDateAs(endA)) {
            if (TimeUtility.clockToMinutes(startP.getHour(), startP.getMinute()) 
                    >= TimeUtility.clockToMinutes(startA.getHour(), startA.getMinute()) 
                    && TimeUtility.clockToMinutes(endP.getHour(), endP.getMinute()) 
                    <= TimeUtility.clockToMinutes(endA.getHour(), endA.getMinute())) {
                return true;
            }
        } else if (startP.sameDateAs(endA)) {
            if (TimeUtility.clockToMinutes(startP.getHour(), startP.getMinute()) 
                    < TimeUtility.clockToMinutes(endA.getHour(), endA.getMinute()) 
                    && TimeUtility.clockToMinutes(endP.getHour(), endP.getMinute())
                    <= TimeUtility.clockToMinutes(endA.getHour(), endA.getMinute())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean pauseInTime(Date start, Date startp) {
        if(convertToMinutes(start, startp) < 360) {
            return true;
        }
        return false;
    }
    
    /**
     * This method converts a given time frame to minutes.
     * @param start starting time
     * @param end ending time
     * @return the time that has passed between the start and the end converted to minutes
     */
    public int convertToMinutes(Date start, Date end) {
        if (start.getMinute() > end.getMinute()) {
            if (start.getHour() > end.getHour()) {
                return ((24 - start.getHour()) + end.getHour()) * 60 - (start.getMinute() - end.getMinute());
            } else {
                return (int) Math.abs(end.getHour() - start.getHour()) * 60 - (start.getMinute() - end.getMinute());
            }
        } else {
            if (start.getHour() > end.getHour()) {
                return ((24 - start.getHour()) + end.getHour()) * 60 + (end.getMinute() - start.getMinute());
            } else {
                return (end.getHour() - start.getHour()) * 60 + (end.getMinute() - start.getMinute());
            }
        }
    }
    
    /**
     * This method checks if the work being done is night work.
     * @param start starting time
     * @param end ending time
     * @param restTime how much rest the employee has given in minutes
     * @return
     */
    public boolean isNightWork(Date start, Date end, int restTime) {
        if (start.getHour() == 23 || start.getHour() >= 0 && start.getHour() < 4
                || start.getHour() == 4 && start.getMinute() == 0) {
            return convertToMinutes(start, end) - restTime >= 120;
        }
        return false;
    }
    
    /**
     * This method checks if the work being done is holiday work.
     * @param startA starting time
     * @param endA ending time
     * @param holidays a list of holidays
     * @return true if the work is holiday work or false if it isn't
     */
    public boolean isHolidayWork(Date startA, Date endA, List<Date> holidays) {
        if (holidays.isEmpty()) {
            return false;
        }
        for (Date d : holidays) {
            if (startA.sameDateAs(d) || endA.sameDateAs(d) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the work being done is Sunday work.
     * @param startA starting time
     * @param endA ending time
     * @return true if the work is Sunday work, false otherwise
     */
    public boolean isSundayWork(Date startA, Date endA) {
        if (startA.getWeekDay() == 0 || endA.getWeekDay() == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * This method checks if there is enough resting time between work times.
     * @param start the start of the work
     * @param end the end of the work
     * @param dailyWork a map that has the path to the day worked
     * @return whether the required resting time is given or not
     */
    public boolean enoughRestTime(Date start, Date end, 
            Map<Integer, Map<Integer, Map<Integer, WorkDay>>> dailyWork) {
        int timeDifference = 0;
        WorkDay wd;
        WorkDay wd2;
        if (dailyWork.isEmpty()) {
            return true;
        } else if (!dailyWork.containsKey(start.getYear())) {
            return true;
        } else if (dailyWork.containsKey(start.getYear()) 
                && !dailyWork.get(start.getYear()).containsKey(start.getMonth())) {
            return true;
        }
        if (dailyWork.get(start.getYear()).get(
                start.getMonth()).containsKey(start.getDay())) {
            wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(start.getDay());
            if (wd.getStart().getHour() <= start.getHour()) {
                wd2 = new WorkDay(start, end, 0);
                timeDifference = dailyTimeDifference(wd, wd2);
                if (!validTimeDifference(timeDifference, start, dailyWork)) {
                    return false;
                }
                if (dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(start.getDay() + 1)) {
                    wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(start.getDay() + 1);
                    wd2 = new WorkDay(start, end, 0);
                    timeDifference = dailyTimeDifference(wd, wd2);
                    if (!validTimeDifference(timeDifference, start, dailyWork)) {
                        return false;
                    }
                }
            } else if (wd.getStart().getHour() > start.getHour()) {
                wd2 = new WorkDay(start, end, 0);
                timeDifference = dailyTimeDifference(wd, wd2);
                
                if (!validTimeDifference(timeDifference, start, dailyWork)) {
                    return false;
                }
                if (dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(start.getDay() - 1)) {
                    wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(start.getDay() - 1);
                    wd2 = new WorkDay(start, end, 0);
                    timeDifference = dailyTimeDifference(wd, wd2);
                    if (!validTimeDifference(timeDifference, start, dailyWork)) {
                        return false;
                    }
                }
            }
        } else {
            if (dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(start.getDay() - 1)) {
                wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(start.getDay() - 1);
                wd2 = new WorkDay(start, end, 0);
                timeDifference = dailyTimeDifference(wd, wd2);
                if (!validTimeDifference(timeDifference, start, dailyWork)) {
                    return false;
                }
            }
            if (dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(start.getDay() + 1)) {
                wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(start.getDay() + 1);
                wd2 = new WorkDay(start, end, 0);
                timeDifference = dailyTimeDifference(wd, wd2);
                if (!validTimeDifference(timeDifference, start, dailyWork)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This method calculates the difference between the time when work ends and
     * the time when the employee has to start working again the other day.
     * @param workDay first work day
     * @param workDay2 second work day
     * @return the time difference
     */
    private int dailyTimeDifference(WorkDay workDay, WorkDay workDay2) {
        int timeDifference = 0;
        if (workDay.getStart().getDay() < workDay2.getStart().getDay()) {
            if (workDay.getEnd().getDay() == workDay2.getStart().getDay()) {
                timeDifference 
                    = TimeUtility.clockToMinutes(workDay2.getStart().getHour(), workDay2.getStart().getMinute())
                        - TimeUtility.clockToMinutes(workDay.getEnd().getHour(),  workDay.getEnd().getMinute());
                if (timeDifference < 0) {
                    return 0;
                } else {
                    return timeDifference;
                }
            } else {
                timeDifference 
                    = TimeUtility.clockToMinutes(24 - workDay.getEnd().getHour(), workDay.getEnd().getMinute())
                        + TimeUtility.clockToMinutes(workDay2.getStart().getHour(), workDay2.getStart().getMinute());
                return timeDifference;
            }
        } else if (workDay.getStart().getDay() > workDay2.getStart().getDay()) {
            if (workDay.getStart().getDay() == workDay2.getEnd().getDay()) {
                timeDifference 
                    = TimeUtility.clockToMinutes(workDay.getStart().getHour(), workDay.getStart().getMinute())
                        - TimeUtility.clockToMinutes(workDay2.getEnd().getHour(), workDay2.getEnd().getMinute());
                if (timeDifference < 0) {
                    return 0;
                } else {
                    return timeDifference;
                }
            }
        } else if (workDay.getStart().getHour() > workDay2.getEnd().getHour()) {
            timeDifference = convertToMinutes(workDay2.getEnd(), workDay.getStart());
            return timeDifference;
        } else if (workDay2.getStart().getHour() > workDay.getEnd().getHour()) {
            timeDifference = convertToMinutes(workDay.getEnd(), workDay2.getStart());
            return timeDifference;
        }
        return 0;
    }
    
    /**
     * This method checks if the time difference between the two working dates is valid.
     * @param timeDifference
     * @param start
     * @param dailyWork a map that has the path to the day worked
     * @return true if there has been enough resting time allocated
     */
    private boolean validTimeDifference(int tDifference, Date start,
            Map<Integer, Map<Integer, Map<Integer, WorkDay>>> dailyWork) {
        int timeDifference = tDifference;
        WorkDay wd;
        WorkDay wd2;
        int tenH = 0;
        int twelveH = 0;
        if (timeDifference >= 600 && timeDifference < 660) {
            int y = 0;
            int monthDays = TimeUtility.daysInAMonth(start.getMonth());
            for (int x = 1; x <= monthDays; x++) {
                y = x + 1;
                if (dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(x)
                        && dailyWork.get(start.getYear()).get(start.getMonth()).containsKey(y)) {
                    wd = dailyWork.get(start.getYear()).get(start.getMonth()).get(x);
                    wd2 = dailyWork.get(start.getYear()).get(start.getMonth()).get(y);
                    timeDifference = dailyTimeDifference(wd, wd2);
                    if (timeDifference >= 600 && timeDifference < 600) {
                        tenH++;
                    } else if (timeDifference >= 720) {
                        twelveH++;
                    }
                    if (twelveH > tenH) {
                        return true;
                    }
                }
            }
        } else if (timeDifference >= 660) {
            return true;
        }
        return false;
    }
}