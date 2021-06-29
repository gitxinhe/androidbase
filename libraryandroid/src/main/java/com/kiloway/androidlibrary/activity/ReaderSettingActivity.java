package com.kiloway.androidlibrary.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.gpio.MainActivity;
import com.kiloway.androidlibrary.R;
import com.kiloway.androidlibrary.R2;
import com.kiloway.androidlibrary.base.BaseActivity;
import com.kiloway.androidlibrary.base.BaseApplication;
import com.kiloway.androidlibrary.base.PickerUtils;
import com.kiloway.androidlibrary.listener.OnSelectListener;
import com.kiloway.commonscanner.base.AllDevice;
import com.kiloway.commonscanner.base.Device;
import com.kiloway.commonscanner.base.DeviceSetting;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 10158 on 2020/9/3.
 */

public class ReaderSettingActivity extends BaseActivity {
    @BindView(R2.id.tv_reader)
    TextView tvReader;
    @BindView(R2.id.tv_power)
    TextView tvPower;
    Device device;
    public void initListener() {

    }

    @Override
    public void initData() {
        init();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        final Device device = BaseApplication.instance.reader;
        tvReader.setText(DeviceSetting.getDevice(context));
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int power = device.getPower();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPower.setText(power+"");
                    }
                });
            }
        }).start();
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reader_setting;
    }


    @Override
    public String initTitle() {
        return "读写器配置";
    }

    public void llReader(View view) {
        List<String> allDevice = AllDevice.getAllDevice();
        PickerUtils.showSingPicker(context, allDevice, new OnSelectListener() {
            @Override
            public void onSelect(String selectStr) {
                if (DeviceSetting.getDevice(context).equals(selectStr)) {
                    return;
                }
                reLoadDevice(selectStr);
                //DeviceSetting.setCurrentReader(context, selectStr);
              /*  final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //杀掉以前进程
                android.os.Process.killProcess(android.os.Process.myPid());*/
                /*finish();
                Intent it = new Intent("readerSetting");
                context.sendBroadcast(it);*/
            }
        });
    }

    private void reLoadDevice(String deviceName) {
        //设置读写器之前停止解除当前读写器的初始化
        BaseApplication.instance.reader.unitReader();
        /*Intent intent = new Intent(context, MainActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        Intent it = new Intent("readerSetting");
        context.sendBroadcast(it);
        //设置读写器
        AllDevice.setDevice(context,deviceName);
        BaseApplication.instance.reader = AllDevice.getDevice(context);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void llPower(View view) {
        final List<String> powers = device.getPowerList();
        PickerUtils.showSingPicker(context, powers, new OnSelectListener() {
            @Override
            public void onSelect(String selectStr) {
                if (DeviceSetting.getDevice(context).equals(selectStr)) {
                    return;
                }
                boolean isSuccess = device.setPower(Integer.valueOf(selectStr));
                if (isSuccess) {
                    showToast("设置成功");
                    tvPower.setText(selectStr);
                } else {
                    showToast("设置失败");
                }
            }
        });
    }
}
