package vn.tp.trinken.Fragment;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tp.trinken.API.APIService;
import vn.tp.trinken.API.RetrofitClient;
import vn.tp.trinken.Adapter.ProductAdapter;
import vn.tp.trinken.Model.Products;
import vn.tp.trinken.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    SearchView searchView;
    TextView textView;
    RecyclerView rcProduct;
    View view;
    ProductAdapter productAdapter;
    List <Products> products;
    APIService apiService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        AnhXa();
        loadActiveProduct();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterListener(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return false;
            }
        });
        return view;
    }

    private void AnhXa(){
        searchView = view.findViewById(R.id.searchView);
        rcProduct = view.findViewById(R.id.scProduct);
        textView = view.findViewById(R.id.txtMessage);
    }
    public void loadActiveProduct(){
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllActiveProduct(true).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    try {
                        products = response.body();
                        Log.d("pppppppppppp",products.toString());
                        productAdapter = new ProductAdapter(getActivity().getApplicationContext(), products, R.layout.item_product_col);
                        rcProduct.setHasFixedSize(true);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                        rcProduct.setLayoutManager(gridLayoutManager);
                        productAdapter.notifyDataSetChanged();
                        rcProduct.setAdapter(productAdapter);
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

    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

        private int colorActive = 0xFFFFFFFF;
        private int colorInactive = 0x66FFFFFF;

        private final float DP = Resources.getSystem().getDisplayMetrics().density;

        /**
         * Height of the space the indicator takes up at the bottom of the view.
         */
        private final int mIndicatorHeight = (int) (DP * 16);

        /**
         * Indicator stroke width.
         */
        private final float mIndicatorStrokeWidth = DP * 2;

        /**
         * Indicator width.
         */
        private final float mIndicatorItemLength = DP * 16;
        /**
         * Padding between indicators.
         */
        private final float mIndicatorItemPadding = DP * 4;

        /**
         * Some more natural animation interpolation
         */
        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();

        public LinePagerIndicatorDecoration() {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int itemCount = parent.getAdapter().getItemCount();

            // center horizontally, calculate width and subtract half from center
            float totalLength = mIndicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

            // center vertically in the allotted space
            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


            // find active page (which should be highlighted)
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            // find offset of active page (if the user is scrolling)
            final View activeChild = layoutManager.findViewByPosition(activePosition);
            int left = activeChild.getLeft();
            int width = activeChild.getWidth();

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
        }

        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
            mPaint.setColor(colorInactive);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            float start = indicatorStartX;
            for (int i = 0; i < itemCount; i++) {
                // draw the line for every item
                c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
                start += itemWidth;
            }
        }

        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                    int highlightPosition, float progress, int itemCount) {
            mPaint.setColor(colorActive);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            if (progress == 0F) {
                // no swipe, draw a normal indicator
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
            } else {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                // calculate partial highlight
                float partialLength = mIndicatorItemLength * progress;

                // draw the cut off highlight
                c.drawLine(highlightStart + partialLength, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);

                // draw the highlight overlapping to the next item as well
                if (highlightPosition < itemCount - 1) {
                    highlightStart += itemWidth;
                    c.drawLine(highlightStart, indicatorPosY,
                            highlightStart + partialLength, indicatorPosY, mPaint);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mIndicatorHeight;
        }
    }
    private void filterListener(String text){
        List<Products> list = new ArrayList<>();
        for(Products product : products){
            if(product.getProduct_name().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                list.add(product);
            }
        }
        if(list.isEmpty()){
            textView.setText("Không tồn tại sản phẩm này");
        }else{
            productAdapter.setListenerList(list);
            textView.setText("");
        }
    }
}