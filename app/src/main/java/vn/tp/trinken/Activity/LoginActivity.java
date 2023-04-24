package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Model.*;
public class LoginActivity extends AppCompatActivity {
    Button btnSignup;
    TextInputEditText edtUsername;
    TextInputEditText edtPassword;

    TextView signup;
    Button btnLogin;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,IndexActivity.class));
//            try {
//                User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
//                Log.d("Text",user.toString());
//                if(user.getLast_login()!=null){
//                    finish();
//                    startActivity(new Intent(this,IndexActivity.class));
//                }else{
//                    finish();
//                    startActivity(new Intent(this,SignupProfileActivity.class));
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }

        }else {
            AnhXa();
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doLogin();
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void AnhXa(){
        btnSignup = findViewById(R.id.btn_Signup);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btn_Login);
        signup = findViewById(R.id.Signup);
    }

    private void doLogin(){
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();


        if(TextUtils.isEmpty(username)){
            edtUsername.setError("Please enter your username");
            edtUsername.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }

        Log.d("username", username);
        Log.d("password",password);

        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.login(username, password).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject obj = new JSONObject(response.body().toString());
                        String json = obj.getJSONObject("user").toString();
                        Toast.makeText(LoginActivity.this, ""+json, Toast.LENGTH_SHORT).show();
                        if(!obj.getBoolean("error")){
                            Gson gson = new Gson();
                            User user= gson.fromJson(json, User.class);
                            Log.d("User", user.toString());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("User",user);
                            bundle.putBoolean("isLogginScreen",true);

                            Intent intent = new Intent();
                            if(user.getLast_login()== null){
                                intent = new Intent(LoginActivity.this,SignupProfileActivity.class);
                                startActivity(intent);
                            }else{
                                intent = new Intent(LoginActivity.this, IndexActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }else{
                            int status_code = response.code();
                            Log.d("Failed", obj.toString());
                        }
                    }catch (JSONException e){
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject((response.errorBody().string()));
                        String message= jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("logging", t.getMessage());
            }


        });



    }
}