package frezc.bangumitimemachine.app.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by freeze on 2015/6/6.
 */
public class Avatar extends DataSupport {
    private String large = "";
    private String medium = "";
    private String small = "";

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "large:"+large+" medium:"+medium+" small:"+small;
    }
}