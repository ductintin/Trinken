package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import vn.tp.trinken.R;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.tp.trinken.Model.*;
import vn.tp.trinken.Adapter.*;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLL;
    SliderAdapter adapter;
    private ArrayList<SliderModal> sliderModalArrayList;
    private TextView[] dots;
    int size;

    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // initializing all our views.
        viewPager = findViewById(R.id.idViewPager);
        dotsLL = findViewById(R.id.idLLDots);

        // in below line we are creating a new array list.
        sliderModalArrayList = new ArrayList<>();

        // on below 3 lines we are adding data to our array list.
        sliderModalArrayList.add(new SliderModal("Trinken", "DISCOVERY", "@drawable/courier",  "YOUR FAVORITE DRINK"));
        sliderModalArrayList.add(new SliderModal("Trinken", "CHOOSE", "https://images.unsplash.com/photo-1610783131813-475d08664ef6?ixid=MXwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMnx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",  "HE DRINK YOU LOVE"));
        sliderModalArrayList.add(new SliderModal("Trinken", "DELIVERY", "https://images.unsplash.com/photo-1610832958506-aa56368176cf?ixid=MXwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxN3x8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","ENJOY THE DRINK"));

        // below line is use to add our array list to adapter class.
        adapter = new SliderAdapter(WelcomeActivity.this, sliderModalArrayList);

        // below line is use to set our
        // adapter to our view pager.
        viewPager.setAdapter(adapter);

        // we are storing the size of our
        // array list in a variable.
        size = sliderModalArrayList.size();

        // calling method to add dots indicator
        addDots(size, 0);

        // below line is use to call on
        // page change listener method.
        viewPager.addOnPageChangeListener(viewListener);
    }

    private void addDots(int size, int pos) {
        // inside this method we are
        // creating a new text view.
        dots = new TextView[size];

        // below line is use to remove all
        // the views from the linear layout.
        dotsLL.removeAllViews();

        // running a for loop to add
        // number of dots to our slider.
        for (int i = 0; i < size; i++) {
            // below line is use to add the
            // dots and modify its color.
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("."));
            dots[i].setTextSize(35);

            // below line is called when the dots are not selected.
            dots[i].setTextColor(getResources().getColor(R.color.black));
            dotsLL.addView(dots[i]);
        }
        if (dots.length > 0) {
            // this line is called when the dots
            // inside linear layout are selected
            dots[pos].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    // creating a method for view pager for on page change listener.
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // we are calling our dots method to
            // change the position of selected dots.
            addDots(size, position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}