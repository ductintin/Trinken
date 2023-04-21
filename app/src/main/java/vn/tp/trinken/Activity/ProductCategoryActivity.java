package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Adapter.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.*;
import vn.tp.trinken.Fragment.*;


public class ProductCategoryActivity extends AppCompatActivity {
    int categoryId;
    APIService apiService;

    List<Products> productsList;

    RecyclerView rcProductCategory;
    TextView tvCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Trinken_Home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        categoryId = bundle.getInt("selectedCategory");
        AnhXa();
        if(categoryId>0){
            loadProductByCategory(categoryId);
        }

    }

    private void loadProductByCategory(int categoryId) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

    }

    private void AnhXa(){
        rcProductCategory = findViewById(R.id.rcProductCategory);
        tvCategory = findViewById(R.id.tvCategoryProduct);

    }
}