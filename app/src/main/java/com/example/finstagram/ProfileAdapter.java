package com.example.finstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

public class ProfileAdapter extends PostsAdapter {
    public ProfileAdapter(Context context, List<Post> posts) {
        super(context, posts);
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    class ViewHolder extends PostsAdapter.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Post post) {
            super.bind(post);

            ivProfileImage.setVisibility(View.GONE);
            tvUsername.setVisibility(View.GONE);
        }
    }
}
