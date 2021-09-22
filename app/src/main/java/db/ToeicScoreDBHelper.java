package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToeicScoreDBHelper extends SQLiteOpenHelper {

    //생성자
    public ToeicScoreDBHelper(Context context) {
        super(context, "ToeicScoreDB", null, 2);
    }

    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table ToeicScore (ToeicNum varchar2(20) PRIMARY KEY , RcScore varchar2(20),LcScore varchar2(20), TotalScore varchar2(20));");

    }

    //테이블 삭제 후 다시 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists ToeicScore");
        onCreate(db);

    }

}
