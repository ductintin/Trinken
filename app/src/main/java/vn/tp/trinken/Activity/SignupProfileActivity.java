package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Dto.*;
public class SignupProfileActivity extends AppCompatActivity {

    EditText edtFirstname, edtLastname, edtPhone, edtAddress;
    Button btnConfirm;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGender;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_profile);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            user = bundle.getSerializable("User",User.class);
        }

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

                UserDto userDto = new UserDto(user.getUser_id(), firstName, lastName, phoneNumber, address, gender);
                doSignupProfile(userDto);
            }
        });

    }

    private void AnhXa(){
        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        btnConfirm = findViewById(R.id.btnConfirm);
        radioGroupGender = findViewById(R.id.radioGroupGender);
    }

    private void doSignupProfile(UserDto userDto){
        String [] users = userDto.toString().split(" ");
        for (int i = 0; i < 5; i++) {
            if(TextUtils.isEmpty(users[i])){

            }

        }
    }


}