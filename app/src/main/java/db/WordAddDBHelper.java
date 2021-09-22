package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordAddDBHelper extends SQLiteOpenHelper {
    public WordAddDBHelper(Context context) {
        super(context, "WordDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table wordAdd (wordmain varchar2(20) primary key, wordmean1 varchar2(20), wordmean2 varchar(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists wordAdd");
        onCreate(db);

    }
}