package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/4/24.
 */
public class Actor {
    private int id;
    private String url;
    private String name;
    private Images images;


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

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
