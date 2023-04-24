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

import org.w3c.dom.Text;

import vn.tp.trinken.Activity.*;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Categories> categories;

    public CategoryAdapter(Context context, List<Categories> categories){
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView cate_name;
        public ImageView cate_img;

        CardView cardView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            cate_name = (TextView) itemView.findViewById(R.id.name_cate);
            cate_img = (ImageView) itemView.findViewById(R.id.image_cate);
            cardView = (CardView) itemView.findViewById(R.id.cardViewCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "You has chosen "+cate_name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.cate_name.setText(category.getCategory_name());
        Glide.with(context).load(category.getImage()).into(holder.cate_img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ProductCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selectedCategoryId", category.getCategory_id());
                bundle.putString("selectedCategoryName", category.getCategory_name());

                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("Intent category", ""+ bundle.getInt("selectedCategory"));
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
