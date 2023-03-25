package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product_Reviews implements Serializable {
    @SerializedName("review_id")
    private int review_id;
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("rating")
    private int rating;
    @SerializedName("comment")
    private String comment;

    public Product_Reviews() {
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product_Reviews(int review_id, int product_id, int user_id, int rating, String comment) {
        this.review_id = review_id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.rating = rating;
        this.comment = comment;
    }
}
