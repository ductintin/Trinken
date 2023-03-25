package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order_Status implements Serializable {
    @SerializedName("status_id")
    private int status_id;
    @SerializedName("status_name")
    private String status_name;

    public Order_Status(int status_id, String status_name) {
        this.status_id = status_id;
        this.status_name = status_name;
    }

    public Order_Status() {
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
