package com.photo.gallery.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.photo.gallery.R;
import com.photo.gallery.adapter.FullScreenPhotoAdapter;
import com.photo.gallery.databinding.ActivityShowFullPhotoBinding;
import com.photo.gallery.pojo.Hits;

import java.util.ArrayList;
import java.util.List;

public class ShowFullPhotoActivity extends AppCompatActivity {
    ActivityShowFullPhotoBinding activityShowFullPhotoBinding;
    private FullScreenPhotoAdapter fullScreenImageAdapter;
    private List<Hits> hitList;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowFullPhotoBinding = DataBindingUtil.setContentView(this,R.layout.activity_show_full_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        hitList = new ArrayList<>();
        if(getIntent() != null){
            hitList = (List<Hits>) i.getSerializableExtra("LIST");
            position = i.getIntExtra("position",0);
        }

        fullScreenImageAdapter = new FullScreenPhotoAdapter(ShowFullPhotoActivity.this, hitList);

        activityShowFullPhotoBinding.viewPager.setAdapter(fullScreenImageAdapter);

        // displaying selected image first
        activityShowFullPhotoBinding.viewPager.setCurrentItem(position);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}