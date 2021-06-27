package com.kinetic.sh.Helpers;

import android.content.Context;
import android.util.Log;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.kinetic.sh.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDownloaderHelper implements OnDownloadListener {
    private static final String TAG = "ImageDownloaderHelper";
    private final Context context;
    private String dirPath;
    private int downloadId;
    private String filename;
    private final String foldername;

    public ImageDownloaderListener imageDownloaderListener;
    private final String url;

    public interface ImageDownloaderListener {
        void onDone(String str);

        void onError(String str);

        void onProgress(int i);
    }

    public ImageDownloaderHelper(Context context2, String str, String str2) {
        this.context = context2;
        this.url = str;
        this.foldername = str2;
    }

    public void setImageDownloaderListner(ImageDownloaderListener imageDownloaderListener2) {
        this.imageDownloaderListener = imageDownloaderListener2;
    }

    public void init() {
        Log.i(TAG, "init:  " + this.url);
        PRDownloader.initialize(this.context, PRDownloaderConfig.newBuilder().setReadTimeout(30000).setConnectTimeout(30000).build());
        this.dirPath = this.context.getExternalFilesDir(null) + "/" + this.context.getString(R.string.tf_filepath) + "/" + this.foldername + "/";
        new File(this.dirPath).mkdirs();
        String sb2 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        this.filename = sb2;
        this.downloadId = PRDownloader.download(this.url, this.dirPath, sb2).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
            public void onStartOrResume() {
            }
        }).setOnPauseListener(new OnPauseListener() {
            public void onPause() {
            }
        }).setOnCancelListener(new OnCancelListener() {
            public void onCancel() {
            }
        }).setOnProgressListener(new OnProgressListener() {
            public void onProgress(Progress progress) {
                int i = (int) ((progress.currentBytes * 100) / progress.totalBytes);
                Log.i(ImageDownloaderHelper.TAG, "onProgress: " + i);
                if (ImageDownloaderHelper.this.imageDownloaderListener != null) {
                    ImageDownloaderHelper.this.imageDownloaderListener.onProgress(i);
                }
            }
        }).start(this);
    }

    public void cancel() {
        PRDownloader.cancelAll();
    }

    public void onDownloadComplete() {
        Log.i(TAG, "onDownloadComplete: image has been stored in the internal storage");
        String str = this.dirPath + this.filename;
        ImageDownloaderListener imageDownloaderListener2 = this.imageDownloaderListener;
        if (imageDownloaderListener2 != null) {
            imageDownloaderListener2.onDone(str);
        }
    }

    public void onError(Error error) {
        ImageDownloaderListener imageDownloaderListener2 = this.imageDownloaderListener;
        if (imageDownloaderListener2 != null) {
            imageDownloaderListener2.onError(error.getServerErrorMessage());
        }
        Log.i(TAG, "onError: " + error.getServerErrorMessage());
    }
}
