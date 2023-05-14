package vn.tp.trinken.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem implements Parcelable {
        private int id;
        private int quantity;

        private double price;

        private Date createdAt;

        private Date updatedAt;
        private Products product;


    protected CartItem(Parcel in) {
        id = in.readInt();
        quantity = in.readInt();
        price = in.readDouble();
        product = in.readParcelable(Products.class.getClassLoader());
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(quantity);
        parcel.writeDouble(price);
        parcel.writeParcelable(product, i);
    }
}
