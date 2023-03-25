package vn.tp.trinken.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import vn.tp.trinken.Model.*;
public interface APIService {

    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
}
