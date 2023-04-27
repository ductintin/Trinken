package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Dto.*;
public class SignupProfileActivity extends AppCompatActivity {

    EditText edtFirstname, edtLastname, edtPhone, edtAddress;
    Button btnConfirm;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGender;

    User user = new User();

    APIService apiService;
    Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_profile);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("userid");

        AnhXa();

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    radioButtonGender = findViewById(i);
                    Toast.makeText(SignupProfileActivity.this, "Gioi tinh" + radioButtonGender.getText(), Toast.LENGTH_SHORT).show();

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = edtFirstname.getText().toString().trim();
                String lastName = edtLastname.getText().toString().trim();
                String phoneNumber = edtPhone.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                String gender = radioButtonGender.getText().toString().trim();

                ProfileDto profileDto= new ProfileDto(null,firstName,lastName,gender,null,phoneNumber,address,null);
                doSignupProfile(id,profileDto);
            }
        });
    }

    private void AnhXa(){
        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);
        edtPhone = findViewById(R.id.edtPhone);
        edtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        edtAddress = findViewById(R.id.edtAddress);
        btnConfirm = findViewById(R.id.btnConfirm);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonGender = findViewById(radioGroupGender.getCheckedRadioButtonId());
    }

    private void doSignupProfile(Integer id, ProfileDto profileDto){
        if(TextUtils.isEmpty(profileDto.getFirstName())){
            edtFirstname.setError("Please enter firstname");
            edtFirstname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(profileDto.getLastName())){
            edtLastname.setError("Please enter lastname");
            edtLastname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty((profileDto.getPhoneNumber()))){
            edtPhone.setError("Please enter phone");
            edtPhone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(profileDto.getAddress())){
            edtAddress.setError("Please enter address");
            edtAddress.requestFocus();
            return;
        }
        if(radioGroupGender.getCheckedRadioButtonId()==-1){
            radioButtonGender.setError("Please select Gender");
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateProfile(id, profileDto.profile()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(response.body().toString());
                        String json = obj.getJSONObject("user").toString();
                        if (!obj.getBoolean("error")) {
                            Gson gson = new Gson();
                            User user = gson.fromJson(json, User.class);
//                        Log.d("User", user.toString());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            Intent intent = new Intent(SignupProfileActivity.this, IndexActivity.class);
                            startActivity(intent);
                        } else {
                            int statusCode = response.code();
                            Toast.makeText(SignupProfileActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject((response.errorBody().string()));
                        String message= jsonObject.getString("message");
                        Log.d("skdfsak",message);
                        Toast.makeText(SignupProfileActivity.this, message, Toast.LENGTH_SHORT).show();
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


}