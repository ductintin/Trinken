package vn.tp.trinken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_item_admin, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders_items==null?0:orders_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TableLayout tl;
        TableRow tr;
        ImageView imgOIProduct;
        TextView tvIdOI, tvOIProductName, tvOIPrice, tvOIQuantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tr = (TableRow) itemView.findViewById(R.id.tbOrderItem);
            imgOIProduct = (ImageView) itemView.findViewById(R.id.imgOIProduct);
            tvIdOI = (TextView) itemView.findViewById(R.id.tvIdOI);
            tvOIProductName = (TextView) itemView.findViewById(R.id.tvOIProductName);
            tvOIPrice = (TextView) itemView.findViewById(R.id.tvOIPrice);
            tvOIQuantity = (TextView) itemView.findViewById(R.id.tvOIQuantity);

        }
    }


}
