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
import db.WordTestDBHelper;

public class WordTest extends AppCompatActivity {

    WordTestDBHelper myHelper;
    WordBookDBHelper bookHelper;

    SQLiteDatabase db, db1;

    static int answercount = 0;
    static int wrongcount = 0;

    int count = 1 ; //문제 수 변수
    int rand[] = new int[4]; //보기 버튼에 넣어줄 보기 단어 뜻 랜덤값
    TextView textVoca,textNum; //문제 수 , 단어 텍스트
    Button[] btnMean  = new Button[4];  //보기 버튼
    Integer[] btnid = {R.id.btn1 , R.id.btn2, R.id.btn3,R.id.btn4}; //보기 버튼 id
    String[] btnex = new String[4];  //보기 뜻 배열

    Button btnQuit;

    String[][] wordarr = {{"account","계좌","설명"},{"pass","통행권","지나치다"},
            {"bill","청구서","법안,의안"},{"initiative","계획","주도권"},{"trip","여행","넘어지다"}
            ,{"produce","농산물","생산하다"},{"immortality","불멸","영원"},{"bush","잡초","잡풀"},{"advancement","발전","진보"}
            , {"inadvance","미리","이전에"},{"annihilate","전멸시키다","완패시키다"}, {"instruction","교육","지시"}, {"mold","형성하다","만들다"},{"temporarily","일시적으로","잠시"}
            , {"pride","자만","자금"},{"impatient","참을성없는","초조한"},{"moderate","사회를 보다","온건한"},{"conceit","자만","자부심"},{"capable","가능한","유능한"},{"durable","튼튼한","내구력있는"},{"fabricate","위조하다","조립하다"}

            ,{"comprehensible","알기 쉬운","알 수 있는"},{"revenue","매출","수입"},{"sovereignty","주권","통치권"},{"prosperous","번영하는","성공한"},{"nominate","지명하다","추천하다"}

            ,{"verify","검증하다","확인하다"},{"further","더 멀리","게다가"},{"authentic","진정한","진짜의"},{"antibiotic","항균","항생제"},{"commemorate","기념하다","행사"}

            ,{"persist","계속하다","고집하다"},{"apprehensive","불안한","신경쓰이는"},{"carriage","운송","마차"},{"healthcare","건강관리","의료의"},{"preemptive","선매의","우선적인"}

            ,{"rearing","양육","사육"},{"circumstances","상황","어떤 상황에 두다"},{"attribute","~로 여기다","~에 따른"},{"regime","정권","체제"},{"run over","~을 치다","초과하다"}

            ,{"figure out","알아내다","해결하다"},{"pessimistic","비관적인","회의적인"},{"share","공유하다","점유하다"},{"incomparable","비교할 수 없는","무쌍의"}

            ,{"contrasting","대조적인","대비적인"},{"sternly","엄격히","엄격하게"},{"variety","다양한","여러 종류"},{"unthinkable","생각할 수 없는","전혀 고려할 가치가 없는"},{"sustain","지속하다","지지하다"}

            ,{"exceed","넘어서다","초과하다"},{"vendor","판매자","노점"},{"startup","운전개시","시동"},{"renowned","유명한","저명한"},{"implicate","연루시키다","유죄"}

            ,{"compliance","준수","추종"},{"accession","취득","계승"},{"retrieve","회수","검색하다"},{"repulse","물리치다","~을 지겹게 하다"},{"unauthorized","권한이 없는","자기식의"}

            ,{"litigation","소송","고소"},{"monopolize","독점하다","(관심,시간을)독차지하다"},{"marginal","경계의","변두리의"},{"irresolute","우유부단한","망설이는"},{"irrelevant","관계가 없는","무의미한"},{"easy","쉬운","좋은"},{"eager","싶다","열망"},{"dramatic","극적인","급격한"},{"diverse","다양한","광범위한"},{"distant","먼","원격의"},{"discard","버리다","폐기"},{"devoted","헌신적인","바쳐진"},{"designated","지정된","정해진"},{"deep","깊은","심"},{"dedicated","몰두하고 있는","열렬한"}
            ,{"considerable","상당한","꽤"},{"confidential","비밀의","기밀의"},{"conceal","감추다","숨기다"},{"comprehensive","종합적인","포괄적인"},{"competitive","경쟁의","경쟁력을 지닌"},{"cautious","신중한","조심스러운"},{"available","가능한","유효한"},{"approachable","사귀기 쉬운","가까이하기 쉬운"},{"aim","목표","겨냥하다"},{"attempted","미수의","기도한"}
            ,{"attached","덧붙여진","첨부된"},{"adequate","적당한","충분한"},{"advanced","선진의","진보한"},{"adaptable","융통성 있는","적응성 있는"},{"accord","협정","합의"},{"accepted","받은","합격한"},{"able","유능한","자격있는"},{"abbreviated","단축된","약자의"},{"violation","위반","유린"},{"trouble","문제","고민"}
            ,{"track","추적하다","궤도"},{"tournament","대회","토너먼트"},{"toleration","관용","인내"},{"term","임기","말하다"},{"technology","기술","공학"},{"step","단계","걸음"},{"statement","발표","진술"},{"stability","안정","안전"},{"spending","지출","쓰는"},{"spectator","관중","방관자"}
            ,{"specification","명세","규격"},{"series","시리즈","일련"},{"separation","분리","이탈"},{"schedule","예정","일정 계획"},{"saying","~라는 말이 있다","속담"},{"reward","보상","대가"},{"revision","개정","개편"},{"retirement","은퇴","퇴직"},{"responsibility","책임","부담"},{"reserve","보유하다","예비"}
            ,{"remittance","송금","납부"},{"replacement","대체","다른"},{"regularity","질서","균정"},{"reference","참고","지칭"},{"record","기록하다","음반"},{"reason","이유","원인"},{"rebate","환불","환급"},{"pursuit","추구","추격"},{"abundance","풍부","대량"},{"rate","속도","비율"}
            ,{"purpose","목적","명분"},{"proposal","제안","청혼"},{"protection","보호","경호"},{"program","계획","프로그램"},{"proficiency","능숙","유창"},{"profession","직업","직종"},{"productivity","생산성","생산력"},{"procession","행렬","진행"},{"prize","상금","경품"},{"procedure","절차","처리"}
            ,{"position","위치","지위"},{"popularity","인기","인지도"},{"policy","정책","제도"},{"permit","허가하다","용납하다"},{"passage","통과","구절"},{"outcome","결과","성과"},{"opposition","반대","반발"},{"opportunity","기회","찬스"},{"offer","제공하다","제시하다"}
            ,{"occupation","직업","점령"},{"occasion","행사","기회"},{"network","네트워크","관계"},{"need","필요","수요"},{"motivation","동기부여","욕구"},{"mentor","스승","조언자"},{"mandate","명령","의무"},{"issue","문제","쟁점"},{"invention","발명","창작"}
            ,{"intention","의도","취지"},{"infusion","주입","고취"},{"influence","영향","요인"},{"indication","가리키는","표시"},{"improvement","개선","증진"},{"imitation","모방","모조품"},{"concentration","농축","수용소"},{"hurt","다치다","상처"},{"complication","복잡","합병증"},{"completion","완성","체결"}
            ,{"chance","기회","우연"},{"claim","주장하다","차지하다"},{"category","범주","부문"},{"caution","주의","경계"},{"capacity","가능성","자격"},{"candidate","후보자","수험생"},{"serve","제공하다","복무하다"},{"facilitate","위하다","촉진하다"},{"temporarily","일시적으로","임시로"},{"necessarily","반드시","필연적으로"}
            ,{"personally","개인적으로","스스로"},{"presumably","아마","추정하건대"},{"prompt","촉발하다","신속한"},{"nearly","거의","가까이"},{"hesitant","망설이다","주저하는"},{"heavily","크게","과하게"},{"frequently","자주","종종"},{"favorably","호의적으로","순조롭게"},{"entirely","완전히","전적으로"},{"currently","현재의","유창하게"}
            ,{"considerably","많이","현저히"},{"expansive","광범위한","발전적인"},{"demanding","요구","부담"},{"critical","비판적인","중요한"},{"courteous","예의 바른","정중한"},{"complicated","복잡한","난해한"},{"confidentiality","기밀성","비밀성"},{"maturity","성숙","만기"},{"flexibility","융통성","신축성"},{"feasibility","타당성","경제성"}
            ,{"optimism","낙천주의","낙관주의"},{"expiration","유효기간","만료"},{"conclusion","결론","타결"},{"portion","부분","분배하다"},{"breed","품종","사육하다"},{"amphibian","양서류의","수륙 양용 비행기"},{"neutral","중립","균형"},{"trim","잘라내다","다듬다"},{"locust","메뚜기","매미"},{"pervasive","퍼지는","스며드는"}
            ,{"inaugurate","취임시키다","출범"},{"burdensome","귀찮은","부담이 되는"},{"alternative","대안","대체"},{"slum","빈민가","슬럼가"},{"robbery","강도","약탈"},{"rob","빼앗다","훔치다"},{"exaggerate","과장하다","지나친"},{"request","요청","요구"},{"unreasonable","불합리한","무리한"},{"upside down","거꾸로","뒤집힌"}
            ,{"uphold","지키다","따르다"},{"expansion","확장","팽창"},{"civil war","내전","시민 전쟁"},{"mess","엉망","망치다"},{"massive","대규모의","막대한"},{"patent","특허","보호"},{"illumination","조명","밝게하기"},{"delve","파다","탐구하다"},{"dignitary","고위 인사","고관"},{"superintendent","관리자","감독하는"}
            ,{"deem","간주하다","생각하다"},{"identify","확인하다","식별하다"},{"ensure","보장하다","~하기 위해"},{"feature","특징","출연하다"},{"tandem","동시에","더불어"},{"bare","노출된","단순한"},{"shimmer","미광","희미하게 빛나다"},{"paramount","최고의","군주"},{"cherish","소중히 여기다","기리다"},{"tranquil","고요한","조용한"}};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);

        myHelper = new WordTestDBHelper(this);
        bookHelper = new WordBookDBHelper(this);

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


        //테이블에 선언한 배열(단어)입력
       db = myHelper.getWritableDatabase();
        for (int i = 0; i < wordarr.length; i++) {
            String words = wordarr[i][0];
            String means1 = wordarr[i][1];
            String means2 = wordarr[i][2];

            db.execSQL("insert into wordTest (wordmain, wordmean1, wordmean2) select '" + words + "', '" + means1 + "', '" + means2 + "' where not exists (select * from wordTest where wordmain = '" + words + "') LIMIT 1;");
        }
        db.close();

        //보기에 단어 출력
        db = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from wordTest order by random()", null);   //테이블의 값들 랜덤으로 가져옴
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
        //보기 버튼에 보기 단어 뜻 입력
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
            final Intent nextq = new Intent(getApplicationContext(), WordTest.class);

            btnMean[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnMean[index].getText() == btnex[3]) {          //btnex[3] = 문제의 정답   ↓정답일 경우 실행문
                        answercount++;
                        String answernotice = answercount + "";

                        Toast.makeText(getApplicationContext(), answernotice + "회 정답입니다!", Toast.LENGTH_LONG).show();

                        count++;

                        if (count > 10) {   //10문제가 끝나면 테스트 종료 화면으로 넘겨줌

                            testend.putExtra("answer", answercount);
                            testend.putExtra("wrong",wrongcount);

                            startActivityForResult(testend,0);

                            finish();

                            wrongcount = 0;
                            answercount = 0;
                        } else if (count <= 10) {  //10문제 이전일 경우 메인화면 재 출력(다음 문제)
                            nextq.putExtra("count1", count);
                            startActivity(nextq);
                            finish();
                        }

                    } else {            // 정답이 틀렸을 경우 실행
                        wrongcount++;
                        String wrongnotice = wrongcount + "";

                        db = myHelper.getReadableDatabase();
                        Cursor cursor;

                        cursor = db.rawQuery("select * from wordTest where wordmean1 = '" + btnex[3] + "' or wordmean2 = '"+ btnex[3] +"';"  ,null);

                        String words ="";
                        String mean1 ="";
                        String mean2 ="";

                        while(cursor.moveToNext()) {

                            words = cursor.getString(0) + "";
                            mean1 = cursor.getString(1) + "";
                            mean2 = cursor.getString(2) + "";

                        }

                        db1 = bookHelper.getWritableDatabase();

                        db1.execSQL("insert into wordBook (wordmain, wordmean1, wordmean2) select '" + words + "', '" + mean1 + "', '" + mean2 + "' where not exists (select * from wordBook where wordmain = '" + words + "') LIMIT 1;");


                        /*db1.execSQL("insert into wordBook values ('" + words + "', '" + mean1 + "', '" + mean2 + "');");*/

                        db1.close();

                        cursor.close();
                        db.close();












                        Toast.makeText(getApplicationContext(), wrongnotice + "회 오답입니다. 단어장에 저장됩니다!", Toast.LENGTH_LONG).show();

                        count++;

                        if (count > 10) {    //10문제가 끝나면 테스트 종료 화면으로 넘겨줌
                            testend.putExtra("answer", answercount);
                            testend.putExtra("wrong",wrongcount);

                            startActivityForResult(testend,0);

                            finish();

                            wrongcount = 0;
                            answercount = 0;
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
                AlertDialog.Builder dlg = new AlertDialog.Builder(WordTest.this);
                dlg.setTitle("최종 결과");
                dlg.setMessage(answercount + "회 정답, " + wrongcount + "회 오답입니다!");

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wrongcount = 0;
                        answercount = 0;
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }
                });
                dlg.show();

            }
        });





    }
}
