package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/4/23.
 */
public class User {
    private Avatar avatar;
    private int id;
    private String nickname;
    private String sign;
    private String url;
    private String username;

    public static class Avatar {
        public String large;
        public String medium;
        public String small;

        @Override
        public String toString() {
            return "large:"+large+" medium:"+medium+" small:"+small;
        }
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

