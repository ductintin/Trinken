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
public class Brands implements Serializable {

    @SerializedName("id")
    private int brand_id;

    @SerializedName("brandName")
    private String brand_name;

    private String image;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;


}
