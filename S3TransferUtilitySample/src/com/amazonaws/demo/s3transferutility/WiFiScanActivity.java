//package com.amazonaws.demo.s3transferutility;
//
///**
// * Created by brian on 4/2/18.
// */
//
//
//
//import java.util.List;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class WiFiScanActivity extends Activity {
//    TextView mainText;
//    WifiManager mainWifi;
//    WifiReceiver receiverWifi;
//    List<ScanResult> wifiList;
//    StringBuilder sb = new StringBuilder();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mainText = (TextView) findViewById(R.id.tv1);
//        mainWifi = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        if (mainWifi.isWifiEnabled() == false)
//        {
//            // If wifi disabled then enable it
//            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
//                    Toast.LENGTH_LONG).show();
//
//            mainWifi.setWifiEnabled(true);
//        }
//        receiverWifi = new WifiReceiver();
//        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//        mainWifi.startScan();
//        mainText.setText("Starting Scan...");
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 0, 0, "Refresh");class WifiReceiver extends BroadcastReceiver {
//
//        // This method call when number of wifi connections changed
//        public void onReceive(Context c, Intent intent) {
//
//            sb = new StringBuilder();
//            wifiList = mainWifi.getScanResults();
//            sb.append("\n        Number Of Wifi connections :"+wifiList.size()+"\n\n");
//
//            for(int i = 0; i < wifiList.size(); i++){
//
//                sb.append(new Integer(i+1).toString() + ". ");
//                sb.append((wifiList.get(i)).toString());
//                sb.append("\n\n");
//            }
//
//            mainText.setText(sb);
//        }
//
//    }
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        mainWifi.startScan();
//        mainText.setText("Starting Scan");
//        return super.onMenuItemSelected(featureId, item);
//    }
//
//    protected void onPause() {
//        unregisterReceiver(receiverWifi);
//        super.onPause();
//    }
//
//    protected void onResume() {
//        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//        super.onResume();
//    }
//
//    // Broadcast receiver class called its receive method
//    // when number of wifi connections changed
//
//
//}
