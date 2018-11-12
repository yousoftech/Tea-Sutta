package com.tesuta.shopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by swarajpal on 19-04-2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();

            Log.d("fgh",sender.toString());
            //You must check here if the sender is your provider and not another one with same text.

           String last6 = sender.substring(sender.length() - 6);

           Log.d("fgh",last6);
           if (last6.equals("HSHOPS"))
           {
               String messageBody = smsMessage.getMessageBody();
               //Pass on the text to our listener.
               mListener.messageReceived(messageBody);
           }


        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
