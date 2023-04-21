package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    @SerializedName("id")
    private int order_id;
    @SerializedName("order_date")
    private Date order_date;
    @SerializedName("total_amount")
    private int total_amount;
    @SerializedName("payment_method_id")
    private int payment_method_id;
    @SerializedName("order_status_id")
    private int status_id;

    private Date cancelAt;

    private Date createdAt;

    private Date updatedAt;

    private Date completeAt;

    private Date deliveryAt;

}
