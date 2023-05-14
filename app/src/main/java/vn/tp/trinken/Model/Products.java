package vn.tp.trinken.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products implements Serializable, Parcelable {

    @SerializedName("id")
    private int product_id;

    @SerializedName("productName")
    private String product_name;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("sold")
    private int sold;

    @SerializedName("active")
    private boolean active;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @SerializedName("brand")
    private Brands brand;

    @SerializedName("categories")
    private List<Categories> categories;

    @SerializedName("discount")
    private Discounts discount;


    protected Products(Parcel in) {
        product_id = in.readInt();
        product_name = in.readString();
        price = in.readDouble();
        description = in.readString();
        image = in.readString();
        quantity = in.readInt();
        sold = in.readInt();
        active = in.readByte() != 0;
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(product_id);
        parcel.writeString(product_name);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeInt(quantity);
        parcel.writeInt(sold);
        parcel.writeByte((byte) (active ? 1 : 0));
    }
}
