package com.school.brian.seniordesign;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        time = getTimeStamp();
                        updateTextViewStart(time);

                        //update wifi strength while button is down
                        updateTextViewWifi();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        time = getTimeStamp();
                        updateTextViewStop(time);
                        //update wifi strength while button is down
                        updateTextViewWifi();
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

    }
    //variables
    public long time = 0;
    public int signalLevel = 0;

    // gets time stamp
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }

    // updates 'START' label wirh start time
    public void updateTextViewStart(long time) {
        TextView textView = findViewById(R.id.start);
        textView.setText(Long.toString(time));
    }

    // updates 'STOP' label wirh start time
    public void updateTextViewStop(long time) {
        TextView textView = findViewById(R.id.stop);
        textView.setText(Long.toString(time));
    }

    public void getWifiStrength() {
       // List<ScanResult> results = wifi.getScanResults();
    }

    public void updateTextViewWifi(){
        getWifiStrength();
        TextView textView = findViewById(R.id.wifistrength);
        textView.setText("");
    }
}



