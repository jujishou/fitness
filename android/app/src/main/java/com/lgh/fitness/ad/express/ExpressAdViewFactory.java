package com.lgh.fitness.ad.express;

import android.app.Activity;
import android.content.Context;

import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.reward.RewardVideoAdView;

import java.util.Map;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class ExpressAdViewFactory extends PlatformViewFactory {
    private final AdListener listener;
    private Activity activity;

    public ExpressAdViewFactory(AdListener listener, Activity activity) {
        super(StandardMessageCodec.INSTANCE);
        this.listener = listener;
        this.activity = activity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        return new ExpressAdView(context, listener, activity);
    }
}
