package com.kinetic.sh.Helpers;

import android.content.Context;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.kinetic.sh.R;

import java.io.File;

class CacheDataSourceFactory implements DataSource.Factory {
    private final Context context;
    private final DefaultDataSourceFactory defaultDatasourceFactory;
    private final long maxCacheSize;
    private final long maxFileSize;

    CacheDataSourceFactory(Context context2, long j, long j2) {
        this.context = context2;
        this.maxCacheSize = j;
        this.maxFileSize = j2;
        String userAgent = Util.getUserAgent(context2, context2.getString(R.string.app_name));
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        this.defaultDatasourceFactory = new DefaultDataSourceFactory(this.context, defaultBandwidthMeter, new DefaultHttpDataSourceFactory(userAgent, defaultBandwidthMeter));
    }

    public DataSource createDataSource() {
        SimpleCache simpleCache = new SimpleCache(new File(this.context.getCacheDir(), "media"), new LeastRecentlyUsedCacheEvictor(this.maxCacheSize));
        return new CacheDataSource(simpleCache, this.defaultDatasourceFactory.createDataSource(), new FileDataSource(), new CacheDataSink(simpleCache, this.maxFileSize), 3, null);
    }
}
