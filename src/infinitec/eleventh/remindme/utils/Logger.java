
package infinitec.eleventh.remindme.utils;

import android.util.Log;

/**
 * @author Sharath Pandeshwar Wrapper class for Logging Utilites
 */
public class Logger {

    public static void v(final String tag, final String message) {
        if (AppConstants.DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void i(final String tag, final String message) {
        if (AppConstants.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(final String tag, final String message) {
        if (AppConstants.DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void d(final String tag, final String message) {
        if (AppConstants.DEBUG) {
            Log.d(tag, message);
        }
    }

}
