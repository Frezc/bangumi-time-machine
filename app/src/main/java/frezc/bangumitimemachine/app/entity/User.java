package frezc.bangumitimemachine.app.entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import org.litepal.crud.DataSupport;

/**
 * Created by freeze on 2015/4/23.
 */
public class User extends SugarRecord<User>{
    Avatar avatar;
    @SerializedName("id")
    String uid;
    String nickname;
    String sign;
    String url;
    String username;


    String auth;
    String auth_encode;

    String netabaAuth;

    public User(){}

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

    public String getNetabaAuth() {
        return netabaAuth;
    }

    public void setNetabaAuth(String netabaAuth) {
        this.netabaAuth = netabaAuth;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "\navatar:"+avatar+"\nid:"+id+"\nnickname"+nickname
                +"\nsign:"+sign+"\nurl"+url+"\nusername:"+username+"\n";
    }
}

