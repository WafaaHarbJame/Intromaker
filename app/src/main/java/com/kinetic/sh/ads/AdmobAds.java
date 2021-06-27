package com.kinetic.sh.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.kinetic.sh.R;

public class AdmobAds {

    private static InterstitialAd interstitialAd;

    public static OnAdsCloseListener mOnAdsCloseListener;

    public interface OnAdsCloseListener {
        void onAdsClose();
    }

    public static void initFullAds(final Context context) {
        if (interstitialAd == null) {
            interstitialAd = new InterstitialAd(context);

            interstitialAd.setAdUnitId(context.getString(R.string.admob_inter_id));
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    AdmobAds.loadFullAds();
                    if (AdmobAds.mOnAdsCloseListener != null) {
                        AdmobAds.mOnAdsCloseListener.onAdsClose();
                    }
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.d("AdmobAds", "onAdLoaded");

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.d("AdmobAds", "onAdLoaded");
                }
            });
        }
        loadFullAds();
    }


    public static void loadFullAds() {
        InterstitialAd interstitialAd2 = interstitialAd;
        if (interstitialAd2 != null) {
            interstitialAd2.loadAd(new AdRequest.Builder().build());
        }
    }

    public static boolean showFullAds(OnAdsCloseListener onAdsCloseListener) {
        mOnAdsCloseListener = onAdsCloseListener;
        InterstitialAd interstitialAd2 = interstitialAd;
        if (interstitialAd2 == null || !interstitialAd2.isLoaded()) {
            return false;
        }
        interstitialAd.show();
        return true;
    }

    public static void loadBanner(Activity activity) {
        AdView adView = new AdView(activity);

        adView.setAdUnitId(activity.getString(R.string.admob_banner_id));
        adView.setAdSize(AdSize.SMART_BANNER);
        ((FrameLayout) activity.findViewById(R.id.admob_banner)).addView(adView);
        activity.findViewById(R.id.fb_banner).setVisibility(View.GONE);
        ((View) activity.findViewById(R.id.admob_banner).getParent()).setVisibility(View.VISIBLE);
        AdRequest build = new AdRequest.Builder().build();
        adView.loadAd(build);
    }


}