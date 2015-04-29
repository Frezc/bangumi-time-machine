package frezc.bangumitimemachine.app;

import android.app.Application;
import frezc.bangumitimemachine.app.entity.LoginUser;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.UIParams;

/**
 * Created by freeze on 2015/4/23.
 */
public class MyApplication extends Application {
    private LoginUser loginUser = null;

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public boolean isUserLogin(){
        return loginUser != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UIParams.density = getResources().getDisplayMetrics().density;
    }
}
