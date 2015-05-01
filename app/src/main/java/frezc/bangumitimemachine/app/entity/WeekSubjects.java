package frezc.bangumitimemachine.app.entity;

import java.util.List;

/**
 * Created by freeze on 2015/5/2.
 */
public class WeekSubjects {
    private Weekday weekday;
    private List<Subject> items;

    public List<Subject> getItems() {
        return items;
    }

    public void setItems(List<Subject> items) {
        this.items = items;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
}
