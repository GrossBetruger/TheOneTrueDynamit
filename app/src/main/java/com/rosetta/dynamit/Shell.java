package com.rosetta.dynamit;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by orenko on 7/4/17.
 */

public class Shell {


    public static String sudo(String cmd) {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            DataInputStream inputStream = new DataInputStream(su.getInputStream());

            outputStream.writeBytes(cmd+"\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();

            return "sudo successful, cmd: " + cmd + "\n output: " + inputStream.readUTF();

        } catch (IOException e) {
        } catch (InterruptedException e) {

        }

    return "sudo failed, cmd: " + cmd;
    }

    public static String userCmd(String cmd){
        try {
            Process process = Runtime.getRuntime().exec(cmd +"\n");
            DataInputStream inputStream = new DataInputStream(process.getInputStream());
            String result = inputStream.readUTF();
            Log.d("results from cmd: " + cmd + "\n", result);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return "cmd failed, cmd: " +  cmd + "\nreason: " + e.getMessage();
        }

    }
}
