
package infinitec.eleventh.remindme.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Reminders;
import android.util.Log;
import android.widget.Toast;

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
            Log.v("Calendar", "Starting to get calendars-2");
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
            Date eventDate, int start_hour, int start_min,
            int end_hour, int end_min, boolean isFullDay) {

        long startCalTime;
        long endCalTime;
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(eventDate);
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
        Logger.v(TAG, "Successfully inserted at " + uri.toString());
    }

}
