package com.spriteapp.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-";
    View mAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimationView = findViewById(R.id.animation_view);
    }

    public void doAnimation(View view) {
        //动画最低支持Api21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        int width = mAnimationView.getWidth();
        int height = mAnimationView.getHeight();
        //指定圆心位于view右上角
        int centerX = width;
        int centerY = 0;
        //半径为对角线长度
        int radius = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        if (mAnimationView.isShown()) {
            //消失动画
            Animator dismissAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, radius, 0);
            dismissAnimation.setDuration(1000);
            dismissAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimationView.setVisibility(View.INVISIBLE);
                }
            });
            dismissAnimation.start();
        } else {
            //显示动画
            Animator showAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, 0, radius);
            showAnimation.setDuration(1000);
            mAnimationView.setVisibility(View.VISIBLE);
            showAnimation.start();
        }
    }

}
