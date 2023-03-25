package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product_Categories implements Serializable {
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("category_id")
    private int category_id;

    public Product_Categories(int product_id, int category_id) {
        this.product_id = product_id;
        this.category_id = category_id;
    }

    public Product_Categories() {
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
