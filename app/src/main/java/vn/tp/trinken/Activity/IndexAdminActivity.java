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

import vn.tp.trinken.Fragment.HomeAdminFragment;
import vn.tp.trinken.Fragment.ProductAdminFragment;
import vn.tp.trinken.R;


public class IndexAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout fl;
    public NavigationView navigationView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index_admin2);

        AnhXa();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

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
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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