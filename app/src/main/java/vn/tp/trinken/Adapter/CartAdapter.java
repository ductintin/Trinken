package vn.tp.trinken.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.R;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import vn.tp.trinken.Model.CartItem;
import vn.tp.trinken.Model.Discounts;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Dto.*;
import vn.tp.trinken.Contants.*;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    Context context;
    List<CartItem> cartItems;

    int cardId;

    public CartAdapter(Context context, List<CartItem> cartItems, int cardId) {
        this.context = context;
        this.cartItems = cartItems;
        this.cardId = cardId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        int quantity = cartItem.getQuantity();
        holder.tvCProductName.setText(cartItem.getProduct().getProduct_name());
        Glide.with(context)
                .load(cartItem.getProduct().getImage())
                .placeholder(R.drawable.coke)
                .into(holder.imgProduct);
        if(checkValidDiscount(cartItem.getProduct().getDiscount())){

            DecimalFormat df = new DecimalFormat("0.00");
            holder.tvCProductPrice.setText(df.format(cartItem.getProduct().getPrice()*(100-cartItem.getProduct().getDiscount().getDiscount_value())/100));
            holder.tvCProductPriceDiscount.setText(String.valueOf(cartItem.getProduct().getPrice()));

            holder.tvCProductPriceDiscount.setPaintFlags(holder.tvCProductPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);;


        }else{

            holder.tvCProductPriceDiscount.setVisibility(View.GONE);
            holder.tvCProductPrice.setText(String.valueOf(cartItem.getProduct().getPrice()));
        }


        holder.tvCProductAmount.setText(String.valueOf(quantity));

        holder.btnCProductDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItem.getQuantity()>1){
                    Toast.makeText(context.getApplicationContext(), "Giam 1", Toast.LENGTH_SHORT).show();
                    holder.tvCProductAmount.setText(String.valueOf(quantity-1));
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setCartId(cardId);
                    cartItemDto.setProductId(cartItem.getProduct().getProduct_id());
                    cartItemDto.setQuantity(Integer.parseInt(String.valueOf(holder.tvCProductAmount.getText())));
                    updateCartItem(cartItemDto);
                    EventBus.getDefault().postSticky(new TotalCart());


                }else{
                    Toast.makeText(context.getApplicationContext(), "Khong the giam", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnCProductInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.tvCProductAmount.setText(String.valueOf(quantity+1));
                CartItemDto cartItemDto = new CartItemDto();
                cartItemDto.setCartId(cardId);
                Toast.makeText(context.getApplicationContext(), "he" + String.valueOf(cardId), Toast.LENGTH_SHORT).show();
                Log.d("Sp luong", String.valueOf(Integer.parseInt(String.valueOf(holder.tvCProductAmount.getText()))));
                cartItemDto.setProductId(cartItem.getProduct().getProduct_id());
                cartItemDto.setQuantity(Integer.parseInt(String.valueOf(holder.tvCProductAmount.getText())));
                updateCartItem(cartItemDto);
                EventBus.getDefault().postSticky(new TotalCart());


            }
        });

        holder.tvCProductTotal.setText(String.valueOf(cartItem.getPrice()));


        holder.tvCProductAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EventBus.getDefault().postSticky(new TotalCart());
            }
        });





    }

    @Override
    public int getItemCount() {
        return cartItems == null ? 0 : cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imgProduct;
        TextView tvCProductName, tvCProductPrice, tvCProductPriceDiscount, tvCProductTotal, tvCProductAmount;
        Button btnCProductDec, btnCProductInc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cartItemCard);
            imgProduct = (ImageView) itemView.findViewById(R.id.cartItemImage);
            tvCProductName = (TextView) itemView.findViewById(R.id.tvCProductName);
            tvCProductPrice = (TextView) itemView.findViewById(R.id.tvCProductPrice);
            tvCProductPriceDiscount = (TextView) itemView.findViewById(R.id.tvCProductPriceDiscount);
            tvCProductTotal = (TextView) itemView.findViewById(R.id.tvCProductTotal);
            tvCProductAmount = (TextView) itemView.findViewById(R.id.tvCProductAmount);
            btnCProductDec = (Button) itemView.findViewById(R.id.btnCProductDec);
            btnCProductInc = (Button) itemView.findViewById(R.id.btnCProductInc);

        }


    }

    private boolean checkValidDiscount(Discounts discount){
        if(discount!=null&&discount.getStatus()!=0){
            Date date = new Date();
            if(date.before(discount.getEnd_date())&&date.after(discount.getStart_date())){
                return true;
            }
        }
        return false;
    }

    private void updateCartItem(CartItemDto cartItemDto){
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.addCartItem(cartItemDto).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Update successfully!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }
}
