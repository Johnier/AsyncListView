package com.yztc.lx.qiubai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lx on 2016/8/13.
 */

public class HttpUtils {
    /**
     * 判断网络状态是否连通
     * @param context  上下文
     * @return  true：连通  false：不连通
     */
    public static boolean isNetConn(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null){
            return  info.isConnected();
        }else{
            return false;
        }
    }

    /**
     * 从网络上下载数据，返回byte数组的形式
     * @param path  网络的url
     * @return  返回得到的数据的byte数组
     */
    public static byte[] DownloadFromNet(String path){
        ByteArrayOutputStream baos=null;
        try {
            baos=new ByteArrayOutputStream();
            URL url=new URL(path);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                int len=0;
                byte b[]=new byte[1024];
                while((len=is.read(b))!=-1){
                    baos.write(b,0,len);
                    baos.flush();
                }
                return baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 下载图片
     * @param imgUrl  图片的url
     * @return   返回图片的Bitmap
     */
    public static Bitmap LoadImg(String imgUrl){
        byte b[]=HttpUtils.DownloadFromNet(imgUrl);
        Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }
}
