package vn.tp.trinken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories){
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
        public int cate_id;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            cate_name = (TextView) itemView.findViewById(R.id.name_cate);
            cate_img = (ImageView) itemView.findViewById(R.id.image_cate);
            cate_id = 0;

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
        Category category = categories.get(position);
        holder.cate_name.setText(category.getCategoryName());
        holder.cate_id = category.getId();
        Glide.with(context).load(category.getImage()).into(holder.cate_img);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
