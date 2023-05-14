package vn.tp.trinken.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.Contants.RealPathUtil;
import vn.tp.trinken.R;
import vn.tp.trinken.API.*;
import vn.tp.trinken.Adapter.*;
import vn.tp.trinken.Model.*;
import vn.tp.trinken.Dto.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductAdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    APIService apiService;
    List<Products> productsList;
    RecyclerView rcProduct;

    ProductTableAdapter productTableAdapter;

    TextView tvAvail, tvStore, tvDelete;
    Boolean fileExist = false;
    List<Categories> categories = new ArrayList<>();
    List<Brands> brands = new ArrayList<>();


    Button btnAddProduct;

    Bitmap bitmap;

    public ProductAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductAdminFragment newInstance(String param1, String param2) {
        ProductAdminFragment fragment = new ProductAdminFragment();
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
        view = inflater.inflate(R.layout.fragment_product_admin, container, false);

        AnhXa();
        getListProduct(true);
        getListCategory();
        getListBrand();

        tvStore.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                getListProduct(false);
                tvStore.setBackground(getResources().getDrawable(R.drawable.bg_unactive));
                tvAvail.setBackground(getResources().getDrawable(R.drawable.bg_active));
                tvDelete.setText("Khôi phục");


            }
        });

        tvAvail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                getListProduct(true);
                tvAvail.setBackground(getResources().getDrawable(R.drawable.bg_unactive));
                tvStore.setBackground(getResources().getDrawable(R.drawable.bg_active));
                tvDelete.setText("Lưu trữ");

            }
        });


        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });



        return view;
    }

    private void AnhXa(){
        rcProduct = view.findViewById(R.id.rcAdminProduct);
        tvAvail = view.findViewById(R.id.tvAvailableProduct);
        tvStore = view.findViewById(R.id.tvStoreProduct);
        btnAddProduct = view.findViewById(R.id.btnAddNewProductAdmin);
        tvDelete = view.findViewById(R.id.tvDelete);

    }


    private Uri mUri;
    private ProgressDialog mProgressDialog;
    public static final int MY_REQUEST_CODE=100;
    public static final String TAG = ProfileFragment.class.getName();


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
                            bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                            Log.d("Load anh:  ","cho nay ne");
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


    private void getListProduct(boolean active){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveProduct(active).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    productsList = response.body();
                    productTableAdapter = new ProductTableAdapter(getActivity().getApplicationContext(), productsList);
                    rcProduct.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                            ,LinearLayoutManager.VERTICAL, false);
                    rcProduct.setLayoutManager(layoutManager);
                    productTableAdapter.notifyDataSetChanged();
                    rcProduct.setAdapter(productTableAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });

    }

    private void showAddProductDialog(){
        Dialog dialogView = new Dialog(getContext());
        //We have added a title in the custom layout. So let's disable the default title.
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialogView.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialogView.setContentView(R.layout.add_product_dialog);

        Spinner spinnerCate, spinnerBrand;
        TextInputEditText tvProductName, tvProductDesc, tvProductPrice, tvProductQuantity;
        Button btnImgFileProduct, btnConfirmAddProduct;
        ImageView imgDialogProduct;


        spinnerCate = dialogView.findViewById(R.id.spinner_categories);
        spinnerBrand = dialogView.findViewById(R.id.spinner_brand);
        tvProductName = dialogView.findViewById(R.id.edtProductName);
        tvProductDesc = dialogView.findViewById(R.id.edtProductDesc);
        tvProductPrice = dialogView.findViewById(R.id.edtProductPrice);
        tvProductQuantity = dialogView.findViewById(R.id.edtProductQuantity);
        btnImgFileProduct = dialogView.findViewById(R.id.btnImgFileProduct);
        btnConfirmAddProduct = dialogView.findViewById(R.id.btnConfirmAddProduct);
        imgDialogProduct = dialogView.findViewById(R.id.imgDialogProduct);



        List<String> cateName = new ArrayList<>();
        List<String> brandName = new ArrayList<>();

        for(Categories category : categories){
            cateName.add(category.getCategory_name());
        }

        for (Brands brand: brands){
            brandName.add(brand.getBrand_name());
        }

        spinnerCate.setAdapter(new ArrayAdapter<String>(view.getRootView().getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cateName));
        spinnerBrand.setAdapter(new ArrayAdapter<String>(view.getRootView().getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brandName));


        btnImgFileProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission();
            }
        });
        btnConfirmAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = tvProductName.getText().toString().trim();
                String pDesc = tvProductDesc.getText().toString().trim();
                String pPrice = tvProductPrice.getText().toString().trim();
                String pQuantity = tvProductQuantity.getText().toString().trim();
                int cate = (int) spinnerCate.getSelectedItemId();
                int brandId = brands.get((int) spinnerBrand.getSelectedItemId()).getBrand_id();

                if(TextUtils.isEmpty(pName)){
                    tvProductName.setError("Please enter product name");
                    tvProductName.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(pDesc)){
                    tvProductDesc.setError("Please enter description");
                    tvProductDesc.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(pPrice) || !isNumeric(pPrice)){
                    tvProductPrice.setError("Please enter valid price");
                    tvProductPrice.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(pQuantity)|| !TextUtils.isDigitsOnly(pQuantity)){
                    tvProductQuantity.setError("Please enter valid quantity");
                    tvProductQuantity.requestFocus();
                    return;
                }



                List<Integer> categoriesId = new ArrayList<>();
                categoriesId.add(categories.get(cate).getCategory_id());


                File file = null;
                if(mUri!=null && fileExist== true)
                {
                    String IMAGE_PATH= RealPathUtil.getRealPath(getContext(),mUri);
//                    Log.d("file",IMAGE_PATH);
                    file = new File(IMAGE_PATH);
                    imgDialogProduct.setImageBitmap(bitmap);
                }

                ProductDto productDto = new ProductDto(
                        pName, pDesc, Double.parseDouble(pPrice), file, Integer.parseInt(pQuantity), brandId, categoriesId, null);

                addProduct(productDto);

                fileExist= false;

                dialogView.dismiss();

            }
        });

        // Set the size of the dialog to be 90% of the screen width and 80% of the screen height
        Window window = dialogView.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels*0.9);
            layoutParams.height = (int) (getResources().getDisplayMetrics().heightPixels*0.9);
            window.setAttributes(layoutParams);}
        dialogView.show();

    }

    private void getListCategory(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveCategory().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if(response.isSuccessful()){
                    categories = response.body();
                    Log.d("cate", categories.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });
    }

    private void getListBrand(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveBrand(true).enqueue(new Callback<List<Brands>>() {
            @Override
            public void onResponse(Call<List<Brands>> call, Response<List<Brands>> response) {
                if(response.isSuccessful()){
                    brands = response.body();
                    Log.d("brand", brands.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Brands>> call, Throwable t) {

            }
        });

    }

    private void addProduct(ProductDto productDto){

        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.addNewProduct(productDto.product()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity().getApplicationContext(), "Create successfully!", Toast.LENGTH_SHORT).show();
                    try {
                        setListenerList(true);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });



    }

    private void setListenerList(boolean action) throws ParseException {
        getListProduct(true);
        ProductTableAdapter productTableAdapter = new ProductTableAdapter(getActivity().getApplicationContext(), productsList) ;
        productTableAdapter.notifyDataSetChanged();
    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}