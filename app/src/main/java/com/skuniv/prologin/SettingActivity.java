package com.skuniv.prologin;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    Button btnUpdate,btnYBM,btnToeicScore,btnAbout,btnAppVersion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnYBM = (Button) findViewById(R.id.btnYBM);
        btnToeicScore = (Button) findViewById(R.id.btnToeicScore);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAppVersion = (Button) findViewById(R.id.btnAppVersion);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        btnUpdate.setTypeface(typeface);
        btnYBM.setTypeface(typeface);
        btnToeicScore.setTypeface(typeface);
        btnAbout.setTypeface(typeface);
        btnAppVersion.setTypeface(typeface);





        btnYBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://toeic.ybmclass.com/toeic/toboco/toboco_v2.asp"));
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SettingActivity.this);
                dlg.setTitle("Daily Toeic Voca에 관하여...");
                dlg.setMessage("D.T.V App은 토익 공부에 있어 중요한 부분인 Vocabulary를 중점적으로 교육시키는 집중학습 앱입니다.");

                dlg.show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MemberUpdateActivity.class);
                startActivity(intent);
            }
        });

        btnToeicScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ToeicScoreActivity.class);
                startActivity(intent);

            }
        });

        btnAppVersion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent = new Intent(getApplicationContext(),OnetoFiftyActivity.class);
                startActivity(intent);


                return false;
            }
        });










    }
}
