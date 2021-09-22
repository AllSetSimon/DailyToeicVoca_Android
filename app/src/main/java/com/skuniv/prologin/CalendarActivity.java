package com.skuniv.prologin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import db.ToeicCalDBHelper;
import db.WordAddDBHelper;
import db.WordTestDBHelper;

public class CalendarActivity extends AppCompatActivity {

    ToeicCalDBHelper myHelper;
    SQLiteDatabase db;
    Cursor cursor;

    ArrayAdapter<String> adapter, adapter2;
    ArrayList<String> arr;
    Spinner spinner1;

    Button btnAdd, btnRefresh;

    ListView lstView;

    String [][] Toecal = {{"358","6","30","4.30-6.18","6.18-6.27","7.12"},
            {"359","7","15","5.14-7.2","7.2-7.12","7.26"},
            {"360","7","29","5.28-7.16","7.16-7.26","8.9"},
            {"361","8","12","6.11-7.30","7.30-8.9","8.23"},
            {"362","8","26","6.25-8.13","8.13-8.23","9.6"}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        myHelper = new ToeicCalDBHelper(this);



        spinner1 = (Spinner) findViewById(R.id.spin);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        lstView = (ListView) findViewById(R.id.lstview);

        final String[] Month = { 1+"월", 2+"월", 3+"월", 4+"월", 5+"월", 6+"월", 7+"월", 8+"월", 9+"월", 10+"월", 11+"월", 12+"월"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Month);
        spinner1.setAdapter(adapter);

        arr = new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        lstView.setAdapter(adapter2);

        spinner1.setSelection(0);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        btnAdd.setTypeface(typeface);
        btnRefresh.setTypeface(typeface);


        db = myHelper.getWritableDatabase();
        for (int i = 0; i < Toecal.length; i++) {
            String number = Toecal[i][0] + "회차";
            String month = Toecal[i][1] + "월";
            String date = Toecal[i][2] + "일";
            String enroll = Toecal[i][3];
            String addenroll = Toecal[i][4];
            String score = Toecal[i][5];

            db.execSQL("insert into ToeicCal (number, month, date, enroll, addenroll, score) select '" + number + "', '" + month + "', '" + date + "', '" + enroll + "', '" + addenroll + "', '" + score + "' where not exists (select * from ToeicCal where number = '" + number + "') LIMIT 1;");

            /*db.execSQL("insert into ToeicCal values('" + number + "', '" + month + "', '" + date + "', '" + enroll + "', '" + addenroll + "','" + score + "');");*/
            Toast.makeText(getApplicationContext(),"토익 기본 일정 탑재 완료", Toast.LENGTH_SHORT).show();
        }
        db.close();


        select();


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://exam.ybmnet.co.kr/"));
                startActivity(intent);
            }
        });



        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                db = myHelper.getWritableDatabase();

                db.execSQL("delete from ToeicCal where number = '" + arr.remove(i) + "';");


                arr.clear();

                adapter2.notifyDataSetChanged();

                select();
                db.close();


                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),CalendarAddActivity.class);
                startActivity(intent1);
            }
        });



       spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               spinner1.setSelection(i);

               String mon = Month[i].toString();
               db = myHelper.getReadableDatabase();
               cursor = db.rawQuery("select * from ToeicCal where month = '" + mon + "';", null);

               arr.clear();


               while (cursor.moveToNext()) {

                   String num = cursor.getString(0);
                   String month = cursor.getString(1);
                   String date = cursor.getString(2);
                   String enroll = cursor.getString(3);
                   String addenroll = cursor.getString(4);
                   String score = cursor.getString(5);

                   String str = num +"            " +  month + "  " + date + "         " + enroll + "          " + addenroll + "            " + score ;




                   arr.add(str);

                   adapter2.notifyDataSetChanged();


               }


               db.close();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

               db = myHelper.getReadableDatabase();
               cursor = db.rawQuery("select * from ToeicCal;", null);

               arr.clear();


               while (cursor.moveToNext()) {

                   String num = cursor.getString(0);
                   String month = cursor.getString(1);
                   String date = cursor.getString(2);
                   String enroll = cursor.getString(3);
                   String addenroll = cursor.getString(4);
                   String score = cursor.getString(5);

                   String str = num +"            " +  month + "  " + date + "         " + enroll + "          " + addenroll + "            " + score ;




                   arr.add(str);

                   adapter2.notifyDataSetChanged();


               }


               db.close();





           }
       });

       /*btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(CalendarActivity.this,CalendarAddActivity.class);
               startActivity(intent);
           }
       });*/


    }

    public void select() {

        db = myHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from ToeicCal ;", null);

        while (cursor.moveToNext()) {

            String num = cursor.getString(0);
            String month = cursor.getString(1);
            String date = cursor.getString(2);
            String enroll = cursor.getString(3);
            String addenroll = cursor.getString(4);
            String score = cursor.getString(5);

            String str = num +"          " +  month + "  " + date + "            " + enroll + "            " + addenroll + "             " + score ;


            arr.clear();

            arr.add(str);

            adapter2.notifyDataSetChanged();



        }
        db.close();


    }
}
