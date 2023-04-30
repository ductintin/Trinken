package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Adapter.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.*;
import vn.tp.trinken.Fragment.*;


public class ProductCategoryActivity extends AppCompatActivity {
    int categoryId;
    String categoryName;
    APIService apiService;

    List<Products> productsList;
    ProductCategoryAdapter productCategoryAdapter;

    RecyclerView rcProductCategory;
    TextView tvCategory;
    ImageView imgPCArrowBack;

    ImageView PCFilterId, PCFilterPrice, PCFilterSold, PCFilterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Trinken_Home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        categoryId = bundle.getInt("selectedCategoryId");
        categoryName = bundle.getString("selectedCategoryName");
        Log.d("cate", String.valueOf(categoryId));
        AnhXa();
        if(categoryId>0){
            loadProductByCategory(categoryId);
            tvCategory.setText(categoryName);

            imgPCArrowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            PCFilterPrice.setOnClickListener(new View.OnClickListener() {
                int count = 0;
                @Override
                public void onClick(View v) {
                    count++;
                    if (count == 1) {
                        // First click action
                        filterProduct(categoryId, 0);
                    } else if (count == 2) {
                        // Second click action
                        filterProduct(categoryId, 1);
                        count = 0; // Reset count for next double click
                    }
                }
            });

            PCFilterId.setOnClickListener(new View.OnClickListener() {
                int count = 0;
                @Override
                public void onClick(View v) {
                    count++;
                    if (count == 1) {
                        // First click action
                        Glide.with(getApplicationContext()).load(R.drawable.list).into(PCFilterId);
                        filterProduct(categoryId, 2);
                    } else if (count == 2) {
                        // Second click action
                        Glide.with(getApplicationContext()).load(R.drawable.download).into(PCFilterId);
                        filterProduct(categoryId, 3);

                        count = 0; // Reset count for next double click
                    }
                }
            });

            PCFilterName.setOnClickListener(new View.OnClickListener() {
                int count = 0;
                @Override
                public void onClick(View v) {
                    count++;
                    if (count == 1) {
                        // First click action
                        Glide.with(getApplicationContext()).load(R.drawable.increases).into(PCFilterName);
                        filterProduct(categoryId, 4);
                    } else if (count == 2) {
                        // Second click action
                        Glide.with(getApplicationContext()).load(R.drawable.descending).into(PCFilterName);
                        filterProduct(categoryId, 5);
                        count = 0; // Reset count for next double click
                    }
                }
            });



            PCFilterSold.setOnClickListener(new View.OnClickListener() {
                int count = 0;
                @Override
                public void onClick(View v) {
                    count++;
                    if (count == 1) {
                        // First click action
                        Glide.with(getApplicationContext()).load(R.drawable.sort_up).into(PCFilterSold);
                        filterProduct(categoryId, 6);
                    } else if (count == 2) {
                        // Second click action
                        Glide.with(getApplicationContext()).load(R.drawable.sort_down).into(PCFilterSold);
                        filterProduct(categoryId, 7);

                        count = 0; // Reset count for next double click
                    }
                }
            });
        }

    }

    private void loadProductByCategory(int categoryId) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductByCategory(true, categoryId).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    try {
                        productsList = response.body();
                        Log.d("products ne", productsList.toString());
                        productCategoryAdapter = new ProductCategoryAdapter(getApplicationContext(), productsList );
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()
                                ,LinearLayoutManager.VERTICAL, false);
                        rcProductCategory.setLayoutManager(layoutManager);
                        productCategoryAdapter.notifyDataSetChanged();
                        rcProductCategory.setAdapter(productCategoryAdapter);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });

    }

    private void filterProduct(int categoryId, int sort){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.filterProductByPrice(true, categoryId, sort).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    try {
                        productsList = response.body();
                        Log.d("products ne", productsList.toString());
                        productCategoryAdapter = new ProductCategoryAdapter(getApplicationContext(), productsList );
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()
                                ,LinearLayoutManager.VERTICAL, false);
                        rcProductCategory.setLayoutManager(layoutManager);
                        productCategoryAdapter.notifyDataSetChanged();
                        rcProductCategory.setAdapter(productCategoryAdapter);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });
    }

    private void AnhXa(){
        rcProductCategory = findViewById(R.id.rcProductCategory);
        tvCategory = findViewById(R.id.tvPC);
        imgPCArrowBack = findViewById(R.id.PCArrowBack);
        PCFilterId = findViewById(R.id.PCFilterId);
        PCFilterPrice = findViewById(R.id.PCFilterPrice);
        PCFilterName = findViewById(R.id.PCFilterName);
        PCFilterSold = findViewById(R.id.PCFilterSold);

    }
}