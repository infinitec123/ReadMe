
package infinitec.eleventh.remindme.action;

import infinitec.eleventh.remindme.R;
import infinitec.eleventh.remindme.utils.AppConstants;
import infinitec.eleventh.remindme.utils.Logger;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;

/**
 * @author Sharath Pandeshwar
 */
public class MakeCalendarEntryService extends IntentService {

    private final String TAG = "MakeCalendarEntryService";

    public MakeCalendarEntryService() {
        super("infinitec.eleventh.remindme.MakeCalendarEntryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        Logger.v(TAG, "Received action:" + action);
        cancelNotification(AppConstants.NOTIFICATION_ID);
    }
    
    
    /**
     * Cancels a notification
     * @param notification_id
     */
    
    private void cancelNotification(int notification_id){
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notification_id);
    }
    
    

}
