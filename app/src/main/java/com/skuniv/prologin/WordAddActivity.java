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

import db.WordAddDBHelper;
import db.WordTestDBHelper;

public class WordAddActivity extends AppCompatActivity {

    WordAddDBHelper wordHelper;
    WordTestDBHelper wordTestHelper;

    EditText edtWord, edtMean1, edtMean2;
    Button btnAdd,btnSelect, btnDelete;
    TextView txtWord, txtMean1, txtMean2 , prnWord, prnMean1, prnMean2, inputWord, inputMean1, inputMean2 ;
    SQLiteDatabase db, db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_add);

        edtWord = (EditText) findViewById(R.id.edtWord);
        edtMean1 = (EditText) findViewById(R.id.edtMean1);
        edtMean2 = (EditText) findViewById(R.id.edtMean2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        txtWord = (TextView) findViewById(R.id.txtWord);
        txtMean1 = (TextView) findViewById(R.id.txtMean1);
        txtMean2 = (TextView) findViewById(R.id.txtMean2);

        prnWord = (TextView) findViewById(R.id.prnWord);
        prnMean1 = (TextView) findViewById(R.id.prnMean1);
        prnMean2 = (TextView) findViewById(R.id.prnMean2);

        inputWord = (TextView) findViewById(R.id.inputWord);
        inputMean1 = (TextView) findViewById(R.id.inputMean1);
        inputMean2 = (TextView) findViewById(R.id.inputMean2);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        btnAdd.setTypeface(typeface);
        btnSelect.setTypeface(typeface);
        btnDelete.setTypeface(typeface);

        edtWord.setTypeface(typeface);
        edtMean1.setTypeface(typeface);
        edtMean2.setTypeface(typeface);

        txtWord.setTypeface(typeface);
        txtMean1.setTypeface(typeface);
        txtMean2.setTypeface(typeface);

        prnWord.setTypeface(typeface);
        prnMean1.setTypeface(typeface);
        prnMean2.setTypeface(typeface);

        inputWord.setTypeface(typeface);
        inputMean1.setTypeface(typeface);
        inputMean2.setTypeface(typeface);


        btnSelect.callOnClick();

        wordHelper = new WordAddDBHelper(this);
        wordTestHelper = new WordTestDBHelper(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtWord.getText().toString().equals("")||edtMean1.getText().toString().equals("")||edtMean2.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),"등록하실 단어 혹은 의미를 전부 입력해주세요!",Toast.LENGTH_SHORT).show();


                } else {

                    InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(edtMean2.getWindowToken(),0);


                    String Word = edtWord.getText().toString();
                    String Mean1 = edtMean1.getText().toString();
                    String Mean2 = edtMean2.getText().toString();

                    db = wordHelper.getWritableDatabase();
                    db1 = wordTestHelper.getWritableDatabase();

                    try{
                        db.execSQL("insert into wordAdd values ('" + Word + "','" + Mean1 + "', '" + Mean2+"');");
                        db1.execSQL("insert into wordTest values ('" + Word + "','" + Mean1 + "', '" + Mean2+"');");

                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(),"중복단어는 등록할 수 없습니다.",Toast.LENGTH_SHORT).show();

                    }
                    db.close();
                    db1.close();

                    edtWord.setText("");
                    edtMean1.setText("");
                    edtMean2.setText("");

                    btnSelect.callOnClick();


                }










            }
        });


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(edtWord.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtMean1.getWindowToken(),0);
                imm.hideSoftInputFromWindow(edtMean2.getWindowToken(),0);

                db = wordHelper.getReadableDatabase();
                Cursor cursor;

                cursor = db.rawQuery("select * from wordAdd",null);

                String words ="\r\n";
                String mean1 ="\r\n";
                String mean2 = "\r\n";

                while(cursor.moveToNext()) {

                    words += cursor.getString(0) + "\r\n";
                    mean1 += cursor.getString(1) + "\r\n";
                    mean2 += cursor.getString(2) + "\r\n";
                }


                txtWord.setText(words);
                txtMean1.setText(mean1);
                txtMean2.setText(mean2);

                cursor.close();
                db.close();


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtWord.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "삭제할 단어를 입력해주세요!", Toast.LENGTH_SHORT).show();

                } else if (edtWord.getText().toString() != "") {

                    db = wordHelper.getWritableDatabase();
                    db1 = wordTestHelper.getWritableDatabase();
                    db.execSQL("Delete from wordAdd Where wordmain = '" + edtWord.getText().toString() + "';");
                    db1.execSQL("Delete from wordTest Where wordmain = '" + edtWord.getText().toString() + "';");
                    db.close();
                    db1.close();

                    edtWord.setText("");

                    Toast.makeText(getApplicationContext(), "단어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    btnSelect.callOnClick();
                }

            }
        });




    }
}
