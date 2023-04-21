package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Products implements Serializable {

    @SerializedName("id")
    private int product_id;

    @SerializedName("productName")
    private String product_name;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("sold")
    private int sold;

    @SerializedName("active")
    private boolean active;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @SerializedName("brand")
    private Brands brand;

    @SerializedName("categories")
    private List<Categories> categories;

}
