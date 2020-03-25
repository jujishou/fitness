package io.flutter.plugins;

import android.app.Activity;

import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.express.ExpressAdViewFactory;
import com.lgh.fitness.ad.native_ad.NativeAdView;
import com.lgh.fitness.ad.native_ad.NativeAdViewFactory;
import com.lgh.fitness.ad.reward.RewardVideoAdViewFactory;
import com.lgh.fitness.ad.splash.SplashAdViewFactory;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry;
import io.flutter.plugin.common.PluginRegistry;

public class SplashAdViewFlutterPlugin {

    public static void registerWith(FlutterEngine flutterEngine, AdListener listener, Activity activity) {
        final String key = SplashAdViewFlutterPlugin.class.getCanonicalName();

        ShimPluginRegistry registry = new ShimPluginRegistry(flutterEngine);
        if (registry.hasPlugin(key)) return;

        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/splash_ad",
                new SplashAdViewFactory(listener));

        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/reward_video",
                new RewardVideoAdViewFactory(listener, activity));

        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/express_ad",
                new ExpressAdViewFactory(listener, activity));

        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/native_ad",
                new NativeAdViewFactory(listener, activity));

    }
}
