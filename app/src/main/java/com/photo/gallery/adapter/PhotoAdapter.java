package com.photo.gallery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.photo.gallery.R;
import com.photo.gallery.databinding.PhotosListBinding;
import com.photo.gallery.interfaces.onRecycleViewItemClick;
import com.photo.gallery.pojo.Hits;
import com.photo.gallery.pojo.ImageModel;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{

    private List<Hits> imageModels;
    private Context context;
    private onRecycleViewItemClick clickListener;

    public PhotoAdapter(List<Hits> imageModels, Context context) {
        this.imageModels = imageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhotosListBinding photosListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photos_list, parent, false);
        return new PhotoViewHolder(photosListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

        Glide.with(context)
                .load(imageModels.get(position).getUserImageURL())
                .placeholder(R.color.design_default_color_on_primary)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(holder.photosListBinding.imageViewOne);

        holder.photosListBinding.imageViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onRecyclerViewItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public void setClickListener(onRecycleViewItemClick clickListener) {
        this.clickListener = clickListener;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        PhotosListBinding photosListBinding;
        public PhotoViewHolder(PhotosListBinding photosListBinding) {
            super(photosListBinding.getRoot());
            this.photosListBinding = photosListBinding;
        }
    }
}
