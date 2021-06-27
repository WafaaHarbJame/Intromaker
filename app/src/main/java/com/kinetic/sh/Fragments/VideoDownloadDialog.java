package com.kinetic.sh.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.downloader.Error;
import com.kinetic.sh.Helpers.VideoDownloadHelper;
import com.kinetic.sh.R;

public class VideoDownloadDialog extends DialogFragment implements View.OnClickListener {
    private Button cancel;
    private AlertDialog dialog;
    private ProgressBar progressBar;
    private TextView progressText;
    private Button resumeBtn;
    private VideoDownloadHelper videoDownloadHelper;

    public void setVideoDownloadHelper(VideoDownloadHelper videoDownloadHelper2) {
        this.videoDownloadHelper = videoDownloadHelper2;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        View inflate = requireActivity().getLayoutInflater().inflate(R.layout.dialog_video_download, null);
        this.cancel = inflate.findViewById(R.id.dvd_btn_cancel);
        this.progressBar = inflate.findViewById(R.id.dvd_progress);
        this.progressText = inflate.findViewById(R.id.progress_in_mb_text_view);
        this.resumeBtn = inflate.findViewById(R.id.dvd_btn_retry);
        this.cancel.setOnClickListener(this);
        this.resumeBtn.setOnClickListener(this);
        this.progressText.setVisibility(View.GONE);
        this.resumeBtn.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(inflate.getContext());
        builder.setView(inflate);
        AlertDialog create = builder.create();
        this.dialog = create;
        return create;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dvd_btn_cancel) {
            VideoDownloadHelper videoDownloadHelper2 = this.videoDownloadHelper;
            if (videoDownloadHelper2 != null) {
                videoDownloadHelper2.cancel();
                dismiss();
                return;
            }
            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
        } else if (id == R.id.dvd_btn_retry) {
            this.resumeBtn.setVisibility(View.GONE);
            this.videoDownloadHelper.retry();
        }
    }

    public void setProgress(int i, String str) {
        ProgressBar progressBar2 = this.progressBar;
        if (progressBar2 != null) {
            progressBar2.setIndeterminate(false);
            this.progressText.setVisibility(View.VISIBLE);
            this.progressBar.setProgress(i);
            this.progressText.setText(str);
        }
    }

    public void onError(Error error) {
        this.progressBar.setIndeterminate(true);
        this.progressText.setText("Error while downloading video");
        this.resumeBtn.setVisibility(View.VISIBLE);
    }
}
