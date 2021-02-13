package com.photo.gallery.pojo;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    private int total;
    private int totalHits;
    @SerializedName("hits")
    private List<Hits> hitsList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Hits> getHitsList() {
        return hitsList;
    }

    public void setHitsList(List<Hits> hitsList) {
        this.hitsList = hitsList;
    }
}
