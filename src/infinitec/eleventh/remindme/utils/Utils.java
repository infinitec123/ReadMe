
package infinitec.eleventh.remindme.utils;

import android.os.Looper;


/**
 * @author Sharath Pandeshwar Utility methods
 */
public class Utils {

    private static final String TAG = "Utils";

    /**
     * Checks if the current thread is the main thread or not
     * 
     * @return <code>true</code> if the current thread is the main/UI thread,
     *         <code>false</code> otherwise
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
    
}
