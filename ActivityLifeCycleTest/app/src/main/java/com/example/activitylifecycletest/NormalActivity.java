package com.example.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class NormalActivity extends AppCompatActivity {
    private static final String TAG = "NormalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this.toString());
        Log.d(TAG, "Task id is " + getTaskId());
        setContentView(R.layout.normal_layout);
        Button button1 = (Button) findViewById(R.id.button_test_standard);
        button1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            Intent intent = new Intent(NormalActivity.this, NormalActivity.class);
            startActivity(intent);
        }});
        Button button2 = (Button) findViewById(R.id.button_test_singleTop);
        button2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            Intent intent = new Intent(NormalActivity.this, MainActivity.class);
            startActivity(intent);
        }});
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
