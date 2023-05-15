package vn.tp.trinken.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Fragment.HomeAdminFragment;
import vn.tp.trinken.Fragment.ProductAdminFragment;
import vn.tp.trinken.R;
import vn.tp.trinken.Model.User;
import vn.tp.trinken.API.*;


public class IndexAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout fl;
    public NavigationView navigationView;

    Toolbar toolbar;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index_admin2);

        AnhXa();
        setSupportActionBar(toolbar);


        HomeAdminFragment homeAdminFragment = new HomeAdminFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragmentAdmin, homeAdminFragment)
                .setReorderingAllowed(true)
                .commit();



        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                HomeAdminFragment homeAdminFragment = new HomeAdminFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragmentAdmin, homeAdminFragment)
                        .setReorderingAllowed(true)
                        .commit();
                break;

            case R.id.nav_product:
                Toast.makeText(IndexAdminActivity.this, "Products", Toast.LENGTH_SHORT).show();
                ProductAdminFragment productAdminFragment = new ProductAdminFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragmentAdmin, productAdminFragment)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.nav_logout:

                try {
                    User user = SharedPrefManager.getInstance(getApplication().getApplicationContext()).getUser();
                    //Log.d("a",user.toString());
                    doLogout(user.getUser_id());
                    SharedPrefManager.getInstance(getApplication().getApplicationContext()).logout();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doLogout(Integer id) throws IOException {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.logout(id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {



            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
//        apiService.logout(id).execute();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    private void AnhXa(){
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        navigationView = findViewById(R.id.nav_view);
        fl = findViewById(R.id.flFragmentAdmin);
        toolbar = findViewById(R.id.toolbar);


    }
}