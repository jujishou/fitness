package com.lgh.fitness.ad.native_ad;

import android.app.Activity;
import android.content.Context;

import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.express.ExpressAdView;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class NativeAdViewFactory extends PlatformViewFactory {
    private final AdListener listener;
    private Activity activity;

    public NativeAdViewFactory(AdListener listener, Activity activity) {
        super(StandardMessageCodec.INSTANCE);
        this.listener = listener;
        this.activity = activity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        return new NativeAdView(context, listener, activity);
    }
}
