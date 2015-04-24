package frezc.bangumitimemachine.app.entity;

import java.util.List;

/**
 * Created by freeze on 2015/4/24.
 */
public class Staff extends Person{
    private List<String> jobs;

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }
}
