package vn.tp.trinken.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import vn.tp.trinken.Activity.ProductDetailActivity;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;
public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder>{
    Context context;
    List<Products> products;

    public ProductCategoryAdapter(Context context, List<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = products.get(position);
        Glide.with(context).load(product.getImage()).placeholder(R.drawable.coke).into(holder.imgProduct);
        holder.tvProductName.setText(product.getProduct_name());
        holder.tvProductDesc.setText(product.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //check discount available
        if(product.getDiscount()!=null&&product.getDiscount().getStatus()!=0
                && date.before(product.getDiscount().getEnd_date()) && date.after(product.getDiscount().getStart_date())){
            holder.tvProductDiscount.setText(String.valueOf(product.getDiscount().getDiscount_value()));
            holder.tvProductPriceDiscount.setText(String.valueOf(product.getPrice()));
            holder.tvProductPriceDiscount.setPaintFlags(holder.tvProductPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);;
            holder.tvProductPrice.setText(String.valueOf(product.getPrice()*(100-product.getDiscount().getDiscount_value())));
        }else{
            holder.linearLayoutProductDiscountRow.setVisibility(View.INVISIBLE);
            holder.tvProductPriceDiscount.setVisibility(View.INVISIBLE);
            holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
        }
        holder.product_id = product.getProduct_id();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selectedProduct", holder.product_id);

                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("Intent product", ""+ bundle.getInt("selectedProduct"));
                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tvProductDiscount, tvProductName, tvProductPrice, tvProductPriceDiscount, tvProductDesc;
        ImageView imgProduct;

        LinearLayout linearLayoutProductDiscountRow;

        int product_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewProductRow);
            tvProductDiscount = (TextView) itemView.findViewById(R.id.tvProductDiscountRow);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductNameRow);
            tvProductPriceDiscount = (TextView) itemView.findViewById(R.id.tvProductPriceSale);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPriceRow);
            tvProductDesc = (TextView) itemView.findViewById(R.id.tvProductDesc);
            linearLayoutProductDiscountRow = (LinearLayout) itemView.findViewById(R.id.layoutDiscountRow);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProductRow);


        }
    }
}
