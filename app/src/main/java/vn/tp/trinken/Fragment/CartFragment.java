package vn.tp.trinken.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.R;
import vn.tp.trinken.Contants.*;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Adapter.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    TextView tvCartTotal;
    Button btnCartBuy;

    List<CartItem> cartItems = new ArrayList<>();

    APIService apiService;

    RecyclerView rcCartItem;
    CartAdapter cartAdapter;

    User user;
    int cartId;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        Intent intent = new Intent();
        if(SharedPrefManager.getInstance(getContext().getApplicationContext()).isLoggedIn()) {
            try {
                user = SharedPrefManager.getInstance(getContext().getApplicationContext()).getUser();
                cartId = user.getCart().getId();

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            AnhXa();
            Log.d("cart id ow day ne", String.valueOf(user.getCart().getId()));
            getCartItem(cartId);

            setCartTotal();

            btnCartBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

        return view;
    }

    private void getCartItem(int cartId) {
        Log.d("cart id ne", String.valueOf(cartId));
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCartItem(cartId).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if(response.isSuccessful()){
                    cartItems = response.body();
                    Log.d("cartitem ne", cartItems.toString());
                    cartAdapter = new CartAdapter(getActivity().getApplicationContext(), cartItems, cartId);
                    rcCartItem.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.VERTICAL, false);

                    rcCartItem.setLayoutManager(layoutManager);
                    cartAdapter.notifyDataSetChanged();
                    rcCartItem.setAdapter(cartAdapter);

                }else{

                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Log.d("Loi o cartfragment ", t.getMessage());
            }
        });
    }

    private void AnhXa(){
        tvCartTotal = view.findViewById(R.id.tvCartTotal);
        btnCartBuy = view.findViewById(R.id.btnCartBuy);
        rcCartItem = view.findViewById(R.id.rcCartItem);
    }

    private void setCartTotal(){
        double total = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        for (CartItem cartItem:cartItems) {
            total += cartItem.getPrice();
        }

        tvCartTotal.setText(df.format(total));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSetTotal(TotalCart event){
        if (event!=null){
            setCartTotal();
            getCartItem(cartId);
        }

    }
}