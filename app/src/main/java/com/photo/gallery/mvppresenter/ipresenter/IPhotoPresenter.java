package com.photo.gallery.mvppresenter.ipresenter;

import com.photo.gallery.pojo.Hits;

import java.util.List;

public interface IPhotoPresenter {
    void btnClick();
    void askPermission();
    void photoList();
    void getPhotoList(List<Hits> hitsList);
}
