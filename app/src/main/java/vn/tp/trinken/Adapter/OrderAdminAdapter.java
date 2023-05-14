package vn.tp.trinken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonyfdev.dropdownview.DropDownView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.R;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.MyViewHolder> {
    Context context;
    List<Orders> ordersList;

    APIService apiService;

    public OrderAdminAdapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders order = ordersList.get(position);
        holder.tvIdOrder.setText(String.valueOf(position));
        holder.tvCustomer.setText(order.getCustomer().getFirst_name()+" "+order.getCustomer().getLast_name());
        holder.tvOrderStatus.setText(order.getOrder_status().getStatus_name());
        holder.tvPaymentMethod.setText(order.getPayment_methods().getPayment_method_name());
        holder.tvAddress.setText(order.getShipping_address().getAddress());

        View expandedView = LayoutInflater.from(context).inflate(R.layout.item_order_item_admin, null, false);
        holder.dropDownView.setExpandedView(expandedView);

        holder.tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.dropDownView.isExpanded()) {
                    holder.dropDownView.collapseDropDown();
                } else {
                    holder.dropDownView.expandDropDown();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return ordersList==null?0:ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TableLayout tl;
        TableRow tr;
        TextView tvIdOrder, tvCustomer, tvOrderStatus, tvPaymentMethod, tvAddress;
        DropDownView dropDownView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tl = (TableLayout) itemView.findViewById(R.id.tbOI);
            tr = (TableRow) itemView.findViewById(R.id.tbOrder);
            tvIdOrder = (TextView) itemView.findViewById(R.id.tvIdOrder);
            tvCustomer = (TextView) itemView.findViewById(R.id.tvCustomer);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tvOrderStatus);
            tvPaymentMethod = (TextView) itemView.findViewById(R.id.tvPaymentMethod);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);

            dropDownView = (DropDownView) itemView.findViewById(R.id.drop_down_view_order);

        }
    }

    private List<Orders> getOrdersList(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrder().enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                     ordersList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
        if(ordersList!=null){
            return ordersList;
        }else
            return ordersList = new ArrayList<>();
    }

    private List<Orders> getOrderByStatus(int status){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrderByStatus(status).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    ordersList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
        if(ordersList!=null){
            return ordersList;
        }else
            return ordersList = new ArrayList<>();
    }
}
