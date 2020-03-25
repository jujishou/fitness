package io.flutter.plugins;

import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.SplashAdViewFactory;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry;
import io.flutter.plugin.common.PluginRegistry;

public class SplashAdViewFlutterPlugin {

    public static void registerWith(FlutterEngine flutterEngine, AdListener listener) {
        final String key = SplashAdViewFlutterPlugin.class.getCanonicalName();

        ShimPluginRegistry registry = new ShimPluginRegistry(flutterEngine);
        if (registry.hasPlugin(key)) return;

        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/adview",
                new SplashAdViewFactory(listener));

        registrar.platformViewRegistry().registerViewFactory("plugins.lgh.top/reward_video",
                new SplashAdViewFactory(listener));

    }
}
