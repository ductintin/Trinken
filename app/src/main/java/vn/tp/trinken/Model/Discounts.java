package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discounts implements Serializable {

    @SerializedName("id")
    private int discount_id;

    @SerializedName("discountCode")
    private String discount_code;

    @SerializedName("discountType")
    private String discount_type;

    @SerializedName("discountValue")
    private int discount_value;

    @SerializedName("startDate")
    private Date start_date;

    @SerializedName("endDate")
    private Date end_date;

    private int status;


}
