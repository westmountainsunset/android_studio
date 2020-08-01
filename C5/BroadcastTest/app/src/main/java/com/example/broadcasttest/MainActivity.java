package com.example.broadcasttest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private  NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new
                        Intent("com.example.broadcasttest.MY_BROADCAST");
                /**intent.setComponent(new ComponentName(MainActivity.this, MyBroadcastReceiver.class));
                  intent.setPackage(getPackageName());
                   code above for fix static register bc receiver*/
                //sendBroadcast(intent);
                sendOrderedBroadcast(intent, null);
            }
        });
        IntentFilter filter = new IntentFilter("com.example.broadcasttest.MY_BROADCAST");
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

        registerReceiver(myBroadcastReceiver, filter);


        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    /**
     *  todo realize the code of test network available?.
     *  */
    private class NetworkChangeReceiver extends BroadcastReceiver{
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =(ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkCapabilities networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                boolean isConn = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                if (isConn) {
                    Toast.makeText(context, "network available", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(context, "network unavailable",Toast.LENGTH_SHORT).show();
            }
        }
    }
}