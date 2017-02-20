package com.view.kevin.myview.activity.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.view.kevin.myview.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class GalleryItemAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context context;
    //图片源数组
    private Integer[] imageInteger = {
            R.mipmap.pleasure,
            R.mipmap.learn,
            R.mipmap.pet,
            R.mipmap.game,
            R.mipmap.social,
            R.mipmap.setting
    };

    public GalleryItemAdapter(Context c) {
        context = c;
//        TypedArray attr = context.obtainStyledAttributes(R.styleable.HelloGallery);
//        mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
//        attr.recycle();
    }

    // 获取图片的个数
    public int getCount() {
        return imageInteger.length;
    }

    // 获取图片在库中的位置
    public Object getItem(int position) {
        return position;
    }

    // 获取图片ID
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        // 给ImageView设置资源
        imageView.setImageResource(imageInteger[position]);
        // 设置显示比例类型
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        // 设置布局 图片120*80
        imageView.setLayoutParams(new Gallery.LayoutParams(500, 450));
//        imageView.setBackgroundResource(mGalleryItemBackground);
        return imageView;
    }
}
