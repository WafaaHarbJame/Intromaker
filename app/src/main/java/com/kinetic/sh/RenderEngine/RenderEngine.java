package com.kinetic.sh.RenderEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import android.view.Surface;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class RenderEngine extends AppCompatActivity {
    private static final int BIT_RATE = 40000000;
    private static final int FRAMES_PER_SECOND = 30;
    static int HEIGHT = 1080;
    private static final int IFRAME_INTERVAL = 30;
    private static final String MIME_TYPE = "video/avc";
    private static final String TAG = "RenderEngine";
    private static final boolean VERBOSE = true;
    static int WIDTH = 1920;
    public InterfaceRenderEngine interfaceRenderEngine;
    private MediaCodec.BufferInfo mBufferInfo;
    private MediaCodec mEncoder;
    private long mFakePts;
    private Surface mInputSurface;
    private MediaMuxer mMuxer;
    private boolean mMuxerStarted;
    private int mTrackIndex;
    private int progress;

    public interface InterfaceRenderEngine {
        void onProgressChange(float f);

        void onRenderError(Exception exc);

        void onRendered(File file);
    }

    public enum RenderMode {
        NATIVE,
        HYBRIDE
    }


    public void init(File file) {
    }

    public void setInterfaceRenderEngine(InterfaceRenderEngine interfaceRenderEngine2) {
        this.interfaceRenderEngine = interfaceRenderEngine2;
    }

    public void setResolution(int[] iArr) {
        HEIGHT = iArr[0];
        WIDTH = iArr[1];
    }

    public void prepareEncoder(File file) throws IOException {
        this.mBufferInfo = new MediaCodec.BufferInfo();
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(MIME_TYPE, WIDTH, HEIGHT);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", BIT_RATE);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setInteger("i-frame-interval", 30);
        Log.d(TAG, "format: " + createVideoFormat);
        MediaCodec createEncoderByType = MediaCodec.createEncoderByType(MIME_TYPE);
        this.mEncoder = createEncoderByType;
        createEncoderByType.configure(createVideoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        this.mInputSurface = this.mEncoder.createInputSurface();
        this.mEncoder.start();
        Log.d(TAG, "output will go to " + file);
        this.mMuxer = new MediaMuxer(file.toString(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
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
        try {
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
                            } else {
                                Log.d(TAG, "end of stream reached");
                            }
                            return;
                        }
                    } else {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                    }
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "drainEncoder: " + e.getMessage());
            InterfaceRenderEngine interfaceRenderEngine2 = this.interfaceRenderEngine;
            if (interfaceRenderEngine2 != null) {
                interfaceRenderEngine2.onRenderError(e);
            }
            e.printStackTrace();
        }
    }

    public void generateFrame(Bitmap bitmap) {
        Canvas lockCanvas = this.mInputSurface.lockCanvas(null);
        try {
            lockCanvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        } finally {
            this.mInputSurface.unlockCanvasAndPost(lockCanvas);
        }
    }

    public Bitmap renderer(View view) {
        Log.e("RENDER: ___", "w=" + view.getWidth() + "  h=" + view.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public float calcFramePercentage(int i, int i2) {
        if (i2 == 0) {
            return 0.0f;
        }
        return (float) ((i * 100) / i2);
    }

    public void extractFramesFromVideo(Context context, String str) {
        Log.i(TAG, "extractFramesFromVideo: " + str);
        String str2 = context.getExternalFilesDir(null).getAbsolutePath() + "/temp_intro_maker/";
        File file = new File(str2);
        deleteAllFilesFromDir(file);
        file.mkdirs();
        final String[] strArr = {"-t", "10", "-i", str, "-r", "30", "-qscale:v", "5", str2 + "temp_img%01d.jpg"};
        new Thread(new Runnable() {
            public void run() {
                Config.enableStatisticsCallback(new StatisticsCallback() {
                    public void apply(Statistics statistics) {
                        Log.d(RenderEngine.TAG, String.format("frame: %d, time: %d", statistics.getVideoFrameNumber(), statistics.getTime()));
                    }
                });
                FFmpeg.execute(strArr);
                Log.i(RenderEngine.TAG, "renderVideoBackground: FFmpeg initiated");
                int lastReturnCode = FFmpeg.getLastReturnCode();
                if (lastReturnCode == 0) {
                    Log.i(Config.TAG, "Command execution completed successfully.");
                } else if (lastReturnCode == 255) {
                    Log.i(Config.TAG, "Command execution cancelled by user.");
                } else {
                    Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", lastReturnCode));
                }
            }
        }).start();
    }

    public void deleteAllFilesFromDir(File file) {
        if (file.isDirectory()) {
            for (File delete : file.listFiles()) {
                delete.delete();
            }
        }
    }
}
