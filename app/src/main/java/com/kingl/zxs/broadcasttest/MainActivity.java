package com.kingl.zxs.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;//描叙intent各种属性
    private NetworkchangeReceiver networkchangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//在自定义广播时候使用(广播发送者)
        //android.net.conn.CONNECTIVITY_CHANGE： 网络状态发生变化时，系统会发出一条值为
        //android.net.conn.CONNECTIVITY_CHANGE的广播
        networkchangeReceiver=new NetworkchangeReceiver();
        registerReceiver(networkchangeReceiver,intentFilter);//networkchangeReceiver广播接收器接收值为android.net.conn.CONNECTIVITY_CHANGE的广播
//        根据IntentFilter筛选出所有符合action、type、data、category的sticky广播(Intent)，
//        创建BroadcastRecord（含有一个Intent和一个BroadcastFilter）并加入无序广播队列，
//        然后调度处理广播（registerReceiver时处理sticky广播）

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.kingl.zxs.broadcasttest.MY_BROADCASTDT");
                sendOrderedBroadcast(intent,null);//有序广播
                //sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkchangeReceiver);//动态广播接收器要取消注册
    }

    class NetworkchangeReceiver extends BroadcastReceiver{
        //广播接收器 BroadcastReceiver
        @Override
        public void onReceive(Context context, Intent intent) {
            //当有广播来时 onReceive方法得到执行
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            //ConnectivityManger:系统服务类，管理网络连接
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            //NetworkInfo类主要是描述网络连接的信息
            if(networkInfo!=null&&networkInfo.isAvailable()){
                //networkInfo.isAvailable()  判断当前是否有网络
                Toast.makeText(context,"网络连接正常",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context,"无网络",Toast.LENGTH_LONG).show();
            }
        }
    }
}
