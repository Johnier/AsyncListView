package com.yztc.lx.qiubai.utils;

import com.alibaba.fastjson.JSON;
import com.yztc.lx.qiubai.bean.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Lx on 2016/8/13.
 */

public class ParserJson {
    public static List<Item> parserJsonToList(String jsonString) {
        List<Item> items = null;
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray arr = obj.getJSONArray("items");
            items = JSON.parseArray(arr.toString(), Item.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
