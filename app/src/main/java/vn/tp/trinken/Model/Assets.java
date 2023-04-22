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
public class Assets implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("path")
    private String path;

    @SerializedName("type")
    private String type;

    @SerializedName("deleteAt")
    private Date deleteAt;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @SerializedName("product")
    private Products product;

}
