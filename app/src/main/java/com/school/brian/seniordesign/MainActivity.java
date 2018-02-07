package com.school.brian.seniordesign;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//AWS
import com.amazonaws.mobile.client.AWSMobileClient;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AWS
        AWSMobileClient.getInstance().initialize(this).execute();

        Button button = (Button) findViewById(R.id.button);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getWifiInfo(v);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        time = getTimeStamp();
                        updateTextViewStart(time);

                        //update wifi strength while button is down
                        getWifiInfo(v);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        time = getTimeStamp();
                        updateTextViewStop(time);
                        //update wifi strength while button is down
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

    }

    //variables
    public String time = "";
    public int signalLevel = 0;
    public int rssi = 0;

    public void onReceive(WifiManager wifiManager) {
        int numberOfLevels=5;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level=WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
        System.out.println("Bars =" +level);
    }


    // gets time stamp
    public String getTimeStamp(){
        String time = "";
        long timeInMillis = System.currentTimeMillis();
        double seconds =  timeInMillis / 1000.0;
        double minutes = (timeInMillis / (1000.0 * 60)) % 60;
        double hours = (timeInMillis / (1000.0 * 60 * 60)) % 60;

        //
        //time = minutes;
        return String.valueOf(hours);
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


    public void getWifiStrength() {
       // List<ScanResult> results = wifi.getScanResults();
    }

    public void updateTextViewWifi(int i){
        //getWifiStrength();
        TextView textView = findViewById(R.id.wifistrength);
        textView.setText(i);
    }

    public void getWifiInfo(View view){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);

        if(wifiManager.isWifiEnabled()){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if(String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")){
                Toast.makeText(this, wifiInfo.getSSID()+"", Toast.LENGTH_SHORT).show();
                rssi = wifiInfo.getRssi();
                wifiInfo.getBSSID();
                //wifiInfo.getFrequency();
                wifiInfo.getIpAddress();
                wifiInfo.getLinkSpeed();
                updateTextViewWifi(rssi);

            }
            else {
                Toast.makeText(this, "Please connect device to a wifi network", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            wifiManager.setWifiEnabled(true);
        }
    }
}



