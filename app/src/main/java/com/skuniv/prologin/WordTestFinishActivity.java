package com.skuniv.prologin;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WordTestFinishActivity extends AppCompatActivity {

    TextView txtanswerq, txtanswera, txtwrongq, txtwronga, txtnotice;
    Button btngo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test_finish);

        txtanswerq = (TextView) findViewById(R.id.txtanswerq);
        txtanswera = (TextView) findViewById(R.id.txtanswera);

        txtwrongq = (TextView)findViewById(R.id.txtwrongq);
        txtwronga = (TextView)findViewById(R.id.txtwronga);

        txtnotice = (TextView) findViewById(R.id.txtnotice);
        btngo = (Button) findViewById(R.id.btngo);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        txtanswera.setTypeface(typeface);
        txtanswerq.setTypeface(typeface);

        txtwrongq.setTypeface(typeface);
        txtwronga.setTypeface(typeface);

        btngo.setTypeface(typeface);

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordBookActivity.class);
                startActivity(intent);
            }
        });



        Intent intentresult = getIntent();

        int answercnt = intentresult.getIntExtra("answer",0);
        int wrongcnt = intentresult.getIntExtra("wrong",0);

        String ans = answercnt + "";
        String wro = wrongcnt  + "";

        txtanswera.setText(ans + "회");
        txtwronga.setText(wro + "회");


        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordBookActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
}
