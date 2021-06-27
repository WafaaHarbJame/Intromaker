package com.kinetic.sh.Helpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.util.MimeTypes;

import java.io.File;

public class ShareIntent {
    private final Context context;
    private final File file;
    private Uri fileUri;
    private final String packagename;

    public ShareIntent(Context context2, String str, File file2) {
        this.context = context2;
        this.packagename = str;
        this.file = file2;
    }

    public void shareOnSpecificApp() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        this.fileUri = Uri.fromFile(this.file);
        AppCompatActivity appCompatActivity = (AppCompatActivity) this.context;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("kinetic/plain");
        intent.setPackage(this.packagename);
        intent.putExtra("android.intent.extra.TEXT", "#MadeWithIntroMaker");
        Toast.makeText(this.context, "Uri: " + this.fileUri.getPath(), Toast.LENGTH_LONG).show();
        Uri uri = this.fileUri;
        if (uri != null) {
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType(MimeTypes.VIDEO_MP4);
        }
        try {
            appCompatActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            showWarningDialog(appCompatActivity, "App not found");
        }
    }

    public void shareToAllApps() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        this.fileUri = Uri.parse(this.file.toString());
        AppCompatActivity appCompatActivity = (AppCompatActivity) this.context;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("kinetic/plain");
        intent.putExtra("android.intent.extra.TEXT", "#MadeWithIntroMaker");
        Uri uri = this.fileUri;
        if (uri != null) {
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType(MimeTypes.VIDEO_MP4);
        }
        try {
            appCompatActivity.startActivity(Intent.createChooser(intent, "Share video with..."));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            showWarningDialog(appCompatActivity, "App not found");
        }
    }

    private static void showWarningDialog(Context context2, String str) {
        new AlertDialog.Builder(context2).setMessage(str).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(true).create().show();
    }
}
