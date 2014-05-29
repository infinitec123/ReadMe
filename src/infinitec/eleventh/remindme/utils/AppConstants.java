
package infinitec.eleventh.remindme.utils;

/**
 * Class that holds the App Constants
 */
public class AppConstants {

    public static final boolean DEBUG = true;

    /**
     * Constant interface. DO NOT IMPLEMENT
     * 
     * @author Sharath Pandeshwar
     */
    public static interface QueryTokens {
        public static final int INSERT_PATTERN = 1;
    }

    //Notification Related
    public static final int NOTIFICATION_ID = 001;
    public static final String NOTIFICATION_TILE = "Make Calendar Entry?";
    
    public static final String ACTION_ACCEPT = "infinitec.eleventh.remindme.ACTION_ACCEPT";
    public static final String ACTION_DISMISS = "infinitec.eleventh.remindme.ACTION_DISMISS";

    // Bundle Keys
    public static final String SMS_SERVICE_SENDER_PHONE_NUMBER = "sender_number";
    public static final String SMS_SERVICE_SMS_TEXT = "sms_text";
    
}
