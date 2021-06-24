package com.kiloway.androidlibrary.webview;

import com.tencent.smtt.sdk.WebView;

/**
 * WebView初始化接口定义
 */

public interface IWebViewInit {
    /**
     * 1. 初始化和设置WebView
     */
    WebView initWebView(WebView webView,String webUrl);
    String getWebUrl();
    String getWebJsName();
    IWebAppInterFace getWebAppInterface();
    IAppWebInterFace getAppWebInterface();
}