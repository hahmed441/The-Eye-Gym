package com.example.haroonahmed.theeyegym;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Animation extends AppCompatActivity {
//screen
    private int screenWidth;
    private int screenHeight;

//image
    private ImageView Eye;
    TextView topTip;
    TextView bottomTip;
    int width = 0;
    int height = 0;
    ImageView closeCross;

    TrainingAnimator trainingAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Eye = (ImageView) findViewById(R.id.eye);
        topTip = (TextView) findViewById(R.id.toptip);
        bottomTip = (TextView) findViewById(R.id.bottomtip);
        closeCross = (ImageView) findViewById(R.id.btnCloseTraining);

        topTip.setAlpha(0);
        bottomTip.setAlpha(0);
        //Display display = getWindowManager().getDefaultDisplay();
        //width = display.getWidth();
        //height = display.getHeight();

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        height = display.heightPixels;
        width = display.widthPixels;
    }


    @Override
    protected void onResume() {
        super.onResume();
        trainingAnimator = new TrainingAnimator(Animation.this, Eye, topTip, bottomTip, width, height);
        trainingAnimator.restExercises();
        ///----------------------------
        closeCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(closeCross, View.SCALE_X, 1.0f, 0.85f, 1.15f, 1.0f);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(closeCross, View.SCALE_Y, 1.0f, 0.85f, 1.15f, 1.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animatorX).with(animatorY);
                animatorSet.setDuration(30);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        onBackPressed();
                        overridePendingTransition(R.anim.in_left, R.anim.out_right);
                    }
                });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        trainingAnimator.stop();
        trainingAnimator = null;
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}