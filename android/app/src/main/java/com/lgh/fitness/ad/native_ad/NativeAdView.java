package com.lgh.fitness.ad.native_ad;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.lgh.fitness.R;
import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.TTAdManagerHolder;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.platform.PlatformView;

/**
 * 插屏广告
 */
public class NativeAdView implements PlatformView {
    private FrameLayout mBannerContainer;
    private TTAdNative mTTAdNative;
    private Activity activity;
    //是否强制跳转到主页面
    private static final String TAG = "NativeAdView";

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private String mCodeId = "945108324";
    private AdListener listener;
    private TTNativeExpressAd mTTAd;

    NativeAdView(Context context, AdListener listener, Activity activity) {

        mBannerContainer = new FrameLayout(context);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        this.listener = listener;
        this.activity = activity;

        initTTSDKConfig(context);
    }

    private void initTTSDKConfig(Context c) {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(c);
        TTAdManagerHolder.get().requestPermissionIfNecessary(c);

        loadExpressAd();
    }


    private void loadExpressAd() {
        mBannerContainer.removeAllViews();
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(mCodeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(600, 150) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "load error : " + code + ", " + message);
                mBannerContainer.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
                mTTAd.render();
            }
        });
    }


    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                Log.d(TAG,  "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.d(TAG,  "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:" );
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" );
                //返回view的宽高 单位 dp
                mBannerContainer.removeAllViews();
                mBannerContainer.addView(view);
            }
        });
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                Log.d(TAG,  "点击开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onInstalled(String fileName, String appName) {
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
            }
        });
    }


    @Override
    public View getView() {
        return mBannerContainer;
    }

    @Override
    public void dispose() {
        mBannerContainer.removeAllViews();
        mBannerContainer = null;
    }
}
