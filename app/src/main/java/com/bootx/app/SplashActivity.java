package com.bootx.app;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.youxiao.ssp.ad.bean.SSPAd;
import com.youxiao.ssp.ad.core.AdClient;
import com.youxiao.ssp.ad.listener.AdLoadAdapter;


/**
 * 开屏广告，默认全屏
 */
public class SplashActivity extends AppCompatActivity {

    // 展示样式示例：全屏、非全屏，底部自定义内容样式
    public static final String SHOW_STYLE = "SHOW_STYLE";
    // 非全屏
    public static final int SHOW_STYLE_NOT_FULL = 0x11;
    // 底部自定义内容
    public static final int SHOW_STYLE_CUSTOM_SCREEN = 0x12;

    // 动态权限请求吗
    private static final int REQUEST_CODE = 0x02;
    // 动态权限
    private String[] mDynamicPermissions;
    // 广告区域容器
    private ViewGroup mAdLayout;
    // 是否跳转主页
    private boolean canSkip = false;
    // 展示样式
    private int showStyle;
    // 倒计时
    private TextView countDownTV;
    // 自定义宣传内容
    private FrameLayout customLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAdLayout = findViewById(R.id.ad_layout);
        customLayout = findViewById(R.id.custom_layout);

        if (getIntent() != null) {
            showStyle = getIntent().getIntExtra(SHOW_STYLE, 0);
        }

        // 版本判断，6.0以上进行动态权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        } else {
            requestSplashAd();
        }
    }

    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        mDynamicPermissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
        requestPermissions(mDynamicPermissions, REQUEST_CODE);
    }

    /**
     * 权限请求结果回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < Math.min(grantResults.length, mDynamicPermissions.length); i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (sb.length() == 0) {
                        sb.append("请同意必要权限:");
                    }
                    sb.append("\n" + mDynamicPermissions[i]);
                }
                if (sb.length() > 0) {
                    Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            requestSplashAd();
        }
    }

    /**
     * 请求开屏广告
     */
    private void requestSplashAd() {
        // 展示样式
        switch (showStyle) {
            case SHOW_STYLE_NOT_FULL:
                customLayout.setVisibility(View.INVISIBLE);
                break;
            case SHOW_STYLE_CUSTOM_SCREEN:
                customLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        //初始化广告客户端实例
        AdClient adClient = new AdClient(this);
        adClient.requestSplashAd(mAdLayout, "12902", new AdLoadAdapter() {
            @Override
            public void onAdShow(SSPAd sspAd) {
                super.onAdShow(sspAd);
                if (showStyle == SHOW_STYLE_NOT_FULL) {
                    Toast.makeText(SplashActivity.this, "开屏广告加载成功(非全屏展示)", Toast.LENGTH_SHORT).show();
                } else if (showStyle == SHOW_STYLE_CUSTOM_SCREEN) {
                    Toast.makeText(SplashActivity.this, "开屏广告加载成功(底部自定义内容样式)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SplashActivity.this, "开屏广告加载成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                super.onError(i, s);
                //获取广告失败，跳转主页
                gotoMainActivity();
            }

            @Override
            public void onAdDismiss(SSPAd ad) {
                super.onAdDismiss(ad);
                //广告关闭（开屏广告展示时间到或用户点击跳转），跳转主页
                gotoMainActivity();
            }
        });
    }

    /**
     * 跳转至主页
     */
    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canSkip) {
            gotoMainActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        canSkip = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
