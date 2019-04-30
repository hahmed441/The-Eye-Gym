package com.example.haroonahmed.theeyegym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;

public class profileActivity extends AppCompatActivity {


    ImageView imageView;

private BottomNavigationView mMainNav;
private FrameLayout mMainFrame;
private HomeFragment HomeFragment;
private ActivityFragment ActivityFragment;
private NotificationFragment NotificationFragment;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottomnav);

        HomeFragment = new HomeFragment();
        ActivityFragment = new ActivityFragment();
        NotificationFragment = new NotificationFragment();

        setFragment(HomeFragment);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(HomeFragment);
                        return true;

                    case R.id.nav_activity:
                        mMainNav.setItemBackgroundResource(R.color.Red);
                        setFragment(ActivityFragment);
                        return true;

                    case R.id.nav_notif:
                        mMainNav.setItemBackgroundResource(R.color.Purple);
                        setFragment(NotificationFragment);
                        return true;

                    default:
                        return false;


                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();

    }



        @Override
        protected void onStart() {
            super.onStart();
            if (mAuth.getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, loginActivity.class));
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, loginActivity.class));

                break;

            case R.id.editdetails:
                startActivity(new Intent(this, editActivity.class));

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}