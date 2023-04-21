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
public class Categories implements Serializable {

    @SerializedName("id")
    private int category_id;

    @SerializedName("categoryName")
    private String category_name;

    private String image;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;


}
