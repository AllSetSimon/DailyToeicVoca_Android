package com.skuniv.prologin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import db.WordBookDBHelper;

public class WordBookActivity extends AppCompatActivity {

    WordBookDBHelper bookhelper;
    SQLiteDatabase db;
    Cursor cursor;

    Button btnRe,btnINIT;


    ListView lstView;

    ArrayAdapter<String> adapter;
    ArrayList<String> arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_book);

        bookhelper = new WordBookDBHelper(this);

        lstView = (ListView) findViewById(R.id.lstView);
        btnRe = (Button) findViewById(R.id.btnRe);
        btnINIT = (Button) findViewById(R.id.btnINIT);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        btnRe.setTypeface(typeface);
        btnINIT.setTypeface(typeface);


        arr = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        lstView.setAdapter(adapter);

        btnINIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = bookhelper.getWritableDatabase();
                bookhelper.onUpgrade(db,1,2);
                db.close();

                db = bookhelper.getReadableDatabase();
                cursor = db.rawQuery("select * from wordBook", null);

                String wordss = "" ;
                String mean1s = "" ;
                String mean2s = "" ;

                while(cursor.moveToNext()) {

                    wordss = cursor.getString(0) ;
                    mean1s = cursor.getString(1) ;
                    mean2s = cursor.getString(2) ;

                    String str = wordss + "\t\t\t\t\t\t\t" + mean1s + "\t\t\t\t\t\t\t" + mean2s;

                    arr.add(str);





                }

                arr.clear();
                adapter.notifyDataSetChanged();




            }
        });





        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter.getCount() < 15 ) {

                    Toast.makeText(getApplicationContext(),"단어 재시험은 단어장의 단어가 15개 이상일 때부터 가능합니다.",Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(getApplicationContext(),WordReTest.class);
                    startActivity(intent);
                    finish();

                }




            }
        });



        db = bookhelper.getReadableDatabase();
        cursor = db.rawQuery("select * from wordBook", null);

        String words = "" ;
        String mean1 = "" ;
        String mean2 = "" ;

        while(cursor.moveToNext()) {

            words = cursor.getString(0) ;
            mean1 = cursor.getString(1) ;
            mean2 = cursor.getString(2) ;

            String str = words + "\t\t\t\t\t\t\t" + mean1 + "\t\t\t\t\t\t\t" + mean2;

            arr.add(str);





        }





    }
}
