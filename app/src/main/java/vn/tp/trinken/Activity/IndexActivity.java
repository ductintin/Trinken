package vn.tp.trinken.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import vn.tp.trinken.*;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Fragment.*;
import vn.tp.trinken.Model.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.ParseException;

public class IndexActivity extends AppCompatActivity implements NavigationBarView
        .OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    CartFragment cartFragment = new CartFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    Toolbar toolbar;


    Boolean isLogginScreen = false;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Trinken_Home);
        setContentView(R.layout.activity_index);
        Intent intent = getIntent();
        isLogginScreen = intent.getBooleanExtra("isLogginScreen",true);
        AnhXa();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            try {
                user = SharedPrefManager.getInstance(this).getUser();
                Log.d("User ne", user.toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            AnhXa();
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            bottomNavigationView
                    .setOnItemSelectedListener(this);
            bottomNavigationView.setSelectedItemId(R.id.home);
        }else{
            Intent intents = new Intent(this, LoginActivity.class);
            startActivity(intents);
        }


    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolBarHome);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .commit();
                return true;

            case R.id.search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, searchFragment)
                        .commit();
                return true;

            case R.id.cart:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, cartFragment)
                        .commit();

            case R.id.notification:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, notificationFragment)
                        .commit();

            case R.id.person:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, profileFragment)
                        .commit();
                return true;
        }
        return false;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {
        if (isLogginScreen == true) {
            // Nếu chưa đăng nhập, không cho phép quay lại
            finishAffinity();
        }
        // Nếu đã đăng nhập, cho phép quay lại bình thường
            super.onBackPressed();
    }


}