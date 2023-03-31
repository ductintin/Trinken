package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Categories implements Serializable {
    @SerializedName("id")
    private int category_id;
    @SerializedName("name")
    private String category_name;

    private String image;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;

    public Categories() {
    }

    public Categories(int category_id, String category_name, String image, boolean active, Date createdAt, Date updatedAt) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.image = image;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
