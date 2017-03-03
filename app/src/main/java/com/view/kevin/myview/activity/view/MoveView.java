package com.view.kevin.myview.activity.view;

/**
 * Created by Administrator on 2017/3/3.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.view.kevin.myview.R;

public class MoveView extends SurfaceView implements Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private int x = 100, y = 100;
    private Thread th;
    private Bitmap bmp;
    private Bitmap bmps[] = new Bitmap[12];
    private int curFrame = 0;
    int frame;
    private Bitmap bg;

    public MoveView(Context context) {
        super(context);
        // TODO 自动生成的构造函数存根
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        frame = R.drawable.setting;
        for (int i = 0; i < 12; i++) {
            bmps[i] = BitmapFactory.decodeResource(getResources(), frame);
            frame++;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO 自动生成的方法存根
        // myDraw();
        th = new Thread(this);
        th.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO 自动生成的方法存根

    }

    public void myDraw() {
        Canvas canvas = sfh.lockCanvas();
        // 绘制背景
        canvas.drawBitmap(bg, 0, 0, paint);
        // 绘制人物
        canvas.drawBitmap(bmps[curFrame], this.getWidth() / 2, this.getHeight() - 450, paint);
        sfh.unlockCanvasAndPost(canvas);
    }

    public void logic() {
        if (curFrame > 10) {
            curFrame = 0;
        } else {
            curFrame++;
        }
    }

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        while (true) {
            myDraw();
            logic();
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            System.out.println("thread running...");
        }
    }

}
