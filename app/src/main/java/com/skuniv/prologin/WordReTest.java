package com.skuniv.prologin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import db.WordBookDBHelper;

public class WordReTest extends AppCompatActivity {

    WordBookDBHelper bookhelper;

    SQLiteDatabase db;

    static int answercounts = 0;
    static int wrongcounts = 0;

    int count = 1 ; //문제 수 변수
    int rand[] = new int[4]; //보기 버튼에 넣어줄 보기 단어 뜻 랜덤값
    TextView textVoca,textNum; //문제 수 , 단어 텍스트
    Button[] btnMean  = new Button[4];  //보기 버튼
    Integer[] btnid = {R.id.btn1 , R.id.btn2, R.id.btn3,R.id.btn4}; //보기 버튼 id
    String[] btnex = new String[4];  //보기 뜻 배열

    Button btnQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_re_test);

        bookhelper = new WordBookDBHelper(this);

        textVoca = (TextView) findViewById(R.id.textVoca);
        textNum = (TextView) findViewById(R.id.textNum);
        btnQuit = (Button) findViewById(R.id.btnQuit);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");

        for (int i = 0; i < btnid.length; i++) { //보기버튼 id 참조
            btnMean[i] = (Button) findViewById(btnid[i]);
            btnMean[i].setTypeface(typeface);
        }

        textVoca.setTypeface(typeface);
        textNum.setTypeface(typeface);

        db = bookhelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from wordBook order by random()", null);   //테이블의 값들 랜덤으로 가져옴
        int c = 0;  //뜻을 보기배열 갯 수만큼 채워주기위한 변수
        String correct = "";
        int currand;   //랜덤값 변수 선언
        while (cursor.moveToNext()) {  //보기 뜻에 단어 입력
            correct = cursor.getString(0);  //정답 보기(영단어)를 correct 변수에 입력 (문제의 정답은 btnex[3]에 들어감)
            currand = (int) (Math.random() * 2) + 1; //1~2사이의 랜덤값
            btnex[c] = cursor.getString(currand);    //보기 배열에 의미1~2 중 랜덤으로 값 입력
            c++;
            if (c > 3) {   //보기배열 4개에 의미를 모두 채웠을경우 반복문 빠져나감
                break;
            }
        }
        textVoca.setText(correct); //정답(보기 btnex[3] 영단어)-> correct에 저장
        //보기 버튼에 넣어줄 보기 단어 뜻 랜덤 값
        for (int a = 0; a < rand.length; a++) {
            rand[a] = (int) (Math.random() * 4);  // 0~3사이의 랜덤값 (문제영단어1개, 보기영단어3개)
            for (int b = 0; b < a; b++) {
                if (rand[a] == rand[b]) {  //중복값 제거
                    a--;
                    break;
                }
            }
        }

        for (int m = 0; m < 4; m++) {
            btnMean[m].setText(String.valueOf(btnex[rand[m]]));
        }
        cursor.close();
        db.close();

        Intent countval = getIntent();    //count(문제수)값을 받아올 인텐트 생성
        count = countval.getIntExtra("count1", 1); //  getIntExtra이용 ->"count1" 이라는 이름의 값을 int값으로 받아옴.
        textNum.setText("Q" + count + ".");

        //보기 버튼 클릭 이벤트
        for (int e = 0; e < btnid.length; e++) {
            final int index = e;
            final Intent testend = new Intent(getApplicationContext(), WordTestFinishActivity.class);
            final Intent nextq = new Intent(getApplicationContext(), WordReTest.class);

            btnMean[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnMean[index].getText() == btnex[3]) {          //btnex[3] = 문제의 정답   ↓정답일 경우 실행문
                        answercounts++;
                        String answernotice = answercounts + "";

                        db = bookhelper.getReadableDatabase();
                        Cursor cursor;

                        cursor = db.rawQuery("select * from wordBook where wordmean1 = '" + btnex[3] + "' or wordmean2 = '"+ btnex[3] +"';"  ,null);

                        String words ="";
                        String mean1 ="";
                        String mean2 ="";

                        while(cursor.moveToNext()) {

                            words = cursor.getString(0) + "";
                            mean1 = cursor.getString(1) + "";
                            mean2 = cursor.getString(2) + "";

                        }

                        db = bookhelper.getWritableDatabase();

                        db.execSQL("delete from wordBook where wordmain = '" + words + "';");

                        cursor.close();
                        db.close();






                        Toast.makeText(getApplicationContext(), answernotice + "회 정답입니다!", Toast.LENGTH_LONG).show();

                        count++;

                        if (count > 10) {   //10문제가 끝나면 테스트 종료 화면으로 넘겨줌

                            testend.putExtra("answer", answercounts);
                            testend.putExtra("wrong",wrongcounts);

                            startActivityForResult(testend,0);

                            finish();

                            wrongcounts = 0;
                            answercounts = 0;
                        } else if (count <= 10) {  //10문제 이전일 경우 메인화면 재 출력(다음 문제)
                            nextq.putExtra("count1", count);
                            startActivity(nextq);
                            finish();
                        }

                    } else {            // 정답이 틀렸을 경우 실행
                        wrongcounts++;
                        String wrongnotice = wrongcounts + "";

                        /*db = bookhelper.getReadableDatabase();
                        Cursor cursor;

                        cursor = db.rawQuery("select * from wordBook where wordmean1 = '" + btnex[3] + "' or wordmean2 = '"+ btnex[3] +"';"  ,null);

                        String words ="";
                        String mean1 ="";
                        String mean2 ="";

                        while(cursor.moveToNext()) {

                            words = cursor.getString(0) + "";
                            mean1 = cursor.getString(1) + "";
                            mean2 = cursor.getString(2) + "";

                        }*/

                        /*db = bookhelper.getWritableDatabase();

                        db.execSQL("insert into wordBook (wordmain, wordmean1, wordmean2) select '" + words + "', '" + mean1 + "', '" + mean2 + "' where not exists (select * from wordBook where wordmain = '" + words + "') LIMIT 1;");
*/

                        /*db1.execSQL("insert into wordBook values ('" + words + "', '" + mean1 + "', '" + mean2 + "');");*/

                        /*cursor.close();

                        db.close();
*/

                        Toast.makeText(getApplicationContext(), wrongnotice + "회 오답입니다. 다시 공부하세요!", Toast.LENGTH_LONG).show();

                        count++;

                        if (count > 10) {    //10문제가 끝나면 테스트 종료 화면으로 넘겨줌
                            testend.putExtra("answer", answercounts);
                            testend.putExtra("wrong",wrongcounts);

                            startActivityForResult(testend,0);

                            finish();

                            wrongcounts = 0;
                            answercounts = 0;
                        } else if (count <= 10) {   //10문제 끝나기 전까진 메인화면 다시 출력( 다음 문제)
                            nextq.putExtra("count1", count);
                            startActivity(nextq);
                            finish();
                        }
                    }
                }
            });
        }

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(WordReTest.this);
                dlg.setTitle("최종 결과");
                dlg.setMessage(answercounts + "회 정답, " + wrongcounts + "회 오답입니다!");

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wrongcounts = 0;
                        answercounts = 0;
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }
                });
                dlg.show();

            }
        });




    }
}
