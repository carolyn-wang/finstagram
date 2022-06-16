package com.example.finstagram;

        import com.parse.ParseClassName;
        import com.parse.ParseFile;
        import com.parse.ParseObject;
        import com.parse.ParseUser;
@Deprecated
@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_CREATED_KEY = "createdAt";


    public String getUsername(){
        return getString(KEY_USERNAME);
    }

    public void setUsernmae(String username){
        put(KEY_USERNAME, username);
    }

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile parseFile){
        put(KEY_PROFILE_IMAGE, parseFile);
    }

}


