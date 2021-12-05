package com.example.oceantones;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv1,tv2,tv3;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.textView4);
        tv2 = findViewById(R.id.textView8);
        tv3 = findViewById(R.id.textView9);

        Constants.startb=findViewById(R.id.button);
        Constants.stopb=findViewById(R.id.button2);
        Log.e("asdf",android.os.Build.MODEL);
        Constants.stopb.setEnabled(false);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);

        Constants.et1=findViewById(R.id.editTextNumber);
        Constants.et2=findViewById(R.id.editTextNumber2);
        Constants.et3=findViewById(R.id.editTextNumber3);
        Constants.et4=findViewById(R.id.editTextNumber4);
        Constants.et5=findViewById(R.id.editTextNumber5);
        Constants.et6=findViewById(R.id.editTextNumber6);
        Constants.et7=findViewById(R.id.editTextNumber7);
        Constants.sw1=findViewById(R.id.switch1);
        Constants.sw2=findViewById(R.id.switch2);
        Constants.sw3=findViewById(R.id.switch3);
        Constants.init(this);
        Constants.setTones(tv2,this);

//        Constants.et2.setEnabled(false);
//        Constants.et3.setEnabled(false);
        Constants.et7.setEnabled(false);

        String[] perms = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        ActivityCompat.requestPermissions(this,
                perms,
                1234);
        Activity av = this;
//        Constants.et1.setText(Constants.file_num);

        Constants.sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("transmit", isChecked);
                editor.commit();
                Constants.transmit  = isChecked;
            }
        });
        Constants.sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("stereo", isChecked);
                editor.commit();
                Constants.stereo  = isChecked;
            }
        });
        Constants.sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("imu", isChecked);
                editor.commit();
                Constants.imu  = isChecked;
            }
        });
        Constants.et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et1.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("file_num", Integer.parseInt(ss));
                    editor.commit();
                    Constants.file_num = Integer.parseInt(ss);

                    if (Constants.file_num >= 1 && Constants.file_num <= 5) {
                        Constants.setTones(tv2, av);
//                        Constants.mode="tones";
                    }
                    else if (Constants.file_num >= 6){
                        Constants.setTones(tv2, av);
//                        Constants.mode="pulse";
                    }
                }
            }
        });

        Constants.et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et2.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("scale1", Float.parseFloat(ss));
                    editor.commit();
                    Constants.scale1 = Float.parseFloat(ss);
                }
            }
        });

        Constants.et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et3.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("scale2", Float.parseFloat(ss));
                    editor.commit();
                    Constants.scale2 = Float.parseFloat(ss);
                }
            }
        });

        Constants.et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et4.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("init_sleep", Integer.parseInt(ss));
                    editor.commit();
                    Constants.init_sleep = Integer.parseInt(ss);
                }
            }
        });

        Constants.et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et5.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("tone_len", Integer.parseInt(ss));
                    editor.commit();
                    Constants.tone_len = Integer.parseInt(ss);
                    Constants.setTones(tv2,av);
                }
            }
        });

        Constants.et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et6.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("gap_len", Float.parseFloat(ss));
                    editor.commit();
                    Constants.gap_len = Float.parseFloat(ss);
                    Constants.setTones(tv2,av);
                }
            }
        });
        Constants.et7.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et7.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("freq_lim", Integer.parseInt(ss));
                    editor.commit();
                    Constants.freq_lim = Integer.parseInt(ss);
                }
            }
        });

//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && !notificationManager.isNotificationPolicyAccessGranted()) {
//            Intent intent = new Intent(
//                    android.provider.Settings
//                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//            startActivity(intent);
//        }
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e("imu",event.values[0]+","+Constants.sensorFlag);
        if (Constants.sensorFlag && Constants.imu) {
            if (event.sensor.equals(accelerometer)) {
                Constants.acc.add(event.values[0]+","+event.values[1]+","+event.values[2]+"\n");
            }
            else if (event.sensor.equals(gyroscope)) {
                Constants.gyro.add(event.values[0]+","+event.values[1]+","+event.values[2]+"\n");
            }
        }
    }

    SendChirpAsyncTask task;
    public void onstart(View v) {
        onstarthelper();
    }

    public void onstarthelper() {
        Log.e("asdf", "onstart");

        String bigts="";
        if (Constants.file_num==8) {
            Constants.pulse = SendChirpAsyncTask.continuouspulse(
                    1, 1000,
                    5000, .5,
                    Constants.SamplingRate,
                    Constants.scale1);
            tv2.setText("chirp 1s (1-5khz)");
            Constants.sp1 = new AudioSpeaker(this, Constants.pulse, 48000, 0, 0);
            Constants.sp1.play(1);

            try {
                Thread.sleep(2000);
            }
            catch(Exception e) {
                Log.e("asdf",e.getMessage());
            }

            for (int i = 0; i < 1; i++) {
                try {
                    while (Constants.writing) {
                        Thread.sleep(100);
                    }
                    Constants.acc = new LinkedList<>();
                    Constants.gyro = new LinkedList<>();

                    Constants.file_num = 9 + i;
                    Constants.setTones(tv2, this);
                    String ts = System.currentTimeMillis() + "";
                    Constants.ts = ts;
                    String trim = ts.substring(ts.length() - 4, ts.length());
                    bigts += trim + "\n";

                    if (i == 0) {
                        task = new SendChirpAsyncTask(this, ts, tv2, tv3, true);
                    } else {
                        task = new SendChirpAsyncTask(this, ts, tv2, tv3, false);
                    }
                    task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR).get();
                    Thread.sleep(1000);
                }
                catch(Exception e) {
                    Log.e("asdf",e.getMessage());
                }
                Log.e("asdf","NEXT");
            }
            Constants.file_num=8;
        }
        else {
            String ts = System.currentTimeMillis() + "";
            Constants.ts = ts;
            String trim = ts.substring(ts.length() - 4, ts.length());
            bigts+=trim+"\n";

            task = new SendChirpAsyncTask(this, ts, tv2, tv3, true);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        tv1.setText(bigts);
    }

    public void onstop(View v) {
        Log.e("asdf","onstop");
        Constants.sensorFlag=false;
        FileOperations.writeSensors(this,Constants.ts);
        task.cancel(true);
        if (Constants._OfflineRecorder!=null) {
            Constants._OfflineRecorder.halt();
        }
        if (Constants.sp1!=null) {
            Constants.sp1.pause();
        }
        Constants.enableUI();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}