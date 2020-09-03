package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView responseText;

    // threadpool to req
    ThreadPoolExecutor threadpool = new ThreadPoolExecutor(5, 10 ,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.send_request){
                    //sendRequestWithHttpURLConnection();
                    /*callback to improve cpu utilization*/
                   // sendRequestWithHttpUtil();
                    sendReqByThreadPool();
                }
            }
        });
    }
    
    //todo ThreadPool realize
    private void sendRequestWithHttpUtil(){
        HttpUtil.sendHttpRequest("https://www.baidu.com", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                showResponse(response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    // todo ThreadPool realize
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //thread pool realize
    private void sendReqByThreadPool(){
        threadpool.execute(new Runnable() {
            @Override
            public void run() {
               HttpUtil.sendReq( "https://www.baidu.com", new HttpCallbackListener() {
                   @Override
                   public void onFinish(String response) {
                       showResponse(response);
                   }

                   @Override
                   public void onError(Exception e) {
                       e.printStackTrace();
                   }
               });
            }
        });
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作，将结果显示到界面上
                responseText.setText(response);
            }
        });
    }
}