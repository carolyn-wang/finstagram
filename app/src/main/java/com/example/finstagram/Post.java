package com.example.finstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_LIKE_USERS = "likeUsers";
    public boolean isLiked = false;

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

    public int getLikes() {
        return getInt(KEY_LIKES);
    }

    public void setLikes(int likes) {
        put(KEY_LIKES, likes);
    }

    public ParseObject getLikeUsers(){
        return getParseObject(KEY_LIKE_USERS);
    }


    public void likePost() {
        setLikes(getLikes() + 1);
        isLiked = true;

        saveInBackground();
    }

    public void unlikePost() {
        setLikes(getLikes() - 1);
        isLiked = false;
        saveInBackground();
    }

//    public boolean isLiked() {
//        if (getLikes() > 0){
//            return true;
//        }
//        return false;
//    }

}
