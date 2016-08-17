package com.example.asa.chrometextension;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private static final String LOG_TAG = SMSReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Object pdus[];
        if (bundle != null) {
            pdus = (Object[]) bundle.get("pdus");
        } else {
            return;
        }
        int n = pdus.length;
        SmsMessage[] msgs = new SmsMessage[n];
        /* Gets the messages from the intent */
        if (Build.VERSION.SDK_INT >= 19) {
             msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        }
        else {
            for (int i = 0;i<pdus.length;i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[0]);
            }
        }

        for (SmsMessage msg : msgs){
            String message = msg.getMessageBody();
            String from = msg.getOriginatingAddress();
            Chrome_TExtension_Service.getInstance().writeToServer("New message from: " + from + ":" + message);

        }

    }
}
