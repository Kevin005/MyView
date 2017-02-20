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

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.adapter.GalleryItemAdapter;
import com.view.kevin.myview.activity.view.GalleryCoverFlow;

import java.util.ArrayList;

/**
 * this is add
 */
public class Main2Activity extends AppCompatActivity {
    private GalleryCoverFlow gallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery = (GalleryCoverFlow) findViewById(R.id.gallery);
        gallery.setUnselectedScale(0.75f);
        gallery.setUnselectedAlpha(0.75f);
        gallery.setSpacing(20);
        gallery.setGravity(Gravity.CENTER_VERTICAL);
        //设置图片适配器
        gallery.setAdapter(new GalleryItemAdapter(this));
        gallery.setSelection(2);
        //设置监听器
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(Main2Activity.this, "点击了第" + arg2 + "张图片", Toast.LENGTH_LONG).show();
            }
        });

    }

}

