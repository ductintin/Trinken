package vn.tp.trinken.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Adapter.*;
import vn.tp.trinken.Contants.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtOrderAll, txtOrderPending, txtOrderShipping, txtOrderCancel, txtOrderDelivered, txtOrderProcessing;
    APIService apiService;
    List<Orders> ordersList;
    List<Orders_Items> orders_itemsList;
    OrderAdapter orderAdapter;

    RecyclerView rcOrder;
    View view;
    User user = new User();


    public OrderAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderAdminFragment newInstance(String param1, String param2) {
        OrderAdminFragment fragment = new OrderAdminFragment();
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
        view = inflater.inflate(R.layout.fragment_order_admin, container, false);

        try {
            user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
            if(user.getRoles().getRole_id()==1){
                AnhXa();
                getOrderByStatus(0);
                txtOrderAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(0);
                        txtOrderShipping.setTextColor(Color.BLACK);
                        txtOrderPending.setTextColor(Color.BLACK);
                        txtOrderAll.setTextColor(Color.RED);
                        txtOrderDelivered.setTextColor(Color.BLACK);
                        txtOrderCancel.setTextColor(Color.BLACK);
                    }
                });
                txtOrderPending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(1);
                        txtOrderShipping.setTextColor(Color.BLACK);
                        txtOrderPending.setTextColor(Color.RED);
                        txtOrderAll.setTextColor(Color.BLACK);
                        txtOrderDelivered.setTextColor(Color.BLACK);
                        txtOrderCancel.setTextColor(Color.BLACK);
                        txtOrderProcessing.setTextColor(Color.BLACK);
                    }
                });

                txtOrderProcessing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(2);
                        txtOrderShipping.setTextColor(Color.BLACK);
                        txtOrderProcessing.setTextColor(Color.RED);
                        txtOrderPending.setTextColor(Color.BLACK);
                        txtOrderAll.setTextColor(Color.BLACK);
                        txtOrderDelivered.setTextColor(Color.BLACK);
                        txtOrderCancel.setTextColor(Color.BLACK);
                    }
                });
                txtOrderShipping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(3);
                        txtOrderShipping.setTextColor(Color.RED);
                        txtOrderPending.setTextColor(Color.BLACK);
                        txtOrderAll.setTextColor(Color.BLACK);
                        txtOrderDelivered.setTextColor(Color.BLACK);
                        txtOrderCancel.setTextColor(Color.BLACK);
                        txtOrderProcessing.setTextColor(Color.BLACK);
                    }
                });
                txtOrderDelivered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(4);
//                    Log.d("skghskg: ", orders.toString());
                        txtOrderShipping.setTextColor(Color.BLACK);
                        txtOrderPending.setTextColor(Color.BLACK);
                        txtOrderAll.setTextColor(Color.BLACK);
                        txtOrderDelivered.setTextColor(Color.RED);
                        txtOrderCancel.setTextColor(Color.BLACK);
                        txtOrderProcessing.setTextColor(Color.BLACK);
                    }
                });
                txtOrderCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrderByStatus(5);
                        txtOrderShipping.setTextColor(Color.BLACK);
                        txtOrderPending.setTextColor(Color.BLACK);
                        txtOrderAll.setTextColor(Color.BLACK);
                        txtOrderDelivered.setTextColor(Color.BLACK);
                        txtOrderCancel.setTextColor(Color.RED);
                        txtOrderProcessing.setTextColor(Color.BLACK);
                    }
                });
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    private void AnhXa(){

        txtOrderAll=view.findViewById(R.id.txtO0);
        txtOrderPending=view.findViewById(R.id.txtO1);
        txtOrderShipping=view.findViewById(R.id.txtO3);
        txtOrderDelivered=view.findViewById(R.id.txtO4);
        txtOrderCancel=view.findViewById(R.id.txtO5);
        txtOrderProcessing = view.findViewById(R.id.txtO2);
        rcOrder=view.findViewById(R.id.rc_order_admin);

    }

    private void getAllOrders(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrder().enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    ordersList = response.body();
                    orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), ordersList);
                    rcOrder.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.VERTICAL, false);
                    rcOrder.setLayoutManager(layoutManager);
                    orderAdapter.notifyDataSetChanged();
                    rcOrder.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
    }

    private List<Orders> getOrderByStatus(int status){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllOrderByStatus(status).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    ordersList = response.body();
                    orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), ordersList);
                    rcOrder.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.VERTICAL, false);
                    rcOrder.setLayoutManager(layoutManager);
                    orderAdapter.notifyDataSetChanged();
                    rcOrder.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
        if(ordersList!=null){
            return ordersList;
        }else
            return ordersList = new ArrayList<>();
    }
}