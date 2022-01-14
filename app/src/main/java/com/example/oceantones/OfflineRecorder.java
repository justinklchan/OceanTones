package com.example.oceantones;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AutomaticGainControl;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class OfflineRecorder extends Thread {
    public boolean recording;
    int samplingfrequency;
    public short[] samples;
    public short[] samples1;
    public short[] samples2;
    public short[] temp;
    int count;
    int count1;
    int count2;
    AudioRecord rec;
    int minbuffersize;
    Context av;
    String filename;
    int channels;

    public OfflineRecorder(Context av, int samplingfrequency, int arrLength, String filename) {
        Log.e("rec","offline recorder");
        this.filename = filename;
        this.av = av;
        count = 0;
        count1 = 0;
        count2 = 0;
        this.samplingfrequency = samplingfrequency;

//        int channels = AudioFormat.CHANNEL_CONFIGURATION_STEREO;
        if (Constants.stereo) {
            channels = AudioFormat.CHANNEL_IN_STEREO;
        }
        else {
            channels = AudioFormat.CHANNEL_IN_MONO;
        }

        minbuffersize = AudioRecord.getMinBufferSize(
                samplingfrequency,
                channels,
                AudioFormat.ENCODING_PCM_16BIT);

        if (ActivityCompat.checkSelfPermission(av, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asdf","perm error");
        }
        rec = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
//                MediaRecorder.AudioSource.UNPROCESSED,
                samplingfrequency, channels,
                AudioFormat.ENCODING_PCM_16BIT,
                minbuffersize);

//        if (AutomaticGainControl.isAvailable()) {
//            AutomaticGainControl agc = AutomaticGainControl.create(
//                    rec.getAudioSessionId()
//            );
//            agc.setEnabled(true);
//        }

        temp = new short[minbuffersize];
//        Utils.log("offlinerecorder length " + arrLength);
        if (channels == AudioFormat.CHANNEL_IN_MONO) {
            samples = new short[arrLength];
        }
        else if (channels == AudioFormat.CHANNEL_IN_STEREO) {
            samples1 = new short[arrLength];
            samples2 = new short[arrLength];
        }

        boolean AGC=true;
        if (AGC) {
            if (AutomaticGainControl.isAvailable()) {
                AutomaticGainControl agc = AutomaticGainControl.create(
                        rec.getAudioSessionId()
                );
                agc.setEnabled(true);
            }
        }
        else {
            if (AutomaticGainControl.isAvailable()) {
                AutomaticGainControl agc = AutomaticGainControl.create(
                        rec.getAudioSessionId()
                );
                agc.setEnabled(false);
            }
        }
    }

    public void halt() {
//        Utils.log("halt");
        if (this.rec.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            this.recording = false;
            this.rec.stop();
            this.rec.release();
            Log.e("asdf","halt "+(samples==null));
            if (channels == AudioFormat.CHANNEL_IN_MONO) {
                FileOperations.writetofile(MainActivity.av, samples, filename+".txt");
            }
            else if (channels == AudioFormat.CHANNEL_IN_STEREO) {
                FileOperations.writetofile(MainActivity.av, samples1, filename+"-top.txt");
                FileOperations.writetofile(MainActivity.av, samples2, filename+"-bottom.txt");

            }

            Constants.sensorFlag=false;
            FileOperations.writeSensors(MainActivity.av,filename+".txt");
        }
    }

    public void run() {
        Log.e("rec","rec");
        if (channels == AudioFormat.CHANNEL_IN_MONO) {
            Log.e("rec","mono");
            count = 0;
            int bytesread;
            rec.startRecording();
            recording = true;
            while (recording) {
                bytesread = rec.read(temp, 0, minbuffersize);
                for (int i = 0; i < bytesread; i++) {
                    if (count >= samples.length && rec.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                        rec.stop();
                        rec.release();
                        recording = false;
                        FileOperations.writetofile(MainActivity.av, samples, filename+".txt");

                        Constants.sensorFlag=false;
                        FileOperations.writeSensors(MainActivity.av,filename+".txt");
                        break;
                    } else if (count < samples.length) {
                        samples[count] = temp[i];
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }
        else if (channels == AudioFormat.CHANNEL_IN_STEREO) {
            Log.e("rec","stereo");
            count1 = 0;
            count2 = 0;
            int bytesread;
            rec.startRecording();
            recording = true;
            while (recording) {
                bytesread = rec.read(temp, 0, minbuffersize);
                if (count1 < samples1.length) {
//                    Log.e("rec",count1+","+samples1.length);
                    for (int i = 0; i < bytesread; i+=2) {
                        if (count1 < samples1.length && count2 < samples2.length) {
                            if (android.os.Build.MODEL.equals("SM-N975U1")) {
                                //s10
                                samples2[count1] = temp[i];
                                samples1[count2] = temp[i + 1];
                            }
                            else if (android.os.Build.MODEL.equals("SM-G950U")) {
                                //s8
                                samples1[count1] = temp[i];
                                samples2[count2] = temp[i + 1];
                            }
                            else if (android.os.Build.MODEL.equals("SM-G960U")) {
                                //s9
                                samples1[count1] = temp[i];
                                samples2[count2] = temp[i + 1];
                            }
                            else {
                                samples1[count1] = temp[i];
                                samples2[count2] = temp[i + 1];
                            }
                            count1++;
                            count2++;
                        }
                    }
                }
                else if (count1 >= samples1.length && rec.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
//                else if (count1 >= samples1.length){
                    Log.e("asdf","OVER");
                    rec.stop();
                    rec.release();
                    recording = false;

                    Constants.sensorFlag=false;
                    new Runnable() {
                        public void run() {
                            FileOperations.writetofile(MainActivity.av, samples1, filename+"-top.txt");
                            FileOperations.writetofile(MainActivity.av, samples2, filename+"-bottom.txt");
                            FileOperations.writeSensors(MainActivity.av,filename+".txt");
                        }
                    }.run();
                    break;
                }
            }
        }
    }
}
