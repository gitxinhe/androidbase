package com.kiloway.androidbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kiloway.androidlibrary.activity.ReaderSettingActivity;
import com.kiloway.androidlibrary.base.BaseActivity;
import com.kiloway.androidlibrary.base.BaseApplication;
import com.kiloway.commonscanner.base.Constant;
import com.kiloway.commonscanner.base.DeviceSetting;
import com.kiloway.commonscanner.model.EpcInfo;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.ll_select1_single)
    LinearLayout llSelect1Single;
    @BindView(R.id.ll_select2_single)
    LinearLayout llSelect2Single;
    @BindView(R.id.ll_province_select)
    LinearLayout llProvinceSelect;
    @BindView(R.id.ll_sure_select)
    LinearLayout llSureSelect;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R.id.ll_finger)
    LinearLayout llFinger;
    @BindView(R.id.iv_big_image)
    RoundedImageView ivBigImage;
    @BindView(R.id.ll_see_big_image)
    LinearLayout llSeeBigImage;
    @BindView(R.id.ll_config_service_url)
    LinearLayout llConfigServiceUrl;
    @BindView(R.id.ll_config_reader)
    LinearLayout llConfigReader;
    @BindView(R.id.ll_web_view)
    LinearLayout llWebView;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.ll_button)
    LinearLayout llButton;
    @BindView(R.id.ll_count_down)
    LinearLayout llCountDown;
    @BindView(R.id.tv_epc)
    TextView tvEpc;
    @BindView(R.id.btn_scan)
    Button btnScan;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initData() {
        DeviceSetting.setCurrentReader(context, Constant.KLWUH55EH2);
        //初始化读写器
        BaseApplication.instance.getReader().init(context);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onReceiveEpc(EpcInfo epcInfo) {
        super.onReceiveEpc(epcInfo);
        tvEpc.setText(epcInfo.getEpc());
    }

    @Override
    protected String initTitle() {
        return "示例";
    }

    boolean isScan;

    @OnClick({R.id.ll_loading, R.id.ll_select1_single,
            R.id.ll_select2_single, R.id.ll_province_select, R.id.ll_sure_select,
            R.id.ll_qrcode, R.id.ll_finger, R.id.iv_big_image, R.id.ll_see_big_image,
            R.id.ll_config_service_url, R.id.ll_config_reader, R.id.ll_web_view,
            R.id.ll_tab, R.id.ll_button, R.id.ll_count_down, R.id.btn_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                if (isScan) {
                    btnScan.setText("扫描");
                    isScan = false;
                    BaseApplication.instance.getReader().stopReading();
                } else {
                    btnScan.setText("停止");
                    isScan = true;
                    BaseApplication.instance.getReader().inventoryTag();
                }
                break;
            case R.id.ll_loading:

                break;
            case R.id.ll_select1_single:
                break;
            case R.id.ll_select2_single:
                break;
            case R.id.ll_province_select:
                break;
            case R.id.ll_sure_select:
                break;
            case R.id.ll_qrcode:
                break;
            case R.id.ll_finger:
                break;
            case R.id.iv_big_image:
                break;
            case R.id.ll_see_big_image:
                break;
            case R.id.ll_config_service_url:
                break;
            case R.id.ll_config_reader:
                startActivity(new Intent(context, ReaderSettingActivity.class));
                break;
            case R.id.ll_web_view:
                startActivity(new Intent(context, WebViewActivity.class));
                break;
            case R.id.ll_tab:
                break;
            case R.id.ll_button:
                break;
            case R.id.ll_count_down:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.instance.getReader().unitReader();
    }

}