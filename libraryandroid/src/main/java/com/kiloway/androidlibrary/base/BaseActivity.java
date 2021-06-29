package com.kiloway.androidlibrary.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.kiloway.androidlibrary.R;
import com.kiloway.androidlibrary.R2;
import com.kiloway.androidlibrary.utils.CustomToast;
import com.kiloway.commonscanner.base.AllDevice;
import com.kiloway.commonscanner.base.BeepUtil;
import com.kiloway.commonscanner.base.Device;
import com.kiloway.commonscanner.model.EpcInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by 10158 on 2020/12/17.
 */

public abstract class BaseActivity extends SwipeBackActivity implements Device.OnEventListener{
    public static final String TAG = BaseActivity.class.getSimpleName();
    public BaseActivity context;
    public RelativeLayout mFraLayoutContent;
    @BindView(R2.id.ivBack)
    public ImageView ivBack;
    @BindView(R2.id.tv_title)
    public TextView tvTitle;
    @BindView(R2.id.iv_right_img)
    public ImageView ivRightImg;
    @BindView(R2.id.relTitleBar)
    RelativeLayout relTitleBar;
    @BindView(R2.id.line)
    View line;
    private FrameLayout mFraLayoutHeadView;
    private SwipeBackLayout mSwipeBackLayout;
    public Device reader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentViewResId());
        ButterKnife.bind(this);
        this.context = this;
        init();
        initEpcResult();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    public void initEpcResult(){
        BaseApplication.instance.reader.setOnEventListener(this);
    }
    @Override
    public void onTagReadedEvent(final EpcInfo info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("tag",info.getEpc());
                BeepUtil.INS.speak();
                onReceiveEpc(info);
            }
        });
    }
    @Override
    public void onAnyTagReadedEvent(final List<EpcInfo> infos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BeepUtil.INS.speak();
                onReceiveAnyEpc(infos);
            }
        });
    }
    public void onReceiveEpc(EpcInfo epcInfo){};
    public void onReceiveAnyEpc(List<EpcInfo> epcInfos){};
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
    private void init(){
        initSwipeBackLayout();
        initData();
        initListener();
        tvTitle.setText(initTitle());
    }
    private void initSwipeBackLayout(){
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
    public void hiddenBack(){
        ivBack.setVisibility(View.GONE);
    }
    public void setTitleBg(int resId){
        relTitleBar.setBackgroundResource(resId);
        tvTitle.setTextColor(getResources().getColor(R.color.white));
        line.setVisibility(View.GONE);
    }
    public void hiddenTitle(){
        mFraLayoutHeadView.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }

    public void showTitle(){
        mFraLayoutHeadView.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
    }
    public void onViewBackClick(View view){
        finish();
    }
    @Override
    public void setContentView(int resId) {
        if (resId == 0) {
            throw new RuntimeException("resId==-1 have u create your layout?");
        }
        super.setContentView(R.layout.activity_base);
        mFraLayoutContent = (RelativeLayout) findViewById(R.id.fraLayoutContent);
        mFraLayoutHeadView = (FrameLayout) findViewById(R.id.fraLayoutHeadView);
        //头部标题栏
        LayoutInflater.from(this).inflate(R.layout.layout_header_base, mFraLayoutHeadView, true);
        //内容填充页面
        LayoutInflater.from(this).inflate(resId, mFraLayoutContent, true);
    }

    protected abstract int getContentViewResId();

    protected abstract void initData();

    protected abstract void initListener();

    public void showToast(String content) {
        CustomToast.getInstance().showToast(context,content);
    }
    protected void showBackView(boolean isShow){
        ivBack.setVisibility(isShow? View.VISIBLE:View.GONE);
    }
    protected void showRightImage(int resId){
        ivRightImg.setVisibility(View.VISIBLE);
        ivRightImg.setImageResource(resId);
    }
    protected abstract String initTitle();
    public void clearStack(Class c){
        Intent intent = new Intent(this,c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        BeepUtil.INS.initSound(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       BeepUtil.INS.destroy();
    }
}
