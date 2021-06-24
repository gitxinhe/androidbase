package com.kiloway.androidlibrary.utils;

import android.content.Context;
import android.content.res.AssetManager;


import com.kiloway.androidlibrary.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: JsonParseUtils
 * @Description: java类作用描述
 * @Author: 何鑫
 * @CreateDate: 2021/1/15 15:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/15 15:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class JsonParseUtils {
    /**
     * 解析json填充集合
     */
    public static Province getProvinceList(Context context) {
        Province province = new Province();
        List<String> provinceBeanList = new ArrayList<>();
        ArrayList<String> cities;
        ArrayList<List<String>> cityBeanList = new ArrayList<>();
        ArrayList<String> district;
        ArrayList<List<String>> districts;
        ArrayList<List<List<String>>> districtBeanList = new ArrayList<>();
        String str = getJson(context, "province.json");
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
//                provinceBeanList.add(new ProvinceBean(provinceName));
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();
                //   声明存放城市的集合
                districts = new ArrayList<>();
                //声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();
                    // 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtBeanList.add(districts);
                //  将存放城市的集合放入集合
                cityBeanList.add(cities);
                province.provinceBeanList = provinceBeanList;
                province.cityBeanList = cityBeanList;
                province.districtBeanList = districtBeanList;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return province;
    }

    /**
     * 从asset目录下读取fileName文件内容
     *
     * @param fileName 待读取asset下的文件名
     * @return 得到省市县的String
     */
    private static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
