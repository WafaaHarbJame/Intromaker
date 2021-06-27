package com.kinetic.sh.RenderEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;
import com.kinetic.sh.R;
import com.kinetic.sh.Ui.MainActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FFmpegRender extends FFmpegRenderEngine {
    public static final int RENDER_STATIC_BACKGROUND = 0;
    public static final int RENDER_VIDEO_BACKGROUND = 1;
    public static int START_RENDER_VIDEO_AT = 200;
    private static final String TAG = "FFmpegRender";
    public int RENDER_TYPE;
    private int RES_CURRENT;
    public int TOTAL_FRAME_IN_EFFECT = 299;
    private int TOTAL_FRAME_IN_FFMPEG = 100;
    public LottieAnimationView animationView;
    public LottieAnimationView animationViewBg;
    public float backgroundPR = 0.0f;
    private File blackLayer;
    public Context context;
    private View darkenLayer;
    public FFmpegRenderListener fFmpegRenderListener;
    private String fileName;
    public float foregroundPR = 0.0f;
    public FrameLayout frameLayout;
    public ImageView imageView;
    private MainActivity mainActivity;
    public String output;
    private final float progress = 0.0f;

    public interface FFmpegRenderListener {
        void onProgressChange(float f);

        void onRendered(File file);
    }

    public void setfFmpegRenderListener(FFmpegRenderListener fFmpegRenderListener2) {
        this.fFmpegRenderListener = fFmpegRenderListener2;
    }

    public FFmpegRender(Context context2, int i, int i2) {
        this.context = context2;
        this.RENDER_TYPE = i;
        if (i2 == 540) {
            this.RES_CURRENT = Resolutions.RW_540;
        } else if (i2 == 720) {
            this.RES_CURRENT = Resolutions.RW_720;
        } else if (i2 == 1080) {
            this.RES_CURRENT = Resolutions.RW_1080;
        }
        init();
    }

    private void init() {
        MainActivity mainActivity2 = (MainActivity) this.context;
        this.mainActivity = mainActivity2;
        this.animationView = mainActivity2.findViewById(R.id.animation_view);
        this.frameLayout = this.mainActivity.findViewById(R.id.frameLayout2);
        this.imageView = this.mainActivity.findViewById(R.id.main_image_view);
        this.animationViewBg = this.mainActivity.findViewById(R.id.animation_view_bg);
        this.darkenLayer = this.mainActivity.findViewById(R.id.darken_layer);
        this.TOTAL_FRAME_IN_EFFECT = (int) this.animationView.getMaxFrame();
        this.TOTAL_FRAME_IN_FFMPEG = (int) this.animationView.getMaxFrame();
        START_RENDER_VIDEO_AT = this.TOTAL_FRAME_IN_EFFECT - 1;
        this.blackLayer = genBlackLayer(this.context);
        this.fileName = new SimpleDateFormat("'intromaker_'yyMMddHHmmss'.mp4'").format(new Date());
        this.output = this.context.getExternalMediaDirs()[0].getAbsolutePath() + "/" + this.fileName;
        if (this.mainActivity.currentVideoPath != null) {
            this.RENDER_TYPE = 1;
        } else {
            this.RENDER_TYPE = 0;
        }
        if (this.RENDER_TYPE == 1) {
            this.frameLayout.setBackgroundColor(0);
            this.imageView.setVisibility(View.GONE);
        }
        genImagesFromVideo();
    }


    public float updateProgress() {
        Log.i(TAG, "getProgress: " + this.foregroundPR + this.backgroundPR);
        return this.foregroundPR + this.backgroundPR;
    }

    private void genImagesFromVideo() {
        Log.i(TAG, "genImagesFromVideo: " + START_RENDER_VIDEO_AT);
        final Handler handler = new Handler();
        setFrameResolution(this.RES_CURRENT, this.frameLayout);
        handler.post(new Runnable() {
            int count = 0;

            public void run() {
                FFmpegRender.this.animationView.setFrame(this.count);
                FFmpegRender.this.animationViewBg.setFrame(this.count);
                if (this.count == FFmpegRender.START_RENDER_VIDEO_AT) {
                    if (FFmpegRender.this.RENDER_TYPE == 0) {
                        FFmpegRender.this.renderStaticBackground();
                    } else {
                        FFmpegRender.this.renderVideoBackground();
                        FFmpegRender.this.imageView.setVisibility(View.VISIBLE);
                    }
                }
                FFmpegRender fFmpegRender = FFmpegRender.this;
                fFmpegRender.foregroundPR = (float) (((this.count * 100) / fFmpegRender.TOTAL_FRAME_IN_EFFECT) / 2);
                Log.i(FFmpegRender.TAG, "current_frame: " + this.count);
                if (FFmpegRender.this.fFmpegRenderListener != null) {
                    FFmpegRender.this.fFmpegRenderListener.onProgressChange(FFmpegRender.this.updateProgress());
                }
                int i = this.count;
                this.count = i + 1;
                if (((float) i) < FFmpegRender.this.animationView.getMaxFrame()) {
                    handler.postDelayed(this, 0);
                    FFmpegRender fFmpegRender2 = FFmpegRender.this;
                    fFmpegRender2.masterWorker(FFmpegRenderEngine.genBitmapFromView(fFmpegRender2.frameLayout), this.count);
                    return;
                }
                FFmpegRender fFmpegRender3 = FFmpegRender.this;
                fFmpegRender3.masterWorker(FFmpegRenderEngine.genBitmapFromView(fFmpegRender3.frameLayout), this.count);
                FFmpegRender fFmpegRender4 = FFmpegRender.this;
                fFmpegRender4.setFrameResolution(-1, fFmpegRender4.frameLayout);
            }
        });
    }

    public void masterWorker(final Bitmap bitmap, final int i) {
        new Thread() {
            public void run() {
                try {
                    FFmpegRenderEngine.saveImage(bitmap, "chunk" + i + ".png");
                    String sb = "run: from master worker" + i;
                    Log.i(FFmpegRender.TAG, sb);
                } catch (Exception unused) {
                    Log.i("---", "Exception in thread");
                }
            }
        }.start();
    }


    public void renderStaticBackground() {
        excFFmpeg(new String[]{"-y", "-r", "30", "-i", this.context.getExternalFilesDir(null).getAbsolutePath() + "/" + this.context.getString(R.string.tf_filepath) + "/chunks/chunk%1d.png", "-t", "10", "-vcodec", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", this.output});
    }


    public void renderVideoBackground() {
        String str = this.mainActivity.currentVideoPath;
        String valueOf = String.valueOf(this.darkenLayer.getAlpha());
        Log.i(TAG, "DarkenLayerAlpha: " + this.darkenLayer.getAlpha());
        excFFmpeg(new String[]{"-y", "-r", "30", "-i", this.context.getExternalFilesDir(null).getAbsolutePath() + "/" + this.context.getString(R.string.tf_filepath) + "/chunks/chunk%1d.png", "-i", this.blackLayer.getAbsolutePath(), "-i", str, "-filter_complex", "[2][0]scale2ref[b][a];[1][a]scale2ref[c][a];[c] format=rgba,colorchannelmixer=aa=" + valueOf + "[c];[b][c]overlay[bg];[bg][a]overlay", "-vcodec", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", this.output});
    }

    private void excFFmpeg(final String[] strArr) {
        new Thread(new Runnable() {
            public void run() {
                Config.enableStatisticsCallback(new StatisticsCallback() {
                    public void apply(Statistics statistics) {
                        FFmpegRender.this._updateProgress(statistics.getVideoFrameNumber());
                        Log.d(FFmpegRender.TAG, String.format("frame: %d, time: %d", statistics.getVideoFrameNumber(), statistics.getTime()));
                    }
                });
                FFmpeg.execute(strArr);
                Log.i(FFmpegRender.TAG, "renderVideoBackground: FFmpeg initiated");
                int lastReturnCode = FFmpeg.getLastReturnCode();
                if (lastReturnCode == 0) {
                    ((MainActivity) FFmpegRender.this.context).runOnUiThread(new Runnable() {
                        public void run() {
                            if (FFmpegRender.this.fFmpegRenderListener != null) {
                                FFmpegRender.this.fFmpegRenderListener.onRendered(new File(FFmpegRender.this.output));
                            }
                        }
                    });
                    Log.i(Config.TAG, "Command execution completed successfully.");
                } else if (lastReturnCode == 255) {
                    Log.i(Config.TAG, "Command execution cancelled by user.");
                } else {
                    Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", lastReturnCode));
                }
            }
        }).start();
    }


    public void _updateProgress(int i) {
        this.backgroundPR = (float) (((i * 100) / this.TOTAL_FRAME_IN_FFMPEG) / 2);
        Log.i(TAG, "apply:  " + updateProgress());
        FFmpegRenderListener fFmpegRenderListener2 = this.fFmpegRenderListener;
        if (fFmpegRenderListener2 != null) {
            fFmpegRenderListener2.onProgressChange(updateProgress());
        }
    }
}
