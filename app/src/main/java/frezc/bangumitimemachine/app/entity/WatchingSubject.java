package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/5/11.
 */
public class WatchingSubject {
    private String name;
    private int ep_status;
    private int lasttouch;
    private Subject subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEp_status() {
        return ep_status;
    }

    public void setEp_status(int ep_status) {
        this.ep_status = ep_status;
    }

    public int getLasttouch() {
        return lasttouch;
    }

    public void setLasttouch(int lasttouch) {
        this.lasttouch = lasttouch;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
