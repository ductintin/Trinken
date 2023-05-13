package vn.tp.trinken.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.R;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Dto.*;

public class ProductTableAdapter extends RecyclerView.Adapter<ProductTableAdapter.MyViewHolder> {
    Context context;
    List<Products> products;

    APIService apiService;
    List<Categories> categories = new ArrayList<>();
    List<Brands> brands = new ArrayList<>();




    public ProductTableAdapter(Context context, List<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_table, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = products.get(position);
        if (!product.isActive()){
            Glide.with(context).load(R.drawable.ic_restore).into(holder.btnDelete);
        }else{
            Glide.with(context).load(R.drawable.ic_delete).into(holder.btnDelete);

        }
        holder.tvSTT.setText(String.valueOf(position+1));
        holder.tvName.setText(product.getProduct_name());
        holder.tvDesc.setText(product.getDescription());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
        holder.tvBrand.setText(product.getBrand().getBrand_name());
        String cate = "";
        for(Categories category : product.getCategories()){
            cate +=category.getCategory_name()+" ";
        }
        holder.tvCate.setText(cate);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProduct(product, v);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = product.getProduct_id();
                if(product.isActive()){
                    updateActiveProduct(id, false);
                    try {
                        setListenerList(false);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    updateActiveProduct(id, true);
                    try {
                        setListenerList(true);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    private void showEditProduct(Products products, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
        View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.add_product_dialog, null);

        Spinner spinnerCate, spinnerBrand;
        TextInputEditText tvProductName, tvProductDesc, tvProductPrice, tvProductQuantity;
        ImageView imgProduct;
        Button btnImgFileProduct, btnConfirmAddProduct;
        ImageView imgDialogProduct;


        spinnerCate = dialogView.findViewById(R.id.spinner_categories);
        spinnerBrand = dialogView.findViewById(R.id.spinner_brand);
        tvProductName = dialogView.findViewById(R.id.edtProductName);
        tvProductDesc = dialogView.findViewById(R.id.edtProductDesc);
        tvProductPrice = dialogView.findViewById(R.id.edtProductPrice);
        tvProductQuantity = dialogView.findViewById(R.id.edtProductQuantity);
        btnImgFileProduct = dialogView.findViewById(R.id.btnImgFileProduct);
        btnConfirmAddProduct = dialogView.findViewById(R.id.btnConfirmAddProduct);
        imgDialogProduct = dialogView.findViewById(R.id.imgDialogProduct);

        getListCategory();
        getListBrand();

        spinnerCate.setAdapter(new ArrayAdapter<Categories>(view.getRootView().getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories));
        spinnerBrand.setAdapter(new ArrayAdapter<Brands>(view.getRootView().getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brands));

        DecimalFormat df = new DecimalFormat("0.00");
        tvProductName.setText(products.getProduct_name());
        tvProductDesc.setText(products.getDescription());
        tvProductPrice.setText(df.format(products.getPrice()));
        tvProductQuantity.setText(String.valueOf(products.getQuantity()));



        if(products.getImage()!=null){
            Glide.with(dialogView).load(products.getImage()).into(imgDialogProduct);
        }

        btnImgFileProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




        builder.setView(dialogView);
        builder.setCancelable(true);
        builder.show();



    }

    @Override
    public int getItemCount() {
        return products==null?0:products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TableLayout tableLayout;
        TableRow tableRow;
        TextView tvSTT, tvName, tvPrice, tvDesc, tvCate, tvBrand;
        ImageView btnEdit, btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tableLayout = (TableLayout) itemView.findViewById(R.id.tlProduct);
            tableRow = (TableRow) itemView.findViewById(R.id.trProduct);
            tvSTT = (TextView) itemView.findViewById(R.id.tvtrProductSTT);
            tvName = (TextView) itemView.findViewById(R.id.tvtrProductName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvtrProductPrice);
            tvDesc = (TextView) itemView.findViewById(R.id.tvtrProductDesc);
            tvCate = (TextView) itemView.findViewById(R.id.tvtrProductCate);
            tvBrand = (TextView) itemView.findViewById(R.id.tvtrProductBrand);
            btnEdit = (ImageView) itemView.findViewById(R.id.btnEditProduct);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDeleteProduct);
        }
    }

    private void getListCategory(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveCategory().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if(response.isSuccessful()){
                    categories = response.body();
                    Log.d("cate", categories.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });
    }

    private void getListBrand(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveBrand(true).enqueue(new Callback<List<Brands>>() {
            @Override
            public void onResponse(Call<List<Brands>> call, Response<List<Brands>> response) {
               if(response.isSuccessful()){
                   brands = response.body();
                   Log.d("brand", brands.toString());
               }
            }

            @Override
            public void onFailure(Call<List<Brands>> call, Throwable t) {

            }
        });

    }

    private void updateProduct(int id, ProductDto productDto){

        apiService= RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateProduct(id, productDto).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void updateActiveProduct(int id, boolean active){
        apiService= RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateActiveProduct(id,active).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }


    private void setListenerList(boolean action) throws ParseException {
        this.products = getProducts(action);
        notifyDataSetChanged();
        ProductTableAdapter productTableAdapter = new ProductTableAdapter(context, products) ;
        productTableAdapter.notifyDataSetChanged();
    }

    private List<Products> getProducts(boolean active){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveProduct(active).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    products = response.body();
                    try {
                        setListenerList(active);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });
        return products;
    }



}
