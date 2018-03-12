//// This class has the ability to put data into AWS
//
//package com.school.brian.seniordesign;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Build;
//import android.os.Environment;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.amazonaws.mobile.config.AWSConfiguration;
//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
//import com.amazonaws.services.s3.AmazonS3Client;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.regex.Pattern;
//
//public class PutObject extends Activity {
//
////    public void uploadData() {
////
////        // Initialize AWSMobileClient if not initialized upon the app startup.
////        // AWSMobileClient.getInstance().initialize(this).execute();
////
////        TransferUtility transferUtility =
////                TransferUtility.builder()
////                        .context(getApplicationContext())
////                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
////                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
////                        .build();
//////
//////        String filePath = Environment.getExternalStorageDirectory().getPath();
//////
//////        TransferObserver uploadObserver =
//////                transferUtility.upload(
//////                        "https://s3.us-east-2.amazonaws.com/this-is-a-test-dt04/AWS_TEST.txt",
//////                        new File(filePath));
//////
//////        uploadObserver.setTransferListener(new TransferListener() {
//////
//////            @Override
//////            public void onStateChanged(int id, TransferState state) {
//////                if (TransferState.COMPLETED == state) {
//////                    // Handle a completed upload.
//////                }
//////            }
//////
//////            @Override
//////            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//////                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
//////                int percentDone = (int) percentDonef;
//////
//////                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
//////            }
//////
//////            @Override
//////            public void onError(int id, Exception ex) {
//////                // Handle errors
//////            }
//////
//////        });
//////
//////        // If your upload does not trigger the onStateChanged method inside your
//////        // TransferListener, you can directly check the transfer state as shown here.
//////        if (TransferState.COMPLETED == uploadObserver.getState()) {
//////            // Handle a completed upload.
//////        }
////    }
////
////    // This function saved a .txt file to an sd card
////    public void generateTextFileOnSD(Context context, String sFileName, String sBody) {
////        try {
////            File root = new File(Environment.getExternalStorageDirectory(), "Note");
////            if (!root.exists()) {
////                root.mkdirs();
////
////            }
////            File gpxfile = new File(root, sFileName);
////            FileWriter writer = new FileWriter(gpxfile);
////            writer.append(sBody);
////            writer.flush();
////            writer.close();
////            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
////        } catch (IOException e) {
////            e.printStackTrace();
////            //Toast.makeText(context, "NOT Saved", Toast.LENGTH_SHORT).show();
////        }
////    }
////}