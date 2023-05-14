package vn.tp.trinken.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.*;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import vn.tp.trinken.Model.*;
import vn.tp.trinken.Dto.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.*;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int productId, amount;
    double price;
    ImageView productImage, arrowBack, addToCart;
    TextView tvProductName, tvProductDescription, tvPrice, tvTotal, tvQuantity, tvPDPriceDiscount;
    Button btnDec, btnInc, btnBuy;

    LinearLayout layoutProductDiscount;

    APIService apiService;
    Products product;

    User user;
    Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Trinken_Home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        productId = bundle.getInt("selectedProduct");
        try {
            user = SharedPrefManager.getInstance(this).getUser();
            cart = user.getCart();
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
                            Toast.makeText(ProductDetailActivity.this, "Can't decrease product anymore!", Toast.LENGTH_SHORT).show();
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

                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart();
                    }
                });

                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    private void addToCart() {
        CartItemDto cartItemDto = new CartItemDto();
        double money;
        if(amount>0){
            cartItemDto.setCartId(cart.getId());
            cartItemDto.setProductId(product.getProduct_id());
            cartItemDto.setQuantity(amount);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DecimalFormat df = new DecimalFormat("#.##");
            Date date = new Date();
            if(product.getDiscount()!=null&&product.getDiscount().getStatus()!=0
                    && date.before(product.getDiscount().getEnd_date()) && date.after(product.getDiscount().getStart_date())){
                money = (product.getPrice()*(100-product.getDiscount().getDiscount_value())/100)*amount;
            }else{
                money = product.getPrice()*amount;
            }
            cartItemDto.setPrice(money);

        }else{
            Toast.makeText(this, "Please choose amount", Toast.LENGTH_SHORT).show();
        }

        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.addCartItem(cartItemDto).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if(response.isSuccessful()){
                    JsonElement jsonObject = response.body();
                    Toast.makeText(ProductDetailActivity.this, "Add to cart successfully!", Toast.LENGTH_SHORT).show();


                }else{
                    int status_code = response.code();
                    switch (status_code){
                        case 501:
                            Toast.makeText(ProductDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.d("loi add cart", t.getMessage());
            }
        });
    }

    private void getProductById(int productId) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductById(productId).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(@NonNull Call<Products> call, @NonNull Response<Products> response) {
                if(response.isSuccessful()){
                    try {
                        product = response.body();

                        Glide.with(getApplicationContext()).load(product.getImage()).placeholder(R.drawable.coke).into(productImage);

                        tvProductName.setText(product.getProduct_name());
                        tvProductDescription.setText(product.getDescription());

                        DecimalFormat df = new DecimalFormat("#.##");
                        Date date = new Date();
                        if(product.getDiscount()!=null&&product.getDiscount().getStatus()!=0
                                && date.before(product.getDiscount().getEnd_date()) && date.after(product.getDiscount().getStart_date())){
                            price = (product.getPrice()*(100-product.getDiscount().getDiscount_value())/100)*amount;
                            tvPDPriceDiscount.setText(String.valueOf(product.getPrice()));
                            tvPDPriceDiscount.setPaintFlags(tvPDPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tvPrice.setText(df.format(price));
                            tvTotal.setText(df.format(price));

                        }else{
                            price = product.getPrice();
                            layoutProductDiscount.setVisibility(View.GONE);
                            tvPrice.setText(String.valueOf(product.getPrice()));
                            tvTotal.setText(String.valueOf(product.getPrice()));
                        }




                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(@NonNull Call<Products> call, @NonNull Throwable t) {

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
        tvPDPriceDiscount = findViewById(R.id.tvPDPriceDiscount);
        btnDec = findViewById(R.id.btnPDDec);
        btnInc = findViewById(R.id.btnPDInc);
        btnBuy = findViewById(R.id.btnPDBuy);
        tvTotal = findViewById(R.id.tvPDTotal);
        arrowBack = (ImageView)findViewById(R.id.PDArrowBack);
        addToCart = (ImageView) findViewById(R.id.imageViewPDAddToCart);
        layoutProductDiscount = (LinearLayout) findViewById(R.id.layoutPDProductDiscount);

    }

    private void setTotalPrice(){
        double totalPrice;
        totalPrice = price * amount;
        DecimalFormat df = new DecimalFormat("#.##");
        tvTotal.setText((df.format(totalPrice)));
    }



}