package com.kiloway.androidlibrary.webview;

import android.content.Context;

/**
 * @ClassName: IAppWebInterFace
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/2 14:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/2 14:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IAppWebInterFace {
    void onGotEpc(String epc);
    void onGotAppId();
    String getIMEI();
}
