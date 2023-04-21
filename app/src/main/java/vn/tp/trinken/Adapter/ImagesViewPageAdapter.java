package vn.tp.trinken.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
import vn.tp.trinken.R;
import vn.tp.trinken.Model.*;

public class ImagesViewPageAdapter extends PagerAdapter {
    private List<Images> imageLists;


    public ImagesViewPageAdapter(List<Images> imageLists) {
        this.imageLists = imageLists;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_images, container, false);
        ImageView imageView = view.findViewById(R.id.imgView);
        Images images = imageLists.get(position);
        imageView.setImageResource(images.getImagesId());

        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        if(imageLists!=null){
            return imageLists.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
