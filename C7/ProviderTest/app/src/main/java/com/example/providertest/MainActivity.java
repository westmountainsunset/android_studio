package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加数据
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });

        // 查询数据
        final TextView textView = (TextView) findViewById(R.id.show_query_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queryData = "";
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                        Cursor cursor = getContentResolver().query(uri, null, null, null,
                                null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex
                                ("name"));
                        String author = cursor.getString(cursor.getColumnIndex
                                ("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex ("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex
                                ("price"));
                        String line = "book: " + name +"\n"
                                + "author: "+author +"\n"
                                + "page: " + pages + "\n"
                                + "price " + price +"\n";
                        queryData += line;
                    }
                    cursor.close();
                }

                textView.setText(queryData);

            }
        });
        // 更新数据
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                        ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages", 1216);
                values.put("price", 24.05);
                getContentResolver().update(uri, values, null, null);
            }
        });
        // 删除数据
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null,
                        null);
                int row = 0;

                if (cursor != null){
                    cursor.moveToLast();
                    row = cursor.getInt(cursor.getColumnIndex("id"));
                }
                String lastID = Integer.toString(row);
                Uri deleteUri =Uri.parse("content://com.example.databasetest.provider/book/" + lastID);
                cursor.close();
                getContentResolver().delete(deleteUri, null, null);
            }
        });
        //清空显示
        Button clear = (Button) findViewById(R.id.clear_text);
        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });
    }
}