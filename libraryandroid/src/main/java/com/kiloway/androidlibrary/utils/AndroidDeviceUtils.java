package com.kiloway.androidlibrary.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.UUID;

/**
 * @ClassName: AndroidDevice
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/6/10 11:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/10 11:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AndroidDeviceUtils {
    private static AndroidDeviceUtils instance;
    private AndroidDeviceUtils (){}

    public static AndroidDeviceUtils getInstance() {
        if (instance == null) {
            instance = new AndroidDeviceUtils();
        }
        return instance;
    }
    public  final String APP_UUID = "APP_UUID";//Android唯一标识

    public  String getDeviceId(Context context) {
        String uuid = getDeviceUUid(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = getAppUUid(context);
        }
        return uuid;
    }

    private  String getAndroidID(Context context) {
         String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }
    private  String getDeviceUUid(Context context)
    {
        String androidId = getAndroidID(context);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32));
        return deviceUuid.toString();
    }

    private  String getUUID(Context context){
        return PreferencesUtils.getString(context, APP_UUID);
    }
    private  String putUUID(Context context,String UUID){
        return PreferencesUtils.getString(context, APP_UUID,UUID);
    }
    private  String getAppUUid(Context context) {
        String uuid = getUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            putUUID(context,uuid);
        }
        return uuid;
    }
}
