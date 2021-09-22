package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToeicCalDBHelper extends SQLiteOpenHelper {
    public ToeicCalDBHelper(Context context) {
        super(context, "ToeicCalDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table ToeicCal (number varchar2(20) ,month varchar2(20), date varchar2(20), enroll varchar2(20), addenroll varchar2(20), score varchar2(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists ToeicCal");
        onCreate(db);

    }
}