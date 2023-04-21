package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Shipping_Addresses implements Serializable {
    @SerializedName("id")
    private int address_id;

    @SerializedName("name")
    private String name;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("address")
    private String address;
    private boolean active;

    private Date createdAt;

    private Date updatedAt;
    @SerializedName("user_id")
    private int user_id;

    public Shipping_Addresses(int address_id, String name, String phone_number, String address, boolean active, Date createdAt, Date updatedAt, int user_id) {
        this.address_id = address_id;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user_id = user_id;
    }

    public Shipping_Addresses() {
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
