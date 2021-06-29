package com.kiloway.androidbase;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.kiloway.androidlibrary.base.BaseActivity;
import com.kiloway.androidlibrary.base.BaseApplication;
import com.kiloway.androidlibrary.webview.WebViewInitImpl;
import com.kiloway.commonscanner.model.EpcInfo;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: WebActivity
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/1 17:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/1 17:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    com.tencent.smtt.sdk.WebView mWebView;
    private WebViewInitImpl webViewInit;
    private String jsUrl = "http://192.168.1.78:7004";
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void onReceiveEpc(EpcInfo epcInfo) {
        super.onReceiveEpc(epcInfo);
        String epc = epcInfo.getEpc();
        webViewInit.getAppWebInterface().onGotEpc(epc);
    }
    @Override
    public void onReceiveAnyEpc(List<EpcInfo> infos) {
        super.onReceiveAnyEpc(infos);
    }
    @Override
    protected void initData() {
        hiddenTitle();
        webViewInit = new WebViewInitImpl(this);
        mWebView = webViewInit.initWebView(mWebView,jsUrl);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        //初始化读写器
        //BaseApplication.instance.getReader().init(context);
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected String initTitle() {
        return null;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String url = this.mWebView.getUrl();
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            //与上次点击返回键时刻作差
            if (url.contains("menu")) {
                finish();
            } else {
                mWebView.goBack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView!=null)
            mWebView.destroy();
        super.onDestroy();
        //BaseApplication.instance.reader.unitReader();
    }
}
