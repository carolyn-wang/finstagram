package com.example.finstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Utils extends AppCompatActivity {

    public static void goMainActivity(Runnable activity) {
        Activity c = (SplashActivity)activity;
        Intent i = new Intent(c, MainActivity.class);
        c.startActivity(i);
        c.finish();
    }

    public static void goLoginActivity(Runnable activity) {
        Activity c = (Activity)activity;
        Intent i = new Intent(c, LoginActivity.class);
        c.startActivity(i);
        c.finish();
    }
}
