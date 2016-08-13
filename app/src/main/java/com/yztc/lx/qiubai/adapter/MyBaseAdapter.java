package com.yztc.lx.qiubai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yztc.lx.qiubai.R;
import com.yztc.lx.qiubai.async.LoadImageAsync;
import com.yztc.lx.qiubai.bean.Item;
import com.yztc.lx.qiubai.utils.Constant;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lx on 2016/8/13.
 */

public class MyBaseAdapter extends BaseAdapter {

    private static final String TAG = "MyBaseAdapter";
    private List<Item> lists;
    private Context mContext;
    private ListView lv;
    //定义缓存Map
    Map<String, Bitmap> cashImg = new HashMap<>();
    ViewHolder holder;

    public void setLists(List<Item> lists) {
        this.lists = lists;
    }

    public MyBaseAdapter(List<Item> lists, Context mContext, ListView lv) {
        this.lists = lists;
        this.mContext = mContext;
        this.lv = lv;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = lists.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Log.d(TAG, "getView: " + item);
        if (item.getUser() != null) {
            holder.tv_username.setText(item.getUser().getLogin());
        }
        holder.tv_content.setText(item.getContent());
        //图片路径
        String imgUrl = item.getImage();
        final String path = Constant.BASE_IMG_PATH + item.getId().substring(0, 5) + File.separator + item.getId() + File.separator + Constant.MIDDLE_IMG_PATH + File.separator + imgUrl;
        if (holder.iv_image != null) {
            holder.iv_image.setTag(path);
        }
        //没有图片就不显示
        if (imgUrl != null) {
            if (cashImg.containsKey(path)) {
                //holder.iv_image.setVisibility(View.VISIBLE);
                if(holder.iv_image!=null) {
                    holder.iv_image.setImageBitmap(cashImg.get(path));
                }
                Log.d(TAG, "getView-cashMap: " + cashImg);
            } else {
                new LoadImageAsync(mContext, cashImg, new LoadImageAsync.callBackForImg() {
                    @Override
                    public void sendImg(Bitmap bitmap, String imgUrl) {
                        holder.iv_image = (ImageView) lv.findViewWithTag(imgUrl);
                        if (holder.iv_image != null) {
                            Log.d(TAG, "sendImg: "+"哈哈哈");
                            //holder.iv_image.setVisibility(View.VISIBLE);
                            holder.iv_image.setImageBitmap(bitmap);
                        }
                    }
                }).execute(path);
            }
        }


        return convertView;
    }

    static class ViewHolder {
        TextView tv_username, tv_content;
        ImageView iv_image;
    }

    public void onDateChanged(List<Item> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }
}
