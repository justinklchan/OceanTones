package com.example.oceantones;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.NestedScrollView;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv1,tv2;

    private static SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor rotate;
    ImageView iv,iv2;
    static Activity av;

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.av = this;
        getSupportActionBar().hide();
        hideSystemBars();
        tv1 = findViewById(R.id.textView4);
        tv2 = findViewById(R.id.textView8);
        Constants.tv12 = findViewById(R.id.textView12);
        Constants.tv13 = findViewById(R.id.textView13);
        Constants.tv3 = findViewById(R.id.textView9);

        Constants.tv5 = (TextView) findViewById(R.id.debugPane);
        Constants.sview = (NestedScrollView) findViewById(R.id.scrollView);
        Constants.tv5.setMovementMethod(new ScrollingMovementMethod());

        Constants.startb=findViewById(R.id.button);
        Constants.stopb=findViewById(R.id.button2);
        Constants.prepb=findViewById(R.id.button3);
        Log.e("asdf",android.os.Build.MODEL);
        Constants.stopb.setEnabled(false);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        rotate = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, rotate, SensorManager.SENSOR_DELAY_FASTEST);

        Constants.clayout=findViewById(R.id.clayout);
        Constants.et1=findViewById(R.id.editTextNumber);
        Constants.et2=findViewById(R.id.editTextNumber2);
        Constants.et3=findViewById(R.id.editTextNumber3);
        Constants.et4=findViewById(R.id.editTextNumber4);
        Constants.et5=findViewById(R.id.editTextNumber5);
        Constants.et6=findViewById(R.id.editTextNumber6);
        Constants.et7=findViewById(R.id.editTextNumber7);
        Constants.et8=findViewById(R.id.editTextNumber8);
        Constants.sw1=findViewById(R.id.switch1);
        Constants.sw2=findViewById(R.id.switch2);
        Constants.sw3=findViewById(R.id.switch3);
        Constants.sw4=findViewById(R.id.switch4);
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

        iv = findViewById(R.id.imageView);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et1.getText().toString();
                if (ss.length()>0) {
                    int ii = Integer.parseInt(ss)+1;
                    if (ii >= 0) {
                        editor.putInt("file_num", ii);
                        Constants.et1.setText(ii + "");
                        editor.commit();
                        Constants.file_num = ii;
                        Constants.setTones(tv2, av);
                    }
                }
            }
        });

        iv2 = findViewById(R.id.imageView2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et1.getText().toString();
                if (ss.length()>0) {
                    int ii = Integer.parseInt(ss)-1;
                    if (ii >= 0) {
                        editor.putInt("file_num", ii);
                        Constants.et1.setText(ii + "");
                        editor.commit();
                        Constants.file_num = ii;
                        Constants.setTones(tv2, av);
                    }
                }
            }
        });

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
        Constants.sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("gap", isChecked);
                editor.commit();
                Constants.gap  = isChecked;
                Constants.setTones(tv2,av);
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
                    int time = Integer.parseInt(ss);
                    if (time >= 30) {
                        time = 30;
                    }
                    editor.putInt("init_sleep", time);
                    editor.commit();
                    Constants.init_sleep = time;
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
//                    Constants.setTones(tv2,av);
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
        Constants.et8.addTextChangedListener(new TextWatcher() {
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
                String ss = Constants.et8.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("reps", Integer.parseInt(ss));
                    editor.commit();
                    Constants.reps = Integer.parseInt(ss);
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

//        email();
    }

    public void email() {

//        Log.e("asdf","asdf1");

        String dir = getExternalFilesDir(null).toString();
        File file = new File(dir + File.separator + "1642486551703-bottom.txt");

        Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", file);

        Log.e("asdf",uri.toString());

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//        emailIntent.setType("message/rfc822");
//        emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"chan.justin.hk@gmail.com"});
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "body text");
//        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, rotate, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void prep(View v) {
        Constants.prep(this);
    }

    int offset = 0;
    float[] orientation = new float[3];
    float[] rMat = new float[9];
    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.e("imu",event.sensor.equals(rotate)+"");
        if (Constants.sensorFlag && Constants.imu) {
            if (event.sensor.equals(accelerometer)) {
                Constants.acc.add(event.values[0]+","+event.values[1]+","+event.values[2]+"\n");
            }
            else if (event.sensor.equals(gyroscope)) {
                Constants.gyro.add(event.values[0]+","+event.values[1]+","+event.values[2]+"\n");
            }
            else if (event.sensor.equals(rotate)) {
//                Log.e("asdf","rotate");
                SensorManager.getRotationMatrixFromVector( rMat, event.values );
                int a=(int)Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]);
                if (a < 0) {
                    a=180+(180-Math.abs(a));
                }
                a=(int)(Math.round(a/5.0) * 5);

                if (Constants.azimuth.size()==0) {
                    offset = a;
                }

                int delta = a-offset;
                int adjusted_azimuth = delta;
                if (adjusted_azimuth<0) {
                    adjusted_azimuth=(360+adjusted_azimuth);
                }
                if (Constants.gap) {
                    adjusted_azimuth = 360 - adjusted_azimuth;
                }

                Constants.azimuth.add(adjusted_azimuth);

                int p=(int)Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[1]);
                p=(int)(Math.round(p/5.0) * 5);
                Constants.pitch.add(p);

                Constants.tv12.setText(adjusted_azimuth+"");
                Constants.tv13.setText(p+"");
            }
        }
    }

    public void onstart(View v) {
        onstarthelper();
    }

    public void onstarthelper() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, rotate, SensorManager.SENSOR_DELAY_FASTEST);

        Log.e("asdf", "onstart");

        String bigts="";
        if (Constants.contains(Constants.file_num) != -1) {
            Log.e("asdf", "option 1");
            String longts="";
            String ts=System.currentTimeMillis()+"";
                Constants.ts = ts;
                String trim = ts.substring(ts.length() - 4, ts.length());
                bigts += trim + "\n";
            tv1.setText(bigts);

            Constants.task = new SendChirpAsyncTask(this, longts, tv2, Constants.tv3);
            Constants.task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            Log.e("asdf", "option 2");
            String ts= "";
//            if (Constants.sp1 == null) {
//                Log.e("asdf", "2a");
                ts = System.currentTimeMillis() + "";
                Constants.ts = ts;
//            }
//            else {
//                Log.e("asdf", "2b");
//                ts = Constants.ts;
//            }
            String trim = ts.substring(ts.length() - 4, ts.length());
            bigts+=trim+"\n";
            tv1.setText(bigts);

            Constants.task = new SendChirpAsyncTask(this, ts, tv2, Constants.tv3);
            Constants.task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void onstop(View v) {
        if (Constants.timer!=null) {
            Constants.timer.cancel();
        }
        Constants.tv3.setText("");

        sensorManager.unregisterListener(this);

        Log.e("asdf","onstop");
        Constants.sensorFlag=false;
        if (Constants.acc != null && Constants.acc.size() > 0) {
            FileOperations.writeSensors(this, Constants.ts+".txt");
        }
        if (Constants._OfflineRecorder!=null) {
            Constants._OfflineRecorder.halt();
        }
        Constants.task.cancel(true);
        if (Constants.sp1!=null) {
            Constants.sp1.pause();
        }
        Constants.enableUI();

        for (int i = 0; i < Constants.repeatNum.length; i++) {
            if (Constants.file_num >= Constants.repeatNum[i] &&
                Constants.file_num <= Constants.repeatNum[i] + Constants.numToRepeat[i]) {
                Constants.file_num = Constants.repeatNum[i];
                break;
            }
        }
        Constants.clayout.setBackgroundColor(Color.argb(255, 255, 255, 255));
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

    public static void unreg(Activity av) {
        sensorManager.unregisterListener((MainActivity)av);
    }
}