package vn.tp.trinken.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonyfdev.dropdownview.DropDownView;
import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.Activity.ProductCategoryActivity;
import vn.tp.trinken.Model.Categories;
import vn.tp.trinken.Model.Orders;
import vn.tp.trinken.Model.User;
import vn.tp.trinken.Model.Orders_Items;
import vn.tp.trinken.R;
import vn.tp.trinken.Contants.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Adapter.*;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<Orders> orderList;

    List<Orders_Items> orders_itemsList;


    OrderItemAdminAdapter orderItemAdminAdapter;

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

        DropDownView dropDownView;
        RecyclerView rcOrderItem;
        LinearLayout llOrderCheck;
        ImageView imgCheck;

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
            dropDownView = (DropDownView) itemView.findViewById(R.id.drop_down_view_order);
            rcOrderItem = (RecyclerView) itemView.findViewById(R.id.rc_order_item_admin);
            llOrderCheck = (LinearLayout) itemView.findViewById(R.id.ll_check_order);
            imgCheck = (ImageView) itemView.findViewById(R.id.ic_check_order);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

    @SuppressLint("MissingInflatedId")
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

        if(orders.getOrder_status().getStatus_id()>4){
            holder.llOrderCheck.setVisibility(View.GONE);
        }



        try {
            User user = SharedPrefManager.getInstance(context).getUser();
            if (user.getRoles().getRole_id()==1){
                holder.llOrderCheck.setVisibility(View.VISIBLE);
                holder.dropDownView.setVisibility(View.VISIBLE);
                View expandedView = LayoutInflater.from(context).inflate(R.layout.item_order_item_admin, null, false);
                holder.dropDownView.setExpandedView(expandedView);

               holder.cardView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (holder.dropDownView.isExpanded()) {
                           holder.rcOrderItem.setVisibility(View.GONE);
                           holder.dropDownView.collapseDropDown();

                       } else {
                           holder.rcOrderItem.setVisibility(View.VISIBLE);
                           getOrderItem(orders.getOrder_id(), holder.rcOrderItem);
                           holder.dropDownView.expandDropDown();
                       }
                   }
               });

               holder.imgCheck.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       int orderStatusId = orders.getOrder_status().getStatus_id();
                      switch (orderStatusId){
                           case 1 :case 2: case 3:
                              Toast.makeText(context, "Hello" +user.getUser_id()+""+orders.getOrder_id()+""+orderStatusId, Toast.LENGTH_SHORT).show();
                              verifyOrder(user.getUser_id(), orders.getOrder_id(), orderStatusId+1);
                              setListener(orders.getOrder_status().getStatus_id());
                               break;
                           default:
                               break;
                       }
                   }
               });
            }else{
                holder.llOrderCheck.setVisibility(View.GONE);
                holder.dropDownView.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }




       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    private APIService apiService;

    @Override
    public int getItemCount() {
        if( orderList==null)
        {return 0;}
        return orderList.size();
    }

    private void getOrderItem(int orderId, RecyclerView rcOrderItem){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrderItemByOrderId(orderId).enqueue(new Callback<List<Orders_Items>>() {
            @Override
            public void onResponse(Call<List<Orders_Items>> call, Response<List<Orders_Items>> response) {
                if(response.isSuccessful()){
                    orders_itemsList = response.body();
                    orderItemAdminAdapter = new OrderItemAdminAdapter(context, orders_itemsList);
                    rcOrderItem.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext()
                            ,LinearLayoutManager.VERTICAL, false);
                    rcOrderItem.setLayoutManager(layoutManager);

                    orderItemAdminAdapter.notifyDataSetChanged();
                    rcOrderItem.setAdapter(orderItemAdminAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Orders_Items>> call, Throwable t) {
                Log.d("orderAdapter", t.getMessage());

            }
        });
    }

    private void verifyOrder(int userId, int orderId, int statusId){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.verifyOrder(userId, orderId, statusId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    setListener(statusId);
                    Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void setListener(int status){
        this.orderList = getOrderByStatus(status);
        notifyDataSetChanged();
        OrderAdapter orderAdapter = new OrderAdapter(context, orderList);
        orderAdapter.notifyDataSetChanged();
    }
    private List<Orders> getOrderByStatus(int status){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrderByStatus(status).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    orderList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
        if(orderList!=null){
            return orderList;
        }else
            return orderList = new ArrayList<>();
    }

}
