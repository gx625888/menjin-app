package com.example.yunxuan.menjin.utils;

import com.example.yunxuan.menjin.bean.NewData;
import com.example.yunxuan.menjin.bean.ResponceData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ChangeDataUtils {


    public static ResponceData handleonlylistResponse(String response) {
        ResponceData data = new ResponceData();

        if (getDataBean(response) != null) {
            data.setList(getDataBean(response));
            return data;
        } else {
            return null;
        }
    }

    public static ArrayList<NewData> handlelistResponse(String response) {
        Gson gson = new Gson();
        ArrayList<NewData> json_to_list = gson.fromJson(response, new TypeToken<List<NewData>>(){}.getType());
        return json_to_list;
    }

    private static ArrayList<NewData> getDataBean(String response) {
        ArrayList<NewData> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            //遍历这个json格式的数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject string = null;
                try {
                    string = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                //添加到集合里面去
                list.add(new Gson().fromJson(String.valueOf(string), NewData.class));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



}
