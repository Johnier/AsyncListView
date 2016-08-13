package com.yztc.lx.qiubai.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.yztc.lx.qiubai.adapter.MyBaseAdapter;
import com.yztc.lx.qiubai.bean.Item;
import com.yztc.lx.qiubai.utils.HttpUtils;
import com.yztc.lx.qiubai.utils.ParserJson;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载文字的异步任务
 * Created by Lx on 2016/8/13.
 */

public class DownloadAsync extends AsyncTask<String, Integer, List<Item>> {
    private static final String TAG = "DownloadAsync";
    private ProgressDialog dialog;
    private Context mContext;
    private List<Item> totalItem;
    private int pageIndex;
    private ListView lv;
    private MyBaseAdapter adapter;

    public DownloadAsync(Context mContext, List<Item> totalItem, int pageIndex, ListView lv, MyBaseAdapter adapter) {
        this.mContext = mContext;
        this.totalItem = totalItem;
        this.pageIndex = pageIndex;
        this.adapter = adapter;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(mContext);
        dialog.setTitle("提示信息");
        dialog.setMessage("请稍等...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected List<Item> doInBackground(String... params) {
        List<Item> items = null;
        if (HttpUtils.isNetConn(mContext)) {
            items = new ArrayList<>();
            byte b[] = HttpUtils.DownloadFromNet(params[0]);
            String jsonString = new String(b);
            //Log.d(TAG, "doInBackground: " + jsonString);
            items = ParserJson.parserJsonToList(jsonString);
            //Log.d(TAG, "doInBackground: " + items);
        } else {
            Toast.makeText(mContext, "请检查网络设置", Toast.LENGTH_SHORT).show();
        }
        return items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        if (items != null) {
            totalItem.addAll(items);
            if (pageIndex == 1) {
                adapter.setLists(totalItem);
                lv.setAdapter(adapter);
            } else {
                adapter.onDateChanged(totalItem);
            }
        } else {
            Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }

}
