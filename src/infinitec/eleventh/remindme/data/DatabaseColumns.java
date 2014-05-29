
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

    public static final String SMS_SENDER_NUMBER = "sms_sender_number";
    
    /*
     * Message body
     */
    public static final String SMS_BODY = "sms_body";
    

    /*
     * Weather user has chosen to Add the event to Calendar or not. Will hold a
     * integer 1(positive) and 0(negative)
     */

    public static final String STATUS = "status";

}
