package edu.kit.informatik;

/**
 * This class has some basic time functions that other classes use.
 * @author USERONE
 * @version 1.0
 */
public final class TimeUtility {
    
    /**
     * This method checks if the year is a leap year or not.
     * @param year
     * @return whether the given year is a leap year
     */
    public static boolean leapYear(int year) {
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * this method converts a given time point to minutes
     * @param hour
     * @param minute
     * @return time converted to minutes
     */
    public static int clockToMinutes(int hour, int minute) {
        return hour * 60 + minute;
    }
    
    /**
     * This method takes a month and gives back how many days are in it.
     * @param month the month we want to know about
     * @return the number of days in the month
     */
    public static int daysInAMonth(int month) {
        int days = 31;
        if (month == 2 && TimeUtility.leapYear(month)) {
            days = 29;
            return days;
        } else if (month == 2 && !TimeUtility.leapYear(month)) {
            days = 28;
            return days;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
            return days;
        }
        return days;
    }
}