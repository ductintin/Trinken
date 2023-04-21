package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders implements Serializable {

    @SerializedName("id")
    private int order_id;

    @SerializedName("orderDate")
    private Date order_date;

    @SerializedName("totalAmount")
    private int total_amount;

    @SerializedName("customer")
    private Users customer;

    @SerializedName("paymentmethod")
    private Payment_Methods payment_methods;

    @SerializedName("orderStatus")
    private Order_Status order_status;

    private Date cancelAt;

    private Date createdAt;

    private Date updatedAt;

    private Date completeAt;

    private Date deliveryAt;

}
