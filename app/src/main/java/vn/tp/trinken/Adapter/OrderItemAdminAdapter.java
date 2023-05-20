package vn.tp.trinken.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import vn.tp.trinken.API.APIService;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;

public class OrderItemAdminAdapter extends RecyclerView.Adapter<OrderItemAdminAdapter.MyViewHolder>{
    Context context;
    List<Orders_Items> orders_items;

    APIService apiService;

    public OrderItemAdminAdapter(Context context, List<Orders_Items> orders_items) {
        this.context = context;
        this.orders_items = orders_items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pre_order_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders_Items ordersItems = orders_items.get(position);
        int quantity = ordersItems.getQuantity();
        holder.tvCProductName.setText(ordersItems.getProducts().getProduct_name());
        Glide.with(context).load(ordersItems.getProducts().getImage()).placeholder(R.drawable.coke).into(holder.imgProduct);
        DecimalFormat df = new DecimalFormat("0.00");
        if(checkValidDiscount(ordersItems.getProducts().getDiscount())){
            holder.tvCProductPrice.setText(df.format(ordersItems.getProducts().getPrice()*(100-ordersItems.getProducts().getDiscount().getDiscount_value())/100));
            holder.tvCProductPriceDiscount.setText(String.valueOf(ordersItems.getProducts().getPrice()));
            holder.tvCProductPriceDiscount.setText(String.valueOf(ordersItems.getProducts().getPrice()));
            holder.tvCProductPriceDiscount.setPaintFlags(holder.tvCProductPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);;
        }else{
            holder.tvCProductPriceDiscount.setVisibility(View.GONE);
            holder.ll.setVisibility(View.GONE);
            holder.tvCProductPrice.setText(String.valueOf(ordersItems.getProducts().getPrice()));
        }
        holder.tvCProductAmount.setText(String.valueOf(quantity));
        holder.tvCProductTotal.setText(df.format(ordersItems.getPrice()));


    }

    @Override
    public int getItemCount() {
        return orders_items==null?0:orders_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        /*TableLayout tl;
        TableRow tr;
        ImageView imgOIProduct;
        TextView tvIdOI, tvOIProductName, tvOIPrice, tvOIQuantity;*/

        CardView cardView;
        ImageView imgProduct;
        TextView tvCProductName, tvCProductPrice, tvCProductPriceDiscount, tvCProductTotal, tvCProductAmount;
        LinearLayout ll;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           /* tr = (TableRow) itemView.findViewById(R.id.tbOrderItem);
            imgOIProduct = (ImageView) itemView.findViewById(R.id.imgOIProduct);
            tvIdOI = (TextView) itemView.findViewById(R.id.tvIdOI);
            tvOIProductName = (TextView) itemView.findViewById(R.id.tvOIProductName);
            tvOIPrice = (TextView) itemView.findViewById(R.id.tvOIPrice);
            tvOIQuantity = (TextView) itemView.findViewById(R.id.tvOIQuantity);*/

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
