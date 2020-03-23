package com.lgh.fitness;


import com.lgh.fitness.ad.SplashAdViewFlutterPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    //通讯名称,回到手机桌面
    private final String chanel = "android/back/desktop";
    //返回手机桌面事件
    static final String eventBackDesktop = "backDesktop";

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        SplashAdViewFlutterPlugin.registerWith(flutterEngine);

        initBackTop(flutterEngine);
    }

    //注册返回到手机桌面事件
    private void initBackTop(FlutterEngine flutterEngine) {
        new MethodChannel(flutterEngine.getDartExecutor(), chanel).setMethodCallHandler(
                (methodCall, result) -> {
                    if (methodCall.method.equals(eventBackDesktop)) {
                        moveTaskToBack(false);
                        result.success(true);
                    }
                }
        );
    }
}
