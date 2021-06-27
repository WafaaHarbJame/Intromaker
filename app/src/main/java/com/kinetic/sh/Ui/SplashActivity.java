package com.kinetic.sh.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.kinetic.sh.Purchase.PurchaseHelper;
import com.kinetic.sh.R;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    public final static String TAG = "SplashActivity";
    public LottieAnimationView animationView;
    public ImageView splashImageView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_splash);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        new PurchaseHelper(this).checkAndEnablePro();
        new PurchaseHelper(this).setupBillingClient();


        this.splashImageView = findViewById(R.id.splash_imgview);
        this.splashImageView.setVisibility(View.VISIBLE);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        }, 600);
    }


    private void jumpToMain() {
        SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), TemplatePickerActivity.class));
        SplashActivity.this.killActivity();

    }

    private void logAllLayers() {
        this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition lottieComposition) {
                List<KeyPath> resolveKeyPath = SplashActivity.this.animationView.resolveKeyPath(new KeyPath("**"));
                SplashActivity.this.animationView.addValueCallback(new KeyPath("**"), LottieProperty.STROKE_COLOR, new SimpleLottieValueCallback<Integer>() {
                    public Integer getValue(LottieFrameInfo<Integer> lottieFrameInfo) {
                        return SupportMenu.CATEGORY_MASK;
                    }
                });
                for (KeyPath keyPath : resolveKeyPath) {
                    Log.i(TAG, "logAllLayers: " + keyPath);
                }
            }
        });
    }

    public void changePathColor(int i, String... strArr) {
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
    }


    public void killActivity() {
        finish();
    }
}
