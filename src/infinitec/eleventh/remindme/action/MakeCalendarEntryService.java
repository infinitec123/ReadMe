
package infinitec.eleventh.remindme.action;

import java.util.ArrayList;

import infinitec.eleventh.remindme.data.DBInterface;
import infinitec.eleventh.remindme.data.DatabaseColumns;
import infinitec.eleventh.remindme.data.TablePatterns;
import infinitec.eleventh.remindme.data.DBInterface.AsyncDbQueryCallback;
import infinitec.eleventh.remindme.models.OurDate;
import infinitec.eleventh.remindme.utils.AppConstants;
import infinitec.eleventh.remindme.utils.AppConstants.QueryTokens;
import infinitec.eleventh.remindme.utils.CalendarUtils;
import infinitec.eleventh.remindme.utils.Logger;
import infinitec.eleventh.remindme.utils.RegExUtils;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
        int date;
        int month;
        int year;
        Bundle extras = intent.getExtras();
        cancelNotification(AppConstants.NOTIFICATION_ID);
        if (extras != null) {
            // It means 'Yes' button was chosen

            String message_body = extras.getString(AppConstants.SMS_SERVICE_SMS_TEXT);
            String message_title = "RemindMe: "
                    + extras.getString(AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER);
            ArrayList<OurDate> datesInMessage = RegExUtils.getDatesInText(message_body);
            if (datesInMessage.size() == 0 || datesInMessage == null) {
                Logger.v(TAG, "No date found. Ignoring!");
            }

            Log.v(TAG, "Printing Received Dates");
            for (int i = 0; i < datesInMessage.size(); i++) {
                Logger.v(TAG, datesInMessage.get(i).toString());
            }
            /*
             * TODO remove assumption that there would be only two dates
             */
            year = datesInMessage.get(0).getYear();
            month = datesInMessage.get(0).getMonth();
            date = datesInMessage.get(0).getDate();
            if ((datesInMessage.size() > 1)
                    && (datesInMessage.get(1).compareTo(datesInMessage.get(0)) > 0)) {
                year = datesInMessage.get(1).getYear();
                month = datesInMessage.get(1).getMonth();
                date = datesInMessage.get(1).getDate();
            }
            Logger.v(TAG, "I want entry made on:" + month + "/" + date + "/" + year);

            int calendarid = myCalendarUtils.getDefaultCalendarID();
            if (calendarid == -1) {
                Logger.v(TAG, "Calendar Account Not Found!");
                /*
                 * TODO Uncomment
                 */
                // return;
            }

            int start_hour = 9;
            int start_min = 00;
            int end_hour = 9;
            int end_min = 15;
            boolean isFullDay = true;

            final ContentValues values = new ContentValues();
            values.put(DatabaseColumns.PATTERN_NAME,
                    extras.getString(AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER));
            values.put(DatabaseColumns.SMS_SENDER_NUMBER,
                    extras.getString(AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER));
            values.put(DatabaseColumns.SMS_BODY,
                    extras.getString(AppConstants.SMS_SERVICE_SMS_TEXT));
            values.put(DatabaseColumns.STATUS, 1);

            DBInterface.insert(TablePatterns.NAME, null, values);

            /*
             * TODO Uncomment
             */
            // myCalendarUtils.makeCalendarEntry(calendarid, message_title,
            // message_body, date, month,
            // year, start_hour, start_min, end_hour, end_min, isFullDay);

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
