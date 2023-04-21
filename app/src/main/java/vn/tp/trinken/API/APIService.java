package vn.tp.trinken.API;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.tp.trinken.Model.*;
public interface APIService {

    @GET("categories.php")
    Call<List<Category>> getCategoryAll();

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonElement> login(@Field("username") String username, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("user/signup")
//    Call<JsonElement> signup(@Field("userName") String username,@Field("password") String password, @Field("email") String email, @Field("repassword") String repassword);
    @POST("user/signup")
    Call<JsonElement> signup(@Body SignUp signUp);
}
