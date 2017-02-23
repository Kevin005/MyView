package com.view.kevin.myview.activity.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.adapter.GalleryItemAdapter;
import com.view.kevin.myview.activity.view.GalleryCoverFlow;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
    }

    /**
     * 走廊动画
     */
    private void grlleryView() {
        GalleryCoverFlow mGallery = (GalleryCoverFlow) findViewById(R.id.gallery_flow);
        mGallery.setUnselectedScale(0.75f);
        mGallery.setUnselectedAlpha(0.75f);
        mGallery.setSpacing(20);
        mGallery.setGravity(Gravity.CENTER_VERTICAL);
//        mGallery.setTouchViewPager(pager);
        mGallery.setAdapter(new GalleryItemAdapter(CommonActivity.this));
        mGallery.setSelection(2);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO arg1 is current view
            }
        });
    }
}
