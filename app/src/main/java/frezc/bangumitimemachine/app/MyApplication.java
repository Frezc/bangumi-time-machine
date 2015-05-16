package frezc.bangumitimemachine.app;

import android.app.Application;
import frezc.bangumitimemachine.app.entity.LoginUser;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.UIParams;

/**
 * Created by freeze on 2015/4/23.
 */
public class MyApplication extends Application {
    //保存登陆账号的信息，app中只允许登陆一个账号
    //static方便获取与修改账号
    private static LoginUser loginUser = null;

    public static void setLoginUser(LoginUser loginUser) {
        MyApplication.loginUser = loginUser;
    }

    public static LoginUser getLoginUser() {
        return loginUser;
    }

    public static boolean isUserLogin(){
        return loginUser != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UIParams.density = getResources().getDisplayMetrics().density;
    }
}
