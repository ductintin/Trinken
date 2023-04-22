package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product_Reviews implements Serializable {
    @SerializedName("id")
    private int review_id;

    @SerializedName("product")
    private Products products;

    @SerializedName("customer")
    private User customer;

    @SerializedName("rating")
    private int rating;

    @SerializedName("comment")
    private String comment;


}
