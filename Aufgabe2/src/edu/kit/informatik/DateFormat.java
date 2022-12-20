package edu.kit.informatik;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class serves the function to check whether the format used for dates in different classes is correct or not.
 * @author Rezart Toli
 * @version 1.0
 */
public final class DateFormat {
    private static final Pattern DATEWITHTIME = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
    private static final Pattern DATEWITHOUTTIME = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    /**
     * This method checks if the format for date and time is correct.
     * @param dateAndTime the date and time
     * @return if the format used is correct or not
     */
    public static boolean correctDateAndTimeFormat(String dateAndTime) {
        Matcher matcher = DATEWITHTIME.matcher(dateAndTime);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * This method checks if the format for the date is correct.
     * @param date the date
     * @return if the format used is correct or not
     */
    public static boolean correctDateFormat(String date) {
        Matcher matcher = DATEWITHOUTTIME.matcher(date);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}