package com.kinetic.sh.Fragments;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.kinetic.sh.Adapters.AdapterExportRes;
import com.kinetic.sh.Helpers.ShareIntent;
import com.kinetic.sh.Models.ExportResolutions;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Qutils.QuickUtils;
import com.kinetic.sh.R;
import com.kinetic.sh.Ui.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class RenderingDialog extends DialogFragment implements View.OnClickListener, Animator.AnimatorListener {
    private static final String O_FACEBOOK = "https://www.facebook.com/";
    private static final String O_INSTAGRAM = "https://www.instagram.com/";
    private static final String O_YOUTUBE = "https://www.youtube.com/";
    private static final String TAG = "RenderingDialog";
    private LottieAnimationView animationView;
    private Uri backedUri;
    private Button btnClose;
    private Button btnOpenVideo;
    private AlertDialog.Builder builder;
    private TextView clickHere;
    private LinearLayout clickHereContainer;
    private Button close0;
    private Context context;

    public AlertDialog dialog;
    private File file;
    private MainApplication global;
    private LayoutInflater inflater;

    private Boolean isRendering = false;
    public boolean isVideoOpened = false;
    private TextView metaText;
    private LinearLayout oContainer;
    private ImageButton oFacebook;
    private ImageButton oInstagram;
    private ImageButton oYoutube;

    public LinearLayout processContainer;
    private ProgressBar progressBar;

    public RenderingDialogListener renderingDialogListener;
    private String renderingType;
    private ListView resList;

    public LinearLayout resolutionContainer;
    private ImageButton shareAll;
    private LinearLayout shareContainer;
    private ImageButton shareFacebook;
    private ImageButton shareTwitter;
    private ImageButton shareWhatsApp;
    private View view;
    private LinearLayout warningContainer;

    public interface RenderingDialogListener {
        void onDialogClosed();

        void onResolutionSelected(ExportResolutions exportResolutions);
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }

    public void setRenderingDialogListener(RenderingDialogListener renderingDialogListener2) {
        this.renderingDialogListener = renderingDialogListener2;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        this.inflater = requireActivity().getLayoutInflater();
        View v = this.inflater.inflate(R.layout.dialog_rendering, null);
        this.view = v;
        this.progressBar = v.findViewById(R.id.dialog_rendering_progress);
        this.btnOpenVideo = this.view.findViewById(R.id.dialog_rendering_open_video);
        this.btnClose = this.view.findViewById(R.id.dialog_rendering_button_close);
        this.animationView = this.view.findViewById(R.id.dialog_animation_view);
        this.resList = this.view.findViewById(R.id.export_res_list);
        this.processContainer = this.view.findViewById(R.id.rendering_process);
        this.resolutionContainer = this.view.findViewById(R.id.resolution_selection);
        this.close0 = this.view.findViewById(R.id.dialog_rendering_cancel_0);
        this.shareWhatsApp = this.view.findViewById(R.id.export_share_wa);
        this.shareFacebook = this.view.findViewById(R.id.export_share_insta);
        this.shareTwitter = this.view.findViewById(R.id.export_share_tw);
        this.shareAll = this.view.findViewById(R.id.export_share);
        this.shareContainer = this.view.findViewById(R.id.share_container);
        this.warningContainer = this.view.findViewById(R.id.rendering_process_warning);
        this.metaText = this.view.findViewById(R.id.rendering_meta_text);
        this.oFacebook = this.view.findViewById(R.id.export_o_facebook);
        this.oInstagram = this.view.findViewById(R.id.export_o_instagram);
        this.oYoutube = this.view.findViewById(R.id.export_o_youtube);
        this.oContainer = this.view.findViewById(R.id.export_o_container);
        this.clickHere = this.view.findViewById(R.id.dialog_rendering_switch_rendering_method);
        this.clickHereContainer = this.view.findViewById(R.id.rendering_switch_container);
        String string = PreferenceManager.getDefaultSharedPreferences(this.context).getString("software encoder", "");
        this.renderingType = string;
        this.metaText.setText(string);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        this.builder = builder2;
        builder2.setView(this.view);
        this.btnOpenVideo.setOnClickListener(this);
        this.btnOpenVideo.setEnabled(false);
        this.btnClose.setOnClickListener(this);
        this.close0.setOnClickListener(this);
        this.btnClose.setEnabled(false);
        this.builder.setMessage("Select Video resolution");
        this.shareWhatsApp.setOnClickListener(this);
        this.shareFacebook.setOnClickListener(this);
        this.shareTwitter.setOnClickListener(this);
        this.shareAll.setOnClickListener(this);
        this.clickHereContainer.setVisibility(View.GONE);
        this.clickHere.setOnClickListener(this);
        this.oYoutube.setOnClickListener(this);
        this.oFacebook.setOnClickListener(this);
        this.oInstagram.setOnClickListener(this);
        this.dialog = this.builder.create();
        this.global = (MainApplication) ((AppCompatActivity) this.context).getApplication();
        this.animationView.setAnimation(R.raw.rendering);
        this.animationView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        changePathColor(ContextCompat.getColor(this.context, R.color.accent), "**");
        this.animationView.setVisibility(View.VISIBLE);
        this.animationView.playAnimation();
        this.animationView.setRepeatCount(-1);
        this.shareContainer.setVisibility(View.GONE);

        loadNativeAds(view);

        final ArrayList<ExportResolutions> arrayList = new ArrayList<>();
        arrayList.add(new ExportResolutions("Faster in render", 540));
        arrayList.add(new ExportResolutions("Medium in render", 720));
        arrayList.add(new ExportResolutions("Slow in render", 1080));
        this.resList.setAdapter(new AdapterExportRes(this.context, arrayList));
        this.progressBar.setVisibility(View.VISIBLE);
        this.warningContainer.setVisibility(View.VISIBLE);
        this.processContainer.setVisibility(View.GONE);
        this.resolutionContainer.setVisibility(View.VISIBLE);
        this.oContainer.setVisibility(View.GONE);
        this.resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ExportResolutions exportResolutions = arrayList.get(i);
                if (RenderingDialog.this.dialog != null) {
                    ((TextView) RenderingDialog.this.dialog.findViewById(16908299)).setText("Preparing video file...");
                }
                RenderingDialog.this.processContainer.setVisibility(View.VISIBLE);
                RenderingDialog.this.resolutionContainer.setVisibility(View.GONE);
                if (RenderingDialog.this.renderingDialogListener != null) {
                    RenderingDialog.this.renderingDialogListener.onResolutionSelected(exportResolutions);
                }
            }
        });
        return this.dialog;
    }

    private void loadNativeAds(View view) {

        AdLoader adLoader = new AdLoader.Builder(context, getString(R.string.admob_native_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = view.findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        RenderingDialogListener renderingDialogListener2 = this.renderingDialogListener;
        if (renderingDialogListener2 != null) {
            renderingDialogListener2.onDialogClosed();
        }
        super.onDismiss(dialogInterface);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void changePathColor(int i, String... strArr) {
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
    }

    public void onAttach(@NonNull Context context2) {
        this.context = context2;
        super.onAttach(context2);
    }

    public void setProgressBar(float f) {
        if (this.dialog != null) {
            if (!this.isRendering) {
                this.isRendering = true;
                this.animationView.setAnimation(R.raw.loader);
                this.animationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                changePathColor(ContextCompat.getColor(this.context, R.color.accent), "**");
                this.animationView.playAnimation();
            }
            ((TextView) this.dialog.findViewById(16908299)).setText("Video is rendering...");
            this.progressBar.setProgress((int) f);
        }
    }

    public void setRenderedFile(File file2) {

        this.isRendering = false;
        this.animationView.pauseAnimation();
        this.animationView.setAnimation(R.raw.done_loading);
        this.progressBar.setVisibility(View.GONE);
        this.animationView.setRepeatCount(0);
        this.animationView.playAnimation();
        this.animationView.addAnimatorListener(this);
        ((TextView) this.dialog.findViewById(16908299)).setText("Video has been rendered");
        this.shareContainer.setVisibility(View.VISIBLE);
        this.backedUri = Uri.parse(file2.toString());
        this.file = file2;
        this.btnOpenVideo.setEnabled(true);
        this.btnClose.setEnabled(true);

    }


    public void onClick(View view2) {
        switch (view2.getId()) {
            case R.id.dialog_rendering_button_close:
            case R.id.dialog_rendering_cancel_0:
                dismiss();
                return;
            case R.id.dialog_rendering_open_video:
                if (this.file != null) {
                    this.isVideoOpened = true;
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(this.file.getAbsolutePath()), MimeTypes.VIDEO_MP4);
                    startActivity(Intent.createChooser(intent, "Open video"));
                    return;
                }
                Toast.makeText(getContext(), "video is not rendered yet", Toast.LENGTH_LONG).show();
                return;
            case R.id.dialog_rendering_switch_rendering_method:
                dismiss();
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
                edit.putString("software encoder", "hardware_encoder");
                edit.apply();
                MainActivity mainActivity = (MainActivity) this.context;
                mainActivity._bakeIntoMp4(mainActivity.currentVideoPath);
                Toast.makeText(this.context, "Switched to hardware rendering method", Toast.LENGTH_LONG).show();
                return;
            case R.id.export_o_facebook:
                QuickUtils.openInBrowser(this.context, O_FACEBOOK);
                return;
            case R.id.export_o_instagram:
                QuickUtils.openInBrowser(this.context, O_INSTAGRAM);
                return;
            case R.id.export_o_youtube:
                QuickUtils.openInBrowser(this.context, O_YOUTUBE);
                return;
            case R.id.export_share:
                new ShareIntent(this.context, null, this.file).shareToAllApps();
                return;
            case R.id.export_share_insta:
                new ShareIntent(this.context, "com.instagram.android", this.file).shareOnSpecificApp();
                return;
            case R.id.export_share_tw:
                new ShareIntent(this.context, "com.twitter.android", this.file).shareOnSpecificApp();
                return;
            case R.id.export_share_wa:
                new ShareIntent(this.context, "com.whatsapp", this.file).shareOnSpecificApp();
                return;
            default:
                break;
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.animationView.pauseAnimation();
        this.animationView.setVisibility(View.GONE);
        this.warningContainer.setVisibility(View.GONE);
        this.oContainer.setVisibility(View.GONE);
        AlertDialog alertDialog = this.dialog;
        alertDialog.setMessage("Video has been saved! \n\nLocation: " + this.file.getAbsolutePath());
        if (!this.renderingType.equals("hardware_encoder")) {
            this.clickHereContainer.setVisibility(View.VISIBLE);
        }
    }
}
