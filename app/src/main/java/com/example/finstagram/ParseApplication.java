package com.example.finstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1qlGMHvW1XtQZnmI39aQiFLKpRnAznajx7UCnGqU")
                .clientKey("VtnBWRnqAXXCA04T0ivOBuR9jknMCZuYyoek5rV2")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
