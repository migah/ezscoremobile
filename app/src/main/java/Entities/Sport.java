package Entities;

import java.io.Serializable;

/**
 * Created by rasmusmadsen on 03/05/2017.
 */

public class Sport implements Serializable {
    private String id;
    private String img;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return getName();
    }
}
