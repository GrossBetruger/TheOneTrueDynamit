package com.rosetta.dynamit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.rosetta.dynamit.MainActivity.DATA_KEY;

public class EventHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String action = intent.getAction().toString();
        Log.d("event:", action);
        Intent reportServiceIntent = new Intent(context, ReportService.class);
        reportServiceIntent.putExtra(DATA_KEY, action);
        context.startService(reportServiceIntent);


    }
}
