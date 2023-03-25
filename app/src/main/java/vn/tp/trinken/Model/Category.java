package vn.tp.trinken.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private String description;
    private String images;

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Category(int id, String name, String description, String images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
    }
}
