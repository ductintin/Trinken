package vn.tp.trinken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Activity.HomeActivity;
import vn.tp.trinken.Adapter.CategoryAdapter;
import vn.tp.trinken.Model.Category;
import vn.tp.trinken.R;

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

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    SearchView searchView;

    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categories;

    View view;

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
        return view;
    }

    private void ActionViewFlipper(){
        List<String> banner = new ArrayList<>();
        //banner.add()
    }

    private void AnhXa(){
        drawerLayout = view.findViewById(R.id.drawerLayout);
        toolbar = view.findViewById(R.id.toolBarHome);
        viewFlipper = view.findViewById(R.id.viewFlipperHome);
        recyclerView = view.findViewById(R.id.rcNewProduct);
        navigationView = view.findViewById(R.id.navigation);
        listView = view.findViewById(R.id.listView);

    }

    private void getCategories(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){

                    categories = response.body();

//                    Category category = new Category(1,"Juice","Juice", "@drawable/juice.png");
//                    categories.add(category);
                    Log.d("API",categories.toString());
                    categoryAdapter = new CategoryAdapter(getActivity().getApplicationContext(), categories);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);

                    categoryAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(categoryAdapter);

                }else{
                    int status_code = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}