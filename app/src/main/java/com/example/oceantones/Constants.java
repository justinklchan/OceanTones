package com.example.oceantones;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.LinkedList;

public class Constants {
    public static int SamplingRate=48000;
    public static AudioSpeaker sp1;
    public static OfflineRecorder _OfflineRecorder;
    public static EditText et1,et2,et3,et4,et5,et6,et7;
    public static Button startb, stopb;
    public static float scale1=1f,scale2=1f,gap_len=.5f;
    public static int file_num=4,init_sleep=5,tone_len=5,freq_lim=18000;
    public static boolean transmit=true;
    public static boolean stereo=true;
    public static boolean imu=true;
    public static Switch sw1,sw2,sw3;
    public static String mode="tone";
    public static int[] tones=new int[]{};
    public static short[] pulse=new short[]{};
    public static short[] ofdm1=new short[]{};
    public static short[] ofdm2=new short[]{};
    public static LinkedList<String>acc;
    public static LinkedList<String>gyro;
    public static boolean sensorFlag=false;
    public static String ts;
    public static boolean writing=false;

    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Constants.scale1=prefs.getFloat("scale1",Constants.scale1);
        Constants.scale2=prefs.getFloat("scale2",Constants.scale2);
        Constants.file_num=prefs.getInt("file_num",Constants.file_num);
        Constants.init_sleep=prefs.getInt("init_sleep",Constants.init_sleep);
        Constants.tone_len=prefs.getInt("tone_len",Constants.tone_len);
        Constants.gap_len=prefs.getFloat("gap_len",Constants.gap_len);
        Constants.transmit=prefs.getBoolean("transmit",Constants.transmit);
        Constants.freq_lim=prefs.getInt("freq_lim",Constants.freq_lim);
        Constants.stereo=prefs.getBoolean("stereo",Constants.stereo);
        Constants.imu=prefs.getBoolean("imu",Constants.imu);

        Constants.et1.setText(file_num+"");
        Constants.et2.setText(scale1+"");
        Constants.et3.setText(scale2+"");
        Constants.et4.setText(init_sleep+"");
        Constants.et5.setText(tone_len+"");
        Constants.et6.setText(gap_len+"");
        Constants.et7.setText(freq_lim+"");
        Constants.sw1.setChecked(Constants.transmit);
        Constants.sw2.setChecked(Constants.stereo);
        Constants.sw3.setChecked(Constants.imu);

//        ofdm1=FileOperations.readrawasset(context, R.raw.sig1);
//        ofdm2=FileOperations.readrawasset(context, R.raw.sig2);
    }

    public static void disableUI() {
        Constants.startb.setEnabled(false);
        Constants.stopb.setEnabled(true);
        Constants.sw1.setEnabled(false);
        Constants.sw2.setEnabled(false);
        Constants.sw3.setEnabled(false);
        Constants.et1.setEnabled(false);
        Constants.et2.setEnabled(false);
        Constants.et3.setEnabled(false);
        Constants.et4.setEnabled(false);
        Constants.et5.setEnabled(false);
        Constants.et6.setEnabled(false);
        Constants.et7.setEnabled(false);
    }

    public static void enableUI() {
        startb.setEnabled(true);
        stopb.setEnabled(false);
        Constants.sw1.setEnabled(true);
        Constants.sw2.setEnabled(true);
        Constants.sw3.setEnabled(true);

        Constants.et1.setEnabled(true);
//        Constants.et2.setEnabled(true);
//        Constants.et3.setEnabled(true);
        Constants.et4.setEnabled(true);
        Constants.et5.setEnabled(true);
        Constants.et6.setEnabled(true);
//        Constants.et7.setEnabled(true);
    }

    public static void setTones(TextView tv2, Context context) {
        if (Constants.file_num >= 1 && Constants.file_num <= 5) {
            if (Constants.file_num == 1) {
//            5000, 6000,8000, 9000, 11000, 1200014000, 15000,17000
                Constants.tones = new int[]{500, 1000, 1500, 2000, 3000, 4000, 7000, 10000};
            } else if (Constants.file_num == 2) {
                Constants.tones = new int[]{50, 100, 150, 200, 300, 500, 1000, 1500, 2000, 2500, 3000};
            } else if (Constants.file_num == 3) {
                Constants.tones = new int[]{50, 100, 150, 200, 350, 500};
            } else if (Constants.file_num == 4) {
                Constants.tones = new int[]{1500};
            } else if (Constants.file_num == 5) {
                Constants.tones = new int[]{500, 1000};
            }
            String rep = Constants.tones.length + " tones\n";
            for (int i = 0; i < Constants.tones.length; i++) {
                rep += Constants.tones[i] + ",";
            }
            tv2.setText(rep);
            Constants.mode="tone";
        }
        else {
            if (Constants.file_num == 6) {
//                short[] temp = new short[]{-18482,-15574,-12306,-8737,-4928,-951,3119,7204,11225,15101,18754,22110,25102,27667,29752,31314,32321,32751,32595,31858,30554,28709,26364,23566,20373,16850,13070,9108,5043,955,-3075,-6972,-10662,-14079,-17163};
//                Constants.pulse = new short[30+24000];
//                for (int i = 0; i < temp.length; i++) {
//                    Constants.pulse[i]=temp[i];
//                }
//                tv2.setText("gpulse 30 samples");
                Constants.tones = new int[]{500};
                Constants.mode="ask";
                String rep = Constants.tones.length + " ASK\n";
                for (int i = 0; i < Constants.tones.length; i++) {
                    rep += Constants.tones[i] + ",";
                }
                tv2.setText(rep);
            }
            else if (Constants.file_num==7) {
//                short[] temp = new short[]{26078,28476,30377,31742,32543,32762,32396,31452,29950,27920};
////                Constants.pulse1 = new short[10+24000];
//                for (int i = 0; i < temp.length; i++) {
//                    Constants.pulse[i]=temp[i];
//                }
//
//                tv2.setText("gpulse 10 samples");
            }
            else if (Constants.file_num==8) {
                tv2.setText("multipath (multiple signals)");
                Constants.mode="mp";
                Constants.scale1=1;
                Constants.scale2=1;
            }
            else if (Constants.file_num==9) {
                Constants.pulse = SendChirpAsyncTask.continuouspulse(
                        .05, 1000,
                        5000, Constants.gap_len,
                        Constants.SamplingRate,
                        Constants.scale1);

                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("chirp 50ms (1-5khz)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==10) {
                Constants.pulse = SendChirpAsyncTask.continuouspulse(
                        .5, 1000,
                        5000, Constants.gap_len,
                        Constants.SamplingRate,
                        Constants.scale1);

                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("chirp 500ms (1-5khz)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==11) {
                Constants.pulse = SendChirpAsyncTask.continuouspulse(
                        1, 1000,
                        5000, Constants.gap_len,
                        Constants.SamplingRate,
                        Constants.scale1);

                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("chirp 1s (1-5khz)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==12) {
                short[] sig = FileOperations.readrawasset(context, R.raw.barker);

                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
                int cc = 0;
                for (int i = 0; i < sig.length; i++) {
                    Constants.pulse[cc++]=sig[i];
                }
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("barker (312 samples)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==13) {
                 short[] sig= FileOperations.readrawasset(context, R.raw.barker2);

                 Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
                int cc = 0;
                for (int i = 0; i < sig.length; i++) {
                    Constants.pulse[cc++]=sig[i];
                }

                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("barker (624 samples)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==14) {
                short[] sig= FileOperations.readrawasset(context, R.raw.zc);

                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
                int cc = 0;
                for (int i = 0; i < sig.length; i++) {
                    Constants.pulse[cc++]=sig[i];
                }
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("zadoff-chu (50ms)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==15) {
                short[] sig= FileOperations.readrawasset(context, R.raw.zc2);

                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
                int cc = 0;
                for (int i = 0; i < sig.length; i++) {
                    Constants.pulse[cc++]=sig[i];
                }
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("zadoff-chu (500ms)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==16) {
                short[] sig= FileOperations.readrawasset(context, R.raw.zc3);

                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
                int cc = 0;
                for (int i = 0; i < sig.length; i++) {
                    Constants.pulse[cc++]=sig[i];
                }
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText("zadoff-chu (1s)");
                    }
                });
                Constants.mode="mp";
            }
            else if (Constants.file_num==17) {
                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm1);
                tv2.setText("ofdm1");
                Constants.mode="ofdm";
            }
            else if (Constants.file_num==18) {
                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm2);
                tv2.setText("ofdm2");
                Constants.mode="ofdm";
            }
            else if (Constants.file_num==19) {
                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm3);
                tv2.setText("ofdm3");
                Constants.mode="ofdm";
            }
            else if (Constants.file_num==20) {
                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm4);
                tv2.setText("ofdm4");
                Constants.mode="ofdm";
            }
            else if (Constants.file_num==21) {
                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm5);
                tv2.setText("ofdm5");
                Constants.mode="ofdm";
            }
        }
    }
}
