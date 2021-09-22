package com.skuniv.prologin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import db.ToeicCalDBHelper;

public class CalendarAddActivity extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4,txt5,txt6;
    EditText edtNum, edtMonth, edtDate, edtEnroll, edtAddEnroll, edtScore;
    Button btnADD, btnCancel;

    ToeicCalDBHelper myHelper;
    SQLiteDatabase db;

    LinearLayout ln1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add);

        myHelper = new ToeicCalDBHelper(this);


        edtNum = (EditText) findViewById(R.id.edtNum);
        edtMonth = (EditText) findViewById(R.id.edtMonth);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtEnroll = (EditText) findViewById(R.id.edtEnroll);
        edtAddEnroll = (EditText) findViewById(R.id.edtAddEnroll);
        edtScore = (EditText) findViewById(R.id.edtScore);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);

        ln1 = (LinearLayout)  findViewById(R.id.ln1);

        /*Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        txt3.setTypeface(typeface);
        txt4.setTypeface(typeface);
        txt5.setTypeface(typeface);
        txt6.setTypeface(typeface);
        btnADD.setTypeface(typeface);
        btnCancel.setTypeface(typeface);*/


        btnADD = (Button) findViewById(R.id.btnADD);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(edtNum.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtMonth.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtDate.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtEnroll.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtAddEnroll.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtScore.getWindowToken(),0);
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String number = edtNum.getText().toString() + "회차";
                String month = edtMonth.getText().toString() + "월";
                String date = edtDate.getText().toString() + "일";
                String enroll = edtEnroll.getText().toString();
                String addenroll = edtAddEnroll.getText().toString();
                String score = edtScore.getText().toString();

                db = myHelper.getWritableDatabase();

                db.execSQL("insert into ToeicCal values ('" + number + "', '" + month + "', '" + date + "', '" + enroll + "', '" + addenroll + "', '" + score + "');");

                Toast.makeText(getApplicationContext(),"일정 등록 완료", Toast.LENGTH_LONG);

                db.close();






                Intent addintent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(addintent);
                finish();
                /*addintent.putExtra("NUMBER", edtNum+"");
                addintent.putExtra("MONTH", edtMonth+"");
                addintent.putExtra("DATE", edtDate+"");
                addintent.putExtra("ENROLL", edtEnroll+"");
                addintent.putExtra("ADDENROLL", edtAddEnroll+"");
                addintent.putExtra("SCORE", edtScore+"");

                startActivityForResult(addintent,0);

                finish();
*/
            }
        });

    }
}
