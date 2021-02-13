package com.photo.gallery.mvppresenter;

import android.content.Context;

import com.photo.gallery.mvpmodel.PhotoModel;
import com.photo.gallery.mvpmodel.imodel.IPhotoModel;
import com.photo.gallery.mvppresenter.ipresenter.IPhotoPresenter;
import com.photo.gallery.mvpview.PhotosView;
import com.photo.gallery.pojo.Hits;

import java.util.List;

public class PhotoPresenter implements IPhotoPresenter {
    PhotosView photosView;
    IPhotoModel photoModel;

    public PhotoPresenter(PhotosView photosView, Context context) {
        this.photosView = photosView;
        photoModel = new PhotoModel(this,context);
        photosView.init();
    }

    @Override
    public void btnClick() {
        photoModel.btnClickForPermission();
    }

    @Override
    public void askPermission() {
        photosView.showPermission();
    }

    @Override
    public void photoList() {
        photosView.showProgress();
        photoModel.getPhotoList();
    }

    @Override
    public void getPhotoList(List<Hits> hitsList) {
        photosView.dismissProgress();
        photosView.photoList(hitsList);
    }
}
