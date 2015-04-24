package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/4/24.
 */
public class Topic {
    private int id;
    private String url;
    private String title;
    private int main_id;
    private long timestamp;
    private long lastpost;
    private int replies;
    private User user;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLastpost() {
        return lastpost;
    }

    public void setLastpost(long lastpost) {
        this.lastpost = lastpost;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
