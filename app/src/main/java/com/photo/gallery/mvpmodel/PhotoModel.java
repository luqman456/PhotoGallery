package com.photo.gallery.mvpmodel;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.provider.MediaStore;
import android.util.Log;

import com.photo.gallery.mvpmodel.imodel.IPhotoModel;
import com.photo.gallery.mvppresenter.ipresenter.IPhotoPresenter;
import com.photo.gallery.pojo.Hits;
import com.photo.gallery.pojo.ImageModel;
import com.photo.gallery.pojo.Images;
import com.photo.gallery.retrofitconfiguration.ApiInterface;
import com.photo.gallery.retrofitconfiguration.RetrofitConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoModel implements IPhotoModel {
    IPhotoPresenter photoPresenter;
    private Context context;
    private String[] projection = {MediaStore.MediaColumns.DATA};
    ArrayList<Hits> hitsArrayList = new ArrayList<>();

    public PhotoModel(IPhotoPresenter photoPresenter, Context context) {
        this.photoPresenter = photoPresenter;
        this.context = context;
    }

    @Override
    public void btnClickForPermission() {
        photoPresenter.askPermission();
    }

    @Override
    public void getPhotoList() {
        Retrofit retrofit = RetrofitConnection.getRetrofitFactoryForNonToken();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Images> call = apiInterface.getImages("20231563-2d2b7a0d96f3c8aca8c7b1780", "yellow+flower", "photo", true);
        call.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                Log.e("response", "" + response.code() + " " + response.toString());
                if (response.isSuccessful()) {
                    if(response.body().getHitsList().size() > 0){
                        Log.e("response2", "" + response.body().getTotal()+" size "+response.body().getHitsList().size());
                        photoPresenter.getPhotoList(response.body().getHitsList());
                    }
                    else{
                        getImageFromExternalStorage();
                    }
                }
                else {
                    getImageFromExternalStorage();
                }
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
                Log.e("onFailure", "" + t.getMessage());
                getImageFromExternalStorage();
            }
        });

    }

    private void getImageFromExternalStorage() {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            Hits hits = new Hits();
            hits.setUserImageURL(absolutePathOfImage);
            hitsArrayList.add(hits);
        }
        cursor.close();
        photoPresenter.getPhotoList(hitsArrayList);
    }
}
