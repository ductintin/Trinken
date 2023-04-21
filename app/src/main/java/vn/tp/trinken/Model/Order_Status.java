package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order_Status implements Serializable {

    @SerializedName("id")
    private int status_id;

    @SerializedName("orderStatusName")
    private String status_name;
}
