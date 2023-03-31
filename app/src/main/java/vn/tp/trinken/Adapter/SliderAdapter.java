package vn.tp.trinken.Adapter;

import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    // creating variables for layout
    // inflater, context and array list.
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<SliderModal> sliderModalArrayList;

    // creating constructor.
    public SliderAdapter(Context context, ArrayList<SliderModal> sliderModalArrayList) {
        this.context = context;
        this.sliderModalArrayList = sliderModalArrayList;
    }

    @Override
    public int getCount() {
        // inside get count method returning
        // the size of our array list.
        return sliderModalArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // inside isViewFromobject method we are
        // returning our Relative layout object.
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        // in this method we will initialize all our layout
        // items and inflate our layout file as well.
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        // below line is use to inflate the
        // layout file which we created.
        View view = layoutInflater.inflate(R.layout.welcome_layout1, container, false);

        // initializing our views.
        ImageView imageView = view.findViewById(R.id.image);
        TextView appName = view.findViewById(R.id.tvAppName);
        TextView title = view.findViewById(R.id.tvTitle);
        TextView subTitle = view.findViewById(R.id.tvSubTitle);
        ConstraintLayout sliderRL = view.findViewById(R.id.layout);

        // setting data to our views.
        SliderModal modal = sliderModalArrayList.get(position);
        appName.setText(modal.getTitle());
        title.setText(modal.getHeading());
        subTitle.setText(modal.getSubTitle());
        int resourceId = context.getResources().getIdentifier(modal.getImgUrl(), "drawable",
                    context.getPackageName());
        imageView.setImageResource(resourceId);

        //Picasso.get().load(modal.getImgUrl()).into(imageView);



        // below line is to set background
        // drawable to our each item
        //sliderRL.setBackground(context.getResources().getDrawable(modal.getBackgroundDrawable()));
        //sliderRL.setBackground(ContextCompat.getDrawable(context, modal.getBackgroundDrawable()));
        // after setting the data to our views we
        // are adding the view to our container.
        container.addView(view);

        // at last we are
        // returning the view.
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // this is a destroy view method
        // which is use to remove a view.
        container.removeView((ConstraintLayout) object);
    }
}
