package vn.tp.trinken.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;
import vn.tp.trinken.Activity.*;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    Context context;
    List<Products> products;

    int layout;

    public ProductAdapter(Context context, List<Products> products, int layout) {
        this.context = context;
        this.products = products;
        this.layout = layout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = products.get(position);
        holder.product_name.setText(product.getProduct_name());
        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.coke)
                .into(holder.product_image);
        holder.product_id = product.getProduct_id();
        holder.product_price.setText(String.valueOf(product.getPrice()));

        if(product.getDiscount()!=null){
            holder.tvProductDiscount.setText(String.valueOf(product.getDiscount().getDiscount_value()));
            holder.product_price.setTextSize(10);
            holder.product_price.setPaintFlags(holder.product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvProductPriceDiscount.setText(String.valueOf(product.getPrice()*(100-product.getDiscount().getDiscount_value())));
        }else{
            holder.layoutProductDiscount.setVisibility(View.INVISIBLE);
            holder.tvProductPriceDiscount.setVisibility(View.INVISIBLE);

        }
        holder.product_card.setOnClickListener(new View.OnClickListener() {
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
        ImageView product_image;
        TextView product_name;
        CardView product_card;
        TextView product_price;
        ImageView add_product;
        TextView tvProductDiscount;
        TextView tvProductPriceDiscount;
        LinearLayout layoutProductDiscount;
        int product_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = (ImageView) itemView.findViewById(R.id.imageProduct);
            product_name = (TextView) itemView.findViewById(R.id.tvProductName);
            product_card = (CardView) itemView.findViewById(R.id.cardProduct);
            product_price = (TextView) itemView.findViewById(R.id.tvProductPrice);

            add_product = (ImageView) itemView.findViewById(R.id.imgAddProductToCart);
            tvProductDiscount = (TextView) itemView.findViewById(R.id.tvProductDiscount);
            tvProductPriceDiscount = (TextView) itemView.findViewById(R.id.tvProductPriceDiscount);
            layoutProductDiscount = (LinearLayout) itemView.findViewById(R.id.layoutDiscount);

            product_id = 0;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You choose "+product_name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
