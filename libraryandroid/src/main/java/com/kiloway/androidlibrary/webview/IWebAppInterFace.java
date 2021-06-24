package com.kiloway.androidlibrary.webview;

/**
 * @ClassName: IwebAppInterFace
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/1 17:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/1 17:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IWebAppInterFace {
    //开始扫描
    void startInventory();
    //停止扫描
    void stopInventory();
    //跳转到读写去配置页面
    void startReaderConfigActivity();
    String getIMEI();
}
