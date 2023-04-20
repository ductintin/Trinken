package vn.tp.trinken.API;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.tp.trinken.Model.*;
public interface APIService {

    @GET("category/get-all")
    Call<List<Category>> getCategoryAll();

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonElement> login(@Field("username") String username, @Field("password") String password);
}
