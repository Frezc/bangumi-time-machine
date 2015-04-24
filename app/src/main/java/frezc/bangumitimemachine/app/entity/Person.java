package frezc.bangumitimemachine.app.entity;

import java.util.List;

/**
 * Created by freeze on 2015/4/24.
 */
public class Person {
    private int id;
    private String url;
    private String name;
    private String name_cn;
    private String role_name;
    private Images images;
    private int comment;
    private int collects;
    private Info info;

    public static class Info{
        public String name_cn;
        public Alias alias;
        public String gender;
        public String birth;
        public String bloodtype;
        public String height;
        public String weight;
        public String bwh;
        public String source;
    }

    public static class Alias{
        public String zh;
        public String en;
        public String jp;
        public String kana;
        public String romaji;
        public String nick;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getCollects() {
        return collects;
    }

    public void setCollects(int collects) {
        this.collects = collects;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
