package com.example.activitytest;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        //for Intent that pass data
        String data = intent.getStringExtra("extra_data");
        if (data!=null) {
            Log.d(TAG, data);
        }
    }
}