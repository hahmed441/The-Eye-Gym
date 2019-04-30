package com.example.haroonahmed.theeyegym;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TrainingAnimator {

    private int correctExercises;
    private int onCornersCount;
    private Activity activity;
    private MediaPlayer mediaPlayer;
    private ImageView img;
    private TextView tvTop;
    private TextView tvDown;
    private int width;
    private int height;
    private final float speed = 0.56f;

    private AnimatorSet animatorSet = null;
    private AnimatorSet animatorSetShow = null;
    private AnimatorSet animationSetGone = null;
    private ObjectAnimator objectAnimatorUdDown = null;
    private ObjectAnimator objectAnimatorUdDownLeftRight = null;

    private ObjectAnimator objectAnimatorGoToTopRight = null;
    private ObjectAnimator objectAnimatorGoToBottomRight = null;
    private ObjectAnimator objectAnimatorGoToBottomLeft = null;
    private ObjectAnimator objectAnimatorGoToTopLeft = null;
    private AnimatorSet animatorSetGoToTopLeft = null;
    private AnimatorSet animatorSetGoToCenter = null;


    private AnimatorSet animatorSetGoToTopLeftC = null;
    private ObjectAnimator objectAnimatorGoToBottomLeftC = null;
    private ObjectAnimator objectAnimatorGoToBottomRightC = null;
    private ObjectAnimator objectAnimatorGoToTopRightC = null;
    private ObjectAnimator objectAnimatorGoToTopLeftC = null;
    private AnimatorSet animatorSetGoToCenterC = null;

    private AnimatorSet animatorSetShowC = null;
    private AnimatorSet animatorSetC = null;

    public TrainingAnimator(Activity act, ImageView img1, TextView tvTop1, TextView tvDown1, int width1, int height1) {
        correctExercises = 0;
        onCornersCount = 0;
        activity = act;
        img = img1;
        tvTop = tvTop1;
        tvDown = tvDown1;
        width = width1;
        height = height1;
        mediaPlayer = MediaPlayer.create(activity, R.raw.sound);
    }

    public void restExercises() {
        animTips(activity.getString(R.string.close_eyes), activity.getString(R.string.relax));
        ObjectAnimator objectAnimatorScaleX = ObjectAnimator.ofFloat(img, View.SCALE_X, 0.8f, 1.2f);
        objectAnimatorScaleX.setDuration(3000);
        objectAnimatorScaleX.setRepeatCount(5);
        objectAnimatorScaleX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator objectAnimatorScaleY = ObjectAnimator.ofFloat(img, View.SCALE_Y, 0.8f, 1.2f);
        objectAnimatorScaleY.setDuration(3000);
        objectAnimatorScaleY.setRepeatCount(5);
        objectAnimatorScaleY.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimatorScaleX).with(objectAnimatorScaleY);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mediaPlayer.start();
                switch (correctExercises) {
                    case 0:
                        UpDownExercises();
                        correctExercises++;
                        break;
                    case 1:
                        RightLeftExercises();
                        correctExercises++;
                        break;
                    case 2:
                        OnCornersExercises();
                        onCornersCount = 0;
                        correctExercises++;
                        break;
                    case 3:
                        OnCornersExercisesConversely();
                        onCornersCount = 0;
                        correctExercises++;
                        break;
                    case 4:
                        LookDistantObject();
                        correctExercises++;
                        break;
                    case 5:
                        mediaPlayer.start();
                        Intent intent = new Intent(activity, profileActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.in_left, R.anim.out_right);
                        break;
                }
            }
        });

    }

    private void animTips(String text1, String text2) {
        tvTop.setText(text1);
        tvDown.setText(text2);
        tvTop.setVisibility(View.VISIBLE);
        tvDown.setVisibility(View.VISIBLE);
        ObjectAnimator animTvTopShow = ObjectAnimator.ofFloat(tvTop, View.ALPHA, 0.0f, 1.0f);
        ObjectAnimator animTvEndShow = ObjectAnimator.ofFloat(tvDown, View.ALPHA, 0.0f, 1.0f);
        animTvTopShow.setDuration(1000);
        animTvEndShow.setDuration(1000);
        animatorSetShow = new AnimatorSet();
        animatorSetShow.play(animTvTopShow).with(animTvEndShow);
        Interpolator interpolatorShow = new LinearOutSlowInInterpolator();
        animatorSetShow.setInterpolator(interpolatorShow);


        ObjectAnimator animTvTopGone = ObjectAnimator.ofFloat(tvTop, View.ALPHA, 1.0f, 0.0f);
        ObjectAnimator animTvEndGone = ObjectAnimator.ofFloat(tvDown, View.ALPHA, 1.0f, 0.0f);
        animTvTopGone.setDuration(10000);
        animTvEndGone.setDuration(10000);
        animationSetGone = new AnimatorSet();
        animationSetGone.play(animTvTopGone).with(animTvEndGone);
        Interpolator interpolatorGone = new FastOutLinearInInterpolator();
        animationSetGone.setInterpolator(interpolatorGone);

        animatorSetShow.start();
        animatorSetShow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationSetGone.start();

            }
        });
        animationSetGone.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tvTop.setVisibility(View.GONE);
                tvDown.setVisibility(View.GONE);
                tvTop.setAlpha(0);
                tvDown.setAlpha(0);
            }
        });
    }

    private void UpDownExercises() {
        animTips(activity.getString(R.string.up_and_down), activity.getString(R.string.follow_the_pointer));
        int size = height / 2;
        int fraction = height / 35;
        objectAnimatorUdDown = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, 0, -(size - fraction), size - fraction, 0);
        Interpolator interpolator = new LinearInterpolator();
        objectAnimatorUdDown.setDuration(getCountTime(2 * height));
        objectAnimatorUdDown.setRepeatCount(3);
        objectAnimatorUdDown.setInterpolator(interpolator);
        objectAnimatorUdDown.start();
        objectAnimatorUdDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                restExercises();
            }
        });
    }

    private void RightLeftExercises() {
        animTips(activity.getString(R.string.left_and_right), activity.getString(R.string.follow_the_pointer));
        int size = width / 2;
        int fraction = width / 35;
        objectAnimatorUdDownLeftRight = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 0, -(size - fraction), size - fraction, 0);
        Interpolator interpolator = new LinearInterpolator();
        objectAnimatorUdDownLeftRight.setDuration(getCountTime(2 * width));
        objectAnimatorUdDownLeftRight.setRepeatCount(3);
        objectAnimatorUdDownLeftRight.setInterpolator(interpolator);
        objectAnimatorUdDownLeftRight.start();
        objectAnimatorUdDownLeftRight.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                restExercises();
            }
        });
    }

    private void OnCornersExercises() {
        onCornersCount = 0;
        animTips(activity.getString(R.string.trajectory_rectangle), activity.getString(R.string.follow_the_pointer));
        int sizeWeidth = width / 2;
        int sizeHeight = height / 2;
        int fractionWeidth = width / 22;
        int fractionHeight = height / 22;
        Interpolator interpolator = new LinearInterpolator();
        //----------------------------
        objectAnimatorGoToTopRight = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, -(sizeWeidth - fractionWeidth), (sizeWeidth - fractionWeidth));
        objectAnimatorGoToTopRight.setDuration(getCountTime(width));
        objectAnimatorGoToTopRight.setInterpolator(interpolator);
        //-----------------------------
        objectAnimatorGoToBottomRight = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, -(sizeHeight - fractionHeight), (sizeHeight - fractionHeight));
        objectAnimatorGoToBottomRight.setDuration(getCountTime(height));
        objectAnimatorGoToBottomRight.setInterpolator(interpolator);
        //-----------------------------
        objectAnimatorGoToBottomLeft = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, (sizeWeidth - fractionWeidth), -(sizeWeidth - fractionWeidth));
        objectAnimatorGoToBottomLeft.setDuration(getCountTime(width));
        objectAnimatorGoToBottomLeft.setInterpolator(interpolator);
        //----------------------------
        objectAnimatorGoToTopLeft = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, (sizeHeight - fractionHeight), -(sizeHeight - fractionHeight));
        objectAnimatorGoToTopLeft.setDuration(getCountTime(height));
        objectAnimatorGoToTopLeft.setInterpolator(interpolator);
        //------------------------------------
        ///робити всякі круті штуки////
        //-----рух в верхній правий кут
        ObjectAnimator objectAnimatorGoToTopLeftX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 0, -(sizeWeidth - fractionWeidth));
        ObjectAnimator objectAnimatorGoToTopLeftY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, 0, -(sizeHeight - fractionHeight));
        animatorSetGoToTopLeft = new AnimatorSet();
        animatorSetGoToTopLeft.setInterpolator(interpolator);
        animatorSetGoToTopLeft.setDuration(getCountTime(getHypotenuse()));
        animatorSetGoToTopLeft.play(objectAnimatorGoToTopLeftX).with(objectAnimatorGoToTopLeftY);
        animatorSetGoToTopLeft.start();
        //рух з правого кута в центр
        ObjectAnimator objectAnimatorGoToCenterX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, -(sizeWeidth - fractionWeidth), 0);
        ObjectAnimator objectAnimatorGoToCenterY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, -(sizeHeight - fractionHeight), 0);
        animatorSetGoToCenter = new AnimatorSet();
        animatorSetGoToCenter.setInterpolator(interpolator);
        animatorSetGoToCenter.setDuration(getHypotenuse());
        animatorSetGoToCenter.play(objectAnimatorGoToCenterX).with(objectAnimatorGoToCenterY);
        //----------------------------------------
        animatorSetGoToTopLeft.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToTopRight.start();
            }
        });
        objectAnimatorGoToTopRight.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToBottomRight.start();
            }
        });
        objectAnimatorGoToBottomRight.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToBottomLeft.start();
            }
        });
        objectAnimatorGoToBottomLeft.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToTopLeft.start();
            }
        });
        objectAnimatorGoToTopLeft.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onCornersCount < 1) {
                    objectAnimatorGoToTopRight.start();
                    onCornersCount++;
                } else {
                    animatorSetGoToCenter.start();
                    onCornersCount = 0;
                }
            }
        });
        animatorSetGoToCenter.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                restExercises();
            }
        });
    }

    private void OnCornersExercisesConversely() {
        onCornersCount = 0;
        animTips(activity.getString(R.string.trajectory_rectangle_vice_verca), activity.getString(R.string.follow_the_pointer));
        int sizeWeidth = width / 2;
        int sizeHeight = height / 2;
        int fractionWeidth = width / 22;
        int fractionHeight = height / 22;
        Interpolator interpolator = new LinearInterpolator();
        //---------------------------------------
        //-----рух в верхній правий кут
        ObjectAnimator objectAnimatorGoToTopLeftX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 0, -(sizeWeidth - fractionWeidth));
        ObjectAnimator objectAnimatorGoToTopLeftY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, 0, -(sizeHeight - fractionHeight));
        animatorSetGoToTopLeftC = new AnimatorSet();
        animatorSetGoToTopLeftC.setInterpolator(interpolator);
        animatorSetGoToTopLeftC.setDuration(getCountTime(getHypotenuse()));
        animatorSetGoToTopLeftC.play(objectAnimatorGoToTopLeftX).with(objectAnimatorGoToTopLeftY);
        animatorSetGoToTopLeftC.start();
        //-----рух по кутах
        objectAnimatorGoToBottomLeftC = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, -(sizeHeight - fractionHeight), (sizeHeight - fractionHeight));
        objectAnimatorGoToBottomLeftC.setDuration(getCountTime(height));
        objectAnimatorGoToBottomLeftC.setInterpolator(interpolator);
        //--------------------
        objectAnimatorGoToBottomRightC = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, -(sizeWeidth - fractionWeidth), (sizeWeidth - fractionWeidth));
        objectAnimatorGoToBottomRightC.setDuration(getCountTime(width));
        objectAnimatorGoToBottomRightC.setInterpolator(interpolator);
        //---------------------
        objectAnimatorGoToTopRightC = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, (sizeHeight - 2 * fractionWeidth), -(sizeHeight - fractionWeidth));
        objectAnimatorGoToTopRightC.setDuration(getCountTime(height));
        objectAnimatorGoToTopRightC.setInterpolator(interpolator);
        //--------------------------------------------------------
        objectAnimatorGoToTopLeftC = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, (sizeWeidth - fractionWeidth), -(sizeWeidth - fractionWeidth));
        objectAnimatorGoToTopLeftC.setDuration(getCountTime(width));
        objectAnimatorGoToTopLeftC.setInterpolator(interpolator);
        //------------------------------------------------------
        ObjectAnimator objectAnimatorGoToCenterX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, -(sizeWeidth - fractionWeidth), 0);
        ObjectAnimator objectAnimatorGoToCenterY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, -(sizeHeight - fractionHeight), 0);
        animatorSetGoToCenterC = new AnimatorSet();
        animatorSetGoToCenterC.setInterpolator(interpolator);
        animatorSetGoToCenterC.setDuration(getCountTime(getHypotenuse()));
        animatorSetGoToCenterC.play(objectAnimatorGoToCenterX).with(objectAnimatorGoToCenterY);
        //--------------------------------------------------------
        animatorSetGoToTopLeftC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToBottomLeftC.start();
            }
        });
        objectAnimatorGoToBottomLeftC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToBottomRightC.start();
            }
        });
        objectAnimatorGoToBottomRightC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToTopRightC.start();
            }
        });
        objectAnimatorGoToTopRightC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimatorGoToTopLeftC.start();
            }
        });
        objectAnimatorGoToTopLeftC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onCornersCount < 1) {
                    objectAnimatorGoToBottomLeftC.start();
                    onCornersCount++;
                } else {
                    animatorSetGoToCenterC.start();
                    onCornersCount = 0;
                }
            }
        });
        animatorSetGoToCenterC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                restExercises();
            }
        });


    }

    private void LookDistantObject() {
        FrameLayout.LayoutParams layoutParamsTop = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTop.setMargins(0, height / 10, 0, 0);
        tvTop.setLayoutParams(layoutParamsTop);
        FrameLayout.LayoutParams layoutParamsBottom = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsBottom.setMargins(0, height - (height / 5), 0, 0);
        tvDown.setLayoutParams(layoutParamsBottom);
        //////-------------------
        tvTop.setText(activity.getString(R.string.look_at_distance_object));
        tvDown.setText(activity.getString(R.string.look_at_subject_to_sound));
        tvTop.setVisibility(View.VISIBLE);
        tvDown.setVisibility(View.VISIBLE);
        ObjectAnimator animTvTopShow = ObjectAnimator.ofFloat(tvTop, View.ALPHA, 0.0f, 1.0f);
        ObjectAnimator animTvEndShow = ObjectAnimator.ofFloat(tvDown, View.ALPHA, 0.0f, 1.0f);
        animTvTopShow.setDuration(1000);
        animTvEndShow.setDuration(1000);
        animatorSetShowC = new AnimatorSet();
        animatorSetShowC.play(animTvTopShow).with(animTvEndShow);
        Interpolator interpolatorShow = new LinearOutSlowInInterpolator();
        animatorSetShowC.setInterpolator(interpolatorShow);
        animatorSetShowC.start();
        ////-----------------
        img.setImageResource(R.drawable.distant_object);
        ObjectAnimator objectAnimatorScaleX = ObjectAnimator.ofFloat(img, View.SCALE_X, 0.9f, 1.1f);
        objectAnimatorScaleX.setDuration(3000);
        objectAnimatorScaleX.setRepeatCount(7);
        objectAnimatorScaleX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator objectAnimatorScaleY = ObjectAnimator.ofFloat(img, View.SCALE_Y, 0.9f, 1.1f);
        objectAnimatorScaleY.setDuration(3000);
        objectAnimatorScaleY.setRepeatCount(7);
        objectAnimatorScaleY.setRepeatMode(ValueAnimator.REVERSE);
        animatorSetC = new AnimatorSet();
        animatorSetC.play(objectAnimatorScaleX).with(objectAnimatorScaleY);
        animatorSetC.start();
        animatorSetC.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tvTop.setVisibility(View.GONE);
                tvDown.setVisibility(View.GONE);
                tvTop.setAlpha(0);
                tvDown.setAlpha(0);
                img.setImageResource(R.drawable.eye0);
                restExercises();
            }
        });
    }

    private int getCountTime(int size) {
        return Math.round(size / speed);
    }

    private int getHypotenuse() {
        int a = (int) Math.sqrt(height * height + width * width);
        a = a / 2;
        return a;
    }

    public void stop() {
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        if (animatorSetShow != null) {
            animatorSetShow.removeAllListeners();
            animatorSetShow.cancel();
        }
        if (animationSetGone != null) {
            animationSetGone.removeAllListeners();
            animationSetGone.cancel();
        }
        if (objectAnimatorUdDown != null) {
            objectAnimatorUdDown.removeAllListeners();
            objectAnimatorUdDown.cancel();
        }
        if (objectAnimatorUdDownLeftRight != null) {
            objectAnimatorUdDownLeftRight.removeAllListeners();
            objectAnimatorUdDownLeftRight.cancel();
        }
        if (objectAnimatorGoToTopRight != null) {
            objectAnimatorGoToTopRight.removeAllListeners();
            objectAnimatorGoToTopRight.cancel();
        }
        if (objectAnimatorGoToBottomRight != null) {
            objectAnimatorGoToBottomRight.removeAllListeners();
            objectAnimatorGoToBottomRight.cancel();
        }
        if (objectAnimatorGoToBottomLeft != null) {
            objectAnimatorGoToBottomLeft.removeAllListeners();
            objectAnimatorGoToBottomLeft.cancel();
        }
        if (objectAnimatorGoToTopLeft != null) {
            objectAnimatorGoToTopLeft.removeAllListeners();
            objectAnimatorGoToTopLeft.cancel();
        }
        if (animatorSetGoToTopLeft != null) {
            animatorSetGoToTopLeft.removeAllListeners();
            animatorSetGoToTopLeft.cancel();
        }
        if (animatorSetGoToCenter != null) {
            animatorSetGoToCenter.removeAllListeners();
            animatorSetGoToCenter.cancel();
        }
        if (animatorSetGoToTopLeftC != null) {
            animatorSetGoToTopLeftC.removeAllListeners();
            animatorSetGoToTopLeftC.cancel();
        }
        if (objectAnimatorGoToBottomLeftC != null) {
            objectAnimatorGoToBottomLeftC.removeAllListeners();
            objectAnimatorGoToBottomLeftC.cancel();
        }
        if (objectAnimatorGoToBottomRightC != null) {
            objectAnimatorGoToBottomRightC.removeAllListeners();
            objectAnimatorGoToBottomRightC.cancel();
        }
        if (objectAnimatorGoToTopRightC != null) {
            objectAnimatorGoToTopRightC.removeAllListeners();
            objectAnimatorGoToTopRightC.cancel();
        }
        if (objectAnimatorGoToTopLeftC != null) {
            objectAnimatorGoToTopLeftC.removeAllListeners();
            objectAnimatorGoToTopLeftC.cancel();
        }
        if (animatorSetGoToCenterC != null) {
            animatorSetGoToCenterC.removeAllListeners();
            animatorSetGoToCenterC.cancel();
        }
        if (animatorSetShowC != null) {
            animatorSetShowC.removeAllListeners();
            animatorSetShowC.cancel();
        }
        if (animatorSetC != null) {
            animatorSetC.removeAllListeners();
            animatorSetC.cancel();
        }
    }

}
