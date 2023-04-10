package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Shipping_Addresses implements Serializable {
    @SerializedName("address_id")
    private int address_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("address")
    private String address;
    private boolean is_deleted;

    private Date createdAt;

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Shipping_Addresses(int address_id, int user_id, String name, String phone_number, String address, boolean is_deleted, Date createdAt, Date updatedAt) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.is_deleted = is_deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Date updatedAt;

    public Shipping_Addresses() {
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Shipping_Addresses(int address_id, int user_id, String name, String phone_number, String address) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
    }
}
