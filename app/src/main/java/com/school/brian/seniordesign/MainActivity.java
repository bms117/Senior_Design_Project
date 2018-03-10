package com.school.brian.seniordesign;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//AWS
import com.amazonaws.mobile.client.AWSMobileClient;
import java.io.File;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.services.s3.AmazonS3Client;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AWS
        AWSMobileClient.getInstance().initialize(this).execute();

        Button button = (Button) findViewById(R.id.button);

        button.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getWifiInfo(v);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        time = getTimeStamp();
                        time1 = time;
                        updateTextViewStart(time);

                        //update wifi strength while button is down
                        getWifiInfo(v);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        time = getTimeStamp();
                        time2 = time;
                        updateTextViewStop(time);
                        //update wifi strength while button is down

                        //save time stamp interval and wifi strength to file to send to AWS
                        PutObject p = new PutObject();

                        try(FileWriter fw = new FileWriter(Filename, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw))
                        {
                            out.println(time1);
                            out.println(time2);
                            out.println(distanceCalculated);
                        } catch (IOException e) {
                            //exception handling left as an exercise for the reader
                        }

                        p.generateTextFileOnSD(mContext, Filename, Body);
                       // p.uploadData();

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

    }

    //variables
    public String time = "";
    public String time1, time2;
    public double rssi = 0;
    public String Filename = "File.txt";
    public String Body = "";
    public String distanceCalculated;

    // gets time stamp
    public String getTimeStamp() {
        long millis = System.currentTimeMillis();

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(hours);
        sb.append(".");
        sb.append(minutes);
        sb.append(".");
        sb.append(seconds);
        sb.append(".");
        sb.append(millis);

        return (sb.toString());
    }

    // updates 'START' label wirh start time
    public void updateTextViewStart(String time) {
        TextView textView = findViewById(R.id.start);
        textView.setText(time);
    }

    // updates 'STOP' label wirh start time
    public void updateTextViewStop(String time) {
        TextView textView = findViewById(R.id.stop);
        textView.setText(time);
    }

    public void updateTextViewWifi(double i) {
        String j = String.valueOf(i);
        TextView textView = findViewById(R.id.wifistrength);
        textView.setText(j);
    }


    // Gets WiFi info and updates the text on screen with distance and time stamps
    public void getWifiInfo(View view) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")) {
                //Toast.makeText(this, wifiInfo.getSSID() + "", Toast.LENGTH_SHORT).show();
                rssi = wifiInfo.getRssi();
                //wifiInfo.getBSSID();
                //wifiInfo.getFrequency();
                //wifiInfo.getIpAddress();
                //wifiInfo.getLinkSpeed();

                double d = calculateDistance(rssi);
                updateTextViewWifi(d);

            } else {
                Toast.makeText(this, "Please connect device to a wifi network", Toast.LENGTH_SHORT).show();
            }
        } else {
            wifiManager.setWifiEnabled(true);
        }
    }

    double calculateDistance(double rssi) {
        double r = rssi;
        //power value hardcoded
        double txPower = -59;

        if (r == 0) {
            return -1.0;
        }

        double ratio = r * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            distanceCalculated = String.valueOf(distance);
            return distance;
        }
    }
}



