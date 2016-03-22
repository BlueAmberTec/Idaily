package com.liuguangqiang.idaily.ui.act;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.liuguangqiang.idaily.R;
import com.liuguangqiang.idaily.ui.widget.RevealBackgroundView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OtherActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @Bind(R.id.iv_share)
    ImageView ivTest;

    @Bind(R.id.rl_background)
    RevealBackgroundView revealBackgroundView;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, OtherActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void onBackPressed() {
        revealBackgroundView.back();
    }

    int[] startingLocation;

    public void initViews() {
        ivTest.setVisibility(View.GONE);

        startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
        revealBackgroundView.setOnStateChangeListener(this);
        revealBackgroundView.startFromLocation(startingLocation);
        revealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                revealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                revealBackgroundView.startFromLocation(startingLocation);
                return true;
            }
        });

//        ViewAnimationUtils.createCircularReveal()

    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.BACK_FINISHED == state) {
            finish();
            overridePendingTransition(0, 0);
        }
    }

}
