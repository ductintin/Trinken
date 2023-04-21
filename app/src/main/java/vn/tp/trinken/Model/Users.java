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
public class Users implements Serializable {
    @SerializedName("id")
    private int user_id;

    @SerializedName("userName")
    private String user_name;

    @SerializedName("password")
    private String password;

    @SerializedName("firstName")
    private String first_name;

    @SerializedName("lastName")
    private String last_name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phone_number;

    @SerializedName("address")
    private String address;

    @SerializedName("role")
    private Roles roles;

    private String image;

    private Boolean active;

    private Date createdAt;

    private Date updatedAt;

    private Date last_login;

    private String gender;






}
