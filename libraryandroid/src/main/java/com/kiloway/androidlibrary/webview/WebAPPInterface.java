package com.kiloway.androidlibrary.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.kiloway.androidlibrary.activity.ReaderSettingActivity;
import com.kiloway.androidlibrary.base.BaseApplication;
import com.kiloway.commonscanner.base.AllDevice;

/**
 * @ClassName: WebAPPInterface
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/1 17:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/1 17:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class WebAPPInterface implements IWebAppInterFace{
    private Context context;
    private IAppWebInterFace iAppWebInterFace;
    public WebAPPInterface(Context context,IAppWebInterFace iAppWebInterFace){
        this.context = context;
        this.iAppWebInterFace = iAppWebInterFace;
    }
    @JavascriptInterface
    @Override
    public void startInventory() {
        BaseApplication.instance.reader.inventoryTag();
    }
    @JavascriptInterface
    @Override
    public void stopInventory() {
        BaseApplication.instance.reader.stopReading();
    }
    @JavascriptInterface
    @Override
    public void startReaderConfigActivity() {
        context.startActivity(new Intent(context, ReaderSettingActivity.class));
    }
    @JavascriptInterface
    @Override
    public String getIMEI() {
        return iAppWebInterFace.getIMEI();
    }

}
