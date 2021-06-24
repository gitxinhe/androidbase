package com.kiloway.androidlibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kiloway.androidlibrary.R;
import com.kiloway.androidlibrary.listener.OnSelectListener;
import com.kiloway.androidlibrary.model.Province;
import com.kiloway.androidlibrary.utils.JsonParseUtils;


import java.util.List;

/**
 * @ClassName: PickerUtils
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/1/15 16:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/15 16:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PickerUtils {
    public static void showCityPicker(Context context, final OnSelectListener listener){
        final Province province =  JsonParseUtils.getProvinceList(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city = province.provinceBeanList.get(options1);
                String address; //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address =  province.provinceBeanList.get(options1) + "-" + province. districtBeanList.get(options1).get(options2).get(options3);
                } else {
                    address = province.provinceBeanList.get(options1) + "-" +
                              province.cityBeanList.get(options1).get(options2)+"-" +
                              province.districtBeanList.get(options1).get(options2).get(options3);
                }
                if (listener!=null){
                    listener.onSelect(address);
                }
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setContentTextSize(18)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        pvOptions.setPicker(province.provinceBeanList, province.cityBeanList,province.districtBeanList);//添加数据源
        Dialog mDialog = pvOptions.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvOptions.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
        pvOptions.show();
    }
    public static void showSingPicker(Context context, final List<String> data, final OnSelectListener listener){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (listener!=null){
                    listener.onSelect(data.get(options1));
                }
            }
        }).setCancelColor(context.getResources().getColor(R.color.appcolor))
                .setSubmitColor(context.getResources().getColor(R.color.appcolor))
                .setLineSpacingMultiplier(2)
                .setSubmitText("确定")
                .setCancelText("取消")
                .build();
        pvOptions.setPicker(data);
        pvOptions.show();
    }
}
