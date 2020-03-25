package com.lgh.fitness.ad;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class RewardVideoAdViewFactory extends PlatformViewFactory {
    private final AdListener listener;

    public RewardVideoAdViewFactory(AdListener listener) {
        super(StandardMessageCodec.INSTANCE);
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new RewardVideoAdView(context, listener, id, params);
    }
}
