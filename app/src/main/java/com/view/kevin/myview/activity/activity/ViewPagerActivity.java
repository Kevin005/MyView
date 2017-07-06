package com.future.myapplication11;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
左右无限滑动
*/
public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    static final int arrays[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private List<ImageView> views;
    private int currentPage = 0;
    private ImageView imageView;

    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
    }

    private void initWidget() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        views = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            views.add(imageView);
        }

        initImageData();

        viewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(this);
    }

    private void initImageData() {
        for (int i = 0; i < 3; i++) {
            imageView = views.get(i);
            if (i == 0) {
                imageView.setImageResource(arrays[arrays.length - 1]);
            } else {
                imageView.setImageResource(arrays[i - 1]);
            }
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            imageView = views.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {
        System.out.println("--onPageScrollStateChanged--state--:" + state);
        switch (state) {
            // 在滚动完成后
            case ViewPager.SCROLL_STATE_DRAGGING:
                int currentItem = viewPager.getCurrentItem();

                System.out.println("--currentItem--00--:" + currentItem);
                System.out.println("--currentPage--00--:" + currentPage);
                if (viewPager.getCurrentItem() == 1) {
                    // 如果位置没有变终止循环
                    break;
                }

                if (viewPager.getCurrentItem() > 1) {
                    currentPage++;
                } else {
                    currentPage--;
                }

                System.out.println("--currentPage--11--:" + currentPage);
                if (currentPage == arrays.length) {
                    currentPage = 0;
                }

                if (currentPage == -1) {
                    currentPage = arrays.length - 1;
                }

                System.out.println("--currentPage--22--:" + currentPage);

                if (currentPage == 0) {
                    views.get(0).setImageResource(arrays[arrays.length - 1]);
                } else {
                    views.get(0).setImageResource(arrays[currentPage - 1]);
                }

                views.get(1).setImageResource(arrays[currentPage]);

                if (currentPage == arrays.length - 1) {
                    views.get(2).setImageResource(arrays[0]);
                } else {
                    views.get(2).setImageResource(arrays[currentPage + 1]);
                }

                viewPager.setCurrentItem(1, false);

                currentItem = viewPager.getCurrentItem();

                System.out.println("--currentItem--11--:" + currentItem);
                break;
        }
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
        // System.out.println("--onPageScrolled--position--:" + position);
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        System.out.println("--onPageSelected--position--:" + position);
    }
}
