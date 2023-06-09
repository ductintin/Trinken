package vn.tp.trinken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import vn.tp.trinken.R;
import vn.tp.trinken.Activity.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
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

    private int last_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // initializing all our views.
        AnhXa();

        // in below line we are creating a new array list.
        sliderModalArrayList = new ArrayList<>();

        // on below 3 lines we are adding data to our array list.

        sliderModalArrayList.add(new SliderModal("Trinken", "DISCOVERY","", "delivery",  "YOUR FAVORITE DRINK"));
        sliderModalArrayList.add(new SliderModal("Trinken", "CHOOSE","", "welcome_img2",  "HE DRINK YOU LOVE"));
        sliderModalArrayList.add(new SliderModal("Trinken", "DELIVERY","", "shipper","ENJOY THE DRINK"));


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
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last_position = viewPager.getCurrentItem();
                if(last_position == size - 1){
                    Intent intent = new Intent(WelcomeActivity.this, IndexActivity.class);
                    startActivity(intent);
                }else{
                    viewPager.setCurrentItem(last_position + 1,true);
                }

            }
        });
    }

    private void AnhXa(){
        viewPager = findViewById(R.id.idViewPager);
        dotsLL = findViewById(R.id.idLLDots);
        btnSkip = findViewById(R.id.idBtnSkip);
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
            dots[pos].setTextColor(getResources().getColor(R.color.orange));
            dots[pos].setText(Html.fromHtml("_"));
            dots[pos].setTextSize(50);
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

            //change the content of skip button
            if(position == size - 1){
                btnSkip.setText("Let's start!");
            }else {
                btnSkip.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}