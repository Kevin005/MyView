package com.view.kevin.myview.activity.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.view.kevin.myview.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/20.
 */

public class ViewPagerActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ViewPager mViewPager;
    private PagerTitleStrip mPagerTitleStrip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_view_pager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pagertitle);

        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
//        View view1 = mLi.inflate(R.layout.activity_view1, null);
//        View view2 = mLi.inflate(R.layout.activity_view2, null);
//        View view3 = mLi.inflate(R.layout.activity_view3, null);

        //每个页面的Title数据
        final ArrayList<View> views = new ArrayList<View>();
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);

        final ArrayList<String> titles = new ArrayList<String>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");

        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
    }
}
