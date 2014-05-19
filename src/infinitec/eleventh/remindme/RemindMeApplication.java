
package infinitec.eleventh.remindme;

import android.app.Application;
import android.content.Context;

/**
 * @author Sharath Pandeshwar
 *
 */
public class RemindMeApplication extends Application{
    
    private static final String TAG = "RemindMeApplication";

    /**
     * Maintains a reference to the application context so that it can be
     * referred anywhere without fear of leaking.
     */
    private static Context      sStaticContext;
    
    /**
     * Gets a reference to the application context
     */
    public static Context getStaticContext() {
        if (sStaticContext != null) {
            return sStaticContext;
        }

        //Should NEVER hapen
        throw new RuntimeException("No static context instance");
    }
    
    
    @Override
    public void onCreate() {
        sStaticContext = getApplicationContext();
    };

}
