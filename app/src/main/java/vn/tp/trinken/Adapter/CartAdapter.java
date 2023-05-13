package vn.tp.trinken.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.Model.Products;
import vn.tp.trinken.Model.User;
import vn.tp.trinken.R;
import vn.tp.trinken.Fragment.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
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

    APIService apiService;

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
        DecimalFormat df = new DecimalFormat("0.00");
        if(checkValidDiscount(cartItem.getProduct().getDiscount())){
            holder.tvCProductPrice.setText(df.format(cartItem.getProduct().getPrice()*(100-cartItem.getProduct().getDiscount().getDiscount_value())/100));
            holder.tvCProductPriceDiscount.setText(String.valueOf(cartItem.getProduct().getPrice()));
            holder.tvCProductPriceDiscount.setPaintFlags(holder.tvCProductPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);;
        }else{
            holder.tvCProductPriceDiscount.setVisibility(View.GONE);
            holder.tvCProductPrice.setText(String.valueOf(cartItem.getProduct().getPrice()));
        }
        holder.tvCProductAmount.setText(String.valueOf(quantity));
        holder.tvCProductTotal.setText(df.format(cartItem.getPrice()*Integer.parseInt(holder.tvCProductAmount.getText().toString())));
        holder.btnCProductDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItem.getQuantity()>1){
                    Toast.makeText(context.getApplicationContext(), "Giam 1", Toast.LENGTH_SHORT).show();
                    holder.tvCProductAmount.setText(String.valueOf(quantity-1));
                    updateCartItem(cartItem, Integer.parseInt(holder.tvCProductAmount.getText().toString()));
                    EventBus.getDefault().postSticky(new TotalCart());
                    try {
                        setListenerList();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    deleteDialog(cartItem.getId());
                }
            }
        });

        holder.btnCProductInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.tvCProductAmount.setText(String.valueOf(quantity+1));
                updateCartItem(cartItem, Integer.parseInt(holder.tvCProductAmount.getText().toString()));
                EventBus.getDefault().postSticky(new TotalCart());
                try {
                    setListenerList();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


            }
        });




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

        holder.cartItemClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(cartItem.getId());
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
        ImageView cartItemClear;



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
            cartItemClear = (ImageView) itemView.findViewById(R.id.cartItemClear);

        }


    }

    private boolean checkValidDiscount(Discounts discount){
        if(discount!=null && discount.getStatus()!=0){
            Date date = new Date();
            if(date.before(discount.getEnd_date())&&date.after(discount.getStart_date())){
                return true;
            }
        }
        return false;
    }

    private void updateCartItem(CartItem cartItem, Integer count) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateCartItem(cartItem.getId(),count).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void setListenerList() throws ParseException {
        User user = SharedPrefManager.getInstance(context.getApplicationContext()).getUser();
        this.cartItems = getCartItem(user.getCart().getId());
        notifyDataSetChanged();
        CartAdapter cartAdapter =new CartAdapter( context, cartItems,cardId);
        cartAdapter.notifyDataSetChanged();
    }

    private List<CartItem> getCartItem(int cartId) {
        Log.d("cart id ne", String.valueOf(cartId));
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCartItem(cartId).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful()) {
                    cartItems = response.body();
                }
            }
            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Log.d("Loi o cartfragment ", t.getMessage());
            }
        });
        return cartItems;
    }

    private void deleteCartItem(int cartItemId){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.deleteCartItem(cartItemId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                    try {
                        setListenerList();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }

    private void deleteDialog(int cartItemId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc xóa sản phẩm khỏi giỏ hàng?");
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCartItem(cartItemId);
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog flooralert = builder.create();
        flooralert.show();
    }
}
