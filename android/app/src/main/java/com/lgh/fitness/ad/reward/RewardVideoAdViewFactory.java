package com.lgh.fitness.ad.reward;

import android.app.Activity;
import android.content.Context;

import com.lgh.fitness.ad.AdListener;

import java.util.Map;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class RewardVideoAdViewFactory extends PlatformViewFactory {
    private final AdListener listener;
    private Activity activity;

    public RewardVideoAdViewFactory(AdListener listener,Activity activity) {
        super(StandardMessageCodec.INSTANCE);
        this.listener = listener;
        this.activity=activity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        return new RewardVideoAdView(context, listener,activity);
    }
}
