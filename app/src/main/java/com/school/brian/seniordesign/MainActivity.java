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
import java.util.concurrent.TimeUnit;
//AWS
//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amazonaws.mobile.config.AWSConfiguration;
//not working
//import com.amazonaws.mobileconnectors.s3.transferutility.*;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AWS
        //AWSMobileClient.getInstance().initialize(this).execute();

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
//    public int signalLevel = 0;
    public int rssi = 0;

//    public void onReceive(WifiManager wifiManager) {
//        int numberOfLevels=5;
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int level=WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
//        System.out.println("Bars =" +level);
//    }


    // gets time stamp
    public String getTimeStamp(){
        String time = "";
        long millis = System.currentTimeMillis();
//        double seconds =  timeInMillis / 1000.0;
//        double minutes = (timeInMillis / (1000.0 * 60)) % 60;
//        double hours = (timeInMillis / (1000.0 * 60 * 60)) % 60;
//        hours = (minutes*60)%60;

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        //sb.append(days);
        //sb.append(" Days ");
        sb.append(hours);
        sb.append(".");
        sb.append(minutes);
        sb.append(".");
        sb.append(seconds);
        sb.append(".");
        sb.append(millis);


        return(sb.toString());
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


    public void updateTextViewWifi(int i){
        //getWifiStrength();
        String j = String.valueOf(i);
        TextView textView = findViewById(R.id.wifistrength);
        textView.setText(j);
    }

    public void getWifiInfo(View view){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);

        if(wifiManager.isWifiEnabled()){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if(String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")){
                Toast.makeText(this, wifiInfo.getSSID()+"", Toast.LENGTH_SHORT).show();
                rssi = wifiInfo.getRssi();
                //wifiInfo.getBSSID();
                //wifiInfo.getFrequency();
                //wifiInfo.getIpAddress();
                //wifiInfo.getLinkSpeed();
                //String d = calculateDistance(rssi);
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

//String calculateDistance(int rssi){
//
//    //power value hardcoded
//    double txPower = -59;
//
//    if (rssi == 0) {
//        return -1.0;
//    }
//
//    double ratio = rssi*1.0/txPower;
//    if (ratio < 1.0) {
//        return Math.pow(ratio,10);
//    }
//    else {
//        double distance =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
//        return String.valueOf(distance);
//    }
//}



