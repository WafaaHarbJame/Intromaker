package com.kinetic.sh.Ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SoftInputSurfaceActivity extends AppCompatActivity {
    private static final int BIT_RATE = 40000000;
    private static final int FRAMES_PER_SECOND = 30;
    private static int HEIGHT = 1080;
    private static final int IFRAME_INTERVAL = 30;
    private static final String MIME_TYPE = "video/avc";
    private static final String TAG = "SoftInputSurface";
    private static final boolean VERBOSE = true;
    private static int WIDTH = 1920;

    public LottieAnimationView animationView;

    public LottieAnimationView bg;

    public Context context;

    public FrameLayout frameLayout;
    InterfaceSoftInputSurfaceActivity interfaceSoftInputSurfaceActivity;
    private MediaCodec.BufferInfo mBufferInfo;
    private MediaCodec mEncoder;
    private long mFakePts;
    private Surface mInputSurface;
    private MediaMuxer mMuxer;
    private boolean mMuxerStarted;
    private int mTrackIndex;

    public MediaPlayer mediaPlayer;

    public int progress;

    public interface InterfaceSoftInputSurfaceActivity {
        void onProgressChange(float f);

        void onRendered(File file);
    }

    public void setInterfaceSoftInputSurfaceActivity(InterfaceSoftInputSurfaceActivity interfaceSoftInputSurfaceActivity2) {
        this.interfaceSoftInputSurfaceActivity = interfaceSoftInputSurfaceActivity2;
    }

    public SoftInputSurfaceActivity(Context context2, LottieAnimationView lottieAnimationView, FrameLayout frameLayout2, LottieAnimationView lottieAnimationView2, MediaPlayer mediaPlayer2) {
        this.animationView = lottieAnimationView;
        this.frameLayout = frameLayout2;
        this.bg = lottieAnimationView2;
        this.context = context2;
        this.mediaPlayer = mediaPlayer2;
    }


    public void init(File file) {
        file.mkdirs();
        Log.i(TAG, "Generating movie...");
        try {
            Toast.makeText(this.context, "Rendering has been started", Toast.LENGTH_LONG).show();
            generateMovie(new File(file, "soft-input-surface.mp4"));
            Log.i(TAG, "Movie generation complete");
        } catch (Exception e) {
            Log.e(TAG, "Movie generation FAILED", e);
        }
    }

    private void generateMovie(final File file) throws IOException {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.frameLayout.getLayoutParams();
        layoutParams.width = 2000;
        this.frameLayout.setLayoutParams(layoutParams);
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.context, Uri.parse(this.context.getExternalFilesDir((String) null).getAbsolutePath() + "/m.mp4"));
        try {
            prepareEncoder(file);
            final ImageView imageView = (ImageView) ((AppCompatActivity) this.context).findViewById(R.id.main_image_view);
            new Thread(new Runnable() {
                int count = 0;
                int videoFrameCount = 0;

                public void run() {
                    SoftInputSurfaceActivity.this.drainEncoder(false);
                    SoftInputSurfaceActivity.this.animationView.setFrame(this.count);
                    SoftInputSurfaceActivity.this.mediaPlayer.stop();
                    Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime((long) this.videoFrameCount, 3);
                    this.videoFrameCount += 33333;
                    imageView.setImageBitmap(frameAtTime);
                    SoftInputSurfaceActivity.this.bg.setFrame(this.count);
                    SoftInputSurfaceActivity softInputSurfaceActivity = SoftInputSurfaceActivity.this;
                    softInputSurfaceActivity.generateFrame(softInputSurfaceActivity.renderer());
                    int i = this.count;
                    this.count = i + 1;
                    if (((float) i) < SoftInputSurfaceActivity.this.animationView.getMaxFrame()) {
                        SoftInputSurfaceActivity.this.progress = this.count;
                        SoftInputSurfaceActivity softInputSurfaceActivity2 = SoftInputSurfaceActivity.this;
                        float access$600 = softInputSurfaceActivity2.calcFramePercentage(this.count, (int) softInputSurfaceActivity2.animationView.getMaxFrame());
                        if (SoftInputSurfaceActivity.this.interfaceSoftInputSurfaceActivity != null) {
                            SoftInputSurfaceActivity.this.interfaceSoftInputSurfaceActivity.onProgressChange(access$600);
                            return;
                        }
                        return;
                    }
                    SoftInputSurfaceActivity.this.drainEncoder(true);
                    SoftInputSurfaceActivity.this.releaseEncoder();
                    mediaMetadataRetriever.release();
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) SoftInputSurfaceActivity.this.frameLayout.getLayoutParams();
                    layoutParams.width = -1;
                    SoftInputSurfaceActivity.this.frameLayout.setLayoutParams(layoutParams);
                    if (SoftInputSurfaceActivity.this.interfaceSoftInputSurfaceActivity != null) {
                        SoftInputSurfaceActivity.this.interfaceSoftInputSurfaceActivity.onRendered(file);
                    }
                    Toast.makeText(SoftInputSurfaceActivity.this.context, "Video path: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareEncoder(File file) throws IOException {
        this.mBufferInfo = new MediaCodec.BufferInfo();
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", WIDTH, HEIGHT);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", BIT_RATE);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setInteger("i-frame-interval", 30);
        Log.d(TAG, "format: " + createVideoFormat);
        MediaCodec createEncoderByType = MediaCodec.createEncoderByType("video/avc");
        this.mEncoder = createEncoderByType;
        createEncoderByType.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mInputSurface = this.mEncoder.createInputSurface();
        this.mEncoder.start();
        Log.d(TAG, "output will go to " + file);
        this.mMuxer = new MediaMuxer(file.toString(), 0);
        this.mTrackIndex = -1;
        this.mMuxerStarted = false;
    }


    public void releaseEncoder() {
        Log.d(TAG, "releasing encoder objects");
        MediaCodec mediaCodec = this.mEncoder;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mEncoder.release();
            this.mEncoder = null;
        }
        Surface surface = this.mInputSurface;
        if (surface != null) {
            surface.release();
            this.mInputSurface = null;
        }
        MediaMuxer mediaMuxer = this.mMuxer;
        if (mediaMuxer != null) {
            mediaMuxer.stop();
            this.mMuxer.release();
            this.mMuxer = null;
        }
    }


    public void drainEncoder(boolean z) {
        Log.d(TAG, "drainEncoder(" + z + ")");
        if (z) {
            Log.d(TAG, "sending EOS to encoder");
            this.mEncoder.signalEndOfInputStream();
        }
        ByteBuffer[] outputBuffers = this.mEncoder.getOutputBuffers();
        while (true) {
            int dequeueOutputBuffer = this.mEncoder.dequeueOutputBuffer(this.mBufferInfo, 10000);
            if (dequeueOutputBuffer == -1) {
                if (z) {
                    Log.d(TAG, "no output available, spinning to await EOS");
                } else {
                    return;
                }
            } else if (dequeueOutputBuffer == -3) {
                outputBuffers = this.mEncoder.getOutputBuffers();
            } else if (dequeueOutputBuffer == -2) {
                if (!this.mMuxerStarted) {
                    MediaFormat outputFormat = this.mEncoder.getOutputFormat();
                    Log.d(TAG, "encoder output format changed: " + outputFormat);
                    this.mTrackIndex = this.mMuxer.addTrack(outputFormat);
                    this.mMuxer.start();
                    this.mMuxerStarted = true;
                } else {
                    throw new RuntimeException("format changed twice");
                }
            } else if (dequeueOutputBuffer < 0) {
                Log.w(TAG, "unexpected result from encoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
            } else {
                ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                if (byteBuffer != null) {
                    if ((this.mBufferInfo.flags & 2) != 0) {
                        Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
                        this.mBufferInfo.size = 0;
                    }
                    if (this.mBufferInfo.size != 0) {
                        if (this.mMuxerStarted) {
                            byteBuffer.position(this.mBufferInfo.offset);
                            byteBuffer.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                            this.mBufferInfo.presentationTimeUs = this.mFakePts;
                            this.mFakePts += 33333;
                            this.mMuxer.writeSampleData(this.mTrackIndex, byteBuffer, this.mBufferInfo);
                            Log.d(TAG, "sent " + this.mBufferInfo.size + " bytes to muxer");
                        } else {
                            throw new RuntimeException("muxer hasn't started");
                        }
                    }
                    this.mEncoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((this.mBufferInfo.flags & 4) != 0) {
                        if (!z) {
                            Log.w(TAG, "reached end of stream unexpectedly");
                            return;
                        } else {
                            Log.d(TAG, "end of stream reached");
                            return;
                        }
                    }
                } else {
                    throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                }
            }
        }
    }


    public void generateFrame(Bitmap bitmap) {
        Canvas lockCanvas = this.mInputSurface.lockCanvas((Rect) null);
        try {
            lockCanvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        } finally {
            this.mInputSurface.unlockCanvasAndPost(lockCanvas);
        }
    }

    public Bitmap renderer() {
        FrameLayout frameLayout2 = this.frameLayout;
        frameLayout2.clearFocus();
        frameLayout2.setPressed(false);
        boolean willNotCacheDrawing = frameLayout2.willNotCacheDrawing();
        frameLayout2.setWillNotCacheDrawing(false);
        int drawingCacheBackgroundColor = frameLayout2.getDrawingCacheBackgroundColor();
        frameLayout2.setBackgroundColor(0);
        if (drawingCacheBackgroundColor != 0) {
            frameLayout2.destroyDrawingCache();
        }
        frameLayout2.buildDrawingCache();
        Bitmap drawingCache = frameLayout2.getDrawingCache();
        if (drawingCache == null) {
            Log.e(TAG, "failed getViewBitmap(" + frameLayout2 + ")", new RuntimeException());
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawingCache);
        frameLayout2.destroyDrawingCache();
        frameLayout2.setWillNotCacheDrawing(willNotCacheDrawing);
        frameLayout2.setDrawingCacheBackgroundColor(drawingCacheBackgroundColor);
        return createBitmap;
    }


    public float calcFramePercentage(int i, int i2) {
        if (i2 == 0) {
            return 0.0f;
        }
        return (float) ((i * 100) / i2);
    }
}
