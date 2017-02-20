package com.view.kevin.myview.activity.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.adapter.GalleryItemAdapter;
import com.view.kevin.myview.activity.view.GalleryCoverFlow;

import java.util.ArrayList;

/**
 * this is add
 */
public class Main2Activity extends AppCompatActivity {
    private LottieAnimationView animationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("hello-world.json");
        animationView.loop(true);

    }
}

