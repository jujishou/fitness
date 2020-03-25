package com.lgh.fitness.ad.reward;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.MainThread;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.lgh.fitness.ad.AdListener;
import com.lgh.fitness.ad.TTAdManagerHolder;

import java.util.Map;

import io.flutter.plugin.platform.PlatformView;

/**
 * 激励视频广告
 */
public class RewardVideoAdView implements PlatformView {
    private FrameLayout mSplashContainer;
    private TTAdNative mTTAdNative;
    //是否强制跳转到主页面
    private static final String TAG = "RewardVideoAdView";

    private String mCodeId = "945107791";
    private AdListener listener;
    private TTRewardVideoAd mttRewardVideoAd;

    RewardVideoAdView(Context context, AdListener listener, Activity activity) {

        mSplashContainer = new FrameLayout(context);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        this.listener = listener;

        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(context);
        loadAd(activity);
    }

    private void loadAd(Activity activity) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;

        //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
        adSlot = new AdSlot.Builder()
                .setCodeId(mCodeId)
                .setSupportDeepLink(true)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(3)  //奖励的数量
                .setUserID("user123")//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(TTAdConstant.VERTICAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "onError: " + code + ", " + String.valueOf(message));
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "onRewardVideoCached");
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                    //该方法直接展示广告
//                    mttRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this);

                    //展示广告，并传入广告展示的场景
                    mttRewardVideoAd.showRewardVideoAd(activity, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRewardVideoAd = null;
                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "onRewardVideoAdLoad");

                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.e(TAG, "onAdShow: ");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.e(TAG, "onAdVideoBarClick: ");
                    }

                    @Override
                    public void onAdClose() {
                        Log.e(TAG, "onAdClose: ");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.e(TAG, "onVideoComplete: ");
                        goFlutter("completed");
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "onVideoError: ");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Log.e(TAG, "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "onSkippedVideo: ");
                        goFlutter("skipped");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        Log.e(TAG, "onIdle: ");
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.e(TAG, "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
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
