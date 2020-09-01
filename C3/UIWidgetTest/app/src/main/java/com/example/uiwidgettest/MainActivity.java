package com.example.uiwidgettest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private  EditText editText;
    private ImageView imageView;
    //onClick
    private boolean isTwice = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Every widget should be instantiated
        Button button = (Button) findViewById(R.id.button_get_EditText);
        editText = (EditText) findViewById(R.id.EditText);
        imageView = (ImageView) findViewById(R.id.image_view);
        //register button click event
        button.setOnClickListener(this);

        Button button_img = (Button) findViewById(R.id.button_change_img);
        button_img.setOnClickListener(this);

        Button button_alert = (Button) findViewById(R.id.button_alert);
        button_alert.setOnClickListener(this);

        Button button_prog_bar = (Button) findViewById(R.id.button_progressbar);
        button_prog_bar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_get_EditText:
                String inputText = editText.getText().toString();
                Toast.makeText(MainActivity.this, inputText,Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_change_img:
                imageView.setImageResource(R.drawable.jwm2);
                break;
            case R.id.button_alert:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("this is dialog");
                dialog.setMessage("something important;");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            case R.id.button_progressbar:
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                if (!isTwice) {
                    progressBar.setVisibility(View.VISIBLE);
                    isTwice = true;
                }else{
                    progressBar.setVisibility(View.GONE);
                    isTwice = false;
                }
            default:
                break;
        }
    }
}