package com.skuniv.prologin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import db.ToeicScoreDBHelper;

public class ToeicScoreActivity extends AppCompatActivity {


    EditText edtNum,edtRC,edtLC;
    TextView listNum,listRC,listLC,listTotal;
    Button btnInit,btnInsert,btnSelect;
    ToeicScoreDBHelper myHelper;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_score);


        edtNum= (EditText) findViewById(R.id.edtNum);
        edtRC = (EditText) findViewById(R.id.edtRC);
        edtLC = (EditText)findViewById(R.id.edtLC);

        listNum= (TextView) findViewById(R.id.listNum);
        listRC= (TextView) findViewById(R.id.listRC);
        listLC= (TextView) findViewById(R.id.listLC);
        listTotal= (TextView) findViewById(R.id.listTotal);

        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button)findViewById(R.id.btnSelect);

        myHelper = new ToeicScoreDBHelper(this);

        btnSelect.callOnClick();

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        edtNum.setTypeface(typeface);
        edtRC.setTypeface(typeface);
        edtLC.setTypeface(typeface);
        listNum.setTypeface(typeface);
        listRC.setTypeface(typeface);
        listLC.setTypeface(typeface);
        listTotal.setTypeface(typeface);
        btnInit.setTypeface(typeface);
        btnInsert.setTypeface(typeface);
        btnSelect.setTypeface(typeface);





        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                db = myHelper.getWritableDatabase();
                myHelper.onUpgrade(db,1,2);
                db.close();

                edtNum.setText("");
                edtRC.setText("");
                edtLC.setText("");
                btnSelect.callOnClick();
            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(edtNum.getText().toString().equals("") || edtRC.getText().toString().equals("") || edtLC.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),"회차 및 점수를 모두 입력해주십시오",Toast.LENGTH_SHORT).show();

                } else {

                    InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(edtLC.getWindowToken(),0);

                    String getnum = edtNum.getText().toString();
                    String getRC = edtRC.getText().toString();
                    String getLC = edtLC.getText().toString();
                    int totalscore = Integer.parseInt(getRC) + Integer.parseInt(getLC);    //Total 점수 = RC + LC

                    db = myHelper.getWritableDatabase();

                    db.execSQL("insert into ToeicScore values(  '" + getnum + "' ,'" + getRC + "','" + getLC + "' , '" + String.valueOf(totalscore) + "');  ");
                    db.close();

                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_LONG).show();

                    edtNum.requestFocus();
                    edtNum.setText("");
                    edtRC.setText("");
                    edtLC.setText("");
/*
                //키보드내리기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtRC.getWindowToken(),0);
*/
                    btnSelect.callOnClick(); // 조회 버튼 메소드 호출
                }






            }

        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(edtNum.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtRC.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtLC.getWindowToken(),0);

                db=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.rawQuery("select * from ToeicScore",null);
                String numbers="\r\n"; //회차목록
                String RCs = "\r\n"; //RC 점수 목록
                String LCs = "\r\n"; //LC 점수 목록
                String Total = "\r\n"; //RC 점수 목록

                while(cursor.moveToNext()){
                    numbers += cursor.getString(0) + "\r\n";
                    RCs += cursor.getString(1) + "\r\n";
                    LCs += cursor.getString(2) + "\r\n";
                    Total += cursor.getString(3) + "\r\n";

                }

                listNum.setText(numbers);
                listRC.setText(RCs);
                listLC.setText(LCs);
                listTotal.setText(Total);

                cursor.close();
                db.close();


            }
        });










    }
}
