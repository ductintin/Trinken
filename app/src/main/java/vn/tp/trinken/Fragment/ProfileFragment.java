package vn.tp.trinken.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Activity.IndexActivity;
import vn.tp.trinken.Activity.SignupProfileActivity;
import vn.tp.trinken.Adapter.CategoryAdapter;
import vn.tp.trinken.Adapter.OrderAdapter;
import vn.tp.trinken.Contants.RealPathUtil;
import vn.tp.trinken.Contants.SharedPrefManager;
import vn.tp.trinken.Dto.ProfileDto;
import vn.tp.trinken.MainActivity;
import vn.tp.trinken.Model.Categories;
import vn.tp.trinken.Model.Orders;
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

    private static final String KEY_USER = "user";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    Button btnLogout, btnEditProfile, btnEditPassword;
    ImageView imgAvatar;
    EditText edtUsername , edtEmail, edtFirstName, edtLastName, edtPhone, edtAddress;
    ImageView imgProfile;
    TextView txtUsername , txtEmail, txtFullname, txtPhone, txtAddress, txtGender, tvAmountOrder;

    EditText edtPassword, edtNewPassword , edtReNewPassword ;
    LinearLayout constraintLayout;

    RecyclerView rcOrder;

    TextView txt0, txt1,txt2, txt3,txt4;

    OrderAdapter orderAdapter;
    List<Orders> orders;

    View view;
    APIService apiService;
    Boolean fileExist = false;

    User user = new User();

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

//    public static ProfileFragment newInstance(){
//
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(KEY_USER, (Parcelable) user);
//    }
//    @Override
//    public void onViewStateRestored(Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            user = savedInstanceState.getParcelable(KEY_USER);
//            // Sử dụng đối tượng người dùng đã khôi phục được ở đây
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Anhxa();
        try {
            loadProfile();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            getOrders(0);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        txt2.setTextColor(Color.BLACK);
        txt1.setTextColor(Color.BLACK);
        txt0.setTextColor(Color.RED);
        txt3.setTextColor(Color.BLACK);
        txt4.setTextColor(Color.BLACK);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
//                    Log.d("a",user.toString());
                    doLogout(user.getUser_id());
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showEditProfile();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPassword();
            }
        });

        txt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOrders(0);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txt2.setTextColor(Color.BLACK);
                txt1.setTextColor(Color.BLACK);
                txt0.setTextColor(Color.RED);
                txt3.setTextColor(Color.BLACK);
                txt4.setTextColor(Color.BLACK);
            }
        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOrders(1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txt2.setTextColor(Color.BLACK);
                txt1.setTextColor(Color.RED);
                txt0.setTextColor(Color.BLACK);
                txt3.setTextColor(Color.BLACK);
                txt4.setTextColor(Color.BLACK);
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOrders(3);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txt2.setTextColor(Color.RED);
                txt1.setTextColor(Color.BLACK);
                txt0.setTextColor(Color.BLACK);
                txt3.setTextColor(Color.BLACK);
                txt4.setTextColor(Color.BLACK);
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOrders(4);
//                    Log.d("skghskg: ", orders.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txt2.setTextColor(Color.BLACK);
                txt1.setTextColor(Color.BLACK);
                txt0.setTextColor(Color.BLACK);
                txt3.setTextColor(Color.RED);
                txt4.setTextColor(Color.BLACK);
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOrders(5);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txt2.setTextColor(Color.BLACK);
                txt1.setTextColor(Color.BLACK);
                txt0.setTextColor(Color.BLACK);
                txt3.setTextColor(Color.BLACK);
                txt4.setTextColor(Color.RED);
            }
        });
        return view;
    }
    private void Anhxa(){
        btnLogout = view.findViewById(R.id.btn_Logout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditPassword = view.findViewById(R.id.btnChangePassword);
        imgProfile = view.findViewById(R.id.imgAvatar);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtFullname = view.findViewById(R.id.txtFullName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtGender = view.findViewById(R.id.txtGender);
        txtPhone = view.findViewById(R.id.txtPhoneNumber);
        txtAddress = view.findViewById(R.id.txtAddress);
        //
        txt0=view.findViewById(R.id.txt0);
        txt1=view.findViewById(R.id.txt1);
        txt2=view.findViewById(R.id.txt2);
        txt3=view.findViewById(R.id.txt3);
        txt4=view.findViewById(R.id.txt4);
        rcOrder=view.findViewById(R.id.rcOrder);

        tvAmountOrder = view.findViewById(R.id.txtOrderCount);
        constraintLayout = view.findViewById(R.id.constraintLayout);
    }
    private void loadProfile() throws ParseException {
        User user = SharedPrefManager.getInstance(getContext().getApplicationContext()).getUser();
        if(user.getImage()!= null){
            Glide.with(getContext()).load(user.getImage()).placeholder(R.drawable.avatar).into(imgProfile);
        }
        txtUsername.setText(user.getUser_name());
        txtFullname.setText(user.getFirst_name()+" "+user.getLast_name());
        txtEmail.setText("Email: "+ user.getEmail());
        txtGender.setText("Gender: "+user.getGender());
        txtPhone.setText("Phone Number: "+user.getPhone_number());
        txtAddress.setText("Address: "+user.getAddress());
    }

    public void doLogout(Integer id) throws IOException {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.logout(id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {



            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
//        apiService.logout(id).execute();
    }
    private void showEditProfile() throws ParseException {
        Dialog dialog = new Dialog(getContext());
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.edit_profile_dialog);

        //
        imgAvatar = dialog.findViewById(R.id.imgAvatar);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnFile = dialog.findViewById(R.id.btnImgFile);
        edtUsername = dialog.findViewById(R.id.edtUsername);
        edtEmail = dialog.findViewById(R.id.edtEmail);
        edtFirstName = dialog.findViewById(R.id.edtFirstname);
        edtLastName = dialog.findViewById(R.id.edtLastname);
        edtPhone = dialog.findViewById(R.id.edtPhone);
        edtAddress = dialog.findViewById(R.id.edtAddress);
        //
        mProgressDialog=new ProgressDialog(getContext());
        mProgressDialog.setMessage("Please wait upload......");

        user = SharedPrefManager.getInstance(getContext()).getUser();
//        Log.d("Test User",user.getFirst_name());
        if(user.getImage()!=null){
            Glide.with(getContext()).load(user.getImage()).placeholder(R.drawable.avatar).into(imgAvatar);
        }
        edtUsername.setText(user.getUser_name());
        edtEmail.setText(user.getEmail());
        edtFirstName.setText(user.getFirst_name());
        edtLastName.setText(user.getLast_name());
        edtPhone.setText(user.getPhone_number());
        edtAddress.setText(user.getAddress());

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = null;
                if(mUri!=null && fileExist== true)
                {
                    String IMAGE_PATH= RealPathUtil.getRealPath(getContext(),mUri);
//                    Log.d("file",IMAGE_PATH);
                    file = new File(IMAGE_PATH);
                }
                ProfileDto profileDto= new ProfileDto(null,edtFirstName.getText().toString().trim(),edtLastName.getText().toString().trim(),user.getGender(),edtEmail.getText().toString().trim(),edtPhone.getText().toString().trim(),edtAddress.getText().toString().trim(),file);
                doSignupProfile(user.getUser_id(),profileDto);
                fileExist= false;
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels*0.9);
            layoutParams.height = (int) (getResources().getDisplayMetrics().heightPixels*0.9);
            window.setAttributes(layoutParams);}

        dialog.show();

    }

    private void showEditPassword(){
        Dialog dialog =new Dialog(getContext());
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.edit_password_dialog);

        edtPassword =dialog.findViewById(R.id.edtPassword);
        edtNewPassword = dialog.findViewById(R.id.edtNewPassword);
        edtReNewPassword = dialog.findViewById(R.id.edtRePassword);
        Button btnConfirm= dialog.findViewById(R.id.btnConfirm2);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    user = SharedPrefManager.getInstance(getContext()).getUser();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                ChangePassword(user.getUser_id(),edtPassword.getText().toString().trim(), edtNewPassword.getText().toString().trim(),edtReNewPassword.getText().toString().trim());
            }
        });

        dialog.show();
    }

    private void ChangePassword(int id, String password,String newPassword, String reNewPassword){
        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            edtNewPassword.setError("Please enter password");
            edtNewPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(reNewPassword)){
            edtReNewPassword.setError("Please confirm new password");
            edtReNewPassword.requestFocus();
            return;
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.changePassword(id,password, newPassword, reNewPassword).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){

                    try {
                        JSONObject obj = new JSONObject(response.body().toString());
                        if(!obj.getBoolean("error")){
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    //load anh
    private Uri mUri;
    private ProgressDialog mProgressDialog;
    public static final int MY_REQUEST_CODE=100;
    public static final String TAG =ProfileFragment.class.getName();


    public static String[] storage_permissions={
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33={
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p=storage_permissions;
        }
        return p;
    }
    private void CheckPermission(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            openGallery();
        }
        else {
            requestPermissions(permissions(),MY_REQUEST_CODE);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==MY_REQUEST_CODE)
        {
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }
        }
    }
    private void openGallery()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG,"onActivityResult");
                    if(result.getResultCode()== Activity.RESULT_OK)
                    {
                        Intent data=result.getData();
                        if(data==null)
                        {
                            return;
                        }
                        Uri uri=data.getData();
                        mUri=uri;
                        try{
                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                            Log.d("Load anh:  ","cho nay ne");
                            imgAvatar.setImageBitmap(bitmap);
                            fileExist = true;

                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    //load anh

    //
    private void doSignupProfile(Integer id, ProfileDto profileDto){
        /*if(TextUtils.isEmpty(profileDto.getUserName()) || edtUsername.getText().toString().length()==0){
            edtUsername.setError("Please enter firstname");
            edtUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(profileDto.getFirstName())){
            edtFirstName.setError("Please enter firstname");
            edtFirstName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(profileDto.getLastName())){
            edtLastName.setError("Please enter lastname");
            edtLastName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty((profileDto.getPhoneNumber()))){
            edtPhone.setError("Please enter phone");
            edtPhone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(profileDto.getAddress())){
            edtAddress.setError("Please enter address");
            edtAddress.requestFocus();
            return;
        }*/


        if(mUri != null){
            mProgressDialog.show();
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateProfile(id, profileDto.profile()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(mUri != null){
                    mProgressDialog.dismiss();
                }
                if(response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(response.body().toString());
                        String json = obj.getJSONObject("user").toString();
                        if (!obj.getBoolean("error")) {
                            Gson gson = new Gson();
                            User user1 = gson.fromJson(json, User.class);
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getContext().getApplicationContext()).remove();
                            SharedPrefManager.getInstance(getContext().getApplicationContext()).userLogin(user1);
                            user =SharedPrefManager.getInstance(getContext().getApplicationContext()).getUser();
                            setListener();
                        } else {
                            int statusCode = response.code();
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject((response.errorBody().string()));
                        String message= jsonObject.getString("message");
                        Log.d("skdfsak",message);
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void getOrders(int statusId) throws ParseException {
        User user1 =SharedPrefManager.getInstance(getContext()).getUser();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getOrder(user1.getUser_id(),statusId).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    orders = response.body();
                    if(orders !=null){

                        constraintLayout.setVisibility(View.VISIBLE);
                        tvAmountOrder.setText(String.valueOf(orders.size()));
                    }else{
                        constraintLayout.setVisibility(View.GONE);
                    }

                    orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), orders);
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
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void setListener() throws ParseException {
        loadProfile();
    }

}