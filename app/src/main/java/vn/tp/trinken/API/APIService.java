package vn.tp.trinken.API;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("category/get-all-active")
    Call<List<Categories>> getAllActiveCategory();

    @GET("product/get-all-active/active={active}")
    Call<List<Products>> getAllActiveProduct(@Path("active") boolean active);

    @GET("product/get-product-by-id/{id}")
    Call<Products> getProductById(@Path("id") int id);

    @GET("product/get-product-by-category/active={active}/cateid={id}")
    Call<List<Products>> getProductByCategory(@Path("active") boolean active, @Path("id") int cate_id);

    @GET("product/get-product-by-category/active={active}/cateid={id}/sort={sort}")
    Call<List<Products>> filterProductByPrice(@Path("active") boolean active, @Path("id") int cate_id, @Path("sort") int sort);

    @PUT("product/update/productId={id}")
    Call<JsonElement> updateProduct(@Path("id") int id, @Body ProductDto productDto);

    @PUT("product/update-active/productId={id}/active={active}")
    Call<JsonElement> updateActiveProduct(@Path("id") int id, @Path("active") boolean active);

    @Multipart
    @POST("product/create")
    Call<JsonElement> addNewProduct(@PartMap Map<String,RequestBody> product);

    @POST("cartitem/add")
    Call<JsonElement> addCartItem(@Body CartItemDto cartItemDto);

    @GET("cartitem/get-all/{cartId}")
    Call<List<CartItem>> getCartItem(@Path("cartId") int cartId);


    @DELETE("cartitem/delete/{cartItemId}")
    Call<JsonElement> deleteCartItem(@Path("cartItemId") int cartItemId);

    @GET("brand/get-all-active/active={active}")
    Call<List<Brands>> getAllActiveBrand(@Path("active") boolean active);

    @GET("order/get-all")
    Call<List<Orders>> getAllOrder();

    @GET("order/get-all-by-status/status={status}")
    Call<List<Orders>> getAllOrderByStatus(@Path("status") int status);



    @FormUrlEncoded
    @PUT("cartitem/update-quantity/{id}")
    Call<Void>updateCartItem(@Path("id") Integer id, @Field("count") Integer count );


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

    @FormUrlEncoded
    @PUT("user/{id}/change-password")
    Call<JsonElement> changePassword(@Path("id") Integer id, @Field("password") String password, @Field("newPassword") String newPassword, @Field("rePassword") String rePassword);

//    @POST("order/add/{cartid}")
//    Call<JsonElement> addOrder(@Path("cartid") Integer id, @Body OrderDto orderDto);
    @FormUrlEncoded
    @POST("order/create/{cartid}")
    Call<JsonElement> addOrder(@Path("cartid") Integer id, @Field("shippingId") Integer shipId, @Field("paymentId") Integer payId);

    @FormUrlEncoded
    @POST("shipping-address/create")
    Call<JsonElement> addAddress(@Field("userId") Integer userId, @Field("name") String name, @Field("phone") String phone, @Field("address") String address);

    @GET("payment-method/get-all")
    Call<List<Payment_Methods>> getPayment();

//    @FormUrlEncoded
    @GET("order/get/{userId}/{statusId}")
    Call<List<Orders>> getOrder(@Path("userId") Integer id, @Path("statusId") Integer statusId);



}
