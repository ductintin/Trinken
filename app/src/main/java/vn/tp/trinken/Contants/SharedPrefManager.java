package vn.tp.trinken.Contants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.ParseException;

import vn.tp.trinken.Activity.LoginActivity;
import vn.tp.trinken.Model.User;

public class SharedPrefManager {
    private static final  String SHARED_PREF_NAME = "trinken";
    private static final String KEY_USER = "keyuser";

    Gson gson = new Gson();

    private static SharedPrefManager mInstance;
    private static Context ctx;
    private SharedPrefManager(Context context){
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance ==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void userLogin(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, gson.toJson(user));
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER,null) !=null;
    }
    public User getUser() throws ParseException {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_USER,null);
        User user = gson.fromJson(json,User.class);
        return user;
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx,LoginActivity.class));
    }

}
