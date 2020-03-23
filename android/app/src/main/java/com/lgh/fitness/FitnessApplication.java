package com.lgh.fitness;



import com.lgh.fitness.ad.TTAdManagerHolder;

import io.flutter.app.FlutterApplication;

/**
 * Create by lgh on 3/23/2020
 */
@SuppressWarnings("unused")
public class FitnessApplication extends FlutterApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        TTAdManagerHolder.init(this);
    }

}
