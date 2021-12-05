package com.example.oceantones;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

public class SendChirpAsyncTask extends AsyncTask<Void, Void, Void> {
    Activity av;
    String ts;
    TextView tv;
    TextView tv2;
    boolean initSleep;
    public SendChirpAsyncTask(Activity activity, String ts, TextView tv, TextView tv2, boolean initSleep) {
        this.av = activity;
        this.ts = ts;
        this.tv = tv;
        this.tv2 = tv2;
        this.initSleep = initSleep;
    }

    int num_tones=0;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("asdf","preexec "+ts);
        Constants.disableUI();
        num_tones = Constants.tones.length;
        Constants.sensorFlag=true;
        Constants.writing=true;
    }

    public static short[] preamble2() {
        short[] preamble = continuouspulse(
                1, 1000,
                5000, Constants.gap_len,
                Constants.SamplingRate,
                Constants.scale1);
        short[] preamble2 = continuouspulse(
                1, 5000,
                1000, Constants.gap_len,
                Constants.SamplingRate,
                Constants.scale1);
        short[] preambleout=new short[preamble.length+preamble2.length];
        int cc=0;
        for (Short s : preamble) {
            preambleout[cc++]=s;
        }
        for (Short s : preamble2) {
            preambleout[cc++]=s;
        }
        return preambleout;
    }

    public static short[] preamble() {
        short[] preamble = continuouspulse(
                .5, 1000,
                5000, 0,
                Constants.SamplingRate,
                Constants.scale1);
        short[] preamble2 = continuouspulse(
                .5, 5000,
                1000, 0,
                Constants.SamplingRate,
                Constants.scale1);
        short[] preambleout=new short[preamble.length+preamble2.length];
        int cc=0;
        for (Short s : preamble) {
            preambleout[cc++]=s;
        }
        for (Short s : preamble2) {
            preambleout[cc++]=s;
        }
        return preambleout;
    }

    public static short[] continuouspulse(double length, double startfreq,
                                          double endfreq,
                                          double gap, int Sampling_Rate,
                                          double scale) {
        short[] signal = new short[(int) ((length + gap)*Constants.SamplingRate)];
        try {
            short[] signal1;
            int index = 0;
            signal1 = Chirp.generateChirpSpeaker(startfreq, endfreq, length, Sampling_Rate, 0,scale);
//            String signal_str=Arrays.toString(signal1);
            for (int j = 0; j < signal1.length; j++) {
                signal[index++] = (short)(signal1[j]);
            }
            for (int j = 0; j < gap * Sampling_Rate; j++) {
                signal[index++] = 0;
            }
        }
        catch (Exception e) {
            Log.e("asdf",e.toString());
        }

        return signal;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.e("asdf","postexec "+ts);

        Constants.enableUI();

        Constants.sp1=null;
        Log.e("asdf","RECORDER NULL");
        Constants._OfflineRecorder = null;
    }

//        if (Constants._OfflineRecorder != null) {
//            Constants._OfflineRecorder.halt();
//            FileOperations.writetofile(av, Constants._OfflineRecorder.samples, ts); // Write the microphone recording to a file.
//        }

    public short[] combine(short[] s1,short[]s2){
        short[] s3=new short[s1.length+s2.length];
        int counter=0;
        for (Short s : s1) {
            s3[counter++]=s;
        }
        for (Short s : s2) {
            s3[counter++]=s;
        }
        return s3;
    }

    public short[] combine(short[]preamble,short[][] dat) {
        short[] out = new short[preamble.length+(dat.length*dat[0].length)];
        int counter=0;
        for (Short s : preamble) {
            out[counter++]=s;
        }

        for (int i = 0; i < dat.length; i++) {
            for (int j = 0; j < dat[i].length; j++) {
                out[counter++]=dat[i][j];
            }
        }
        return out;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("asdf","bg "+ts);

//        if (Constants.file_num >= 1 && Constants.file_num <= 5) {
        if (Constants.mode.equals("tone")) {
            int preamble_len=9600;

            int preamble_length=(int)Math.ceil(preamble_len+(Constants.gap_len*Constants.SamplingRate));
            int single_tone_length=preamble_length+(int)Math.ceil((Constants.SamplingRate*Constants.tone_len));

            int all_tone_length=(int)(num_tones*(single_tone_length+(Constants.gap_len*Constants.SamplingRate)));

            int init_sleep_length = Constants.init_sleep * Constants.SamplingRate;
            int record_length = (int)Math.ceil(preamble_length+all_tone_length);
            if (initSleep) {
                record_length = (int)Math.ceil(init_sleep_length+preamble_length+all_tone_length);
            }
            int slackTime = 2 * Constants.SamplingRate;
            record_length += slackTime;

            Constants._OfflineRecorder = new OfflineRecorder(av, Constants.SamplingRate, record_length, ts);
            Constants._OfflineRecorder.start();

            short[] preamble = preamble();
//            String aa=Arrays.toString(preamble);

            if (initSleep) {
                //INIT
                Log.e("asdf", "init sleep");
                try {
                    Thread.sleep((long) (Constants.init_sleep * 1000));
                } catch (Exception e) {
                    Log.e("asdf", e.getMessage());
                }
            }

            // PULSE
            for (int i = 0; i < Constants.tones.length; i++) {
                long t1 = System.currentTimeMillis();
                float s1 = Constants.scale1;
                float s2 = Constants.scale2;

                if (Constants.tones[i] <= Constants.freq_lim) {
                    s1 = 1;
                    s2 = 1;
                }

                short[] pulse = continuouspulse(
                        1, Constants.tones[i],
                        Constants.tones[i], 0,
                        Constants.SamplingRate,
                        s1);
//                FileOperations.writetofile(av,pulse,"input-"+Constants.tones[i]+".txt");

                short[] pulse2 = combine(preamble, pulse);
                //            String pulse2_str=Arrays.toString(pulse2);
                Log.e("asdf", "time " + (System.currentTimeMillis() - t1));

                Constants.sp1 = new AudioSpeaker(av, pulse2, 48000, Constants.tone_len, preamble.length);
                Log.e("asdf", "play tone " + Constants.tones[i]);

                int finalI = i;
                av.runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        tv2.setText(Constants.tones[finalI] + "");
                    }
                }));

                if (Constants.transmit) {
                    Constants.sp1.play(s2);
                }

                try {
                    int stime = (single_tone_length / Constants.SamplingRate) * 1000;
                    Log.e("asdf", "sleep for " + stime);
                    Thread.sleep((long) stime);
                } catch (Exception e) {
                    Log.e("asdf", e.getMessage());
                }
                if (Constants.sp1 != null) {
                    Constants.sp1.reset();
                    Constants.sp1 = null;
                }

                try {
                    int stime = (int) (Constants.gap_len * 1000);
                    Log.e("asdf", "sleep gap for " + stime);
                    Thread.sleep((long) stime);
                    Log.e("asdf","sleep gap done");
                } catch (Exception e) {
                    Log.e("asdf", e.getMessage());
                }
            }

            Log.e("asdf", "done sleeping");
        }
        else if (Constants.mode.equals("mp")) {
            int pulse_length = Constants.tone_len*Constants.SamplingRate;
            int record_length = (Constants.init_sleep*Constants.SamplingRate)+pulse_length;
            Log.e("asdf","RECORD "+record_length);
            Constants._OfflineRecorder = new OfflineRecorder(av, Constants.SamplingRate, record_length, ts);
            Log.e("asdf","STATE "+Constants._OfflineRecorder.getState()+","+Constants._OfflineRecorder.rec.getRecordingState());
            Constants._OfflineRecorder.start();

            Constants.sp1 = new AudioSpeaker(av, Constants.pulse, 48000, -1, 0);

            if (initSleep) {
                Log.e("asdf", "init sleep");
                try {
                    Thread.sleep((long) (Constants.init_sleep * 1000));
                } catch (Exception e) {
                    Log.e("asdf", e.getMessage());
                }
            }

            if (Constants.transmit) {
                Constants.sp1.play(Constants.scale2);
            }

            try {
                int stime = (record_length/Constants.SamplingRate)*1000;
                Log.e("asdf", "sleep for " + stime);
                Thread.sleep((long) stime);
            } catch (Exception e) {
                Log.e("asdf", e.getMessage());
            }
            Constants.sp1.reset();
            Constants.sp1 = null;
        }
        return null;
    }
}
