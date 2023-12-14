package com.google.mlkit.vision.demo.java;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mlkit.vision.demo.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Integer> photoList;

    private Context context;

    public PhotoAdapter(List<Integer> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        int photoResId = photoList.get(position);
        holder.photoImageView.setImageResource(photoResId);

        holder.photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event, for example, start a new activity
                Intent intent = new Intent(context, ShowFoodActivity.class);
                // You can also pass data to the new activity if needed
                // intent.putExtra("key", value);
                intent.putExtra("imageId", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.imageView);
        }
    }
}
