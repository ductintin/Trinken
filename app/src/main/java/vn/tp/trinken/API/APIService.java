package vn.tp.trinken.API;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import vn.tp.trinken.Dto.*;
import vn.tp.trinken.Model.*;
public interface APIService {

    @GET("category/get-all")
    Call<List<Categories>> getCategoryAll();

    @GET("product/get-all-active")
    Call<List<Products>> getAllActiveProduct();

    @GET("product/get-product-by-id/{id}")
    Call<Products> getProductById(@Path("id") int id);

    @GET("product/get-product-by-category/active={active}/cateid={id}")
    Call<List<Products>> getProductByCategory(@Path("active") boolean active, @Path("id") int cate_id);

    @GET("product/get-product-by-category/active={active}/cateid={id}/sort={sort}")
    Call<List<Products>> filterProductByPrice(@Path("active") boolean active, @Path("id") int cate_id, @Path("sort") int sort);

    @POST("cartitem/add")
    Call<JsonElement> addCartItem(@Body CartItemDto cartItemDto);

    @GET("cartitem/get-all/{cartId}")
    Call<List<CartItem>> getCartItem(@Path("cartId") int cartId);

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonElement> login(@Field("username") String username, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("user/signup")
//    Call<JsonElement> signup(@Field("userName") String username,@Field("password") String password, @Field("email") String email, @Field("repassword") String repassword);
    @POST("user/signup")
    Call<JsonElement> signup(@Body SignUp signUp);

    @PUT("user/logout/userid={id}")
    Call<Void> logout(@Path("id") Integer userId);

    @Multipart
    @PUT("user/profile/{id}")
    Call<JsonElement> updateProfile(@Path("id") Integer userId, @PartMap Map<String,RequestBody> profile);
}
