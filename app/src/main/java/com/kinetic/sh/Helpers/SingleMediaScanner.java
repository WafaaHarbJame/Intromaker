package com.kinetic.sh.Helpers;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private final File mFile;
    private final MediaScannerConnection mMs;

    public SingleMediaScanner(Context context, File file) {
        this.mFile = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mMs = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    public void onMediaScannerConnected() {
        this.mMs.scanFile(this.mFile.getAbsolutePath(), null);
    }

    public void onScanCompleted(String str, Uri uri) {
        this.mMs.disconnect();
    }
}
