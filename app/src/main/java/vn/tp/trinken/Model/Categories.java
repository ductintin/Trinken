package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categories implements Serializable {
    @SerializedName("id")
    private int category_id;
    @SerializedName("name")
    private String category_name;

    public Categories() {
    }

    public Categories(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
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