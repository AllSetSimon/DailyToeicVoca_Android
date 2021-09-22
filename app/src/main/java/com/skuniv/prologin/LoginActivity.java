package com.skuniv.prologin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import db.DBHelper;

import static java.sql.DriverManager.println;

public class LoginActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    SQLiteDatabase db;
    String dbName, tableName;
    static String loginID, loginPassword;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    EditText input_id,input_pwd;
    Button btnLogin;
    TextView txtJoin , txtmain, txtsub;

    LinearLayout ln1, ln2;

    boolean dbCreated = false;
    boolean tableCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        input_id = (EditText) findViewById(R.id.input_id);
        input_pwd = (EditText) findViewById(R.id.input_pwd);

        ln1 = (LinearLayout) findViewById(R.id.ln1);
        ln2 = (LinearLayout) findViewById(R.id.ln2);




        btnLogin = (Button) findViewById(R.id.btnLogin);

        txtJoin = (TextView) findViewById(R.id.txtJoin);
        txtmain = (TextView) findViewById(R.id.txtmain);
        txtsub = (TextView) findViewById(R.id.txtsub);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        txtmain.setTypeface(typeface);
        txtsub.setTypeface(typeface);
        input_id.setTypeface(typeface);
        btnLogin.setTypeface(typeface);
        txtJoin.setTypeface(typeface);

        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(input_pwd.getWindowToken(),0);
                imm.hideSoftInputFromWindow(input_id.getWindowToken(),0);

            }
        });

        ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(input_pwd.getWindowToken(),0);
                imm.hideSoftInputFromWindow(input_id.getWindowToken(),0);

            }
        });




        dbName = "TeamDB";
        createDatabase(dbName);
        tableName = "membersTb";
        createTable(tableName);



        txtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerintent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerintent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginID = input_id.getText().toString();
                loginPassword = input_pwd.getText().toString();

                String password = helper.searchPass(loginID);

                if (loginPassword.equals(password)) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                    dlg.setTitle("안내");
                    dlg.setMessage("로그인 성공! 오늘도 열심히 공부하세요!");
                    dlg.setPositiveButton("입장하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();;

                        }
                    });
                    dlg.show();


                } else {
                        Toast.makeText(getApplication(), "아이디 또는 비밀번호를 다시 입력하세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                }



            });
        };

    private void createTable(String tableName) {
        println("Creating table : " + tableName);
        try {
            if (db != null) {
                db.execSQL("create table if not exists " + tableName + "("
                        + " userName text, "
                        + " userID text, "
                        + " password text,"
                        + " email text,"
                        + " hp text,"
                        + " gender text);");

                println("테이블생성");

                tableCreate = true;
            } else {
                println("데이터베이스에 생성되지않았습니다.");
                Toast.makeText(this,"테이블실패",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            println("테이블이 없습니당.");
        }
    }

    private void createDatabase(String dbName) {
        println("creating database : " + dbName);

        String databaseName = dbName;
        try {
            db = openOrCreateDatabase(databaseName, Activity.MODE_PRIVATE, null);

            dbCreated = true;

            println("데이터베이스 생성");
        } catch (Exception e) {
            e.printStackTrace();
            println("데이터베이스 생성안됨");
        }
    }





    }
