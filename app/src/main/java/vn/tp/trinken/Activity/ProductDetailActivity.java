package vn.tp.trinken.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import vn.tp.trinken.Adapter.CartAdapter;
import vn.tp.trinken.Fragment.CartFragment;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Dto.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.*;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int productId, amount;
    double price;
    ImageView productImage, arrowBack;
    TextView tvProductName, tvProductDescription, tvPrice, tvTotal, tvQuantity, tvPDPriceDiscount;
    Button btnDec, btnInc, btnBuy;

    LinearLayout layoutProductDiscount;

    APIService apiService;
    Products product;

    User user;
    Cart cart;

    ArrayList<CartItem> cartItems = new ArrayList<>();


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

                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                        builder.setMessage("Mua ngay");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Mua ngay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addToCart();
                                dialog.dismiss();
                                Intent intent1 = new Intent(ProductDetailActivity.this, OrderActivity.class);

                                getCartItem(cart.getId());
                                intent1.putParcelableArrayListExtra("listCartItem", cartItems);
                                startActivity(intent1);

                            }
                        });

                        builder.setNegativeButton("Thêm vào giỏ hàng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addToCart();
                                dialog.dismiss();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();


                    }
                });

            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    private void getCartItem(int cartId) {
        Log.d("cart id ne", String.valueOf(cartId));
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCartItem(cartId).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if(response.isSuccessful()){
                    cartItems = (ArrayList<CartItem>) response.body();

                }else{

                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Log.d("Loi o productDetail ", t.getMessage());
            }
        });
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
        layoutProductDiscount = (LinearLayout) findViewById(R.id.layoutPDProductDiscount);

    }

    private void setTotalPrice(){
        double totalPrice;
        totalPrice = price * amount;
        DecimalFormat df = new DecimalFormat("#.##");
        tvTotal.setText((df.format(totalPrice)));
    }



}