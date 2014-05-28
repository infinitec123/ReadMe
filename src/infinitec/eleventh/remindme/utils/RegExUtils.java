
package infinitec.eleventh.remindme.utils;

import infinitec.eleventh.remindme.models.OurDate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Sharath Pandeshwar Utility class to extract 'information' from an SMS
 */
public class RegExUtils {

    public static final String TAG = "RegExUtils";

    /**
     * @param String message
     * @return boolean Uses Regular Expression to return true if the message
     *         contains any date element. Else returns false
     */

    public static boolean isAnyIndicatorsPresentInText(final String message,
            final String[] indicators) {
        for (int i = 0; i < indicators.length; i++) {
            if (message.contains(indicators[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDatePresentInText(final String message) {
        Matcher matcher;

        /*
         * Pattern 1: 
         * DD-CCC-YYYY (23-MAR-2013) 
         * DD/CCC/YYYY (9/JUN/1987)
         * DD-CCC-YY (23-MAR-13) 
         * DD/CCC/YY (9/JUN/87)
         */
        String date_pattern1 =
                "(0?[1-9]|[12][0-9]|3[01])\\s*?[/-]\\s*?(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s*?[/-]\\s*?(\\d{4}|\\d{2})";
        Pattern pattern1 = Pattern.compile(date_pattern1, Pattern.CASE_INSENSITIVE);
        matcher = pattern1.matcher(message);

        if (matcher.find()) {
            return true;
        } 
        
        /*
         * Pattern 2: 
         * DD/MM/YYYY (23-12-2113)
         * DD-MM-YYYY (01-9-1913) 
         * DD/MM/YY (23-12-13)
         * DD-MM-YY (01-9-13)
         */
        String date_pattern2 =
                "(0?[1-9]|[12][0-9]|3[01])\\s*?[-/]\\s*?(1[012]|0?[0-9])\\s*?[-/]\\s*?(\\d{4}|\\d{2})";
        Pattern pattern2 = Pattern.compile(date_pattern2, Pattern.CASE_INSENSITIVE);
        matcher = pattern2.matcher(message);

        while (matcher.find()) {
            return true;
        } 
        return false;
    }
    
    /**
     * Utility function to extract dates from a given text
     * @param message Text message to be searched
     * @return ArrayList containing dates
     */
    
    public static ArrayList<OurDate> getDatesInText(String message){
        ArrayList<OurDate> datesList = new ArrayList<OurDate>();
        Matcher matcher;
        String dateString;
        String monthString;
        String yearString;
        /*
         * Pattern 1: 
         * DD-CCC-YYYY (23-MAR-2013) 
         * DD/CCC/YYYY (9/JUN/1987)
         * DD-CCC-YY (23-MAR-13) 
         * DD/CCC/YY (9/JUN/87)
         */
        String date_pattern1 =
                "(0?[1-9]|[12][0-9]|3[01])\\s*?[/-]\\s*?(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s*?[/-]\\s*?(\\d{4}|\\d{2})";
        Pattern pattern1 = Pattern.compile(date_pattern1, Pattern.CASE_INSENSITIVE);
        matcher = pattern1.matcher(message);

        while (matcher.find()) {
            dateString = matcher.group(1);
            monthString = matcher.group(2);
            yearString = matcher.group(3);
            OurDate ourDate = new OurDate(dateString,monthString, yearString);
            Logger.v(TAG, "Found a type-1 date" + ourDate.toString());
            datesList.add(ourDate);
        } 
        
        /*
         * Pattern 2: 
         * DD/MM/YYYY (23-12-2113)
         * DD-MM-YYYY (01-9-1913) 
         * DD/MM/YY (23-12-13)
         * DD-MM-YY (01-9-13)
         */
        String date_pattern2 =
                "(0?[1-9]|[12][0-9]|3[01])\\s*?[-/]\\s*?(1[012]|0?[0-9])\\s*?[-/]\\s*?(\\d{4}|\\d{2})";
        Pattern pattern2 = Pattern.compile(date_pattern2, Pattern.CASE_INSENSITIVE);
        matcher = pattern2.matcher(message);

        while (matcher.find()) {
            dateString = matcher.group(1);
            monthString = matcher.group(2);
            yearString = matcher.group(3);
            OurDate ourDate = new OurDate(dateString,monthString, yearString);
            datesList.add(ourDate);
            Logger.v(TAG, "Found a Type-2 date" + ourDate.toString());
        } 
        
        return datesList;
    }
}
