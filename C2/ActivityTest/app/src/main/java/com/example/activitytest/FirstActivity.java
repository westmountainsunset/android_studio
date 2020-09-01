package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pass layout .xml file in res
        setContentView(R.layout.first_layout);
        //bt_1 for test Toast
        Button bt_1 = (Button) findViewById(R.id.button_1);
        /*todo: java anonymous class syntax*/
        bt_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "you clicked button 1",
                              Toast.LENGTH_SHORT).show();
            }
        });
        //bt_2 for test finish => back in android os
        Button bt_2 = (Button) findViewById(R.id.button_exit);
        bt_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        //bt_to_2nd for testing explicit Intent
        Button bt_to_2nd = (Button) findViewById(R.id.button_intent_2nd);
        bt_to_2nd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        Button bt_of_implicit_intent = (Button) findViewById(R.id.button_intent_implicit);
        bt_of_implicit_intent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.activitytest.ACTION_START");
                //intent.addCategory("com.example.activitytest.MY_CATEGORY");
                startActivity(intent);
            }
        });
        Button bt_of_ImpInt_to_uri = (Button) findViewById(R.id.button_of_ImpInt_to_uri);
        bt_of_ImpInt_to_uri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);//android system action
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        Button bt_of_ImpInt_of_data = (Button) findViewById(R.id.button_of_ImpInt_of_data);
        bt_of_ImpInt_of_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);//android system action
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        Button bt_of_ImpInt_of_dial = (Button) findViewById(R.id.button_of_ImpInt_of_dial);
        bt_of_ImpInt_of_dial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);//android system action
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
        Button bt_of_Int_passdata = (Button) findViewById(R.id.button_of_Int_passdata);
        bt_of_Int_passdata.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String data ="hello second activity";
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);//android system action
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });
        Button bt_of_Int_returndata = (Button) findViewById(R.id.button_of_Int_return_data);
        bt_of_Int_returndata.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ThirdActivity.class);
                //get result form week-up Intent
                startActivityForResult(intent, 1);
            }
        });
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.d("FirstActivity", returnedData);
                }
                break;
            default:
        }
    }
}