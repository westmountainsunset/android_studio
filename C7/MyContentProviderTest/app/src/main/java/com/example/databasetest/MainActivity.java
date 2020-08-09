package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db",
                null, 2);
        Button crtDB = (Button) findViewById(R.id.create_database);
        crtDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values); // 插入第一条数据插入第一条数据
                values.clear();
                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");

                values.put("pages", 510);
                values.put("price", 19.95);
                //db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                //            new String[] { "The Da Vinci Code", "Dan Brown", "454", "16.96" });
                //db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                //            new String[] { "The Lost Symbol", "Dan Brown", "510", "19.95" });
                db.insert("Book", null, values); // 插入第二条数据
            }
        });

        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                //db.execSQL("update Book set price = ? where name = ?", new String[] { "10.99",
                //"The Da Vinci Code" });
                db.update("Book", values, "name = ?",
                        new String[]{"The Da Vinci Code" });
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //db.execSQL("delete from Book where pages > ?", new String[] { "500" });
                db.delete("Book", "pages > ?", new String[] { "500" });
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询查询 Book 表中所有的数据表中所有的数据
                // db.rawQuery("select * from Book", null);
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历遍历 Cursor 对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex
                                ("name"));
                        String author = cursor.getString(cursor.getColumnIndex
                                ("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex
                                ("price"));
                        String line = "book: " + name +"\n"
                                        + "author: "+author +"\n"
                                        + "page: " + pages + "\n"
                                        + "price " + price +"\n";
                        list.add(line);
                    } while (cursor.moveToNext());
                }
                TextView textView = (TextView) findViewById(R.id.show_query_data);
                String text = "";
                for (String s : list){
                    text += s;
                }
                textView.setText(text);
                cursor.close();
            }
        });
    }

}