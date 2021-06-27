package com.kinetic.sh.Ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.RenderMode;
import com.android.billingclient.api.Purchase;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.kinetic.sh.Adapters.AdapterMainTab;
import com.kinetic.sh.Fragments.BackgroundPanel;
import com.kinetic.sh.Fragments.EditPanel;
import com.kinetic.sh.Fragments.RatingDialog;
import com.kinetic.sh.Fragments.RenderingDialog;
import com.kinetic.sh.Helpers.MyPreferences;
import com.kinetic.sh.Helpers.SingleMediaScanner;
import com.kinetic.sh.Intefaces.MainListener;
import com.kinetic.sh.Intefaces.PermissionListener;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.Models.ExportResolutions;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Permissions.StoragePermission;
import com.kinetic.sh.Purchase.PurchaseHelper;
import com.kinetic.sh.R;
import com.kinetic.sh.RenderEngine.FFmpegRender;
import com.kinetic.sh.RenderEngine.RenderEngine;
import com.kinetic.sh.RenderEngine.Renderer;
import com.kinetic.sh.Template.Template;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnUserEarnedRewardListener {
    public String FEEDBACK_DIALOG = "FEEDBACK_DIALOG";
    public final static String TAG = "MainActivity";
    public LottieAnimationView animationView;
    public LottieAnimationView animationViewBg;
    public String bgImagePath;
    public String bgVideoPath;
    private TextView bottomSheetHeader;
    private BottomSheetBehavior bsbColorPicker;
    private BottomSheetBehavior bsbImagePicker;
    private BottomSheetBehavior bsbTypeface;
    private BottomSheetBehavior bsbVideoPicker;
    private LinearLayout colorPickerParent;
    private final List<CompMeta> compMetas = new ArrayList<>();

    public Context context;
    public String currentVideoPath;

    public RenderingDialog dialog;
    private TextView exportBtn;
    public FrameLayout frameLayout;
    private ImageButton gbsCancel;
    public MainApplication global;
    private BottomSheetBehavior gradientBottomSheetBehavior;
    private LinearLayout gradientBottomSheetParent;
    public ImageView imageView;
    private ImageButton ipCancel;
    private boolean isBSExtended = false;
    private Boolean isBakeInMp4Called = false;
    public Boolean isDialogActive = false;
    public Boolean isExported;
    private MainListener mainListener;
    private LinearLayout parentImagePicker;
    private PermissionListener permissionListener;
    private TextView playBtn;

    public ImageButton removeWatermarkBtn;
    private TextView saveButton;
    private RelativeLayout watermarkContainer;
    private LinearLayout watermark;
    private ImageView watermarkImage;
    private TextView watermarkText;
    public StoragePermission storagePermission;
    private TabLayout tabLayout;
    public TapTargetSequence tapTargetSequence;
    private ImageButton tbsCancel;
    private LinearLayout typefaceLayout;
    private LinearLayout videoPikerParent;
    private ViewPager viewPager;
    private ImageButton vpCancel;
    private RewardedInterstitialAd rewardedInterstitialAd;


    public void setTapTargetSequence(TapTargetSequence tapTargetSequence2) {
        this.tapTargetSequence = tapTargetSequence2;
    }


    public void onPostResume() {
        Log.i(TAG, "onPostResume: " + hasRated());
        if (this.dialog.isVideoOpened) {
            feedbackDialog();
        }
        Log.i(TAG, "onPostResume: ");
        new PurchaseHelper(this.context).setupBillingClient();
        MainApplication mainApplication = this.global;
        if (mainApplication != null) {
            if (mainApplication.getFromProActivity()) {
                this.global.setFromProActivity(false);
                if (this.isDialogActive) {
                    this.dialog.dismiss();
                    bakeIntoMp4(this.currentVideoPath);
                }
            }
            if (this.global.getPremium()) {
                this.watermarkContainer.setVisibility(View.GONE);
            }
        }
        super.onPostResume();
    }

    public void loadAd() {
        RewardedInterstitialAd.load(MainActivity.this, getString(R.string.admob_rewarded),
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {

                    @Override
                    public void onRewardedInterstitialAdLoaded(@NonNull RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            /** Called when the ad failed to show full screen content. */
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.i(TAG, "onAdFailedToShowFullScreenContent");
                            }

                            /** Called when ad showed the full screen content. */
                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.i(TAG, "onAdShowedFullScreenContent");
                            }

                            /** Called when full screen content is dismissed. */
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.i(TAG, "onAdDismissedFullScreenContent");

                            }
                        });
                    }

                    @Override
                    public void onRewardedInterstitialAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(TAG, "onAdFailedToLoad");
                    }


                });
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        loadAd();
        this.context = this;
        this.animationView = findViewById(R.id.animation_view);
        this.animationViewBg = findViewById(R.id.animation_view_bg);
        this.playBtn = findViewById(R.id.main_play_button);
        this.bottomSheetHeader = findViewById(R.id.bottomSheetHeader);
        this.tabLayout = findViewById(R.id.main_tab);
        this.viewPager = findViewById(R.id.main_vp);
        this.gbsCancel = findViewById(R.id.gbsCancelButton);
        LinearLayout linearLayout = findViewById(R.id.bottom_sheet_gradients);
        this.gradientBottomSheetParent = linearLayout;
        this.gradientBottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        this.exportBtn = findViewById(R.id.export_button);
        this.frameLayout = findViewById(R.id.frameLayout2);
        LinearLayout linearLayout2 = findViewById(R.id.bottom_sheet_typeface);
        this.typefaceLayout = linearLayout2;
        this.bsbTypeface = BottomSheetBehavior.from(linearLayout2);
        this.tbsCancel = findViewById(R.id.typefaceCancelButton);
        this.imageView = findViewById(R.id.main_image_view);
        this.vpCancel = findViewById(R.id.videoPickerCancelButton);
        LinearLayout linearLayout3 = findViewById(R.id.bottom_sheet_video_picker);
        this.videoPikerParent = linearLayout3;
        this.bsbVideoPicker = BottomSheetBehavior.from(linearLayout3);
        this.removeWatermarkBtn = findViewById(R.id.main_signature_btn);
        this.watermark = findViewById(R.id.signature);
        this.watermarkImage = findViewById(R.id.signature_logo);
        this.watermarkText = findViewById(R.id.signature_text);
        this.watermarkContainer = findViewById(R.id.signature_container);
        LinearLayout linearLayout4 = findViewById(R.id.bottom_sheet_image_picker);
        this.parentImagePicker = linearLayout4;
        this.bsbImagePicker = BottomSheetBehavior.from(linearLayout4);
        this.ipCancel = findViewById(R.id.imagePickerCancelButton);
        this.saveButton = findViewById(R.id.save_button);
        LinearLayout linearLayout5 = findViewById(R.id.cpv_container);
        this.colorPickerParent = linearLayout5;
        BottomSheetBehavior from = BottomSheetBehavior.from(linearLayout5);
        this.bsbColorPicker = from;
        from.setHideable(true);
        this.bsbColorPicker.setState(5);
        this.animationView.setCacheComposition(false);
        this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition lottieComposition) {
                MainActivity.this.animationView.setRenderMode(RenderMode.SOFTWARE);
            }
        });
        this.gradientBottomSheetBehavior.setHideable(true);
        this.gradientBottomSheetBehavior.setState(5);
        this.bsbTypeface.setHideable(true);
        this.bsbTypeface.setState(5);
        this.bsbVideoPicker.setHideable(true);
        this.bsbVideoPicker.setState(5);
        this.bsbImagePicker.setHideable(true);
        this.bsbImagePicker.setState(5);
        this.dialog = new RenderingDialog();
        this.global = (MainApplication) getApplication();
        this.tapTargetSequence = new TapTargetSequence((Activity) this.context);
        this.isExported = false;
        if (this.global.getPremium()) {
            this.watermarkContainer.setVisibility(View.GONE);
        } else {
            this.watermarkContainer.setVisibility(View.VISIBLE);
        }
        AdapterMainTab adapterMainTab = new AdapterMainTab(getSupportFragmentManager(), 1);
        adapterMainTab.setTitles("Effect", new EditPanel());
        adapterMainTab.setTitles("background", new BackgroundPanel());
        this.viewPager.setAdapter(adapterMainTab);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.exportBtn.setOnClickListener(this);
        this.storagePermission = new StoragePermission((Activity) this.context);
        for (int i = 1; i <= 14; i++) {
            CompMeta compMeta = new CompMeta();
            compMeta.setEffectName("Tile" + i);
            this.compMetas.add(compMeta);
        }
        setTapTarget();
        this.tbsCancel.setOnClickListener(this);
        this.gbsCancel.setOnClickListener(this);
        this.vpCancel.setOnClickListener(this);
        this.ipCancel.setOnClickListener(this);
        this.saveButton.setOnClickListener(this);
        this.removeWatermarkBtn.setOnClickListener(this);
        this.watermark.setOnClickListener(this);
    }


    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void onResume() {
        super.onResume();
        if (this.isBakeInMp4Called) {
            bakeIntoMp4(this.currentVideoPath);
            this.isBakeInMp4Called = false;
        }
    }

    public void bakeIntoMp4(final String str) {
        try {
            this.currentVideoPath = str;
            if (!this.isDialogActive) {
                this.dialog.show(((AppCompatActivity) this.context).getSupportFragmentManager(), TAG);
                this.isDialogActive = true;
                this.dialog.setCancelable(false);
                this.dialog.setRenderingDialogListener(new RenderingDialog.RenderingDialogListener() {
                    public void onResolutionSelected(final ExportResolutions exportResolutions) {
                        File file = new File(MainActivity.this.context.getExternalMediaDirs()[0].getAbsolutePath() + "/");
                        file.mkdirs();
                        MainActivity.this.setSignatureSize(exportResolutions.getHeight());
                        if (!MainActivity.this.global.getPremium()) {
                            MainActivity.this.removeWatermarkBtn.setVisibility(View.GONE);
                        }
                        final Renderer renderer = new Renderer(MainActivity.this.context, MainActivity.this.frameLayout, MainActivity.this.imageView, MainActivity.this.animationViewBg, MainActivity.this.animationView, MainActivity.this.currentVideoPath, exportResolutions.getHeight());
                        renderer.setInterfaceRenderEngine(new RenderEngine.InterfaceRenderEngine() {
                            public void onProgressChange(float f) {
                                Log.i(TAG, "onProgressChange: " + f);
                                MainActivity.this.dialog.setProgressBar(f);
                            }

                            public void onRendered(File file) {
                                MainActivity.this.isExported = true;
                                Bundle bundle = new Bundle();
                                bundle.putString("rendered_resolution", String.valueOf(exportResolutions.getHeight()));
                                if (!MainActivity.this.global.getPremium()) {
                                    MainActivity.this.removeWatermarkBtn.setVisibility(View.VISIBLE);
                                    MainActivity.this.setSignatureSize(0);
                                }
                                MainActivity.this.dialog.setRenderedFile(file);
                                new SingleMediaScanner(MainActivity.this.context, file);
                                if (MainActivity.this.global.getPremium()) {
                                    Log.i(TAG, "end render: user is pro");
                                    Purchase purchase = MainActivity.this.global.getPurchase();
                                    if (purchase != null) {
                                        Log.i(TAG, "end render: purchase is not null");
                                        if (purchase.getSku().equals(MainActivity.this.context.getString(R.string.subscription_sku_single_item))) {
                                            Log.i(TAG, "consumed: " + purchase.getSku());
                                            new PurchaseHelper(MainActivity.this.context).consumePurchase(purchase);
                                            Toast.makeText(MainActivity.this.context, MainActivity.this.context.getString(R.string.tf_msg_single_template_used), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }

                            public void onRenderError(Exception exc) {
                                renderer.stopRender();
                                if (!MainActivity.this.dialog.isHidden()) {
                                    MainActivity.this.dialog.dismiss();
                                }
                                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.context).edit();
                                edit.putString("software encoder", "hardware_encoder");
                                edit.apply();
                                MainActivity.this._bakeIntoMp4(str);
                            }
                        });
                        renderer.setSource(file);
                    }

                    public void onDialogClosed() {
                        MainActivity.this.isDialogActive = false;
                    }
                });
            }
        } catch (IllegalStateException unused) {
            this.isBakeInMp4Called = true;
        }
    }

    public void showAlertDialog(String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void _bakeIntoMp4(String str) {
        try {
            this.currentVideoPath = str;
            if (!this.isDialogActive) {
                this.dialog.show(((AppCompatActivity) this.context).getSupportFragmentManager(), TAG);
                this.isDialogActive = true;
                this.dialog.setCancelable(false);
                this.dialog.setRenderingDialogListener(new RenderingDialog.RenderingDialogListener() {
                    public void onResolutionSelected(final ExportResolutions exportResolutions) {
                        new File(MainActivity.this.context.getExternalFilesDir(null).getAbsolutePath() + "/" + MainActivity.this.context.getString(R.string.tf_filepath)).mkdirs();
                        MainActivity.this.setSignatureSize(exportResolutions.getHeight());
                        if (!MainActivity.this.global.getPremium()) {
                            MainActivity.this.removeWatermarkBtn.setVisibility(View.GONE);
                        }
                        new FFmpegRender(MainActivity.this.context, 1, exportResolutions.getHeight()).setfFmpegRenderListener(new FFmpegRender.FFmpegRenderListener() {
                            public void onProgressChange(float f) {
                                Log.i(TAG, "onProgressChange: " + f);
                                MainActivity.this.dialog.setProgressBar(f);
                            }

                            public void onRendered(File file) {
                                MainActivity.this.isExported = true;
                                Bundle bundle = new Bundle();
                                bundle.putString("rendered_resolution", String.valueOf(exportResolutions.getHeight()));
                                if (!MainActivity.this.global.getPremium()) {
                                    MainActivity.this.removeWatermarkBtn.setVisibility(View.VISIBLE);
                                    MainActivity.this.setSignatureSize(0);
                                }
                                MainActivity.this.dialog.setRenderedFile(file);
                                new SingleMediaScanner(MainActivity.this.context, file);
                                if (MainActivity.this.global.getPremium()) {
                                    Log.i(TAG, "end render: user is pro");
                                    Purchase purchase = MainActivity.this.global.getPurchase();
                                    if (purchase != null) {
                                        Log.i(TAG, "end render: purchase is not null");
                                        if (purchase.getSku().equals(MainActivity.this.context.getString(R.string.tf_msg_single_template_used))) {
                                            Log.i(TAG, "consumed: " + purchase.getSku());
                                            new PurchaseHelper(MainActivity.this.context).consumePurchase(purchase);
                                        }
                                    }
                                }
                            }
                        });
                    }

                    public void onDialogClosed() {
                        MainActivity.this.isDialogActive = false;
                    }
                });
            }
        } catch (IllegalStateException unused) {
            this.isBakeInMp4Called = true;
        }
    }

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }


    public void setSignatureSize(int i) {
        if (i == 540) {
            this.watermarkText.setTextSize(2, 6.0f);
            this.watermarkImage.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(12), dpToPx(12)));
        } else if (i == 720) {
            this.watermarkText.setTextSize(2, 9.0f);
            this.watermarkImage.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(15), dpToPx(15)));
        } else {
            this.watermarkText.setTextSize(2, 13.0f);
            this.watermarkImage.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(27), dpToPx(27)));
        }
    }

    private Dialog onPermissionDenyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Storage Permission is required!");
        builder.setMessage("We are not able to store video until you grant this permission.");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.storagePermission.checkPermissions();
            }
        });
        return builder.create();
    }

    public void setPermissionListener(PermissionListener permissionListener2) {
        this.permissionListener = permissionListener2;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                onPermissionDenyDialog().show();
                Toast.makeText(this.context, "Storage Permission is required in order to save the video !", Toast.LENGTH_LONG).show();
                return;
            }
            PermissionListener permissionListener2 = this.permissionListener;
            if (permissionListener2 != null) {
                permissionListener2.onStorageAccess();
            }
        }
    }

    private AlertDialog showExitWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("You have not exported the video");
        builder.setMessage("Are you sure do you want to exit?");
        builder.setPositiveButton("Continue editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.global.setcTemplate(new Template());
                MainActivity.this.global.setTemplate(new Template());
                dialogInterface.dismiss();
                MainActivity.this.finish();
            }
        });
        return builder.create();
    }


    public void setMainListener(MainListener mainListener2) {
        this.mainListener = mainListener2;
    }

    private void setTapTarget() {
        if (this.global.getFirst()) {
            this.tapTargetSequence.target(TapTarget.forView(((ViewGroup) this.tabLayout.getChildAt(0)).getChildAt(0), this.context.getString(R.string.tf_guideline_add_text), this.context.getString(R.string.tf_guideline_edit_the_text)).textColor(R.color.colorAccent).targetCircleColor(R.color.colorAccent));
            this.tapTargetSequence.target(TapTarget.forView(((ViewGroup) this.tabLayout.getChildAt(0)).getChildAt(1), this.context.getString(R.string.tf_guideline_set_background), this.context.getString(R.string.tf_guideline_add_background)).textColor(R.color.colorAccent).targetCircleColor(R.color.colorAccent).targetRadius(70));
            this.tapTargetSequence.target(TapTarget.forView(this.playBtn, this.context.getString(R.string.tf_guideline_preview_animation), this.context.getString(R.string.tf_guideline_preview_current_animation)).textColor(R.color.colorAccent).targetCircleColor(R.color.colorAccent).transparentTarget(true));
            this.tapTargetSequence.target(TapTarget.forView(this.exportBtn, this.context.getString(R.string.tf_guideline_create_video), this.context.getString(R.string.tf_guideline_get_animation_video)).textColor(R.color.colorAccent).targetCircleColor(R.color.colorAccent).transparentTarget(true));
            this.tapTargetSequence.continueOnCancel(true);
            this.tapTargetSequence.start();
            this.tapTargetSequence.listener(new TapTargetSequence.Listener() {
                public void onSequenceCanceled(TapTarget tapTarget) {
                }

                public void onSequenceStep(TapTarget tapTarget, boolean z) {
                }

                public void onSequenceFinish() {
                    MainActivity.this.global.setFirst(MyPreferences.isFirst(MainActivity.this.context));
                }
            });
        }
    }

    private void feedbackDialog() {
        if (!hasRated() && !this.global.isSuppressFeedbackDialog()) {
            new RatingDialog.Builder(this).threshold(4.0f).feedbackTextColor(R.color.colorPrimary).ratingBarColor(R.color.colorAccent).negativeButtonText("Never").onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                public void onFormSubmitted(String str) {
                    Intent intent = new Intent("android.intent.action.SENDTO");
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra("android.intent.extra.EMAIL", new String[]{MainActivity.this.getString(R.string.tf_feedback_email)});
                    intent.putExtra("android.intent.extra.SUBJECT", "Feedback from app");
                    intent.putExtra("android.intent.extra.TEXT", str);
                    try {
                        MainActivity.this.startActivity(Intent.createChooser(intent, "Send mail..."));
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(MainActivity.this, "There are no email apps installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).onRatingNutralButtonListener(new RatingDialog.Builder.RatingNeutralButtonListener() {
                public void onNeutralButtonListener() {
                    MainActivity.this.global.setSuppressFeedbackDialog(true);
                }
            }).onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                public void onRatingSelected(float f, boolean z) {
                    Log.i(TAG, "onRatingSelected: " + f);
                    MainActivity mainActivity = MainActivity.this;
                    SharedPreferences.Editor edit = mainActivity.getSharedPreferences(mainActivity.FEEDBACK_DIALOG, 0).edit();
                    edit.putBoolean("SHOW_FEEDBACK_DIALOG", true);
                    edit.apply();
                }
            }).build().show();
        }
    }

    private boolean hasRated() {
        return getSharedPreferences(this.FEEDBACK_DIALOG, 0).getBoolean("SHOW_FEEDBACK_DIALOG", false);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 53213 && i2 == -1) {
            if (intent != null) {
                ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
                if (stringArrayListExtra.size() > 0) {
                    this.bgVideoPath = stringArrayListExtra.get(0);
                    MainListener mainListener2 = this.mainListener;
                    if (mainListener2 != null) {
                        mainListener2.onVideoPicked(stringArrayListExtra.get(0));
                    }
                } else {
                    Log.d(TAG, "onActivityResult: no videos got selected ~~ " + stringArrayListExtra);
                }
            } else {
                Toast.makeText(this.context, "Something went wrong with permission Try again", Toast.LENGTH_LONG).show();
            }
        }
        if (i != 42141 || i2 != -1) {
            return;
        }
        if (intent != null) {
            Log.i(TAG, "Image Picker");
            ArrayList<String> stringArrayListExtra2 = intent.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (stringArrayListExtra2.size() > 0) {
                this.bgImagePath = stringArrayListExtra2.get(0);
                Log.i(TAG, this.bgImagePath);
                MainListener mainListener3 = this.mainListener;
                if (mainListener3 != null) {
                    mainListener3.onImagePicked(stringArrayListExtra2.get(0));
                    return;
                }
                return;
            }
            Log.d(TAG, "onActivityResult: no videos got selected ~~ " + stringArrayListExtra2);
            return;
        }
        Toast.makeText(this.context, "Something went wrong with permission Try again", Toast.LENGTH_LONG).show();
    }


    private AlertDialog showRemoveWatermarsk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Remove Watermark");

        builder.setPositiveButton("Watch Video", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rewardedInterstitialAd == null) {
                    Toast.makeText(MainActivity.this, "Can't load reward video", Toast.LENGTH_LONG).show();
                    loadAd();
                    return;
                }
                rewardedInterstitialAd.show(/* Activity */ MainActivity.this,/*
    OnUserEarnedRewardListener */ MainActivity.this);
            }
        });
        builder.setNegativeButton("Get Primium", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, PremiumFeatureActivity2.class));
            }
        });
        return builder.create();
    }

    private void removeSignature() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Remove wartermark");
        builder.setPositiveButton("Watch Video", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rewardedInterstitialAd == null) {
                    Toast.makeText(MainActivity.this, "Can't load reward video", Toast.LENGTH_LONG).show();
                    loadAd();
                    return;
                }
                rewardedInterstitialAd.show(/* Activity */ MainActivity.this,/*
    OnUserEarnedRewardListener */ MainActivity.this);

            }
        }).setNegativeButton("Get Premium", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, PremiumFeatureActivity2.class));

            }
        });
        builder.create().show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gbsCancelButton:
                this.gradientBottomSheetBehavior.setHideable(true);
                this.gradientBottomSheetBehavior.setState(5);
                return;

            case R.id.imagePickerCancelButton:
                this.bsbImagePicker.setHideable(true);
                this.bsbImagePicker.setState(5);
                return;
            case R.id.main_signature_btn:
            case R.id.signature:
//                removeSignature();
                AlertDialog alertDialog = showRemoveWatermarsk();
                alertDialog.show();
                alertDialog.getButton(-2).setBackgroundColor(0);
                alertDialog.getButton(-2).setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
                alertDialog.getButton(-1).setBackgroundColor(0);
                alertDialog.getButton(-1).setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.drawable.rounded_dialog);
                return;
            case R.id.save_button:
                //new TemplateHelper(this.context).selectCategoryType();
                return;
            case R.id.typefaceCancelButton:
                this.bsbTypeface.setHideable(true);
                this.bsbTypeface.setState(5);
                return;
            case R.id.videoPickerCancelButton:
                this.bsbVideoPicker.setHideable(true);
                this.bsbVideoPicker.setState(5);
                return;
            default:
                break;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void isBottomSheetExtended(BottomSheetBehavior bottomSheetBehavior) {
        bottomSheetBehavior.setHideable(true);
        if (bottomSheetBehavior.getState() == 3 || bottomSheetBehavior.getState() == 6) {
            this.isBSExtended = true;
            bottomSheetBehavior.setState(5);
        }
    }

    public void onBackPressed() {
        this.isBSExtended = false;
        isBottomSheetExtended(this.bsbImagePicker);
        isBottomSheetExtended(this.bsbVideoPicker);
        isBottomSheetExtended(this.bsbTypeface);
        isBottomSheetExtended(this.gradientBottomSheetBehavior);
        isBottomSheetExtended(this.bsbColorPicker);
        if (this.isBSExtended) {
            return;
        }
        if (!this.isExported) {
            AlertDialog showExitWarning = showExitWarning();
            showExitWarning.show();
            showExitWarning.getButton(-2).setBackgroundColor(0);
            showExitWarning.getButton(-2).setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
            showExitWarning.getButton(-1).setBackgroundColor(0);
            showExitWarning.getButton(-1).setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
            Objects.requireNonNull(showExitWarning.getWindow()).setBackgroundDrawableResource(R.drawable.rounded_dialog);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        watermarkContainer.setVisibility(View.GONE);
    }
}
