package com.skuniv.prologin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OnetoFiftyActivity extends AppCompatActivity {

    Chronometer chronometer1;
    TextView programmer;
    LinearLayout lin1,lin2,lin3;
    Button[] numbtn = new Button[9];
    Integer[] numbtnid = {R.id.btn1 , R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5, R.id.btn6 ,R.id.btn7, R.id.btn8, R.id.btn9};
    int[] imgs={R.drawable.beemo,R.drawable.jake,R.drawable.bubblegum,R.drawable.cloud,R.drawable.guanter,
            R.drawable.finn2,R.drawable.iceking,R.drawable.lemongrab,R.drawable.unicorn,R.drawable.marcellin};


    int i,j;
    int count=1;
    int st = 42;
    int end = 50;
    int rannum[] ={1,2,3,4,5,6,7,8,9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneto_fifty);
        chronometer1 = (Chronometer) findViewById(R.id.chronometer1);
        lin1 = (LinearLayout) findViewById(R.id.lin1);
        lin2 = (LinearLayout) findViewById(R.id.lin2);
        lin3 = (LinearLayout) findViewById(R.id.lin3);
        programmer =(TextView)findViewById(R.id.programmer);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        chronometer1.setTypeface(typeface);
        programmer.setTypeface(typeface);





        chronometer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.start();
                chronometer1.setTextColor(Color.BLACK);
                lin1.setVisibility(View.VISIBLE);
                lin2.setVisibility(View.VISIBLE);
                lin3.setVisibility(View.VISIBLE);
                count = 1;

                for (i = 0; i < rannum.length; i++) { // 랜덤값
                    rannum[i] = (int) (Math.random() * 9) + 1;
                    for(j=0; j<i; j++){
                        if(rannum[i] ==rannum[j]){
                            i--;
                            break;
                        }
                    }
                }
                for (i = 0; i < numbtn.length; i++) {
                    numbtn[i].setText(String.valueOf(rannum[i]));
                }
            }
        });
        for (i = 0; i < numbtnid.length; i++) {
            numbtn[i] = (Button) findViewById(numbtnid[i]);
            numbtn[i].setTypeface(typeface);
        }




        for(i=0; i<numbtnid.length; i++){
            final int index;
            index = i;

            numbtn[index].setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    if(numbtn[index].getText()==String.valueOf(end) && count == end){
                        chronometer1.stop();
                        chronometer1.setTextColor(Color.BLUE);
                        lin1.setVisibility(View.INVISIBLE);
                        lin2.setVisibility(View.INVISIBLE);
                        lin3.setVisibility(View.INVISIBLE);

                        programmer.setText("\n 만든 사람들 \n 박수빈 차기순 윤원석");

                        for (i = 0; i < rannum.length; i++) { // 랜덤값
                            rannum[i] = (int) (Math.random() * 9) + 1;
                            for (j = 0; j < i; j++) {
                                if (rannum[i] == rannum[j]) {
                                    i--;
                                    break;
                                }
                            }
                        }
                        count = 1;
                        for (i = 0; i < numbtn.length; i++) {
                            numbtn[i].setText(String.valueOf(rannum[i]));
                            numbtn[i].setVisibility(View.VISIBLE);
                        }
                    }else if(numbtn[index].getText() == String.valueOf(st) && count == st) {
                        numbtn[index].setVisibility(View.INVISIBLE);
                        st = st + 1;
                        count = count + 1;
                    }else if (numbtn[index].getText()== String.valueOf(count)) {
                        numbtn[index].setText(String.valueOf(count + 9));
                        count = count + 1;

                        int img = (int)(Math.random()*10);
                        numbtn[index].setBackgroundResource(imgs[img]);

                    }else{
                        Toast.makeText(getApplicationContext(),"순서가 틀렸습니다",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }
}