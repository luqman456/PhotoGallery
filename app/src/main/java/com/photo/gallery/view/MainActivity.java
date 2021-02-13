package com.photo.gallery.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.photo.gallery.R;
import com.photo.gallery.adapter.PhotoAdapter;
import com.photo.gallery.databinding.ActivityMainBinding;
import com.photo.gallery.interfaces.onRecycleViewItemClick;
import com.photo.gallery.mvppresenter.PhotoPresenter;
import com.photo.gallery.mvppresenter.ipresenter.IPhotoPresenter;
import com.photo.gallery.mvpview.PhotosView;
import com.photo.gallery.pojo.Hits;
import com.photo.gallery.progressbar.CustomProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotosView, onRecycleViewItemClick {
    private final int PERMISSION_REQUEST_CODE = 100;
    ActivityMainBinding activityMainBinding;
    IPhotoPresenter photoPresenter;
    CustomProgressDialog customProgressDialog;
    ArrayList<Hits> hitList;
    PhotoAdapter photoAdapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        customProgressDialog = new CustomProgressDialog(context);
        photoPresenter = new PhotoPresenter(this,context);
        readPermission();
        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPresenter.btnClick();
            }
        });


    }

    private void readPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                activityMainBinding.linear.setVisibility(View.GONE);
                activityMainBinding.recycleView.setVisibility(View.VISIBLE);
                photoPresenter.photoList();
            } else {
                Toast.makeText(context, "Need Permission to show internal storage photos", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            photoPresenter.photoList();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void showPermission() {
        readPermission();
    }

    @Override
    public void init() {
        hitList = new ArrayList<>();
        activityMainBinding.recycleView.setHasFixedSize(true);
        activityMainBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new PhotoAdapter(hitList, context);
        activityMainBinding.recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        activityMainBinding.recycleView.setAdapter(photoAdapter);
        activityMainBinding.recycleView.setItemAnimator(null);
        photoAdapter.setClickListener(this);
    }

    @Override
    public void photoList(List<Hits> hits) {
        Log.e("image","size "+hits.size());
            hitList.addAll(hits);
            photoAdapter.notifyDataSetChanged();

        }

    @Override
    public void showProgress() {
        customProgressDialog.StartCircularPrograsBar("","Loading...");
    }

    @Override
    public void dismissProgress() {
        customProgressDialog.DismissPrograsBar();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activityMainBinding.linear.setVisibility(View.GONE);
                    activityMainBinding.recycleView.setVisibility(View.VISIBLE);
                    photoPresenter.photoList();

                } else {
                    activityMainBinding.linear.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onRecyclerViewItemClicked(int position) {
        fullScreenImage(position);
    }

    private void fullScreenImage(int position){
        Intent intent = new Intent(MainActivity.this, ShowFullPhotoActivity.class);
        intent.putExtra("LIST", (Serializable) hitList);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}