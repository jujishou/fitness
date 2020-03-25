package com.lgh.fitness;


import com.lgh.fitness.ad.AdListener;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugins.SplashAdViewFlutterPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity implements AdListener {
    private MethodCall methodCall;
    private MethodChannel.Result result;

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        SplashAdViewFlutterPlugin.registerWith(flutterEngine, this, this);

        initBackTop(flutterEngine);
    }

    //注册返回到手机桌面事件
    private void initBackTop(FlutterEngine flutterEngine) {
        new MethodChannel(flutterEngine.getDartExecutor(), "fitness.flutter.io/ad").setMethodCallHandler(
                (MethodCall methodCall, MethodChannel.Result result) -> {
                    this.methodCall = methodCall;
                    this.result = result;

                    if (methodCall.method.equals("back")) {
                        moveTaskToBack(false);
                        result.success(true);
                    }
                }
        );
    }

    @Override
    public void onAdFinished(String msg) {
        if (methodCall == null || result == null) return;

        if (methodCall.method.equals("adFinish")) {
            result.success(true);
        }
    }
}
