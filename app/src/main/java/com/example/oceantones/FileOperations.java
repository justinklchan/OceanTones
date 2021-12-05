package com.example.oceantones;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class FileOperations {

    public static void writetofile(Activity av, short[] buff, String filename) {
//        Log.e("asdf","writetofile");
        try {
            String dir = av.getExternalFilesDir(null).toString();
            writetofile(dir, buff, filename);

        } catch(Exception e) {
            Log.e("asdf",e.toString());
        }
    }

    public static short[] readrawasset(Context context, int id) {
        Scanner inp = new Scanner(context.getResources().openRawResource(id));
        LinkedList<Short> ll = new LinkedList<>();
        while (inp.hasNextLine()) {
            ll.add(Short.parseShort(inp.nextLine()));
        }
        inp.close();
        short[] ar = new short[ll.size()];
        int counter = 0;
        for (Short d : ll) {
            ar[counter++] = d;
        }
        ll.clear();

        return ar;
    }

    public static void writetofile(String _ExternalFilesDir, short[] buff, String filename) {
        Constants.writing=true;
        Log.e("asdf","writetofile " + filename);
        long ts = System.currentTimeMillis();

        try {
            String dir = _ExternalFilesDir;
            File path = new File(dir);
            if (!path.exists()) {
                path.mkdirs();
            }

            File file = new File(dir, filename);

            BufferedWriter buf = new BufferedWriter(new FileWriter(file,false));
            for (int i = 0; i < buff.length; i++) {
                buf.append(""+buff[i]);
                buf.newLine();
            }
            buf.flush();
            buf.close();
        } catch(Exception e) {
            Log.e("asdf",e.toString());
        }
        Log.e("asdf","finish writing "+filename + (System.currentTimeMillis()-ts));
    }

    public static void writeSensors(Activity av, String filename) {
//        if (Constants.imu) {
        Constants.writing=true;
            long ts = System.currentTimeMillis();
            Log.e("asdf","writing sensors "+Constants.acc.size()+","+Constants.gyro.size());

            try {
                String dir = av.getExternalFilesDir(null).toString();

                File path = new File(dir);
                if (!path.exists()) {
                    path.mkdirs();
                }

                File file = new File(dir, "acc-" + filename);
                BufferedWriter buf = new BufferedWriter(new FileWriter(file, false));
                for (String s : Constants.acc) {
                    buf.write(s);
                }
                buf.flush();
                buf.close();

                File file2 = new File(dir, "gyro-" + filename);
                BufferedWriter buf2 = new BufferedWriter(new FileWriter(file2, false));
                for (String s : Constants.gyro) {
                    buf2.write(s);
                }
                buf2.flush();
                buf2.close();
            } catch (Exception e) {
                Log.e("asdf", e.toString());
            }
            Log.e("asdf","finish writing sensors "+(System.currentTimeMillis()-ts));
//        }
        Constants.writing=false;
    }

    public static void newWrite() {

    }
}
