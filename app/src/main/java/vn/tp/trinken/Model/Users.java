package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Users implements Serializable {
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("password")
    private String password;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("address")
    private String address;
    @SerializedName("role_id")
    private int role_id;

    public Users(int user_id, String first_name, String last_name, String phone_number, String address, String gender) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
    }

    private String image;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
    private Date last_login;

    private String gender;

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Users{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", last_name='" + phone_number + '\'' +
                ", last_name='" + address + '\'' +
                ", last_name='" + gender + '\'' +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
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

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Users(int user_id, String user_name, String password, String first_name, String last_name, String email, String phone_number, String address, int role_id, String image, Boolean active, Date createdAt, Date updatedAt, Date last_login, String gender) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.role_id = role_id;
        this.image = image;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.last_login = last_login;
        this.gender = gender;
    }

    public Users() {
    }

    public Users(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }




}
