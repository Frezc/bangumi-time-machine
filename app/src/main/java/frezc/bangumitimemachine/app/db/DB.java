package frezc.bangumitimemachine.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import frezc.bangumitimemachine.app.entity.BaseAuth;

/**
 * Created by freeze on 2015/5/18.
 */
@Deprecated
public class DB {
    private DBHelper helper;

    public DB(Context context){
        helper = new DBHelper(context);
    }

    public void saveAuth(BaseAuth auth){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into "+DBHelper.TABLE_AUTH+" (username, password) values(?,?)",
                new Object[]{auth.getUsername(), auth.getPassword()});
        db.close();
    }

    public BaseAuth getAuth(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor query = db.rawQuery("select * from "+DBHelper.TABLE_AUTH,null);
        BaseAuth auth = null;
        if(query.moveToFirst()){
            auth = new BaseAuth();
            auth.setUsername(query.getString(query.getColumnIndex("username")));
            auth.setPassword(query.getString(query.getColumnIndex("password")));
        }
        query.close();
        db.close();
        return auth;
    }

    public void deleteAuth(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from "+DBHelper.TABLE_AUTH);
        db.close();
    }

    public void logout(){
        deleteAuth();
    }
}
