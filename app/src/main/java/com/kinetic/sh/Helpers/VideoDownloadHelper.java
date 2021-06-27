package com.kinetic.sh.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.kinetic.sh.Qutils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoDownloadHelper implements OnDownloadListener {
    private static final String TAG = "VideoDownloadHelper";
    private final Context context;
    private String currentFileName;
    private final String dlUrl;
    private long downloadId;
    private boolean downloading;
    private final String folderPath;

    public VideoDownloadListener videoDownloadListener;

    public interface VideoDownloadListener {
        void onDone();

        void onError(Error error);

        void onProgress(int i, String str);
    }

    public VideoDownloadHelper(Context context2, String str, String str2) {
        this.context = context2;
        this.dlUrl = str;
        this.folderPath = str2;
    }

    public void setVideoDownloadListener(VideoDownloadListener videoDownloadListener2) {
        this.videoDownloadListener = videoDownloadListener2;
    }

    public void init() {
        String str = this.context.getExternalFilesDir(null).getAbsolutePath() + this.folderPath;
        new File(str).mkdirs();
        Log.i(TAG, "init: " + str);
        this.currentFileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".mp4";
        PRDownloader.initialize(this.context);
        PRDownloader.initialize(this.context, PRDownloaderConfig.newBuilder().setReadTimeout(30000).setConnectTimeout(30000).build());
        pr();
    }

    private void pr() {
        this.downloadId = PRDownloader.download(this.dlUrl, this.context.getExternalFilesDir(null).getAbsolutePath() + this.folderPath, this.currentFileName).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
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
                Log.i(VideoDownloadHelper.TAG, "onProgress: " + progress.totalBytes + "  " + progress.currentBytes);
                int i = (int) ((progress.currentBytes * 100) / progress.totalBytes);
                String progressDisplayLine = Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes);
                if (VideoDownloadHelper.this.videoDownloadListener != null) {
                    VideoDownloadHelper.this.videoDownloadListener.onProgress(i, progressDisplayLine);
                }
            }
        }).start(this);
    }

    public String getFileName() {
        return this.context.getExternalFilesDir(null).getAbsolutePath() + this.folderPath + "/" + this.currentFileName;
    }

    public void cancel() {
        this.downloading = false;
        PRDownloader.cancelAll();
    }

    public void retry() {
        cancel();
        Log.i(TAG, "resume: " + this.downloadId + " -- " + ((int) this.downloadId));
        pr();
        this.downloading = true;
    }

    public void onDownloadComplete() {
        VideoDownloadListener videoDownloadListener2 = this.videoDownloadListener;
        if (videoDownloadListener2 != null) {
            videoDownloadListener2.onDone();
        }
    }

    public void onError(Error error) {
        VideoDownloadListener videoDownloadListener2 = this.videoDownloadListener;
        if (videoDownloadListener2 != null) {
            videoDownloadListener2.onError(error);
        }
        Toast.makeText(this.context, "Something went wrong while downloading video", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onError: " + new File(getFileName()).isFile());
        Log.e(TAG, "onError: " + error.getConnectionException());
    }
}
