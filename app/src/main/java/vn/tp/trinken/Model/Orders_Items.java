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
public class Orders_Items implements Serializable {

    @SerializedName("id")
    private int order_detail_id;

    @SerializedName("order")
    private Orders orders;

    @SerializedName("product")
    private Products products;

    @SerializedName("quantity")
    private int quantity;
    
    @SerializedName("price")
    private double price;


}
