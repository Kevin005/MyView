package com.view.kevin.myview.activity.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 用DownloadManager来实现下载课程文件
 */
public class LessonDownloader {
    private String TAG = LessonDownloader.class.getName();
    private static LessonDownloader lessonDownloader;
    private Context context;
    private long downloadId;
    private BroadcastReceiver downLoadBroadcast;
    private DownloadManager downloadManager;
    private String download_lesson_uri = Environment.getExternalStorageDirectory().getPath() + "/.yqtec" + "/textbook/lesson.final";

    private LessonDownloader(Context context) {
        this.context = context;
    }

    public static LessonDownloader getInstant(Context context) {
        if (lessonDownloader == null) {
            lessonDownloader = new LessonDownloader(context);
        }
        return lessonDownloader;
    }

    /**
     * 下载课程文件
     */
    public void downloadLesson(String url) {
        if (TextUtils.isEmpty(url)) return;
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        /**设置用于下载时的网络状态*/
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        /**设置通知栏是否可见*/
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        /**设置漫游状态下是否可以下载*/
        request.setAllowedOverRoaming(false);
        /**如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，
         我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true.*/
        request.setVisibleInDownloadsUi(true);
        /**设置文件保存路径*/
        request.setDestinationUri(Uri.parse("file://" + download_lesson_uri));
        /**将下载请求放入队列， return下载任务的ID*/
        downloadId = downloadManager.enqueue(request);
        registerBroadcast();
    }

    /**
     * 注册完成广播
     */
    private void registerBroadcast() {
        /**注册service 广播 1.任务完成时 2.进行中的任务被点击*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        context.registerReceiver(downLoadBroadcast = new DownLoadBroadcast(), intentFilter);
    }

    /**
     * 注销完成广播
     */
    public void unregisterBroadcast() {
        if (downLoadBroadcast != null) {
            context.unregisterReceiver(downLoadBroadcast);
            downLoadBroadcast = null;
        }
    }

    /**
     * 接受下载完成广播
     */
    private class DownLoadBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            switch (intent.getAction()) {
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    if (downloadId == downId && downId != -1 && downloadManager != null) {
                        Uri downIdUri = downloadManager.getUriForDownloadedFile(downloadId);
                        if (downIdUri != null) {
                            Log.d(TAG, "广播监听下载完成，APK存储路径为 ：" + downIdUri.getPath());
                            downloadStatus.downloadComplete();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface DownloadStatus {
        void downloadComplete();
    }

    private DownloadStatus downloadStatus;

    public void setDownloadComplete(DownloadStatus downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

}