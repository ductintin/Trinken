package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.*;
import vn.tp.trinken.Activity.*;
import vn.tp.trinken.API.*;
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
                        Toast.makeText(LoginActivity.this, ""+obj, Toast.LENGTH_SHORT).show();
                        Log.d("User", obj.toString());
                        if(!obj.getBoolean("error")){
                            JSONObject userJson = obj.getJSONObject("user");

                            Users user = new Users(
                                    userJson.getInt("user_id")
                            );

                            String last_login = userJson.getString("last_login");


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("User",user);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            if(last_login == "null"){
                                intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                            }
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            int status_code = response.code();
                            Log.d("Failed", obj.toString());
                        }
                    }catch (JSONException e){
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