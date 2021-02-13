package com.photo.gallery.mvpview;

import com.photo.gallery.pojo.Hits;
import com.photo.gallery.pojo.ImageModel;

import java.util.List;

public interface PhotosView {
    void showPermission();
    void init();
    void photoList(List<Hits> imageModelList);
    void showProgress();
    void dismissProgress();
}
