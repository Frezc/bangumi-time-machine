package frezc.bangumitimemachine.app.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by freeze on 2015/6/6.
 */
public class Avatar extends DataSupport {
    public String large = "";
    public String medium = "";
    public String small = "";

    @Override
    public String toString() {
        return "large:"+large+" medium:"+medium+" small:"+small;
    }
}