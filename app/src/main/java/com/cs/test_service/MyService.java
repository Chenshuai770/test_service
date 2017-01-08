package com.cs.test_service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class MyService extends Service {
    private DownloadBinder mBinder=new DownloadBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate: ");
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification no=new NotificationCompat.Builder(this)
                .setContentTitle("服务标题")
                .setContentText("服务内容"+">>>>标题")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .build();
        startForeground(3,no);

    }

    /**
     * 每次服务启动的时候回去创建他
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "onStartCommand: ");

        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * onBind指定activity与service的互动
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("TGG", ">>>startDownload: ");
        }
        public int getProgress(){
            Log.d("TGG", ">>>getProgress: ");
            return 56;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ");
    }

}
