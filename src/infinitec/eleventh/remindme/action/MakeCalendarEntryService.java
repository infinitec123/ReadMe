
package infinitec.eleventh.remindme.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import infinitec.eleventh.remindme.R;
import infinitec.eleventh.remindme.utils.AppConstants;
import infinitec.eleventh.remindme.utils.CalendarUtils;
import infinitec.eleventh.remindme.utils.Logger;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Sharath Pandeshwar
 */
public class MakeCalendarEntryService extends IntentService {

    private final String TAG = "MakeCalendarEntryService";
    private CalendarUtils myCalendarUtils;

    public MakeCalendarEntryService() {
        super("infinitec.eleventh.remindme.MakeCalendarEntryService");
        myCalendarUtils = new CalendarUtils(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
        Bundle extras = intent.getExtras();
        cancelNotification(AppConstants.NOTIFICATION_ID);
        if(extras!=null){
            //It means 'Yes' button was chosen
            Log.v(TAG, "Bundle non empty");
            int calendarid = myCalendarUtils.getDefaultCalendarID();
            if (calendarid == -1) {
                Logger.v(TAG, "Calendar Account Not Found!");
                return;
            }
            
            String description = extras.getString(AppConstants.SMS_SERVICE_SMS_TEXT);
            String title = "RemindMe: " + extras.getString(AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER);
            
            Date eventDate;
            try {
                
                /*
                 * TODO remove below hard codings
                 */
                eventDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/22/2014");
                int start_hour = 9;
                int start_min = 00;
                int end_hour = 9;
                int end_min = 15;
                boolean isFullDay = true;

                myCalendarUtils.makeCalendarEntry(calendarid, title, description, eventDate,
                        start_hour, start_min, end_hour, end_min, isFullDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        } 
        } 


    /**
     * Cancels a notification
     * 
     * @param notification_id
     */

    private void cancelNotification(int notification_id) {
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notification_id);
    }

}
