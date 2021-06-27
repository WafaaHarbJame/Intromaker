package com.kinetic.sh.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.kinetic.sh.R;

public class LockedTemplateDialog extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "LockedTemplateDialog";
    private AlertDialog.Builder builder;
    private Context context;
    private AlertDialog dialog;
    private Button getProBtn;
    private final boolean loadAdRequest = false;
    private LockedTemplateListener lockedTemplateListener;
    private LinearLayout rewardAdStatusContainer;
    private Button seeAdBtn;

    public interface LockedTemplateListener {
        void onAdSeen();

        void onGetProClicked();

        void onSeeAdClicked();
    }

    private void initRewardedAd() {
    }

    private void loadRewardedAd() {
    }

    public void setLockedTemplateListener(LockedTemplateListener lockedTemplateListener2) {
        this.lockedTemplateListener = lockedTemplateListener2;
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        this.context = context2;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        View inflate = requireActivity().getLayoutInflater().inflate(R.layout.dialog_anim_picker_locked_template_dialog, null);
        this.seeAdBtn = inflate.findViewById(R.id.dialog_anim_picker_template_unlock_with_ad);
        this.getProBtn = inflate.findViewById(R.id.dialog_anim_picker_template_locked_btn);
        this.rewardAdStatusContainer = inflate.findViewById(R.id.dialog_anim_picker_locked_container);
        this.getProBtn.setOnClickListener(this);
        this.seeAdBtn.setOnClickListener(this);
        setEnableSeeAdBtn(true);
        initRewardedAd();
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this.context);
        this.builder = builder2;
        builder2.setView(inflate);
        this.builder.setTitle(this.context.getString(R.string.tf_msg_template_locked));
        this.builder.setMessage(this.context.getString(R.string.tf_msg_become_premium_mem));
        AlertDialog create = this.builder.create();
        this.dialog = create;
        return create;
    }

    private void setEnableSeeAdBtn(boolean z) {
        if (z) {
            this.seeAdBtn.setEnabled(true);
            this.seeAdBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.colorPrimaryLessLight)));
            this.seeAdBtn.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
            return;
        }
        this.seeAdBtn.setEnabled(false);
        this.seeAdBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.colorPrimaryLight)));
        this.seeAdBtn.setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_anim_picker_template_locked_btn:
                LockedTemplateListener lockedTemplateListener2 = this.lockedTemplateListener;
                if (lockedTemplateListener2 != null) {
                    lockedTemplateListener2.onGetProClicked();
                    return;
                }
                return;
            case R.id.dialog_anim_picker_template_unlock_with_ad:
                loadRewardedAd();
                LockedTemplateListener lockedTemplateListener3 = this.lockedTemplateListener;
                if (lockedTemplateListener3 != null) {
                    lockedTemplateListener3.onSeeAdClicked();
                    return;
                }
                return;
            default:
                break;
        }
    }
}
