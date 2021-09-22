package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SayingDBHelper extends SQLiteOpenHelper {


    public SayingDBHelper(Context context) {
        super(context, "SayingDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table saying (saymain varchar2(100));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists saying");
        onCreate(db);

    }



}
