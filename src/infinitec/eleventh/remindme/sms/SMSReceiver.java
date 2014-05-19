
package infinitec.eleventh.remindme.sms;

import infinitec.eleventh.remindme.utils.Logger;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * @author Abhilasha Singh
 * This {@code BroadcastReceiver} listens to incoming 
 * SMS Messages.
 * 
 */
public class SMSReceiver extends BroadcastReceiver {

    private final SmsManager mSmsManager = SmsManager.getDefault();
    private final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle mSmsbundle = intent.getExtras();
        try {

            if (mSmsbundle != null) {
                final Object[] pdusObj = (Object[]) mSmsbundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage mCurrentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String mSenderPhoneNumber = mCurrentMessage.getDisplayOriginatingAddress();
                    String mMessage = mCurrentMessage.getDisplayMessageBody();
                    Logger.i(TAG, "Received: " + mMessage + " from: " + mSenderPhoneNumber);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception at SMSReceiver" + e);
        }
    } // End of onReceive
}
