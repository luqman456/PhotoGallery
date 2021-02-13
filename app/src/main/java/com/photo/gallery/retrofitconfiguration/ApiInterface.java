package com.photo.gallery.retrofitconfiguration;

import com.photo.gallery.pojo.Images;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api")
    Call<Images> getImages(@Query("key") String key, @Query("q") String q, @Query("image_type") String image_type, @Query("pretty") Boolean pretty);

}
