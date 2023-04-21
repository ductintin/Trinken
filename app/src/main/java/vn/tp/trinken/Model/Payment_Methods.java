package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment_Methods {

    @SerializedName("id")
    private int payment_method_id;

    @SerializedName("paymentMethodName")
    private String payment_method_name;


}
