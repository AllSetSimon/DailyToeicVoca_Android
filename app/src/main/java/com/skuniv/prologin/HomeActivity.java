package com.skuniv.prologin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import db.SayingDBHelper;

public class HomeActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6;
    TextView txtview1,txtview2,txtview3,txtview4,txtview5,txtview6,txtThink;

    SayingDBHelper sayhelper;
    SQLiteDatabase db;
    Cursor cursor;

    String[][] say = {{"삶이 있는 한 희망은 있다 -키케로"},{"산다는것 그것은 치열한 전투이다. -로망로랑"},
            {"하루에 3시간을 걸으면 7년 후에 지구를 한바퀴 돌 수 있다.  -사무엘존슨"},
            {"언제나 현재에 집중할수 있다면 행복할것이다. -파울로 코엘료"},
            {"진정으로 웃으려면 고통을 참아야하며 , 나아가 고통을 즐길 줄 알아야 해 -찰리 채플린"},
            {"직업에서 행복을 찾아라. 아니면 행복이 무엇인지 절대 모를 것이다 -엘버트 허버드"},
            {"신은 용기있는자를 결코 버리지 않는다 -켄러"},
            {"행복의 문이 하나 닫히면 다른 문이 열린다 그러나 우리는 종종 닫힌 문을 멍하니 바라보다가 우리를 향해 열린 문을 보지 못하게 된다  - 헬렌켈러"},
            {"피할수 없으면 즐겨라 - 로버트 엘리엇"},
            {"단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한 삶을 살아가는가? -이드리스 샤흐"},
            {"먼저 자신을 비웃어라. 다른 사람이 당신을 비웃기 전에 - 엘사 맥스웰 "},
            {"먼저핀꽃은 먼저진다  남보다 먼저 공을 세우려고 조급히 서둘것이 아니다 - 채근담 "},
            {"행복한 삶을 살기위해 필요한 것은 거의 없다. -마르쿠스 아우렐리우스 안토니우스"},
            {"절대 어제를 후회하지 마라 . 인생은 오늘의 나 안에 있고 내일은 스스로 만드는 것이다  - L.론허바드 "},
            {"어리석은 자는 멀리서 행복을 찾고, 현명한 자는 자신의 발치에서 행복을 키워간다  -제임스 오펜하임"},
            {"너무 소심하고 까다롭게 자신의 행동을 고민하지 말라 . 모든 인생은 실험이다 . 더많이 실험할수록 더나아진다. - 랄프 왈도 에머슨"},
            {"한번의 실패와 영원한 실패를 혼동하지 마라  -F.스콧 핏제랄드 "},
            {"내일은 내일의 태양이 뜬다. 피할수 없으면 즐겨라 -로버트 엘리엇"},
            {"절대 어제를 후회하지 마라. 인생은 오늘의  내 안에 있고 내일은 스스로 만드는것이다. -L론허바드 "},
            {"계단을 밟아야 계단 위에 올라설수 있다, -터키속담"},
            {"오랫동안 꿈을 그리는 사람은 마침내 그 꿈을 닮아 간다, -앙드레 말로"},
            {"좋은 성과를 얻으려면 한 걸음 한 걸음이 힘차고 충실하지 않으면 안 된다, -단테"},
            {"행복은 습관이다,그것을 몸에 지니라 -허버드"},
            {"성공의 비결은 단 한 가지, 잘할 수 있는 일에 광적으로 집중하는 것이다. - 톰 모나건"},
            {"자신감 있는 표정을 지으면 자신감이 생긴다 -찰스다윈"},
            {"평생 살 것처럼 꿈을 꾸어라.그리고 내일 죽을 것처럼 오늘을 살아라. - 제임스 딘"},
            {"1퍼센트의 가능성, 그것이 나의 길이다. -나폴레옹"},
            {"고통이 남기고 간 뒤를 보라! 고난이 지나면 반드시 기쁨이 스며든다. -괴테"},
            {"꿈을 계속 간직하고 있으면 반드시 실현할 때가 온다. -괴테"},
            {"화려한 일을 추구하지 말라. 중요한 것은 스스로의 재능이며, 자신의 행동에 쏟아 붓는 사랑의 정도이다. -마더 테레사"},
            {"마음만을 가지고 있어서는 안된다. 반드시 실천하여야 한다. -이소룡"},
            {"만약 우리가 할 수 있는 일을 모두 한다면 우리들은 우리자신에 깜짝 놀랄 것이다. -에디슨 "},
            {"사람은 가는 곳마다 보는 것마다 모두 스승으로서 배울 것이 많은 법이다.  -맹자"},
            {"눈물과 더불어 빵을 먹어 보지 않은 자는 인생의 참다운 맛을 모른다. -괴테"},
            {"진짜 문제는 사람들의 마음이다. 그것은 절대로 물리학이나 윤리학의 문제가 아니다. -아인슈타인"},
            {"해야 할 것을 하라. 모든 것은 타인의 행복을 위해서, 동시에 특히 나의 행복을 위해서이다. -톨스토이"},
            {"사람이 여행을 하는 것은 도착하기 위해서가 아니라 여행하기 위해서이다. -괴테"},
            {"화가 날 때는 100까지 세라. 최악일 때는 욕설을 퍼부어라. -마크 트웨인"},
            {"재산을 잃은 사람은 많이 잃은 것이고 친구를 잃은 사람은 더많이 잃은 것이며 용기를 잃은 사람은 모든것을 잃은 것이다 -세르반테스"},
            {"돈이란 바닷물과도 같다. 그것은 마시면 마실수록 목이 말라진다. -쇼펜하우어"},
            {" 고개 숙이지 마십시오. 세상을 똑바로 정면으로 바라보십시오. -헬렌 켈러"},
            {"고난의 시기에 동요하지 않는 것, 이것은 진정 칭찬받을 만한 뛰어난 인물의 증거다. -베토벤"},
            {"사막이 아름다운 것은 어딘가에 샘이 숨겨져 있기 때문이다 - 생떽쥐베리"},
            {"행복의 한 쪽 문이 닫히면 다른 쪽 문이 열린다. 그러나 흔히 우리는 닫혀진 문을 오랫동안 보기 때문에 우리를 위해 열려 있는 문을 보지 못한다. -헬렌 켈러"},
            {"만족할 줄 아는 사람은진정한 부자이고, 탐욕스러운 사람은 진실로 가난한 사람이다. -솔론"},
            {"성공해서 만족하는 것은 아니다. 만족하고 있었기 때문에 성공한 것이다. -알랭"},
            {"곧 위에 비교하면 족하지 못하나, 아래에 비교하면 남음이 있다.-명심보감"},
            {"그대의 하루 하루를 그대의 마지막 날이라고 생각하라 - 호라티우스"}};







    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);

       txtview1 = (TextView) findViewById(R.id.txtview1);
        txtview2 = (TextView) findViewById(R.id.txtview2);
        txtview3 = (TextView) findViewById(R.id.txtview3);
        txtview4 = (TextView) findViewById(R.id.txtview4);
        txtview5 = (TextView) findViewById(R.id.txtview5);
        txtview6 = (TextView) findViewById(R.id.txtview6);
        txtThink = (TextView) findViewById(R.id.txtThink);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        txtview1.setTypeface(typeface);
        txtview2.setTypeface(typeface);
        txtview3.setTypeface(typeface);
        txtview4.setTypeface(typeface);
        txtview5.setTypeface(typeface);
        txtview6.setTypeface(typeface);
        txtThink.setTypeface(typeface);


        sayhelper = new SayingDBHelper(this);

        db=sayhelper.getWritableDatabase();

        db = sayhelper.getWritableDatabase();
        for (int i = 0; i < say.length; i++) {
            String saytb = say[i][0];

            db.execSQL("insert into saying (saymain) select '" + saytb + "'where not exists (select * from saying where saymain = '" + saytb + "') LIMIT 1;");
        }
        db.close();

        db=sayhelper.getReadableDatabase();
        cursor=db.rawQuery("select * from saying order by random();",null);

        String say = "";

        while(cursor.moveToNext()){
            say = cursor.getString(0);
        }

        txtThink.setText(say);
        cursor.close();
        db.close();








        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordTest.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordAddActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordBookActivity.class);
                startActivity(intent);
            }
        });




        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
            }
        });



    }
}
