package com.kinetic.sh.RenderEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FFmpegRenderEngine {
    private static final String TAG = "FFmpegRenderEngine";

    public static void deleteCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
            return file.delete();
        } else if (file == null || !file.isFile()) {
            return false;
        } else {
            return file.delete();
        }
    }

    public static Bitmap genBitmapFromView(View view) {
        Log.e("RENDER: ___", "w=" + view.getWidth() + "  h=" + view.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }


    public static void saveImage(Bitmap bitmap, String str) {
        String absolutePath = MainApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        File file = new File(absolutePath + "/" + MainApplication.getContext().getString(R.string.tf_filepath) + "/chunks");
        file.mkdirs();
        File file2 = new File(file, str);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (file2.exists()) {
                Log.i(TAG, "exists: " + str);
                return;
            }
            Log.i(TAG, "does_not_exists: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllFilesFromDir(File file) {
        if (file.isDirectory()) {
            for (File delete : file.listFiles()) {
                delete.delete();
            }
        }
    }

    public void setFrameResolution(int w, FrameLayout frameLayout) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.width = w;
        layoutParams.dimensionRatio = "H,16:9";
        layoutParams.height = w * 9 / 16;
        frameLayout.setLayoutParams(layoutParams);
    }

    public void ffConfig(Context context) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput("yourFileName", 0);
            openFileOutput.write("This is some kinetic!".getBytes());
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int parseFrameCount(String str) {
        Matcher matcher = Pattern.compile("(?<=frame=)[^fps]*", 8).matcher(str.replace(" ", ""));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }
        return -1;
    }

    public File genBlackLayer(Context context) {
        InputStream openRawResource = context.getResources().openRawResource(R.raw.b);
        try {
            File createTempFile = File.createTempFile("black_image", "png");
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            byte[] bArr = new byte[102400];
            while (true) {
                int read = openRawResource.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    openRawResource.close();
                    return createTempFile;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
