package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordBookDBHelper extends SQLiteOpenHelper {
    public WordBookDBHelper(Context context) {
        super(context, "WordBookDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table wordBook (wordmain varchar2(20), wordmean1 varchar2(20), wordmean2 varchar(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists wordBook");
        onCreate(db);

    }
}