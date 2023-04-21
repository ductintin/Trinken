package vn.tp.trinken.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.Adapter.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.*;
import vn.tp.trinken.Fragment.*;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int productId, amount;
    double price;
    ImageView productImage, arrowBack;
    TextView tvProductName, tvProductDescription, tvPrice, tvTotal, tvQuantity;
    Button btnDec, btnInc, btnAddToCart;

    APIService apiService;
    Products product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Trinken_Home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        productId = bundle.getInt("selectedProduct");

        if(productId>0){
            AnhXa();


            getProductById(productId);

            amount =Integer.parseInt(tvQuantity.getText().toString().trim());


            Log.d("price chua", tvPrice.getText().toString());
            Log.d("quantity", String.valueOf(amount));
            Log.d("price", String.valueOf(price));
            btnInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    amount+=1;
                    tvQuantity.setText(String.valueOf(amount));
                    setTotalPrice();
                }
            });

            btnDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(amount<=1){
                        return;
                    }else{
                        amount-=1;
                        tvQuantity.setText(String.valueOf(amount));
                        setTotalPrice();
                    }
                }
            });
            arrowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailActivity.this, "Back", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }



    }

    private void getProductById(int productId) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductById(productId).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(response.isSuccessful()){
                    try {
                        product = response.body();

                        Glide.with(getApplicationContext()).load(product.getImage()).placeholder(R.drawable.coke).into(productImage);
                        tvPrice.setText(String.valueOf(product.getPrice()));
                        tvProductName.setText(product.getProduct_name());
                        tvProductDescription.setText(product.getDescription());
                        price = Double.parseDouble(tvPrice.getText().toString().trim());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });
    }


    private void AnhXa(){
        toolbar = findViewById(R.id.toolBartProductDetail);
        productImage = findViewById(R.id.imgPD);
        tvProductDescription = findViewById(R.id.tvPDDescription);
        tvProductName = findViewById(R.id.tvPDName);
        tvPrice = findViewById(R.id.tvPDPrice);
        tvQuantity = findViewById(R.id.tvPDQuantity);
        btnDec = findViewById(R.id.btnPDDec);
        btnInc = findViewById(R.id.btnPDInc);
        btnAddToCart = findViewById(R.id.btnPDAddToCart);
        tvTotal = findViewById(R.id.tvPDTotal);
        arrowBack = (ImageView)findViewById(R.id.PDArrowBack);

    }

    private void setTotalPrice(){
        double totalPrice = 0.0;
        totalPrice = price * amount;
        DecimalFormat df = new DecimalFormat("#.##");
        tvTotal.setText(String.valueOf(df.format(totalPrice)));
    }



}