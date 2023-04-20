package vn.tp.trinken.Contants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.Activity.*;
public class SharedPrefManager {
    private static final  String SHARED_PREF_NAME = "trinken";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keygmail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";
    private static final String KEY_CART = "keycartitems";
    private static final String KEY_FIRSTNAME = "keyfirstname";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_ROLEID = "keyphone";
    private static final String KEY_PHONE= "keyroleid";
    private static final String KEY_LASTLOGIN = "keylastlogin";
    private static final String KEY_CREATEDAT = "keycreatedat";
    private static final String KEY_UPDATEDAT = "keyupdatedat";
    private static final String KEY_ADDRESS = "keyaddress";

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
    public void userLogin(Users user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, user.getUser_id());
        editor.putString(KEY_USERNAME, user.getUser_name());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_IMAGES, user.getImage());
        editor.putInt(KEY_ROLEID, user.getRole_id());
        editor.putString(KEY_FIRSTNAME, user.getFirst_name());
        editor.putString(KEY_LASTNAME, user.getLast_name());
        editor.putString(KEY_PHONE, user.getPhone_number());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_CREATEDAT, String.valueOf(user.getCreatedAt()));
        editor.putString(KEY_UPDATEDAT, String.valueOf(user.getUpdatedAt()));
        editor.putString(KEY_LASTLOGIN, String.valueOf(user.getLast_login()));
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) !=null;
    }
    public Users getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        Type type = new TypeToken< ArrayList < Date >>() {}.getType();

        return new Users(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_USERNAME,null),
                null,
                sharedPreferences.getString(KEY_FIRSTNAME,null),
                sharedPreferences.getString(KEY_LASTNAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getInt(KEY_ROLEID,-1),
                sharedPreferences.getString(KEY_IMAGES,null),
                true,
                gson.fromJson(sharedPreferences.getString(KEY_CREATEDAT,null), type),
                gson.fromJson(sharedPreferences.getString(KEY_UPDATEDAT,null), type),
                gson.fromJson(sharedPreferences.getString(KEY_LASTNAME,null), type),
                sharedPreferences.getString(KEY_GENDER,null)
        );
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx,LoginActivity.class));
    }

}
