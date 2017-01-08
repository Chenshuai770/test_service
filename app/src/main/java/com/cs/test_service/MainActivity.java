package com.cs.test_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 服务Service的start和stop对应,bindService和unbingService对应
 * bindService和unbingService对应 里面实例化了对象,只能单点一次,同时通过参数与start对应
 * unbingService如果没有对象则会报空指针
 * 如果同时开启了start和bind,则关闭服务必须执行stop和unbind,这里没有先后顺序
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMain1;
    private Button btnMain1;
    private Button mBtnMain2;
    private Button mBtnMain3;
    private Button mBtnMain4;
    //首先在服务里面去自定义一个IBinder来获取服务的具体信息
    private MyService.DownloadBinder downloadBinder;
    //然后在ServiceConnection()方法里面重写onServiceConnected和onServiceDisconnected参数,实力化对象,输入刚定义的方法和字段
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private Button mBtnMain5;
    private Button mBtnMain6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvMain1 = (TextView) findViewById(R.id.tv_main1);
        btnMain1 = (Button) findViewById(R.id.btn_main1);

        btnMain1.setOnClickListener(this);
        mBtnMain2 = (Button) findViewById(R.id.btn_main2);
        mBtnMain2.setOnClickListener(this);
        mBtnMain3 = (Button) findViewById(R.id.btn_main3);
        mBtnMain3.setOnClickListener(this);
        mBtnMain4 = (Button) findViewById(R.id.btn_main4);
        mBtnMain4.setOnClickListener(this);
        mBtnMain5 = (Button) findViewById(R.id.btn_main5);
        mBtnMain5.setOnClickListener(this);
        mBtnMain6 = (Button) findViewById(R.id.btn_main6);
        mBtnMain6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main1:
                Intent intent = new Intent(MainActivity.this, MyService.class);
                this.startService(intent);
                // Log.d("TAG",this+"");
                break;
            case R.id.btn_main2:
                Intent intent2 = new Intent(MainActivity.this, MyService.class);
                stopService(intent2);
                break;
            case R.id.btn_main3:
                Intent intent1 = new Intent(this, MyService.class);
                //BIND_AUTO_CREATE=1 这个参数是onStartCommand不会执行
                MainActivity.this.bindService(intent1, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_main4:
                    unbindService(connection);
                break;
            case R.id.btn_main5:
                //调用NotificationManager来管理通知notification
                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("这个是一个通知")
                        .setContentText("我是通知的内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .build();
                manager.notify(2,notification);

                break;
            case R.id.btn_main6:
                Intent intent3 = new Intent(this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent3, 0);
                NotificationManager manager2= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification2 = new NotificationCompat.Builder(this)
                        .setContentTitle("这个第二个通知")
                        .setContentText("我是第二个通知的内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        //取消手机通知栏的通知效果
                        .setAutoCancel(true)
                        //奇数下标为静音,偶数为震动效果
                        .setVibrate(new long[]{0,1000,1000,1000})
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                       // .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager2.notify(3,notification2);
                break;
        }
    }

    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }
    }

}
