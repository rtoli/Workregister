package edu.kit.informatik;

/**
 * This class holds all the information required for a date including a specific time if necessary.
 * @author Rezart Toli
 * @version 1.0
 */
public class Date {
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    
    /**
     * The constructor assigns the date and time to the respective variables
     * @param y the year
     * @param m the month
     * @param d the day
     * @param h the hour
     * @param min the minute
     */
    public Date(String y, String m, String d, String h, String min) {
        year = Integer.parseInt(y);
        month = Integer.parseInt(m);
        day = Integer.parseInt(d);
        hour = Integer.parseInt(h);
        minute = Integer.parseInt(min);
        if (hour == 24) {
            day++;
            hour = 0;
            if (day == 31 && month == 12) {
                year++;
                month = 1;
                day = 1;
            } else if ((month == 2 && TimeUtility.leapYear(year) && day == 29) 
                    || (month == 2 && !TimeUtility.leapYear(year) && day == 28)) {
                month++;
                day = 1;
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 30) {
                month++;
                day = 1;
            } else if (((month % 2 == 1 && month <= 7) || month == 8 || month == 10) && day == 31) {
                month++;
                day = 1;
            }
        }
    }
    
    /**
     * This constructor assigns the date to the respective variables.
     * @param y
     * @param m
     * @param d
     */
    public Date(String y, String m, String d) {
        year = Integer.parseInt(y);
        month = Integer.parseInt(m);
        day = Integer.parseInt(d);
    }
    
    /**
     * This method checks if the date and time between two different dates is the same.
     * @param date another date
     * @return whether the dates and times are equal or not
     */
    public boolean equalDateAndTime(Date date) {
        if (date.getMinute() == minute && date.getHour() == hour && date.getDay() == day
                && date.getMonth() == month && date.getYear() == year) {
            return true;
        }
        return false;
    }
    
    /**
     * This method checks if two dates are equal without taking time in consideration
     * @param date another date
     * @return whether the dates are equal or not
     */
    public boolean sameDateAs(Date date) {
        if (date.getYear() == year && date.getMonth() == month && date.getDay() == day) {
            return true;
        }
        return false;
    }
    
    /**
     * This method makes sure that the date created is a real one.
     * @return true if the date is valid, false otherwise
     */
    public boolean isValidDate() {
        if (year < 1000 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        if ((month == 2 && day > 29)) {
            return false;
        } else if (month == 2 && day == 29 && !TimeUtility.leapYear(year)) {
            return false;
        }
        if (hour > 24 || (hour == 24 && minute >= 0) || minute > 59) {
            return false;
        }
        return true;
    }
    
    /**
     * This method returns which day of the week it is.
     * 0 - Sunday, 1 - Monday and so on
     * @return an integer representing a day of the week
     */
    public int getWeekDay() {
        int x;
        int y;
        int m;
        int d;
        y = year - ((14 - month) / 12);
        x = y + (y / 4) - (y / 100) + (y / 400);
        m = month + (12 * ((14 - month) / 12)) - 2;
        d = (day + x + ((31 * m) / 12)) % 7;
        return d;
    }
    
    /**
     * Getter method for minute.
     * @return minute
     */
    public int getMinute() {
        return minute;
    }
    
    /**
     * Getter method for hour.
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter method for day.
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Getter method for month.
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for year.
     * @return year
     */
    public int getYear() {
        return year;
    }
}