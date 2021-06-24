package com.kiloway.androidlibrary.webview;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * @ClassName: WebViewInitImpl
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/1 17:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/1 17:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewInitImpl implements IWebViewInit{
    private Activity mActivity;
    private String JS_NAME ="AndroidFunction";
    private String JS_URL ="http://192.168.1.98:4200";
    private WebAPPInterface webAPPInterface;
    private IAppWebInterFace appWebInterFaceImp;
    public WebViewInitImpl(Activity activity) {
        mActivity = activity;
    }
    @Override
    public WebView initWebView(WebView webView,String JS_URL) {
        if (!TextUtils.isEmpty(JS_URL)){
            this.JS_URL = JS_URL;
        }
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSetting = webView.getSettings();
        webSetting.setUserAgentString( webSetting.getUserAgentString());
        webSetting.setDatabaseEnabled(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setTextSize(WebSettings.TextSize.NORMAL);
        // ===设置JS可用
        webSetting.setJavaScriptEnabled(true);
        // JS打开窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // ===设置JS可用
        // 可以访问文件
        webSetting.setAllowFileAccess(true);
        // ===缩放可用
        webSetting.setSupportZoom(true);
        webSetting.setDisplayZoomControls(true); //隐藏原生的缩放控件
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //设置缩放功能   //能不能缩放 取决于网页设置
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        // ===缩放可用
        // 支持多窗口
        webSetting.setSupportMultipleWindows(true);
        // ===============缓存
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);// 决定是否从网络上取数据。
        webSetting.setAppCacheEnabled(true);
        // ===============缓存
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // ==定位
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        // ==定位
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 适配图片加载不出来的问题
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        appWebInterFaceImp = new AppWebInterFaceImp(mActivity,webView);
        webAPPInterface = new WebAPPInterface(mActivity,appWebInterFaceImp);
        webView.addJavascriptInterface(webAPPInterface,getWebJsName());
        webView.loadUrl(getWebUrl());
        return webView;
    }

    @Override
    public String getWebUrl() {
        return JS_URL;
    }

    @Override
    public String getWebJsName() {
        return JS_NAME;
    }

    @Override
    public IWebAppInterFace getWebAppInterface() {
        return webAPPInterface;
    }

    @Override
    public IAppWebInterFace getAppWebInterface() {
        return appWebInterFaceImp;
    }
}
