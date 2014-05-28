
package infinitec.eleventh.remindme.sms;

import infinitec.eleventh.remindme.action.MakeCalendarEntryService;
import infinitec.eleventh.remindme.activities.AcceptDetailsForCalendarActivity;
import infinitec.eleventh.remindme.utils.AppConstants;
import infinitec.eleventh.remindme.utils.RegExUtils;
import infinitec.eleventh.remindme.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

/**
 * @author Sharath Pandeshwar
 */
public class ProcessSMSTextService extends Service {

    private NotificationManager mNotificationManager;
    private static final String TAG = "ProcessSMSTextService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
         * TODO 
         * 1. Extract Date and title 
         * 2. Make a calendar entry for 'known pattern' 
         * 3. Send out a notification for 'unknown pattern'
         */

        String messageSender = intent.getExtras().getString(
                AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER);
        String messageContent = intent.getExtras().getString(AppConstants.SMS_SERVICE_SMS_TEXT);

        if (RegExUtils.isAnyIndicatorsPresentInText(messageContent,
                getResources().getStringArray(R.array.indicators))
                && (RegExUtils.isDatePresentInText(messageContent))) {
            requestToMakeCalendarEntryNotificationBuilder(messageSender, messageContent);
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * If the message has a DATE and a new pattern is detected send out a
     * notification
     */
    private void requestToMakeCalendarEntryNotificationBuilder(final String number,
            final String message) {

        Intent notifyIntent = new Intent(this, AcceptDetailsForCalendarActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );

        // Action for dismiss button
        Intent dismissIntent = new Intent(this, MakeCalendarEntryService.class);
        dismissIntent.setAction(AppConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        Intent acceptIntent = new Intent(this, MakeCalendarEntryService.class);
        Bundle entryDetailsBundle = new Bundle();
        entryDetailsBundle.putString(AppConstants.SMS_SERVICE_SMS_TEXT, message);
        entryDetailsBundle.putString(AppConstants.SMS_SERVICE_SENDER_PHONE_NUMBER, number);
        acceptIntent.putExtras(entryDetailsBundle);

        PendingIntent piAccept = PendingIntent.getService(this, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this);
        mNotificationBuilder
                .setContentTitle(AppConstants.NOTIFICATION_TILE)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLights(0xff00ff00, 500, 500)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .addAction(R.drawable.ic_action_accept, getString(R.string.notification_yes),
                        piAccept)
                .addAction(R.drawable.ic_action_cancel, getString(R.string.notification_no),
                        piDismiss);

        // Puts the PendingIntent into the notification builder
        mNotificationBuilder.setContentIntent(notifyPendingIntent);
        issueNotification(mNotificationBuilder);
    }

    /**
     * @param builder Issues notifications
     */

    private void issueNotification(NotificationCompat.Builder builder) {
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Including the notification ID allows you to update the notification
        // later on.
        mNotificationManager.notify(AppConstants.NOTIFICATION_ID, builder.build());
    }

}
