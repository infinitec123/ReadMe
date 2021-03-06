
package infinitec.eleventh.remindme.utils;


import java.util.Calendar;
import java.util.TimeZone;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Reminders;


/**
 * @author Abhilasha Singh Utility class to deal with Calendar
 */
public class CalendarUtils {

    private Context context;
    private final String TAG = "CalendarUtils";
    Uri eventsUri;
    Uri remainderUri;

    public CalendarUtils(Context context) {
        this.context = context;
    }

    /**
     * Returns calendar ID of the default Calendar
     * 
     * @return int calendar id
     */

    public int getDefaultCalendarID() {
        Cursor cursor = null;
        int calendarid = -1;
        if (android.os.Build.VERSION.SDK_INT <= 14) {
            eventsUri = Uri.parse("content://com.android.calendar/events");
            remainderUri = Uri.parse("content://com.android.calendar/reminders");
            cursor = context.getContentResolver().query(
                    Uri.parse("content://com.android.calendar/calendars"), new String[] {
                            "_id", "displayName"
                    }, null, null, null);
        } else if (android.os.Build.VERSION.SDK_INT > 14) {
            eventsUri = Uri.parse("content://com.android.calendar/events");
            remainderUri = Uri.parse("content://com.android.calendar/reminders");
            cursor = context.getContentResolver().query(
                    Uri.parse("content://com.android.calendar/calendars"), new String[] {
                            "_id", "calendar_displayName"
                    }, null, null, null);
        }

        if (cursor.getCount() == 0) {
            return -1;
        } else {
            cursor.moveToFirst();
            calendarid = cursor.getInt(0);
        }
        cursor.close();
        return calendarid;
    } // End of getDefaultCalendarID()

    public void makeCalendarEntry(final int calendarid, String title, String description,
            int date, int month, int year, int start_hour, int start_min,
            int end_hour, int end_min, boolean isFullDay) {

        long startCalTime;
        long endCalTime;
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        /*
         * Calendar entry is between 0-11
         */
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        Logger.v(TAG, "Trying to make calendar entry on " + calendar.toString());
        calendar.set(Calendar.HOUR_OF_DAY, start_hour);
        calendar.set(Calendar.MINUTE, start_min);
        startCalTime = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, end_hour);
        calendar.set(Calendar.MINUTE, end_min);
        endCalTime = calendar.getTimeInMillis();

        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.CALENDAR_ID, calendarid);
        event.put(CalendarContract.Events.TITLE, title);
        event.put(CalendarContract.Events.DESCRIPTION, description);
        event.put(CalendarContract.Events.DTSTART, startCalTime);
        event.put(CalendarContract.Events.DTEND, endCalTime);
        event.put(CalendarContract.Events.STATUS, 1);
        event.put(CalendarContract.Events.HAS_ALARM, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        if (isFullDay) {
            event.put(CalendarContract.Events.ALL_DAY, true);
        }
        Uri uri = context.getContentResolver().insert(eventsUri, event);
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Logger.v(TAG, "Successfully inserted at " + uri.toString() + "Event ID: " + eventID);

        /*
         * TODO 1. externallize process of getting remainderuri 
         * 2. Remove the hard coded remainder value
         */

        ContentValues reminders = new ContentValues();
        reminders.put(Reminders.EVENT_ID, eventID);
        reminders.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        reminders.put(Reminders.MINUTES, 1440);
        context.getContentResolver().insert(remainderUri, reminders);

    }

}
