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
public class Shipping_Addresses implements Serializable {

    @SerializedName("id")
    private int address_id;

    @SerializedName("user")
    private Users users;

    @SerializedName("name")
    private String name;

    @SerializedName("phoneNumber")
    private String phone_number;

    @SerializedName("address")
    private String address;

    private boolean is_deleted;

    private Date createdAt;

    private Date updatedAt;


}
