package frezc.bangumitimemachine.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by freeze on 2015/5/18.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "bgmtm.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_AUTH = "auth";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_AUTH+" (username varchar(25),password varchar(32))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database upgrade","version from "+oldVersion+" to "+newVersion);

    }
}
