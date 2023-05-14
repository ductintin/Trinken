package vn.tp.trinken.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.tp.trinken.Activity.OrderActivity;
import vn.tp.trinken.Activity.ProductCategoryActivity;
import vn.tp.trinken.Model.Payment_Methods;
import vn.tp.trinken.R;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder> {
    Context context;
    List<Payment_Methods> payment_methodsList;
    private Payment_Methods payment_methods1 ;

    public Payment_Methods getPayment_methods1(){
        return payment_methods1;
    }

    public PaymentMethodAdapter(Context context, List<Payment_Methods> payment_methods){
        this.context =context;
        this.payment_methodsList = payment_methods;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payments,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPaymentname;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtPaymentname = (TextView) itemView.findViewById(R.id.txtPaymentName);
            cardView = (CardView) itemView.findViewById(R.id.cardPayment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "You has chosen ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Payment_Methods payment_methods = payment_methodsList.get(position);
        holder.txtPaymentname.setText(payment_methods.getPayment_method_name());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_methods1=payment_methods;
                Log.d("Click payment: ", payment_methods1. toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return payment_methodsList.size();
    }
}
