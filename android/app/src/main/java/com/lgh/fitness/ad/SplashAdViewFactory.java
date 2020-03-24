package com.lgh.fitness.ad;

import android.content.Context;

import java.util.Map;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class SplashAdViewFactory extends PlatformViewFactory {
    private final AdListener listener;

    public SplashAdViewFactory(AdListener listener) {
        super(StandardMessageCodec.INSTANCE);
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new SplashAdView(context, listener, id, params);
    }
}
