package com.yztc.lx.qiubai.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yztc.lx.qiubai.R;
import com.yztc.lx.qiubai.adapter.MyBaseAdapter;
import com.yztc.lx.qiubai.async.DownloadAsync;
import com.yztc.lx.qiubai.bean.Item;
import com.yztc.lx.qiubai.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private int pageIndex = 1;
    private ListView lv_list;
    private List<Item> totalItems;
    private MyBaseAdapter adapter;
    private boolean isDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        totalItems = new ArrayList<>();
        adapter = new MyBaseAdapter(totalItems, MainActivity.this, lv_list);
        new DownloadAsync(MainActivity.this, totalItems, pageIndex, lv_list, adapter).execute(Constant.PATH + pageIndex);
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isDiv && scrollState == SCROLL_STATE_IDLE) {
                    pageIndex++;
                    Log.d(TAG, "onScrollStateChanged: "+pageIndex);
                    new DownloadAsync(MainActivity.this, totalItems, pageIndex, lv_list, adapter).execute(Constant.PATH+pageIndex);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDiv = ((firstVisibleItem + visibleItemCount) == totalItemCount);
                Log.d(TAG, "onScroll: "+isDiv);
            }
        });
    }

    private void initView() {
        lv_list = (ListView) findViewById(R.id.lv_list);
    }
}
