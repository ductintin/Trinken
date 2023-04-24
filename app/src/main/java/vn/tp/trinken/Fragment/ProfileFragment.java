package vn.tp.trinken.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.JsonElement;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Model.User;
import vn.tp.trinken.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    Button btnLogout;
    View view;
    APIService apiService;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Anhxa();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = null;
                try {
                    user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
                    Log.d("a",user.toString());
                    doLogout(user.getUser_id());
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return view;
    }
    private void Anhxa(){
        btnLogout = view.findViewById(R.id.btn_Logout);
    }

    public void doLogout(Integer id){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.logout(id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
    }
}