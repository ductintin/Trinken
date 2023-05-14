package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Adapter.OrderItemAdapter;
import vn.tp.trinken.Adapter.PaymentMethodAdapter;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Dto.OrderDto;
import vn.tp.trinken.Model.CartItem;
import vn.tp.trinken.Model.Payment_Methods;
import vn.tp.trinken.Model.Shipping_Addresses;
import vn.tp.trinken.Model.User;
import vn.tp.trinken.R;
import vn.tp.trinken.Fragment.*;

public class OrderActivity extends AppCompatActivity {

    ArrayList<CartItem> cartItems = new ArrayList<>();

    CartItem cartItem;

    OrderItemAdapter orderItemAdapter;

    RecyclerView rc, rcPayment;
    Button btnConfirm;
    TextView txtAddAddress,txtAddPayment, txtName, txtPhone, txtAddress,txtTotalAmount, txtPayment;

    PaymentMethodAdapter paymentMethodAdapter;
    List<Payment_Methods> payment_methods;
    Payment_Methods payment_methods1;
    APIService apiService;

    Integer shipId ;
    Integer payId ;
    double totalAmount=0;
    Shipping_Addresses shipping_addresses1 = new Shipping_Addresses();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        cartItems = getIntent().getParcelableArrayListExtra("listCartItem");
        Log.d("cartItem", cartItems.toString());
        for(CartItem cartItem1:cartItems){
            totalAmount=totalAmount+cartItem1.getPrice();
        }
        AnhXa();
        getPayment();
        txtTotalAmount.setText((int) totalAmount + "$");
//        txtPayment.setText(paymentMethodAdapter.getPayment_methods1().getPayment_method_name());
        /*cartAdapter = new CartAdapter(this, cartItems);
        rc.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL, false);

        rc.setLayoutManager(layoutManager);
        cartAdapter.notifyDataSetChanged();
        rc.setAdapter(cartAdapter);*/

        orderItemAdapter = new OrderItemAdapter(this, cartItems);
        rc.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL, false);

        rc.setLayoutManager(layoutManager);
        orderItemAdapter.notifyDataSetChanged();
        rc.setAdapter(orderItemAdapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                    OrderDto orderDto = new OrderDto();
                    addOrder(user.getCart().getId(),shipId,payId );

                    Intent intent = new Intent(OrderActivity.this, IndexActivity.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAddress();
            }
        });
        txtAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPayment();
            }
        });
    }

    private void AnhXa(){
        rc = findViewById(R.id.rcCartItem);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtAddAddress = findViewById(R.id.txtAddAddress);
        txtAddPayment = findViewById(R.id.txtAddPayment);
        txtName=findViewById(R.id.txtName);
        txtPhone=findViewById(R.id.txtPhone);
        txtAddress=findViewById(R.id.txtAddress);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        txtPayment= findViewById(R.id.txtPayment);
    }

    private void addOrder(Integer cartId, Integer shipId, Integer payId){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.addOrder(cartId,shipId,payId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Toast.makeText(OrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private  void showAddAddress(){
        Dialog dialog = new Dialog(OrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_address_dialog);

        EditText edtName = dialog.findViewById(R.id.edtName);
        EditText edtPhone = dialog.findViewById((R.id.edtPhone));
        EditText edtAddress = dialog.findViewById(R.id.edtAddress);
        Button btnAdd = dialog.findViewById(R.id.btnAddAddress);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shipping_Addresses shipping_addresses2=new Shipping_Addresses();
                shipping_addresses2.setName(edtName.getText().toString().trim());
                shipping_addresses2.setPhone_number(edtPhone.getText().toString().trim());
                shipping_addresses2.setAddress(edtAddress.getText().toString().trim());
                try {
                    User user =SharedPrefManager.getInstance(getApplicationContext()).getUser();
                    shipping_addresses2.setUser(user);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                AddAddress(shipping_addresses2);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void AddAddress(Shipping_Addresses shipping_addresses){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.addAddress(shipping_addresses.getUser().getUser_id(),shipping_addresses.getName(),shipping_addresses.getPhone_number(),shipping_addresses.getAddress()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String json = jsonObject.getJSONObject("shippingAddress").toString();
                        Gson gson = new Gson();
                        shipping_addresses1=gson.fromJson(json, Shipping_Addresses.class);
                        shipId = shipping_addresses1.getAddress_id();
                        Log.d("Shippping: ",shipping_addresses1.toString());
                        txtName.setText(shipping_addresses1.getName());
                        Log.d("Nameee: ",txtName.getText().toString());
                        txtPhone.setText(shipping_addresses1.getPhone_number());
                        txtAddress.setText("Địa chỉ: "+shipping_addresses1.getAddress());
                        Toast.makeText(OrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject((response.errorBody().string()));
                        String message= jsonObject.getString("message");
                        Toast.makeText(OrderActivity.this, message, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
    private void showAddPayment(){
        Dialog dialog = new Dialog(OrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_payment_dialog);

        rcPayment = dialog.findViewById(R.id.rcPayment);
        getPayment();


        dialog.show();
    }

    private void getPayment(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPayment().enqueue(new Callback<List<Payment_Methods>>() {
            @Override
            public void onResponse(Call<List<Payment_Methods>> call, Response<List<Payment_Methods>> response) {
                if(response.isSuccessful()){
                    payment_methods = response.body();
                    payment_methods1=payment_methods.get(0);
                    Log.d("Payment method: ", payment_methods.toString());
//                    paymentMethodAdapter = new PaymentMethodAdapter(getApplicationContext(), payment_methods);
//                    rcPayment.setHasFixedSize(true);
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()
//                            ,LinearLayoutManager.VERTICAL, false);
//
//                    rcPayment.setLayoutManager(layoutManager);
//                    paymentMethodAdapter.notifyDataSetChanged();
//                    rcPayment.setAdapter(paymentMethodAdapter);
                    payId=payment_methods1.getPayment_method_id();
                    txtPayment.setText(payment_methods1.getPayment_method_name());
                }else{

                }

            }

            @Override
            public void onFailure(Call<List<Payment_Methods>> call, Throwable t) {

            }
        });
    }
}