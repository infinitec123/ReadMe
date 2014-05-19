
package infinitec.eleventh.remindme.data;

/**
 * Constant interface to hold table columns. DO NOT IMPLEMENT.
 * 
 * @author Sharath Pandeshwar
 */
public interface DatabaseColumns {
    /*
     * Under what name the event should be saved?
     */
    public static final String PATTERN_NAME = "pattern_name";

    /*
     * Sender name/number
     */

    public static final String SENDER = "sender";

    /*
     * What is the pattern of date that should be expected. Possible types:
     */
    public static final String DATE_PATTERN = "date_pattern";

    /*
     * Weather user has chosen to Add the event to Calendar or not. Will hold a
     * integer 1(positive) and 0(negative)
     */

    public static final String STATUS = "status";

}
