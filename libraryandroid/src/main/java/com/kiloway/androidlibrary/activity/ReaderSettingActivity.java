package com.kiloway.androidlibrary.activity;

import android.view.View;
import android.widget.TextView;

import com.kiloway.androidlibrary.R;
import com.kiloway.androidlibrary.R2;
import com.kiloway.androidlibrary.base.BaseActivity;
import com.kiloway.androidlibrary.base.BaseApplication;
import com.kiloway.androidlibrary.base.PickerUtils;
import com.kiloway.androidlibrary.listener.OnSelectListener;
import com.kiloway.commonscanner.base.AllDevice;
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
        tvReader.setText(DeviceSetting.getDevice(this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int power = BaseApplication.instance.getReader().getPower();
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
                /*if (DeviceSetting.getDevice(context).equals(selectStr)) {
                    return;
                }*/
                BaseApplication.instance.getReader().unitReader();
                DeviceSetting.setCurrentReader(context, selectStr);
                BaseApplication.instance.getReader().init(context);
                init();
            }
        });
    }

    public void llPower(View view) {
        final List<String> powers = BaseApplication.instance.getReader().getPowerList();
        PickerUtils.showSingPicker(context, powers, new OnSelectListener() {
            @Override
            public void onSelect(String selectStr) {
                if (DeviceSetting.getDevice(context).equals(selectStr)) {
                    return;
                }
                boolean isSuccess = BaseApplication.instance.getReader().setPower(Integer.valueOf(selectStr));
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
