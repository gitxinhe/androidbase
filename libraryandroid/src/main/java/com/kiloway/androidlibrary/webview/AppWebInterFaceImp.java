package com.kiloway.androidlibrary.webview;


import android.content.Context;

import com.kiloway.androidlibrary.utils.AndroidDeviceUtils;
import com.tencent.smtt.sdk.WebView;

/**
 * @ClassName: AppWebInterFaceImp
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/2 14:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/2 14:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AppWebInterFaceImp implements IAppWebInterFace {
    public WebView webView;
    private Context context;
    public AppWebInterFaceImp(Context context, WebView webView){
        this.webView = webView;
        this.context = context;
    }
    @Override
    public void onGotEpc(String epc) {
        webView.loadUrl("javascript:onGotEpc('" + epc + "')");
    }

    @Override
    public void onGotAppId() {

    }

    @Override
    public String getIMEI() {
        return AndroidDeviceUtils.getInstance().getDeviceId(context);
    }

}
