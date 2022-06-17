package com.example.finstagram;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finstagram.fragments.PostsFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Headers;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    protected Context context; // MainActivity
    protected List<Post> posts;
    private static final String TAG = "PostsAdapter";

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    /**
     * Passes LayoutInflater a “blueprint” of the view (reference to XML layout file)
     * Wraps view in a ViewHolder for easy access
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivProfileImage;
        protected TextView tvUsername;
        protected ImageView ivImage;
        protected TextView tvUsername2;
        protected TextView tvDescription;
        protected TextView tvCreatedAt;
        protected View itemUser;
        protected View itemPost;
        protected View itemPostDetails;
        protected TextView tvLikes;
        private ImageButton ibLike;
        private ImageButton ibComment;
        private ParseUser currentUser;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvUsername2 = itemView.findViewById(R.id.tvUsername2);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            itemUser = itemView.findViewById(R.id.itemUser);
            itemPost = itemView.findViewById(R.id.itemPost);
            itemPostDetails = itemView.findViewById(R.id.itemPostDetails);
            ibLike = itemView.findViewById(R.id.ibLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ibComment = itemView.findViewById(R.id.ibComment);
            currentUser = ParseUser.getCurrentUser();
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsername2.setText(post.getUser().getUsername());
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            tvCreatedAt.setText(df.format(post.getCreatedAt()));
            tvLikes.setText(String.valueOf(post.getLikeCount()));

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            ParseFile profileImage = post.getUser().getParseFile("profileImage");
            Glide.with(context).load(profileImage.getUrl()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);

            // set correct heart icon dependent on if User has liked post
            Drawable heart;
            if (!post.isLiked(currentUser)) {
                heart = context.getDrawable(R.drawable.ufi_heart);

            } else {
                heart = context.getDrawable(R.drawable.ufi_heart_active);

            }
            ibLike.setImageDrawable(heart);


            itemUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = posts.get(position);
                        ParseUser postUser = post.getUser();
                        ((MainActivity) context).displayFragmentUserDetail(postUser);
                    }
                }
            });

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!post.isLiked(currentUser)) {
                        post.likePost(currentUser);
                        Drawable newHeart = context.getDrawable(R.drawable.ufi_heart_active);
                        ibLike.setImageDrawable(newHeart);
                        tvLikes.setText(String.valueOf(post.getLikeCount()));
                        Log.i("Like",String.valueOf(post.getLikedUsers().size()));

                    } else { // else, if already favorited, unfavorite
                        post.unlikePost(currentUser);
                        Drawable newHeart = context.getDrawable(R.drawable.ufi_heart);
                        ibLike.setImageDrawable(newHeart);
                        tvLikes.setText(String.valueOf(post.getLikeCount()));
                        Log.i("Unlike", "unfavorited onSuccess");
                    }
                }
            });

            ibComment.setOnClickListener(new View.OnClickListener() {
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

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}
