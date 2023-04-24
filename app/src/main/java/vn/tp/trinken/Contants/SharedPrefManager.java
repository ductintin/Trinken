package vn.tp.trinken.Contants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.Activity.*;
public class SharedPrefManager {
    private static final  String SHARED_PREF_NAME = "trinken";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keygmail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";
    private static final String KEY_CART = "keycart";
    private static final String KEY_FIRSTNAME = "keyfirstname";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_ROLE = "keyrole";
    private static final String KEY_PHONE= "keyphone";
    private static final String KEY_LASTLOGIN = "keylastlogin";
    private static final String KEY_CREATEDAT = "keycreatedat";
    private static final String KEY_UPDATEDAT = "keyupdatedat";
    private static final String KEY_ADDRESS = "keyaddress";

    Gson gson = new Gson();

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en", "VN"));
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

        editor.putInt(KEY_ID, user.getUser_id());
        editor.putString(KEY_USERNAME, user.getUser_name());
        editor.putString(KEY_EMAIL, user.getEmail()==null?"":user.getEmail());
        editor.putString(KEY_GENDER, user.getGender()==null?"":user.getGender());
        editor.putString(KEY_IMAGES, user.getImage()==null?"": user.getImage());
        editor.putString(KEY_ROLE, user.getRoles()==null?"":gson.toJson(user.getRoles()));
        editor.putString(KEY_FIRSTNAME, user.getFirst_name()==null?"": user.getFirst_name());
        editor.putString(KEY_LASTNAME, user.getLast_name()==null?"":user.getLast_name());
        editor.putString(KEY_PHONE, user.getPhone_number()==null?"":user.getPhone_number());
        editor.putString(KEY_ADDRESS, user.getAddress()==null?"":user.getAddress());
        editor.putString(KEY_CREATEDAT, user.getCreatedAt()==null?"":String.valueOf(user.getCreatedAt()));
        editor.putString(KEY_UPDATEDAT, user.getCreatedAt()==null?"":String.valueOf(user.getUpdatedAt()));
        editor.putString(KEY_LASTLOGIN, user.getLast_login()==null?"":String.valueOf(user.getLast_login()));
        editor.putString(KEY_CART, gson.toJson(user.getCart()));

        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) !=null;
    }
    public User getUser() throws ParseException {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_USERNAME,null),
                "",
                sharedPreferences.getString(KEY_FIRSTNAME,null),
                sharedPreferences.getString(KEY_LASTNAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                gson.fromJson(sharedPreferences.getString(KEY_ROLE,null), Roles.class),
                sharedPreferences.getString(KEY_IMAGES,null),
                true,
                dateFormat.parse(sharedPreferences.getString(KEY_CREATEDAT,"")),
                dateFormat.parse(sharedPreferences.getString(KEY_UPDATEDAT,"")),
                dateFormat.parse(sharedPreferences.getString(KEY_LASTLOGIN,"")),
                sharedPreferences.getString(KEY_GENDER,null),
                gson.fromJson(sharedPreferences.getString(KEY_CART,null), Cart.class)
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
