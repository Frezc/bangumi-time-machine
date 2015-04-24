package frezc.bangumitimemachine.app.entity;

import java.util.List;

/**
 * Created by freeze on 2015/4/24.
 */
public class Subject {
    private String air_date;
    private int air_weekday;
    private List<Blog> blog;
    private Collection collection;
    private List<Person> crt;
    private List<Episode> eps;
    private int id;
    private Images images;
    private String name;
    private String name_cn;
    private List<Staff> staff;
    private String summary;
    private List<Topic> topic;
    private int type;
    private String url;


    public static class Images{
        public String large;
        public String common;
        public String medium;
        public  String small;
        public String grid;
    }

    public static class Collection{
        public int wish;
        public int collect;
        public int doing;
        public int on_hold;
        public int dropped;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getAir_weekday() {
        return air_weekday;
    }

    public void setAir_weekday(int air_weekday) {
        this.air_weekday = air_weekday;
    }

    public List<Blog> getBlog() {
        return blog;
    }

    public void setBlog(List<Blog> blog) {
        this.blog = blog;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Person> getCrt() {
        return crt;
    }

    public void setCrt(List<Person> crt) {
        this.crt = crt;
    }

    public List<Episode> getEps() {
        return eps;
    }

    public void setEps(List<Episode> eps) {
        this.eps = eps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
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

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Topic> getTopic() {
        return topic;
    }

    public void setTopic(List<Topic> topic) {
        this.topic = topic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
