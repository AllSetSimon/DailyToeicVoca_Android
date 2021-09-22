package com.skuniv.prologin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import db.DBHelper;

import static java.sql.DriverManager.println;

public class MemberUpdateActivity extends LoginActivity {

    DBHelper helper;
    SQLiteDatabase db;



    EditText udt_id,udt_pwd,udt_mail,udt_phone,udt_Name;
    RadioGroup rGroup;
    RadioButton regi_boy,regi_girl;
    Button udt_register,udt_gender;
    String Username, UserID, UserPassword, Usermail, Userhp, Usergender;

    LinearLayout line1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_update);
        helper = new DBHelper(this);


        udt_Name = (EditText) findViewById(R.id.udt_Name);
        udt_id = (EditText) findViewById(R.id.udt_id);
        udt_pwd = (EditText) findViewById(R.id.udt_pwd);
        udt_mail = (EditText) findViewById(R.id.udt_mail);
        udt_phone = (EditText) findViewById(R.id.udt_phone);
      /*  rGroup = (RadioGroup) findViewById(R.id.rGroup);
        regi_boy = (RadioButton) findViewById(R.id.regi_boy);
        regi_girl = (RadioButton) findViewById(R.id.regi_girl);*/
        udt_register = (Button) findViewById(R.id.udt_register);
        udt_gender = (Button) findViewById(R.id.udt_gender);

        line1 = (LinearLayout) findViewById(R.id.line1);


        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(udt_Name.getWindowToken(),0);
                imm.hideSoftInputFromWindow(udt_id.getWindowToken(),0);
                imm.hideSoftInputFromWindow(udt_pwd.getWindowToken(),0);
                imm.hideSoftInputFromWindow(udt_mail.getWindowToken(),0);
                imm.hideSoftInputFromWindow(udt_phone.getWindowToken(),0);
            }
        });




        udt_Name.setKeyListener(null);


        db = helper.getWritableDatabase();
        Cursor cursor;

        /*String curId = input_id.getText().toString();*/


        cursor = db.rawQuery("select * from membersTb where userID = '" + loginID + "';",null);

        String Name = "";
        String Id = "";
        String Passwd = "";
        String Email = "";
        String Hp = "";
        String Gender = "";

        if(cursor.moveToNext()) {

            Name = cursor.getString(0) + "";
            Id = cursor.getString(1) + "";
            Passwd = cursor.getString(2) + "";
            Email = cursor.getString(3) + "";
            Hp = cursor.getString(4) + "";
            Gender = cursor.getString(5) + "";

            Toast.makeText(getApplicationContext(),"조회성공!",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(),"조회실패!",Toast.LENGTH_SHORT).show();
        }


        udt_Name.setText(Name);
        udt_id.setText(Id);
        udt_pwd.setText(Passwd);
        udt_mail.setText(Email);
        udt_phone.setText(Hp);
        udt_gender.setText(Gender);


        cursor.close();
        db.close();






        udt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = insertRecord("membersTb");


            }
        });

        udt_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String [] genderArray = {"남자","여자"};

                AlertDialog.Builder dlg = new AlertDialog.Builder(MemberUpdateActivity.this);
                dlg.setTitle("성별 선택");
                dlg.setIcon(R.drawable.ic_mood_black_24dp);

                dlg.setItems(genderArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        udt_gender.setText(genderArray[i]);
                    }
                });
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

    }

    private int insertRecord(String tableName) {
        int count = 1;

        Username = udt_Name.getText().toString();
        UserID = udt_id.getText().toString();
        UserPassword = udt_pwd.getText().toString();

        Usermail = udt_mail.getText().toString();
        Userhp = udt_phone.getText().toString();
        Usergender = udt_gender.getText().toString();

        try {
            if (tableName != null) {
                if(Username.equals("") || UserPassword.equals("") || Username.equals("")) {
                    Toast.makeText(getApplication(), "회원정보를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else if(Username.getBytes().length > 0 && Username.getBytes().length < 2) {
                    Toast.makeText(getApplication(), "2자리 이상의 이름을 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else if(UserID.getBytes().length > 0 && UserID.getBytes().length < 3) {
                    Toast.makeText(getApplication(), "3자리 이상의 아이디를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else if(UserPassword.getBytes().length > 0 && UserPassword.getBytes().length < 6) {
                    Toast.makeText(getApplication(), "6자리 이상의 비밀번호를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else {
                    /*db.execSQL("insert into "+tableName+"(username, userID, password, email, hp, gender) values ('"+
                            Username+"', '"+UserID+"', '"+UserPassword+"', '" + Usermail + "', '" + Userhp + "', '" + Usergender+"');");*/

                    String udtName = udt_Name.getText().toString();

                    db = helper.getWritableDatabase();

                    db.execSQL("update membersTb set userID = '" + UserID + "', password = '" + UserPassword + "', email = '" + Usermail + "', hp = '" + Userhp + "', gender = '" + Usergender + "' where userName = '" + udtName + "';");

                    count++;
                    println("Updating records into table : "+ tableName);
                    Toast.makeText(getApplication(), "회원수정이 완료되었습니다. 재로그인 바랍니다",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            println("Record is not inserted.");
            Toast.makeText(getApplication(), "회원가입 실패",
                    Toast.LENGTH_SHORT).show();
        }
        db.close();

        return count;
    }
}