package frezc.bangumitimemachine.app;

import android.database.sqlite.SQLiteException;
import android.util.Log;
import frezc.bangumitimemachine.app.entity.Avatar;
import frezc.bangumitimemachine.app.entity.User;
import frezc.bangumitimemachine.app.ui.UIParams;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by freeze on 2015/4/23.
 */
public class MyApplication extends LitePalApplication {
    //保存登陆账号的信息，app中只允许登陆一个账号
    //static方便获取与修改账号
    private User loginUser = null;

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public boolean isUserLogin(){
        return loginUser != null;
    }

    public void logout(){
        loginUser = null;
        clearUser();
    }

    public void clearUser(){
        try {
            DataSupport.deleteAll(User.class);
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UIParams.density = getResources().getDisplayMetrics().density;

        List<User> list = DataSupport.findAll(User.class);
        if(list.size() > 0) {
            loginUser = list.get(0);
            Log.i("Test", ""+loginUser);
        }
    }
}
