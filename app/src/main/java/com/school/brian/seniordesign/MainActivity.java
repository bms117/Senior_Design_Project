// access key KIAJZRYD3T2Z4BWI3JA
// private key XICb8jyxVXdb7sIkkdxr0LZAtsVALDa9tMnEpWmQ



package com.school.brian.seniordesign;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//AWS
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import java.io.File;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.services.s3.AmazonS3Client;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static Context Context;

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
                       // PutObject p = new PutObject();

                        wrtieFileOnInternalStorage(context, Filename, time1);
                        uploadData();

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
    String filePath = String.valueOf(Environment.getDataDirectory());
    public File file = new File(filePath);
    public String Body = "";
    public String distanceCalculated = "";

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






    public void uploadData() {

//        AWSCredentials credential = new BasicAWSCredentials("AKIAJZRYD3T2Z4BWI3JA", "XICb8jyxVXdb7sIkkdxr0LZAtsVALDa9tMnEpWmQ");
//        TransferManager manager = new TransferManager(credential);
//// Transfer a file to an S3 bucket.
//        Upload upload = manager.upload("arn:aws:s3:::this-is-a-test-dt04", "AKIAJZRYD3T2Z4BWI3JA", file);
////upload code end

//
        //Initialize AWSMobileClient if not initialized upon the app startup.
        AWSMobileClient.getInstance().initialize(this).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();



        TransferObserver uploadObserver =
                transferUtility.upload(
                        "uploads/README.txt",
                        new File(filePath));

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                Log.v("MyApp", "Could NOT upload");
            }

           });

//         If your upload does not trigger the onStateChanged method inside your
//         TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }

    // This function saved a .txt file to an sd card
//    public void generateTextFileOnSD(Context context, String sFileName, String sBody) {
//        try {
//            File root = new File(Environment.getExternalStorageDirectory(), "Note");
//            if (!root.exists()) {
//                root.mkdirs();
//
//            }
//            File gpxfile = new File(root, sFileName);
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append(sBody);
//            writer.flush();
//            writer.close();
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //Toast.makeText(context, "NOT Saved", Toast.LENGTH_SHORT).show();
//        }
//    }


    public void wrtieFileOnInternalStorage(Context mcoContext,String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(),Filename);
        if(!file.exists()){
            file.mkdir();
            Toast.makeText(getBaseContext(),"file created",Toast.LENGTH_SHORT).show();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

            Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();

        }
    }


}






