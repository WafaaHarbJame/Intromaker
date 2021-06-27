package com.kinetic.sh.RenderEngine;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.Ui.MainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Renderer extends RenderEngine {
    public static int[] RES_FHD = {1080, 1920};
    public static int[] RES_HD = {720, 1280};
    public static int[] RES_SD = {540, 960};
    private static final String TAG = "Renderer";
    private int[] RES_CURRENT;
    public LottieAnimationView animationView;
    public LottieAnimationView bgAnimationView;
    public Context context;
    public boolean forceStop = false;
    public FrameLayout frameLayout;
    public ImageView imageView;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;
    public int process;
    private RenderEngine.RenderMode renderMode;
    private final String videoPath;

    public void setRenderMode(RenderEngine.RenderMode renderMode2) {
        this.renderMode = renderMode2;
    }

    public Renderer(Context context2, FrameLayout frameLayout2, ImageView imageView2, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, String str, int i) {
        this.context = context2;
        this.frameLayout = frameLayout2;
        this.imageView = imageView2;
        this.bgAnimationView = lottieAnimationView;
        this.animationView = lottieAnimationView2;
        this.videoPath = str;
        if (i == 540) {
            this.RES_CURRENT = RES_SD;
        } else if (i == 720) {
            this.RES_CURRENT = RES_HD;
        } else if (i == 1080) {
            this.RES_CURRENT = RES_FHD;
        }
    }

    public void setSource(File file) {
        this.forceStop = false;
        init(file);
    }

    public void init(final File file) {
        Log.i(TAG, "Generating movie...");
        this.mainActivity = (MainActivity) this.context;
        setResolution(this.RES_CURRENT);
        final String format = new SimpleDateFormat("'intromaker_'yyMMddHHmmss'.mp4'", Locale.getDefault()).format(new Date());
        if (this.videoPath != null) {
            Log.i(TAG, "init: have video path");
            extractFramesFromVideo(this.context, this.videoPath);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        Renderer.this.generateMovieWithVideoBackground(new File(file, format));
                        Log.i(Renderer.TAG, "Movie generation complete");
                    } catch (Exception e) {
                        if (Renderer.this.interfaceRenderEngine != null) {
                            Renderer.this.interfaceRenderEngine.onRenderError(e);
                        }
                    }
                }
            }, 5000);
            return;
        }
        try {
            generateMovie(new File(file, format));
            Log.i(TAG, "Movie generation complete");
        } catch (Exception e) {
            if (this.interfaceRenderEngine != null) {
                this.interfaceRenderEngine.onRenderError(e);
            }
        }
    }

    public void generateMovie(final File file) {
        setFrameResolution(this.RES_CURRENT[1]);
        try {
            prepareEncoder(file);
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                int count = 0;

                public void run() {
                    Renderer.this.drainEncoder(false);
                    Renderer.this.animationView.setFrame(this.count);
                    Renderer.this.bgAnimationView.setFrame(this.count);
                    Renderer renderer = Renderer.this;
                    renderer.generateFrame(renderer.renderer(renderer.frameLayout));
                    if (!Renderer.this.forceStop) {
                        int i = this.count;
                        this.count = i + 1;
                        if (((float) i) < Renderer.this.animationView.getMaxFrame()) {
                            handler.postDelayed(this, 0);
                            Renderer renderer2 = Renderer.this;
                            float calcFramePercentage = renderer2.calcFramePercentage(this.count, (int) renderer2.animationView.getMaxFrame());
                            if (Renderer.this.interfaceRenderEngine != null) {
                                Renderer.this.interfaceRenderEngine.onProgressChange(calcFramePercentage);
                                return;
                            }
                            return;
                        }
                        Renderer.this.drainEncoder(true);
                        Renderer.this.releaseEncoder();
                        Renderer.this.setFrameResolution(-1);
                        if (Renderer.this.interfaceRenderEngine != null) {
                            Renderer.this.interfaceRenderEngine.onRendered(file);
                        }
                        Context access$600 = Renderer.this.context;
                        Toast.makeText(access$600, "Video path: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFrameResolution(int w) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.frameLayout.getLayoutParams();
        layoutParams.width = w;
        layoutParams.dimensionRatio = "H,16:9";
        layoutParams.height = w * 9 / 16;
        this.frameLayout.setLayoutParams(layoutParams);
        this.frameLayout.requestLayout();
    }


    public void generateMovieWithVideoBackground(final File file) throws IOException {
        setFrameResolution(this.RES_CURRENT[1]);
        this.imageView.setVisibility(View.VISIBLE);
        Log.i(TAG, "generateMovieWithVideoBackground: " + file.getAbsolutePath());
        prepareEncoder(file);
        final String pathStr = this.context.getExternalFilesDir(null).getAbsolutePath() + "/temp_intro_maker";
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int count = 0;

            public void run() {
                Renderer.this.drainEncoder(false);
                String str = pathStr + "/temp_img" + this.count + ".jpg";
                if (new File(str).isFile()) {
                    Renderer.this.imageView.setImageBitmap(BitmapFactory.decodeFile(str));
                }
                Renderer.this.animationView.setFrame(this.count);
                Renderer.this.bgAnimationView.setFrame(this.count);
                Renderer renderer = Renderer.this;
                renderer.generateFrame(renderer.renderer(renderer.frameLayout));
                if (!Renderer.this.forceStop) {
                    int i = this.count;
                    this.count = i + 1;
                    if (((float) i) < Renderer.this.animationView.getMaxFrame()) {
                        Renderer.this.process = this.count;
                        handler.postDelayed(this, 0);
                        Renderer renderer2 = Renderer.this;
                        float calcFramePercentage = renderer2.calcFramePercentage(this.count, (int) renderer2.animationView.getMaxFrame());
                        if (Renderer.this.interfaceRenderEngine != null) {
                            Renderer.this.interfaceRenderEngine.onProgressChange(calcFramePercentage);
                            return;
                        }
                        return;
                    }
                    Renderer.this.imageView.setVisibility(View.INVISIBLE);
                    Renderer.this.deleteAllFilesFromDir(new File(str));
                    Renderer.this.drainEncoder(true);
                    Renderer.this.releaseEncoder();
                    Renderer.this.setFrameResolution(-1);
                    if (Renderer.this.interfaceRenderEngine != null) {
                        Renderer.this.interfaceRenderEngine.onRendered(file);
                    }
                    Toast.makeText(Renderer.this.context, "Video path: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void stopRender() {
        this.forceStop = true;
        setFrameResolution(-1);
        if (this.videoPath != null) {
            this.imageView.setVisibility(View.INVISIBLE);
        }
    }
}
