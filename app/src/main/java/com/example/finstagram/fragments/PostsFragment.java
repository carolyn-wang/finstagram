package com.example.finstagram.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finstagram.EndlessRecyclerViewScrollListener;
import com.example.finstagram.Post;
import com.example.finstagram.PostsAdapter;
import com.example.finstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";
    protected static final int POSTS_TO_LOAD = 5;
    private Context mContext;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    protected int scrollCounter;


    public PostsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getContext(); // MainActivity
        rvPosts = view.findViewById(R.id.rvPosts);
        scrollCounter = 0;

        allPosts = new ArrayList();
        adapter = new PostsAdapter(mContext, allPosts);
        rvPosts.setAdapter(adapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(mContext));
        queryPosts(scrollCounter);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * Refreshes feed
             * Sets refreshing to false once network request has completed successfully
             */
            @Override
            public void onRefresh() {
                adapter.clear();
                scrollCounter = 0;
                queryPosts(scrollCounter);
                swipeContainer.setRefreshing(false);

//                adapter = new PostsAdapter(mContext, allPosts);
//                rvPosts.setAdapter(adapter);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvPosts.setLayoutManager(linearLayoutManager);
            // Retain an instance so that you can call `resetState()` for fresh searches
            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    queryPosts(scrollCounter);
                    scrollCounter = scrollCounter + POSTS_TO_LOAD;
                }
            };
            // Adds the scroll listener to RecyclerView
            rvPosts.addOnScrollListener(scrollListener);

    }

//    // Append the next page of data into the adapter
//    // This method probably sends out a network request and appends new data items to your adapter.
//    public void loadNextDataFromApi(int offset) {
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        query.include(Post.KEY_USER);
//        query.setLimit(POSTS_TO_LOAD);
//        query.addDescendingOrder(Post.KEY_CREATED_AT);
//        query.setSkip(offset);
//
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> posts, ParseException e) {
//                // check for errors
//                if (e != null) {
//                    Log.e(TAG, "Issue with getting posts", e);
//                    return;
//                }
//
//                // for debugging purposes let's print every post description to logcat
//                for (Post post : posts) {
//                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
//                }
//
//                // save received posts to list and notify adapter of new data
//                allPosts.addAll(posts);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//    }


    protected void queryPosts(int offset) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(POSTS_TO_LOAD);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.setSkip(offset);
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}