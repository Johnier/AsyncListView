package com.yztc.lx.qiubai.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yztc.lx.qiubai.utils.HttpUtils;

import java.util.Map;

/**
 * 下载图片的异步任务
 * Created by Lx on 2016/8/13.
 */

public class LoadImageAsync extends AsyncTask<String,Void,Bitmap> {

    private static final String TAG="LoadImageAsync";
    private Context mContext;
    private Map<String,Bitmap> cashImg;
    private callBackForImg iCallBack;
    private String imgUrl;

    public LoadImageAsync(Context mContext,Map<String,Bitmap> cashImg,callBackForImg iCallBack) {
        this.mContext = mContext;
        this.cashImg=cashImg;
        this.iCallBack=iCallBack;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        if(HttpUtils.isNetConn(mContext)){
            imgUrl=params[0];
            bitmap=HttpUtils.LoadImg(params[0]);
        }else {
            Toast.makeText(mContext,"请检查网络设置",Toast.LENGTH_SHORT).show();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) { 
        if(bitmap!=null){
            cashImg.put(imgUrl,bitmap);
            iCallBack.sendImg(bitmap,imgUrl);
            Log.d(TAG, "onPostExecute: "+bitmap+imgUrl);
        }
    }

    //回传图片的回调函数
    public interface callBackForImg{
        //图片存在，返回图片
        public void sendImg(Bitmap bitmap,String imgUrl);
    }
}
