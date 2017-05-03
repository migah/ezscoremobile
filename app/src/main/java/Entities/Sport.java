package Entities;

/**
 * Created by rasmusmadsen on 03/05/2017.
 */

public class Sport {
    private String $key;
    private String img;
    private String name;

    public String get$key() {
        return $key;
    }

    public void set$key(String $key) {
        this.$key = $key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
