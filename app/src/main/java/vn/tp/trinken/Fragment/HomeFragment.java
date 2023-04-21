package vn.tp.trinken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.tp.trinken.Model.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Adapter.CategoryAdapter;
import vn.tp.trinken.R;

import vn.tp.trinken.Adapter.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Toolbar toolbar;
    RecyclerView rcCategory, rcHotProduct;
    BottomNavigationView bottomNavigationView;
    SearchView searchView;

    CategoryAdapter categoryAdapter;

    ProductAdapter productAdapter;

    APIService apiService;
    List<Category> categories = new ArrayList<>();
    List<Products> products = new ArrayList<>();

    View view;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<Images> imagesList;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == imagesList.size()-1){
                viewPager.setCurrentItem(0, true);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa();
        getCategories();
        loadActiveProduct();

        imagesList = getListImages();
        ImagesViewPageAdapter adapter = new ImagesViewPageAdapter(imagesList);
        viewPager.setAdapter(adapter);

        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        handler.postDelayed(runnable, 3000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void ActionViewFlipper(){
        List<String> banner = new ArrayList<>();
        //banner.add()
    }

    private void AnhXa(){
        toolbar = view.findViewById(R.id.toolBarHome);
        viewPager = view.findViewById(R.id.viewPagerHome);
        rcCategory = view.findViewById(R.id.rcCategory);
        rcHotProduct = view.findViewById(R.id.rcHotProduct);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        circleIndicator = view.findViewById(R.id.circle_indicator);
    }

    private List<Images> getListImages() {

        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.ban1));
        list.add(new Images(R.drawable.ban2));
        list.add(new Images(R.drawable.ban1));
        list.add(new Images(R.drawable.ban2));
        list.add(new Images(R.drawable.ban1));

        return list;

    }
    private void getCategories(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categories = response.body();
                    Log.d("API",categories.toString());
                    categoryAdapter = new CategoryAdapter(getActivity().getApplicationContext(), categories);
                    rcCategory.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.HORIZONTAL, false);
                    rcCategory.setLayoutManager(layoutManager);

                    categoryAdapter.notifyDataSetChanged();
                    rcCategory.setAdapter(categoryAdapter);

                }else{
                    int status_code = response.code();
                    switch (status_code){
                        case 204:
                            Toast.makeText(getActivity().getApplicationContext(), "There is no category", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    public void loadActiveProduct(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveProduct().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                   try {
                       products = response.body();
                       productAdapter = new ProductAdapter(getActivity().getApplicationContext(), products, R.layout.item_product_col);
                       rcHotProduct.setHasFixedSize(true);
                       GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                       rcHotProduct.setLayoutManager(gridLayoutManager);
                       productAdapter.notifyDataSetChanged();
                       rcHotProduct.setAdapter(productAdapter);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }else{
                    int status_code = response.code();
                    switch (status_code){
                        case 204:
                            Toast.makeText(getActivity().getApplicationContext(), "There is no product", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}