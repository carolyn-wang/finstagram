package com.example.finstagram;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_LIKED_USERS = "likedUsers";
//    public boolean isLiked = false;

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

//    public int getLikes() {
//        return getInt(KEY_LIKES);
//    }

    public void setLikedUsers(List<ParseUser> users) {
        put(KEY_LIKED_USERS, users);
    }

    public List<ParseUser> getLikedUsers(){
        List<ParseUser> likedUsers = getList(KEY_LIKED_USERS);
        if (likedUsers == null){
            likedUsers = new ArrayList<>();
            setLikedUsers(likedUsers);
        }
        return likedUsers;
    }

    public void addLikedUser(ParseUser user){
        add(KEY_LIKED_USERS, user);
    }

    public void removeLikedUser(ParseUser user){
        List<ParseUser> likedUsers = getLikedUsers();
        for (int i = 0; i < likedUsers.size(); i++) {
            if(likedUsers.get(i).hasSameId(user)) {
                likedUsers.remove(i);
            }
        }
        put(KEY_LIKED_USERS, likedUsers);
    }

    public void likePost(ParseUser user) {
//        setLikes(getLikes() + 1);
//        isLiked = true;
        addLikedUser(user);
        saveInBackground();
        // set isLiked to true

    }

    public void unlikePost(ParseUser user) {
//        setLikes(getLikes() - 1);
//        isLiked = false;
        removeLikedUser(user);
        saveInBackground();
    }

    public int getLikeCount(){
        if (getLikedUsers() != null){
            return getLikedUsers().size();
        }
        return 0;
    }


    public boolean isLiked(ParseUser user) {
        for (int i = 0; i < getLikedUsers().size(); i++) {
            if(getLikedUsers().get(i).hasSameId(user)) {
                return true;
            }
        }
        return false;
    }

}
