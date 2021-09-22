package com.skuniv.prologin;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends TabActivity {


    Chronometer chrono;
    Button btnStart1, btnEnd1, btnStart2, btnEnd2, inputTime;
    ListView lstview;


    long START_TIME_IN_MILLIS;

    TextView mTextViewCountDown, timer;


    CountDownTimer mCountDownTimer;

    boolean mTimerRunning;

    long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    EditText edtMin, edtSec;

    long min, sec, result1, result2;

    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        chrono = (Chronometer) findViewById(R.id.stopWatch);


        btnStart1 = (Button) findViewById(R.id.btnStart1);
        btnEnd1 = (Button) findViewById(R.id.btnEnd1);

        btnStart2 = (Button) findViewById(R.id.btnStart2);
        btnEnd2 = (Button) findViewById(R.id.btnEnd2);

        lstview = (ListView) findViewById(R.id.lstview);


        inputTime = (Button) findViewById(R.id.inputTimer);


        edtMin = (EditText) findViewById(R.id.edtMin);
        edtSec = (EditText) findViewById(R.id.edtSec);


        mTextViewCountDown = findViewById(R.id.timer);

        btnStart1.setVisibility(View.VISIBLE);
        btnEnd1.setVisibility(View.VISIBLE);

        timer = (TextView) findViewById(R.id.timer);

        mTextViewCountDown = findViewById(R.id.timer);

        btnStart1.setVisibility(View.VISIBLE);
        btnEnd1.setVisibility(View.VISIBLE);





        final ArrayList<String> time = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,time);

        lstview.setAdapter(adapter);


        final TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSW =
                tabHost.newTabSpec("SW").setIndicator("스톱워치");
        tabSW.setContent(R.id.stopWatch);

        tabHost.addTab(tabSW);

        TabHost.TabSpec tabTM =
                tabHost.newTabSpec("TM").setIndicator("타이머");
        tabTM.setContent(R.id.timer);

        tabHost.addTab(tabTM);


        updateCountDownText();


        btnStart1.setVisibility(View.VISIBLE);
        btnEnd1.setVisibility(View.VISIBLE);
        btnStart2.setVisibility(View.INVISIBLE);
        btnEnd2.setVisibility(View.INVISIBLE);
        inputTime.setVisibility(View.INVISIBLE);
        lstview.setVisibility(View.VISIBLE);
        edtMin.setVisibility(View.INVISIBLE);
        edtSec.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.INVISIBLE);

        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart1.setEnabled(false);

                Toast.makeText(TimerActivity.this, "오늘도 열공하세요! 화이팅!", Toast.LENGTH_LONG).show();
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.GREEN);
            }
        });

        btnEnd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnStart1.setEnabled(true);

                chrono.stop();
                chrono.setTextColor(Color.BLUE);

                long elap = (SystemClock.elapsedRealtime() - chrono.getBase()) / 1000;

                long elapmin = elap / 60;
                long elapsecond = elap - (elapmin * 60);
                long elaphour = elapmin / 60;


                time.add(elaphour + "시간" + elapmin + "분 " + elapsecond + "초");
                adapter.notifyDataSetChanged();

                Toast.makeText(TimerActivity.this, "공부하느라 고생하셨습니다! ^^ 푹쉬세요!", Toast.LENGTH_LONG).show();
                chrono.setBase(SystemClock.elapsedRealtime());

            }
        });



















        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                /*AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("시간 설정");
                dlg.setView(dlgView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        min = Long.parseLong(dlgedtNum1.getText().toString());
                        sec = Long.parseLong(dlgedtNum2.getText().toString());
                        result1 = (long)(min*60) + sec;
                        result2 = (long)result1 * 1000;

                        mTimeLeftInMillis = result2;

                        updateCountDownText();


                    }
                });
                dlg.show();*/
            }
        });


       /* mTextViewCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/




        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                time.remove(i);
                adapter.notifyDataSetChanged();

                return false;
            }


        });


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

                int position = tabHost.getCurrentTab();

                if(position == 0) {
                    btnStart1.setVisibility(View.VISIBLE);
                    btnEnd1.setVisibility(View.VISIBLE);
                    btnStart2.setVisibility(View.INVISIBLE);
                    btnEnd2.setVisibility(View.INVISIBLE);
                    inputTime.setVisibility(View.INVISIBLE);
                    lstview.setVisibility(View.VISIBLE);
                    edtMin.setVisibility(View.INVISIBLE);
                    edtSec.setVisibility(View.INVISIBLE);
                    timer.setVisibility(View.INVISIBLE);

                    btnStart1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStart1.setEnabled(false);

                            Toast.makeText(TimerActivity.this, "오늘도 열공하세요! 화이팅!", Toast.LENGTH_LONG).show();
                            chrono.setBase(SystemClock.elapsedRealtime());
                            chrono.start();
                            chrono.setTextColor(Color.GREEN);
                        }
                    });

                    btnEnd1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            btnStart1.setEnabled(true);

                            chrono.stop();
                            chrono.setTextColor(Color.BLUE);

                            long elap = (SystemClock.elapsedRealtime() - chrono.getBase()) / 1000;

                            long elapmin = elap / 60;
                            long elapsecond = elap - (elapmin * 60);
                            long elaphour = elapmin / 60;


                            time.add(elaphour + "시간" + elapmin + "분 " + elapsecond + "초");
                            adapter.notifyDataSetChanged();

                            Toast.makeText(TimerActivity.this, "공부하느라 고생하셨습니다! ^^ 푹쉬세요!", Toast.LENGTH_LONG).show();
                            chrono.setBase(SystemClock.elapsedRealtime());

                        }
                    });


                } else if(position == 1) {
                    btnStart1.setVisibility(View.INVISIBLE);
                    btnEnd1.setVisibility(View.INVISIBLE);
                    btnStart2.setVisibility(View.VISIBLE);
                    btnEnd2.setVisibility(View.VISIBLE);
                    inputTime.setVisibility(View.VISIBLE);
                    lstview.setVisibility(View.INVISIBLE);
                    edtMin.setVisibility(View.VISIBLE);
                    edtSec.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);

                    btnStart2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mTimerRunning) {
                                pauseTimer();
                                timer.setTextColor(Color.BLUE);
                            } else {

                                if (mTimeLeftInMillis == 0) {

                                    Toast.makeText(getApplicationContext(), "시간입력을 눌러 타이머를 세팅해주세요!", Toast.LENGTH_SHORT).show();

                                } else {

                                    startTimer();
                                    timer.setTextColor(Color.RED);
                                }

                            }
                        }
                    });

                    btnEnd2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            resetTimer();
                            btnStart2.setEnabled(false);
                            timer.setTextColor(Color.BLACK);
                        }
                    });

                    inputTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (edtMin.getText().toString().equals("") || edtSec.getText().toString().equals("")) {

                                Toast.makeText(TimerActivity.this, "분, 초를 모두 입력하세요(없을경우 '0'입력)", Toast.LENGTH_SHORT).show();

                            } else {


                                btnStart2.setVisibility(View.VISIBLE);
                                btnEnd2.setVisibility(View.VISIBLE);

                                btnStart2.setEnabled(true);
                                btnEnd2.setEnabled(true);

                                    /*pauseTimer();
                                    resetTimer();
                                    timer.setTextColor(Color.BLACK);*/


                                min = Long.parseLong(edtMin.getText().toString());
                                sec = Long.parseLong(edtSec.getText().toString());

                                result1 = (long) (min * 60) + sec;
                                result2 = (long) result1 * 1000;

                                mTimeLeftInMillis = result2;

                                updateCountDownText();


                            }


                        }


                    });














                }


            }
        });




    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStart2.setText("Start");
                btnStart2.setVisibility(View.INVISIBLE);
                btnEnd2.setVisibility(View.VISIBLE);
                vibrator.vibrate(3000);

            }
        }.start();

        mTimerRunning = true;
        btnStart2.setText("pause");
        btnEnd2.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        btnStart2.setText("Start");
        btnEnd2.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnEnd2.setVisibility(View.INVISIBLE);
        btnStart2.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }






}
