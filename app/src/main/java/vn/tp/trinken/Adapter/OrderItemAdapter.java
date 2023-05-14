package vn.tp.trinken.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;
import vn.tp.trinken.API.*;


public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>{
    Context context;
    List<CartItem> cartItems;

    public OrderItemAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pre_order_item,parent, false);
        return new MyViewHolder(view);
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
            holder.ll.setVisibility(View.GONE);
            holder.tvCProductPrice.setText(String.valueOf(cartItem.getProduct().getPrice()));
        }
        holder.tvCProductAmount.setText(String.valueOf(quantity));
        holder.tvCProductTotal.setText(df.format(cartItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems==null?0:cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imgProduct;
        TextView tvCProductName, tvCProductPrice, tvCProductPriceDiscount, tvCProductTotal, tvCProductAmount;

        LinearLayout ll;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.POICard);
            imgProduct = (ImageView) itemView.findViewById(R.id.POIImage);
            tvCProductName = (TextView) itemView.findViewById(R.id.tvPOIProductName);
            tvCProductPrice = (TextView) itemView.findViewById(R.id.tvPOIProductPrice);
            tvCProductPriceDiscount = (TextView) itemView.findViewById(R.id.tvPOIProductPriceDiscount);
            tvCProductTotal = (TextView) itemView.findViewById(R.id.tvPOIProductTotal);
            tvCProductAmount = (TextView) itemView.findViewById(R.id.tvPOIProductAmount);
            ll = (LinearLayout) itemView.findViewById(R.id.lnOIDiscount);

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
}
