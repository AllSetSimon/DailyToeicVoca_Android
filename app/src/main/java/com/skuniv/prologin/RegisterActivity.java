package com.skuniv.prologin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
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

public class RegisterActivity extends LoginActivity {

    EditText regi_id,regi_pwd,regi_mail,regi_phone,regi_Name;
    RadioGroup rGroup;
    RadioButton regi_boy,regi_girl;
    Button btnregister,btngender;
    String Username, UserID, UserPassword, Usermail, Userhp, Usergender;

    LinearLayout lin1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regi_Name = (EditText) findViewById(R.id.regi_Name);
        regi_id = (EditText) findViewById(R.id.regi_id);
        regi_pwd = (EditText) findViewById(R.id.regi_pwd);
        regi_mail = (EditText) findViewById(R.id.regi_mail);
        regi_phone = (EditText) findViewById(R.id.regi_phone);
      /*  rGroup = (RadioGroup) findViewById(R.id.rGroup);
        regi_boy = (RadioButton) findViewById(R.id.regi_boy);
        regi_girl = (RadioButton) findViewById(R.id.regi_girl);*/
        btngender = (Button) findViewById(R.id.btngender);
        btnregister = (Button) findViewById(R.id.btnregister);
        lin1 = (LinearLayout) findViewById(R.id.lin1);




        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(regi_Name.getWindowToken(),0);
                imm.hideSoftInputFromWindow(regi_id.getWindowToken(),0);
                imm.hideSoftInputFromWindow(regi_pwd.getWindowToken(),0);
                imm.hideSoftInputFromWindow(regi_mail.getWindowToken(),0);
                imm.hideSoftInputFromWindow(regi_phone.getWindowToken(),0);
            }
        });


        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/bmdohyeon.ttf");
        regi_Name.setTypeface(typeface);
        regi_id.setTypeface(typeface);
        regi_mail.setTypeface(typeface);
        regi_phone.setTypeface(typeface);
        btngender.setTypeface(typeface);
        btnregister.setTypeface(typeface);





        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = insertRecord(tableName);


            }
        });

        btngender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String [] genderArray = {"남자","여자"};

                AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterActivity.this);
                dlg.setTitle("성별 선택");
                dlg.setIcon(R.drawable.ic_mood_black_24dp);

                dlg.setItems(genderArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btngender.setText(genderArray[i]);
                    }
                });
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

    }

    private int insertRecord(String tableName) {
        int count = 1;

        Username = regi_Name.getText().toString();
        UserID = regi_id.getText().toString();
        UserPassword = regi_pwd.getText().toString();

        Usermail = regi_mail.getText().toString();
        Userhp = regi_phone.getText().toString();
        Usergender = btngender.getText().toString();

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
                    db.execSQL("insert into "+tableName+"(username, userID, password, email, hp, gender) values ('"+
                            Username+"', '"+UserID+"', '"+UserPassword+"', '" + Usermail + "', '" + Userhp + "', '" + Usergender+"');");
                    count++;
                    println("Inserting records into table : "+ tableName);
                    Toast.makeText(getApplication(), "회원가입 완료",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
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





