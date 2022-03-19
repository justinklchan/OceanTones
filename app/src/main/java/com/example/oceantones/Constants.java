package com.example.oceantones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class Constants {
    public static ConstraintLayout clayout;
    public static int SamplingRate=44100;
    public static AudioSpeaker sp1;
    public static AudioSpeaker sp2;
    public static OfflineRecorder _OfflineRecorder;
    public static EditText et1,et2,et3,et4,et5,et6,et7,et8;
    public static Button startb, stopb,prepb;
    public static NestedScrollView sview;
    public static float scale1=1f,scale2=1f,gap_len=.5f;
    public static int file_num=51,init_sleep=5,tone_len=5,freq_lim=18000,reps=1;
    public static boolean transmit=true;
    public static boolean stereo=true;
    public static boolean imu=true;
    public static boolean gap=true;
    public static Switch sw1,sw2,sw3,sw4;
    public static String mode="tone";
    public static int[] tones=new int[]{};
    public static short[] pulse=new short[]{};
    public static short[] pulse2=new short[]{};
    public static short[] ofdm1=new short[]{};
    public static short[] ofdm2=new short[]{};
    public static LinkedList<String>acc;
    public static LinkedList<String>gyro;
    public static LinkedList<Integer>azimuth;
    public static LinkedList<Integer>pitch;
    public static boolean sensorFlag=false;
    public static String ts;
    public static boolean writing=false;
    public static int[] repeatNum= new int[]{36,44};
    public static int[] numToRepeat=new int[]{5,3};
    public static HashMap<Integer,Integer> aa;


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
        Constants.reps=prefs.getInt("reps",Constants.reps);
        Constants.gap=prefs.getBoolean("gap",Constants.gap);

        Constants.et1.setText(file_num+"");
        Constants.et2.setText(scale1+"");
        Constants.et3.setText(scale2+"");
        Constants.et4.setText(init_sleep+"");
        Constants.et5.setText(tone_len+"");
        Constants.et6.setText(gap_len+"");
        Constants.et7.setText(freq_lim+"");
        Constants.et8.setText(reps+"");
        Constants.sw1.setChecked(Constants.transmit);
        Constants.sw2.setChecked(Constants.stereo);
        Constants.sw3.setChecked(Constants.imu);
        Constants.sw4.setChecked(Constants.gap);

//        ofdm1=FileOperations.readrawasset(context, R.raw.sig1);
//        ofdm2=FileOperations.readrawasset(context, R.raw.sig2);

        aa=new HashMap<>();

        aa.put(37,12);
        aa.put(38,12);
        aa.put(39,12);
        aa.put(40,12);
        aa.put(41,12);

        int sum=0;
        for (int i = 37; i <= 41; i++) {
            sum+=aa.get(i)+4;
        }
        aa.put(36,sum);

        aa.put(42,26);
        aa.put(43,26);

        aa.put(45,4+(2*2));
        aa.put(46,5+(3*2));
        aa.put(47,9+(5*2));

        sum=0;
        for (int i = 45; i <= 47; i++) {
            sum+=aa.get(i)+4;
        }
        aa.put(44,sum);

        aa.put(48,5);

        aa.put(51,36);
        aa.put(52,36*2);
        aa.put(53,36*3);
        aa.put(55,56);

        aa.put(56,30);
        aa.put(57,30);
        aa.put(58,30);
        aa.put(59,30);
        aa.put(60,7);
        aa.put(61,7);
        aa.put(62,7);
        aa.put(67,20);


        aa.put(64,30);
        aa.put(66,30);

        aa.put(68,20);
        aa.put(69,20);
        aa.put(70,20);
        aa.put(71,20);
        aa.put(72,20);
        aa.put(73,20);
        aa.put(74,20);
        aa.put(75,20);
        aa.put(76,20);
        aa.put(77,20);

        aa.put(78,32);
        aa.put(79,32);
        aa.put(80,32);
        aa.put(81,32);
        aa.put(82,1);
        aa.put(83,32);
        aa.put(84,32);
        aa.put(85,32);
//        aa.put(47,18);
//        aa.put(48,18);
//        aa.put(49,18);
    }

    public static void disableUI() {
        Constants.startb.setEnabled(false);
        Constants.stopb.setEnabled(true);
        Constants.prepb.setEnabled(false);
        Constants.sw1.setEnabled(false);
//        Constants.sw2.setEnabled(false);
//        Constants.sw3.setEnabled(false);
        Constants.sw4.setEnabled(false);
        Constants.et1.setEnabled(false);
        Constants.et2.setEnabled(false);
        Constants.et3.setEnabled(false);
        Constants.et4.setEnabled(false);
        Constants.et5.setEnabled(false);
        Constants.et6.setEnabled(false);
        Constants.et7.setEnabled(false);
        Constants.et8.setEnabled(false);
    }

    public static void enableUI() {
        startb.setEnabled(true);
        stopb.setEnabled(false);
        prepb.setEnabled(true);
        Constants.sw1.setEnabled(true);
//        Constants.sw2.setEnabled(true);
//        Constants.sw3.setEnabled(true);
        Constants.sw4.setEnabled(true);

        Constants.et1.setEnabled(true);
        Constants.et2.setEnabled(true);
        Constants.et3.setEnabled(true);
        Constants.et4.setEnabled(true);
        Constants.et5.setEnabled(true);
        Constants.et6.setEnabled(true);
//        Constants.et7.setEnabled(true);
        Constants.et8.setEnabled(true);
    }

    public static CountDownTimer timer;
    public static TextView tv3,tv12,tv13,tv5;
    public static int fidxRepeat=0;
    public static SendChirpAsyncTask task;

    public static int contains(int k) {
        for (int i = 0; i<Constants.repeatNum.length; i++) {
            if (Constants.repeatNum[i] == k) {
                return i;
            }
        }
        return -1;
    }

    public static void setTones(TextView tv2, Context context) {
        Log.e("asdf","set tones"+Constants.file_num);
        if (aa.containsKey(Constants.file_num)) {
            Constants.tone_len = aa.get(Constants.file_num);

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putInt("tone_len", aa.get(Constants.file_num));
            editor.commit();
        }
//        if (Constants.file_num >= 1 && Constants.file_num <= 5) {
//            if (Constants.file_num == 1) {
////            5000, 6000,8000, 9000, 11000, 1200014000, 15000,17000
//                Constants.tones = new int[]{500, 1000, 1500, 2000, 3000, 4000, 7000, 10000};
//            } else if (Constants.file_num == 2) {
//                Constants.tones = new int[]{50, 100, 150, 200, 300, 500, 1000, 1500, 2000, 2500, 3000};
//            } else if (Constants.file_num == 3) {
//                Constants.tones = new int[]{50, 100, 150, 200, 350, 500};
//            } else if (Constants.file_num == 4) {
//                Constants.tones = new int[]{1500};
//            } else if (Constants.file_num == 5) {
//                Constants.tones = new int[]{500, 1000};
//            }
//            String rep = Constants.tones.length + " tones\n";
//            for (int i = 0; i < Constants.tones.length; i++) {
//                rep += Constants.tones[i] + ",";
//            }
//            tv2.setText(rep);
//            Constants.mode="tone";
//        }
//        else {
//            if (Constants.file_num == 6) {
////                short[] temp = new short[]{-18482,-15574,-12306,-8737,-4928,-951,3119,7204,11225,15101,18754,22110,25102,27667,29752,31314,32321,32751,32595,31858,30554,28709,26364,23566,20373,16850,13070,9108,5043,955,-3075,-6972,-10662,-14079,-17163};
////                Constants.pulse = new short[30+24000];
////                for (int i = 0; i < temp.length; i++) {
////                    Constants.pulse[i]=temp[i];
////                }
////                tv2.setText("gpulse 30 samples");
//                Constants.tones = new int[]{500};
//                Constants.mode="ask";
//                String rep = Constants.tones.length + " ASK\n";
//                for (int i = 0; i < Constants.tones.length; i++) {
//                    rep += Constants.tones[i] + ",";
//                }
//                tv2.setText(rep);
//            }
//            else if (Constants.file_num==7) {
////                short[] temp = new short[]{26078,28476,30377,31742,32543,32762,32396,31452,29950,27920};
//////                Constants.pulse1 = new short[10+24000];
////                for (int i = 0; i < temp.length; i++) {
////                    Constants.pulse[i]=temp[i];
////                }
////
////                tv2.setText("gpulse 10 samples");
//            }
//            else if (Constants.file_num==8) {
//                tv2.setText("multipath (multiple signals)");
//                Constants.mode="mp";
//                Constants.scale1=1;
//                Constants.scale2=1;
//            }
//            if (Constants.file_num==36) {
//                Constants.pulse = SendChirpAsyncTask.continuouspulse(
//                        .05, 1000,
//                        1000, Constants.gap_len,
//                        Constants.SamplingRate,
//                        Constants.scale1);
////                Constants.pulse2 = SendChirpAsyncTask.continuouspulse(
////                        .05, 1000,
////                        1000, Constants.gap_len,
////                        Constants.SamplingRate,
////                        Constants.scale1);
////
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("tone 500");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==10) {
//                Constants.pulse = SendChirpAsyncTask.continuouspulse(
//                        .5, 1000,
//                        10000, Constants.gap_len,
//                        Constants.SamplingRate,
//                        Constants.scale1);
//
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("chirp 500ms (1-5khz)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==11) {
//                Constants.pulse = SendChirpAsyncTask.continuouspulse(
//                        1, 1000,
//                        5000, Constants.gap_len,
//                        Constants.SamplingRate,
//                        Constants.scale1);
//
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("chirp 1s (1-5khz)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==12) {
//                short[] sig = FileOperations.readrawasset(context, R.raw.barker);
//
//                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
//                int cc = 0;
//                for (int i = 0; i < sig.length; i++) {
//                    Constants.pulse[cc++]=sig[i];
//                }
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("barker (312 samples)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==13) {
//                 short[] sig= FileOperations.readrawasset(context, R.raw.barker2);
//
//                 Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
//                int cc = 0;
//                for (int i = 0; i < sig.length; i++) {
//                    Constants.pulse[cc++]=sig[i];
//                }
//
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("barker (624 samples)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==14) {
//                short[] sig= FileOperations.readrawasset(context, R.raw.zc);
//
//                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
//                int cc = 0;
//                for (int i = 0; i < sig.length; i++) {
//                    Constants.pulse[cc++]=sig[i];
//                }
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("zadoff-chu (50ms)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==15) {
//                short[] sig= FileOperations.readrawasset(context, R.raw.zc2);
//
//                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
//                int cc = 0;
//                for (int i = 0; i < sig.length; i++) {
//                    Constants.pulse[cc++]=sig[i];
//                }
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("zadoff-chu (500ms)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==16) {
//                short[] sig= FileOperations.readrawasset(context, R.raw.zc3);
//
//                Constants.pulse = new short[sig.length+(int)(Constants.gap_len*Constants.SamplingRate)];
//                int cc = 0;
//                for (int i = 0; i < sig.length; i++) {
//                    Constants.pulse[cc++]=sig[i];
//                }
//                ((MainActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv2.setText("zadoff-chu (1s)");
//                    }
//                });
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==17) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm1500);
//                tv2.setText("ofdm1500");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==18) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm3000);
//                tv2.setText("ofdm3000");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==19) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm5000);
//                tv2.setText("ofdm5000");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==20) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm7000);
//                tv2.setText("ofdm7000");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==21) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm10000);
//                tv2.setText("ofdm10000");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==22) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_1_2);
//                tv2.setText("ofdm_1_2");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==23) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_2_3);
//                tv2.setText("ofdm_2_3");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==24) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_3_4);
//                tv2.setText("ofdm_3_4");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==25) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_4_5);
//                tv2.setText("ofdm_4_5");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==26) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_5_6);
//                tv2.setText("ofdm_5_6");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==27) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_zeros);
//                tv2.setText("ofdm_zeros");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==28) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.mp_signal1);
//                tv2.setText("equalizer");
//                Constants.mode="mp";
//            }
//            else if (Constants.file_num==29) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_null);
//                tv2.setText("null");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==30) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdm_null2);
//                tv2.setText("null2");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==31) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdmz_200);
//                tv2.setText("z200");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==32) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdmz_2000);
//                tv2.setText("z2000");
//                Constants.mode="ofdm";
//            }
//            else if (Constants.file_num==33) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.ofdmz_20000);
//                tv2.setText("z20000");
//                Constants.mode="ofdm";
//            }
            //////////////////////////////////////////////////////////////////////////////////
        if (Constants.file_num==36) {
            Constants.mode="mp";
        }
        if (Constants.file_num==37) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_50);
            tv2.setText("preamble_50");
            Constants.mode="mp";
        }
        if (Constants.file_num==38) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_100);
            tv2.setText("preamble_100");
            Constants.mode="mp";
        }
        if (Constants.file_num==39) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_200);
            tv2.setText("preamble_200");
            Constants.mode="mp";
        }
        if (Constants.file_num==40) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_500);
            tv2.setText("preamble_500");
            Constants.mode="mp";
        }
        if (Constants.file_num==41) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_1000);
            tv2.setText("preamble_1000");
            Constants.mode="mp";
        }
        if (Constants.file_num==42) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.crc_signal_debug3_960_0_67_1000_1250_data);
            tv2.setText("crc_signal_debug3_960_0_67_1000_1250_data");
            Constants.mode="mp";
        }
        if (Constants.file_num==43) {
            Constants.pulse = FileOperations.readrawasset(context, R.raw.diff_signal_debug3_960_0_67_1000_1250_data);
            tv2.setText("diff_signal_debug3_960_0_67_1000_1250_data");
            Constants.mode="mp";
        }
        if (Constants.file_num==44) {
            Constants.mode="mp";
        }
//        if (Constants.file_num==45) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.single_signal_debug3_960_0_67_1000_3000_data);
//            tv2.setText("single_signal_debug3_960_0_67_1000_3000_data");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==46) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.single_signal_debug3_960_0_134_1000_3000_data);
//            tv2.setText("single_signal_debug3_960_0_134_1000_3000_data");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==47) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.single_signal_debug3_960_0_268_1000_3000_data);
//            tv2.setText("single_signal_debug3_960_0_268_1000_3000_data");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==49) {
//            Constants.pulse = SendChirpAsyncTask.continuouspulse(
//                        .15, 1800,
//                        4400, .25,
//                        Constants.SamplingRate,
//                        Constants.scale1);
//            tv2.setText("chirp");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==50) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_1000);
//            tv2.setText("preamble100");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==51||Constants.file_num==52||Constants.file_num==53) {
//            if (Constants.gap) {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.chirps2);
//                Log.e("asdf","chirps1");
//            }
//            else {
//                Constants.pulse = FileOperations.readrawasset(context, R.raw.chirps1);
//                Log.e("asdf","chirps2");
//            }
//            tv2.setText("chirps");
//            Constants.mode="ofdm";
//
//            prep(context);
//        }
        if (Constants.file_num==54) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.preambles);
            tv2.setText("chirp");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==55) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.out);
            tv2.setText("chirp");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==56) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.ofdm_sym);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==57) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.full_sig);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==58) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.preamble);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==59) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==60) {
            Constants.pulse = SendChirpAsyncTask.continuouspulse(
                    .15, 1000,
                    5000, .25,
                    Constants.SamplingRate,
                    Constants.scale1);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==61) {
            Constants.pulse = SendChirpAsyncTask.continuouspulse(
                    .15, 5000,
                    1000, .25,
                    Constants.SamplingRate,
                    Constants.scale1);
            tv2.setText("ofdm");
            Constants.mode="ofdm";
        }
//        if (Constants.file_num==62) {
//            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_120_preamble);
//            tv2.setText("120");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==63) {
//            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_240_preamble);
//            tv2.setText("240");
//            Constants.mode="ofdm";
//        }
        if (Constants.file_num==64) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_480_preamble);
            Constants.pulse = new short[48000*7+pre.length];
            for (int i = 0; i < pre.length; i++) {
                Constants.pulse[i] = pre[i];
            }

            short[] c=Chirp.generateChirpSpeaker(200,200,.25,48e3,0,1.0);
            int counter=0;
            for (int i = pre.length; i < pre.length+12000; i++) {
                Constants.pulse[i] = c[counter++];
            }
            tv2.setText("480");
            Constants.mode="ofdm";
        }
//        if (Constants.file_num==65) {
//            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_120_long_preamble);
//            tv2.setText("120_long");
//            Constants.mode="ofdm";
//        }
        if (Constants.file_num==66) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_240_long_preamble);
            short[] c=Chirp.generateChirpSpeaker(200,200,2,48e3,0,.05);
            Constants.pulse = new short[48000*5+pre.length+c.length];
            for (int i = 0; i < pre.length; i++) {
                Constants.pulse[i] = pre[i];
            }

            int counter=0;
            for (int i = pre.length; i < pre.length+c.length; i++) {
                Constants.pulse[i] = c[counter++];
            }

            tv2.setText("240_long");
            Constants.mode="ofdm";
        }
        /////////////////////////////////
        if (Constants.file_num==68) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.mseq_128_1);
            tv2.setText("mseq128_1");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==69) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.mseq_256_1);
            tv2.setText("mseq256_1");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==70) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.mseq_256_2);
            tv2.setText("mseq256_2");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==71) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.mseq_512_1);
            tv2.setText("mseq512_1");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==72) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.mseq_512_2);
            tv2.setText("mseq512_2");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==73) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.new_512);
            tv2.setText("new_512");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==74) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_120);
            tv2.setText("naiser_120");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==75) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_240);
            tv2.setText("naiser_240");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==76) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser_480);
            tv2.setText("naiser_480");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==77) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.long_240);
            tv2.setText("naiser_long_240");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==78) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_60_420);
            warmdown(pre);
            tv2.setText("naiser_60_420");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==79) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_120_360);
            warmdown(pre);
            tv2.setText("naiser_120_360");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==80) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_long_60_420);
            warmdown(pre);
            tv2.setText("naiser_long_60_420");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==81) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.naiser_long_120_360);
            warmdown(pre);
            tv2.setText("naiser_long_120_360");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==82) {
            short[] temp = FileOperations.readrawasset_binary(context, R.raw.chirp);
            Constants.pulse = new short[4800];
            for (int i = 43200 ; i < temp.length; i++) {
                Constants.pulse[i] = temp[i];
            }
//            warmdown(pre);
            tv2.setText("chirp");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==83) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.training_symbol);
//            warmdown(pre);
            tv2.setText("training_symbol");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==84) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.training_symbol2);
//            warmdown(pre);
            tv2.setText("training_symbol");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==85) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.chirp_1_5);
//            warmdown(pre);
            tv2.setText("chirp3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==86) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.naiser);
//            warmdown(pre);
            tv2.setText("chirp3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==87) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.vol_100);
//            warmdown(pre);
            tv2.setText("chirp3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==88) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.vol_300);
//            warmdown(pre);
            tv2.setText("chirp3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==89) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.vol_500);
//            warmdown(pre);
            tv2.setText("chirp3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==90) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.online1);
//            warmdown(pre);
            tv2.setText("online1");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==91) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.online2);
//            warmdown(pre);
            tv2.setText("online2");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==92) {
            Constants.pulse = FileOperations.readrawasset_binary(context, R.raw.online3);
//            warmdown(pre);
            tv2.setText("online3");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==93) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_720_360_cp);
            warmup(pre);
            tv2.setText("signal_720_360_cp");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==94) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_720_360_gi);
            warmup(pre);
            tv2.setText("signal_720_360_gi");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==95) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_720_360_half);
            warmup(pre);
            tv2.setText("signal_720_360_half");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==96) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.n_1260_480_half_signal);
            warmup(pre);
            tv2.setText("n_1260_480_half_signal");
            Constants.mode="ofdm";
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
        if (Constants.file_num==97) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_960_360_1000_3000);
            warmup2(pre);
            tv2.setText("signal_960_360_1000_3000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==98) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_960_360_1000_5000);
            warmup2(pre);
            tv2.setText("signal_960_360_1000_5000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==99) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_960_360_1000_9000);
            warmup2(pre);
            tv2.setText("signal_960_360_1000_9000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==100) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_1260_480_1000_3000);
            warmup2(pre);
            tv2.setText("signal_1260_480_1000_3000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==101) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_1260_480_1000_5000);
            warmup2(pre);
            tv2.setText("signal_1260_480_1000_5000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==102) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_1260_480_1000_6000);
            warmup2(pre);
            tv2.setText("signal_1260_480_1000_6000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==103) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_1260_480_1000_7000);
            warmup2(pre);
            tv2.setText("signal_1260_480_1000_7000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==104) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_1260_480_1000_9000);
            warmup2(pre);
            tv2.setText("signal_1260_480_1000_9000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==105) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_2160_480_1000_3000);
            warmup2(pre);
            tv2.setText("signal_2160_480_1000_3000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==106) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_2160_480_1000_5000);
            warmup2(pre);
            tv2.setText("signal_2160_480_1000_5000");
            Constants.mode="ofdm";
        }
        if (Constants.file_num==107) {
            short[] pre = FileOperations.readrawasset_binary(context, R.raw.signal_2160_480_1000_9000);
            warmup2(pre);
            tv2.setText("signal_2160_480_1000_9000");
            Constants.mode="ofdm";
        }
//        if (Constants.file_num==53) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.short2);
//            Constants.mode="ofdm";
//            prep(context);
//        }

//        if (Constants.file_num==44) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.preamble_len_check);
//            tv2.setText("preambles");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==42) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_1_signal_debug3_960_0_67_1000_2000_data);
//            tv2.setText("signal_debug3_960_0_268_1000_2000_data");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==43) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.signal_debug3_960_0_536_1000_2000_data);
//            tv2.setText("signal_debug3_960_0_536_1000_2000_data");
//            Constants.mode="mp";
//        }
//        if (Constants.file_num==44) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.r1);
//            tv2.setText("reciprocity");
//            Constants.mode="rc";
//        }
//        if (Constants.file_num==45) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_1_2_signal_debug3_960_0_67_1000_3000_data);
//            tv2.setText("code_1_2_signal_debug3_960_0_67_1000_3000_data");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==46) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_2_3_signal_debug3_960_0_67_1000_3000_data);
//            tv2.setText("code_2_3_signal_debug3_960_0_67_1000_3000_data");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==47) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_1_signal_debug3_960_0_67_1000_2000_data);
//            tv2.setText("code_1_signal_debug3_960_0_67_1000_2000_data");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==48) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_2_signal_debug3_960_0_67_1000_2000_data);
//            tv2.setText("code_2_signal_debug3_960_0_67_1000_2000_data");
//            Constants.mode="ofdm";
//        }
//        if (Constants.file_num==49) {
//            Constants.pulse = FileOperations.readrawasset(context, R.raw.code_4_signal_debug3_960_0_67_1000_2000_data);
//            tv2.setText("code_4_signal_debug3_960_0_67_1000_2000_data");
//            Constants.mode="ofdm";
//        }
    }

    public static void prep(Context context) {
        Log.e("asdf","prep");
//        if (Constants.gap) {
//            Constants.sp1 = new AudioSpeaker(context, Constants.pulse, 48000, -1, 0, false);
//        }
//        else {
//            Constants.sp1 = new AudioSpeaker(context, Constants.pulse, 48000, -1, 0, false);
//        }
//
//        int pulse_length = Constants.tone_len*Constants.SamplingRate;
//        int record_length = pulse_length;
////            if (initSleep) {
//        record_length += (Constants.init_sleep*Constants.SamplingRate);
////            }
//        Constants.ts = System.currentTimeMillis()+"";
//        Constants._OfflineRecorder = new OfflineRecorder(context, Constants.SamplingRate, record_length, Constants.ts);

        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
//        String dir = context.getExternalFilesDir(null).toString();
        File directory = new File(dir);
        File[] files = directory.listFiles();
        Log.e("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains("bottom")) {
                Constants.tv5.setText(Constants.tv5.getText() + "\n" + files[i].getName());
                Log.e("Files", "FileName:" + files[i].getName());
            }
//            FileOperations.readfromfile(MainActivity.av,files[i].getName());
//            break;
        }
        Constants.sview.post(new Runnable() {
            public void run() {
                Constants.sview.smoothScrollTo(0, Constants.tv5.getBottom());
            }
        });
    }

    public static void warmdown(short[] pre) {
        short[] c=Chirp.generateChirpSpeaker(200,200,1,48e3,0,.05);
        Constants.pulse = new short[48000*5+pre.length+c.length];
        for (int i = 0; i < pre.length; i++) {
            Constants.pulse[i] = pre[i];
        }

        int counter=0;
        for (int i = pre.length; i < pre.length+c.length; i++) {
            Constants.pulse[i] = c[counter++];
        }
    }

    public static void warmup(short[] pre) {
        int gap_len=2000;
        int freq = 500;
        int warmup_len=Constants.SamplingRate;
        Constants.pulse = new short[pre.length+warmup_len+gap_len];
        for (int i = 0; i < warmup_len; i++) {
            Constants.pulse[i] = (short)(Math.sin(2 * Math.PI * freq * ((double)i / Constants.SamplingRate))*32767.0*Constants.scale1);
        }

        int counter=0;
        for (int i = warmup_len+gap_len; i < Constants.pulse.length; i++) {
            Constants.pulse[i]=pre[counter++];
        }
    }

    public static void warmup2(short[] pre) {
        int gap_len=(int)(Constants.gap_len*Constants.SamplingRate);
        Constants.pulse = new short[pre.length+gap_len];

        int counter=0;
        for (int i = 0; i < pre.length; i++) {
            Constants.pulse[i] = pre[counter++];
        }
    }

    public static void setTimer(Context context) {
        ((MainActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Constants.task != null && Constants.task.getStatus() == AsyncTask.Status.RUNNING) {
                    Constants.et5.setText(Constants.tone_len + "");
                    if (timer != null) {
                        timer.cancel();
                    }

                    int fnum = contains(Constants.file_num);
//                    if ((Constants.file_num != Constants.repeatNum || !Constants.sw1.isChecked())) {
                    if (fnum == -1 || !Constants.sw1.isChecked()) {
                        int clen = (Constants.tone_len + Constants.init_sleep + 1) * 1000;
//                        if (Constants.file_num > Constants.repeatNum[fnum] + 1 &&
//                            Constants.file_num <= Constants.repeatNum[fnum] + Constants.numToRepeat[fnum]) {
//                            clen = (Constants.tone_len + 1) * 1000;
//                        }
                        Log.e("timer", "time " + clen);
                        timer = new CountDownTimer(clen, 1000) {
                            public void onTick(long millisUntilFinished) {
//                                if (Constants.sw1.isChecked() &&
//                                    Constants.file_num >= Constants.repeatNum[fnum] &&
//                                    Constants.file_num <= Constants.repeatNum[fnum] + Constants.numToRepeat[fnum]) {
//                                    Constants.tv3.setText((Constants.fidxRepeat + 1) + "/" + Constants.numToRepeat + ":" + (millisUntilFinished / 1000));
//                                } else {
                                    Constants.tv3.setText("" + (millisUntilFinished / 1000));
//                                }
                            }

                            public void onFinish() {
                            }
                        }.start();
                    }
                    else if (fnum != -1 && Constants.sw1.isChecked()) {
                        int clen = (Constants.tone_len + Constants.init_sleep + 1) * 1000;
                        if (Constants.file_num > Constants.repeatNum[fnum] + 1 &&
                            Constants.file_num <= Constants.repeatNum[fnum] + Constants.numToRepeat[fnum]) {
                            clen = (Constants.tone_len + 1) * 1000;
                        }
                        timer = new CountDownTimer(clen, 1000) {
                            public void onTick(long millisUntilFinished) {
//                                if (Constants.sw1.isChecked() && Constants.file_num >= Constants.repeatNum && Constants.file_num <= Constants.repeatNum + Constants.numToRepeat) {
//                                    Constants.tv3.setText((Constants.fidxRepeat + 1) + "/" + Constants.numToRepeat + ":" + (millisUntilFinished / 1000));
//                                } else {
                                    Constants.tv3.setText("" + (millisUntilFinished / 1000));
//                                }
                            }

                            public void onFinish() {
                            }
                        }.start();
                    }
                }
            }
        });
    }
}
