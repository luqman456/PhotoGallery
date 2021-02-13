package com.photo.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jsibbold.zoomage.ZoomageView;
import com.photo.gallery.R;
import com.photo.gallery.interfaces.onRecycleViewItemClick;
import com.photo.gallery.pojo.Hits;


import java.util.List;

public class FullScreenPhotoAdapter extends PagerAdapter {

    private Activity activity;
    private List<Hits> images;
    private LayoutInflater inflater;

    public FullScreenPhotoAdapter(Activity _activity, List<Hits> images) {
        this.activity = _activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomageView imgDisplay;


        inflater = (LayoutInflater)activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay =  viewLayout.findViewById(R.id.imgDisplay);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(images.get(position)), options);
//        imgDisplay.setImageBitmap(bitmap);

        Glide.with(activity)
                .load(images.get(position).getUserImageURL())
                .placeholder(R.color.design_default_color_on_primary)
                .error(R.drawable.icon)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imgDisplay);

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
