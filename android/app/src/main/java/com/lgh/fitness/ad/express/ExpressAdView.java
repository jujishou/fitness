package com.lgh.fitness.ad.express;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.TTAdManagerHolder;

import java.util.List;

import io.flutter.plugin.platform.PlatformView;

/**
 * 插屏广告
 */
public class ExpressAdView implements PlatformView {
    private FrameLayout mSplashContainer;
    private TTAdNative mTTAdNative;
    //是否强制跳转到主页面
    private static final String TAG = "RewardVideoAdView";

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private String mCodeId = "945108176";
    private AdListener listener;
    private TTNativeExpressAd mTTAd;

    ExpressAdView(Context context, AdListener listener, Activity activity) {

        mSplashContainer = new FrameLayout(context);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        this.listener = listener;

        initTTSDKConfig(context, activity);
    }

    private void initTTSDKConfig(Context c, Activity activity) {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(c);
        TTAdManagerHolder.get().requestPermissionIfNecessary(c);

        loadExpressAd(activity);
    }

    private void loadExpressAd(Activity activity) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(mCodeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(300, 300) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "load error : " + code + ", " + message);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd, activity);
                mTTAd.render();
            }
        });
    }


    private void bindAdListener(TTNativeExpressAd ad, Activity activity) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                Log.d(TAG, "广告关闭");
            }

            @Override
            public void onAdClicked(View view, int type) {
                Log.d(TAG, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.d(TAG, "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.d(TAG, "render fail:");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.d(TAG, "render suc:");
                //返回view的宽高 单位 dp
                mTTAd.showInteractionExpressAd(activity);

            }
        });

        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                Log.d(TAG, "点击开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "下载中，点击暂停");
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "下载暂停，点击继续");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "下载失败，点击重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                Log.d(TAG, "安装完成，点击图片打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                Log.d(TAG, "点击安装");
            }
        });
    }

    private void goFlutter(String msg) {
        listener.onAdFinished(msg);
        dispose();
    }

    @Override
    public View getView() {
        return mSplashContainer;
    }

    @Override
    public void dispose() {
        mSplashContainer.removeAllViews();
        mSplashContainer = null;
    }
}
