package com.example.finstagram;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finstagram.fragments.ComposeFragment;
import com.example.finstagram.fragments.PostsFragment;
import com.example.finstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private View itemUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment().newInstance(ParseUser.getCurrentUser());
                        break;
                    default: return true;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }


        });
        // Set default fragment
        bottomNavigationView.setSelectedItemId(R.id.action_home);

//         Find the toolbar view and set as ActionBar
        Toolbar topToolbar = findViewById(R.id.topToolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    protected void displayFragmentUserDetail(ParseUser user){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment userFragment = new ProfileFragment().newInstance(user);
        ft.replace(R.id.flContainer, userFragment);
        ft.commit();
    }

    protected void displayFragmentPostDetail(Post post){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment postFragment = new ComposeFragment();
        ft.replace(R.id.flContainer, postFragment);
        ft.commit();
    }

}