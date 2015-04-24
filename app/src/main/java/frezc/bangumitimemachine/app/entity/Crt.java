package frezc.bangumitimemachine.app.entity;

import java.util.List;

/**
 * Created by freeze on 2015/4/24.
 */
public class Crt extends Person {
    private List<Actor> actors;

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
