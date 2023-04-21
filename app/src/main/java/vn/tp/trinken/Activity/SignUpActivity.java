package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Model.SignUp;
import vn.tp.trinken.R;
public class SignUpActivity extends AppCompatActivity {

    Button btnSignup;
    EditText edtUsername;
    EditText edtPassword;
    EditText edtEmail;
    EditText edtRepassword;
    APIService apiService;

    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username =edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String repassword = edtRepassword.getText().toString().trim();
                SignUp signUp =new SignUp(username,email,password,repassword);
                doSignup(signUp);
            }
        });
    }

    public void AnhXa(){
        btnSignup = findViewById(R.id.btnSignup);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtRePassword);
        tvLogin = findViewById(R.id.tvLogin);
    }

    public void doSignup(SignUp signUp){
        if(TextUtils.isEmpty(signUp.getUserName())){
            edtUsername.setError("Please enter username");
            edtUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(signUp.getEmail())){
            edtEmail.setError("Please enter your email");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(signUp.getEmail()).matches()){
            edtEmail.setError("Enter a valid email");
            edtEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(signUp.getPassword())){
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(signUp.getRepassword())){
            edtRepassword.setError("Please enter repassword");
            edtRepassword.requestFocus();
            return;
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.signup(signUp).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if(!jsonObject.getBoolean("error")){
                            String message= jsonObject.getString("message");
                            Log.d("Hello", message);
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else if(response.code()==409){
                    try {
                        JSONObject jsonObject = new JSONObject((response.errorBody().string()));
                        String message= jsonObject.getString("message");
                        Log.d("Hello", message);
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}