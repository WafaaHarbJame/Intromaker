package com.kinetic.sh.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTemplatePicker2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "AdapterTemplatePicker2";
    AdapterTileInterface2 adapterTileInterface2;

    public AdapterTemplatePicker2.AnimationPickerListener animationPickerListener;
    private final List<Object> mDataset = new ArrayList<>();
    public Context mContext;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private Boolean isShowAdmobNative = true;
    public static final int ANIMATION_VIEW_TYPE = 0;
    public static final int NATIVE_AD_VIEW_TYPE = 1;

    public interface AdapterTileInterface2 {
        void onClickCallback(CompMeta compMeta);
    }

    public interface AnimationPickerListener {
        void onClicked(CompMeta compMeta, ViewHolderAnimation viewHolderAnimation);
    }

    public void setAnimationPickerListener(AdapterTemplatePicker2.AnimationPickerListener animationPickerListener2) {
        this.animationPickerListener = animationPickerListener2;
    }

    public AdapterTemplatePicker2(Context context, List<Object> list, Boolean isShowAdmobNative) {
        this.mContext = context;
        this.mDataset.addAll(list);
        this.isShowAdmobNative = isShowAdmobNative;
    }

    public void updateData(List<Object> datas) {
        this.mDataset.clear();
        this.mDataset.addAll(datas);
        notifyDataSetChanged();
    }

    public void setAdapterTileInterface(AdapterTileInterface2 adapterTileInterface2) {
        this.adapterTileInterface2 = adapterTileInterface2;
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (this.mDataset.get(position) instanceof CompMeta) ? ANIMATION_VIEW_TYPE : NATIVE_AD_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ANIMATION_VIEW_TYPE:
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animation_picker, parent, false);
                return new ViewHolderAnimation(inflate);
            case NATIVE_AD_VIEW_TYPE:
                // fall through
            default:
                return new UnifiedNativeAdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gg_ad_unified, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ANIMATION_VIEW_TYPE:
                final ViewHolderAnimation viewHolderAnimation = (ViewHolderAnimation) viewHolder;
                final CompMeta compMeta = (CompMeta) this.mDataset.get(position);
                int identifier = this.mContext.getResources().getIdentifier(compMeta.getEffectName().toLowerCase(), "raw", this.mContext.getPackageName());
                this.expansionsCollection.add(viewHolderAnimation.getExpansionLayout());
                viewHolderAnimation.animationView.setAnimation(identifier);
                viewHolderAnimation.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
                    public void onCompositionLoaded(LottieComposition lottieComposition) {
                        int maxFrame = (int) viewHolderAnimation.animationView.getMaxFrame();
                        Log.i(AdapterTemplatePicker2.TAG, "onBindViewHolder: " + maxFrame);
                        viewHolderAnimation.animationView.setFrame(maxFrame);
                    }
                });
                if (compMeta.isProTemplate()) {
                    viewHolderAnimation.lockedTemplateContainer.setVisibility(View.VISIBLE);
                } else {
                    viewHolderAnimation.lockedTemplateContainer.setVisibility(View.GONE);
                }
                viewHolderAnimation.animationView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        viewHolderAnimation.animationView.playAnimation();
                        if (viewHolderAnimation.expansionLayout.isExpanded()) {
                            viewHolderAnimation.expansionLayout.collapse(true);
                        } else {
                            viewHolderAnimation.expansionLayout.expand(true);
                        }
                    }
                });
                viewHolderAnimation.editBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (AdapterTemplatePicker2.this.animationPickerListener != null) {
                            AdapterTemplatePicker2.this.animationPickerListener.onClicked(compMeta, viewHolderAnimation);
                        }
                    }
                });
                if (position == 0 && ((MainApplication) ((AppCompatActivity) this.mContext).getApplication()).getFirst()) {
                    TapTargetView.showFor((Activity) this.mContext,
                            TapTarget.forView(viewHolderAnimation.animationView, "Select design", "Preview the animation and use it")
                                    .textColor(R.color.colorAccent)
                                    .cancelable(true)
                                    .transparentTarget(true)
                                    .cancelable(true), new TapTargetView.Listener() {
                                public void onTargetClick(TapTargetView tapTargetView) {
                                    viewHolderAnimation.animationView.playAnimation();
                                    viewHolderAnimation.expansionLayout.expand(true);
                                    super.onTargetClick(tapTargetView);
                                }
                            });
                }
                break;

            case NATIVE_AD_VIEW_TYPE:
                // fall through
            default:
                if (this.isShowAdmobNative) {
                    if (this.mDataset.get(position) instanceof UnifiedNativeAd) {
                        final UnifiedNativeAdViewHolder viewHolderNative = (UnifiedNativeAdViewHolder) viewHolder;
                        UnifiedNativeAd unifiedNativeAd = (UnifiedNativeAd) this.mDataset.get(position);
                        viewHolderNative.setNativeAd(unifiedNativeAd);
                    }
                }
        }
    }

    public static class ViewHolderAnimation extends RecyclerView.ViewHolder {
        public LottieAnimationView animationView;
        public Button editBtn;
        public ExpansionLayout expansionLayout;
        public LinearLayout lockedTemplateContainer;

        public ViewHolderAnimation(View view) {
            super(view);
            this.animationView = view.findViewById(R.id.card_anim_picker_animation_view);
            this.expansionLayout = view.findViewById(R.id.card_anim_picker_expansionLayout);
            this.editBtn = view.findViewById(R.id.card_anim_picker_edit_btn);
            this.lockedTemplateContainer = view.findViewById(R.id.card_anim_picker_locked_container);
        }

        public ExpansionLayout getExpansionLayout() {
            return this.expansionLayout;
        }
    }

    public static class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

        private int templateType;
        private UnifiedNativeAd nativeAd;
        private UnifiedNativeAdView nativeAdView;

        private TextView primaryView;
        private TextView secondaryView;
        private RatingBar ratingBar;
        private TextView tertiaryView;
        private ImageView iconView;
        private MediaView mediaView;
        private Button callToActionView;
        private ConstraintLayout background;

        public UnifiedNativeAdView getAdView() {
            return nativeAdView;
        }

        UnifiedNativeAdViewHolder(View view) {
            super(view);
            nativeAdView = (UnifiedNativeAdView) view.findViewById(R.id.native_ad_view);
            primaryView = view.findViewById(R.id.primary);
            secondaryView = view.findViewById(R.id.secondary);
            tertiaryView = view.findViewById(R.id.body);

            ratingBar = view.findViewById(R.id.rating_bar);
            ratingBar.setEnabled(false);

            callToActionView = view.findViewById(R.id.cta);
            iconView = view.findViewById(R.id.icon);
            mediaView = view.findViewById(R.id.media_view);
            background = view.findViewById(R.id.background);


        }

        public void setNativeAd(UnifiedNativeAd nativeAd) {
            this.nativeAd = nativeAd;

            String store = nativeAd.getStore();
            String advertiser = nativeAd.getAdvertiser();
            String headline = nativeAd.getHeadline();
            String body = nativeAd.getBody();
            String cta = nativeAd.getCallToAction();
            Double starRating = nativeAd.getStarRating();
            NativeAd.Image icon = nativeAd.getIcon();

            String secondaryText;

            nativeAdView.setCallToActionView(callToActionView);
            nativeAdView.setHeadlineView(primaryView);
            nativeAdView.setMediaView(mediaView);
            secondaryView.setVisibility(View.VISIBLE);
            if (adHasOnlyStore(nativeAd)) {
                nativeAdView.setStoreView(secondaryView);
                secondaryText = store;
            } else if (!TextUtils.isEmpty(advertiser)) {
                nativeAdView.setAdvertiserView(secondaryView);
                secondaryText = advertiser;
            } else {
                secondaryText = "";
            }

            primaryView.setText(headline);
            callToActionView.setText(cta);

            //  Set the secondary view to be the star rating if available.
            if (starRating != null && starRating > 0) {
                secondaryView.setVisibility(View.GONE);
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setMax(5);
                nativeAdView.setStarRatingView(ratingBar);
            } else {
                secondaryView.setText(secondaryText);
                secondaryView.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.GONE);
            }

            if (icon != null) {
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageDrawable(icon.getDrawable());
            } else {
                iconView.setVisibility(View.GONE);
            }

            if (tertiaryView != null) {
                tertiaryView.setText(body);
                nativeAdView.setBodyView(tertiaryView);
            }

            nativeAdView.setNativeAd(this.nativeAd);
        }

        private boolean adHasOnlyStore(UnifiedNativeAd nativeAd) {
            String store = nativeAd.getStore();
            String advertiser = nativeAd.getAdvertiser();
            return !TextUtils.isEmpty(store) && TextUtils.isEmpty(advertiser);
        }
    }
}
