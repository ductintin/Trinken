package vn.tp.trinken.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import vn.tp.trinken.Activity.ProductCategoryActivity;
import vn.tp.trinken.Model.Categories;
import vn.tp.trinken.Model.Orders;
import vn.tp.trinken.Model.Orders_Items;
import vn.tp.trinken.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<Orders> orderList;

    public OrderAdapter(Context context, List<Orders> orderList){
        this.context=context;
        this.orderList=orderList;
    }
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView cate_id, cate_product,cate_delivery, cate_orderDate, cate_status, cate_payment, cate_totalAmount;

        CardView cardView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            cate_id = (TextView) itemView.findViewById(R.id.txtOrderId);
            cate_product = (TextView) itemView.findViewById(R.id.txtProducts);
            cate_delivery =(TextView) itemView.findViewById(R.id.txtDelivery);
            cate_orderDate=(TextView) itemView.findViewById(R.id.txtOrderDate);
            cate_status=(TextView) itemView.findViewById(R.id.txtStatus);
            cate_payment=(TextView) itemView.findViewById(R.id.txtPayment);
            cate_totalAmount = (TextView) itemView.findViewById(R.id.txtTotalAmount);
            cardView = (CardView) itemView.findViewById(R.id.cardOrder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        Orders orders = orderList.get(position);
        holder.cate_id.setText("Id: "+orders.getOrder_id());
        String products ="Products:";
        for(Orders_Items orders_items : orders.getOrders_items()){
            products=products+" "+orders_items.getProducts().getProduct_name()+",";
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat df = new DecimalFormat("0.00");
        holder.cate_product.setText(products);
        holder.cate_orderDate.setText("OrderDate: " +dateFormat.format(orders.getOrder_date()));
        holder.cate_status.setText("Status: "+orders.getOrder_status().getStatus_name());
        holder.cate_payment.setText(orders.getPayment_methods().getPayment_method_name());
        holder.cate_totalAmount.setText("Tổng tiền: " +df.format(orders.getTotal_amount())+"$");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context.getApplicationContext(), ProductCategoryActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("selectedCategoryId", category.getCategory_id());
//                bundle.putString("selectedCategoryName", category.getCategory_name());
//
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.d("Intent category", ""+ bundle.getInt("selectedCategory"));
//                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if( orderList==null)
        {return 0;}
        return orderList.size();
    }
}
