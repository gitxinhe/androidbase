package com.kiloway.androidlibrary.base;

import android.app.Application;
import android.util.Log;

import com.kiloway.commonscanner.base.AllDevice;
import com.kiloway.commonscanner.base.BeepUtil;
import com.kiloway.commonscanner.base.Device;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/23 11:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/23 11:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseApplication extends Application {
    public static BaseApplication instance;
    public static Device reader;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        reader =AllDevice.getDevice(this);
        initX5();
    }
    private void initX5(){
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("tag", "x5初始化结果====" + b);
            }
        });
    }
}
