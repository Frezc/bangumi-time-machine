package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/4/24.
 */
public class LoginUser extends User {
    private String auth;
    private String auth_encode;


    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth_encode() {
        return auth_encode;
    }

    public void setAuth_encode(String auth_encode) {
        this.auth_encode = auth_encode;
    }

}
