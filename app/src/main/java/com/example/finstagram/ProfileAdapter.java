package com.example.finstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            // TODO: set by getting device width
            itemPost.setLayoutParams(new GridLayoutManager.LayoutParams(360,360));
            itemUser.setVisibility(View.GONE);
            itemPostDetails.setVisibility(View.GONE);


            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = posts.get(position);
                        ((MainActivity) context).displayFragmentPostDetail(post);
                    }
                }
            });
        }
    }
}
