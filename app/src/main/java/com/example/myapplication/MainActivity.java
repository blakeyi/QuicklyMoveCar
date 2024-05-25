package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.content.IntentFilter;
import com.example.myapplication.SmsReceiver;

import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.Manifest;

import androidx.annotation.NonNull;


import android.app.Notification;

import android.app.Service;

public class MainActivity extends AppCompatActivity {
//    private MediaPlayer mediaPlayer;

    private SmsReceiver smsReceiver;

    private MusicPlayer mediaPlayer;

    private static final int NOTIFICATION_ID = 1;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("111","NIHAO");

        // 注册sms
        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver, filter);

        // 权限

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    200);
        }

        // 初始化音频
        mediaPlayer = new MusicPlayer(this,  R.raw.test);

        Button btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("111","点击了");
                playAudio();
                showDialog();

            }
        });

        Button btn_click1 = (Button) findViewById(R.id.btn_click1);
        btn_click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("111","点击了");
                stopAudio();
            }
        });

    }

    private void showDialog() {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 设置弹窗标题
        builder.setTitle("你的车要被拍照啦!!!");

        // 设置弹窗消息
        builder.setMessage("10分钟内, 快去挪车!!!!");

        // 创建一个按钮，当点击后关闭弹窗
        builder.setPositiveButton("我现在就去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                stopAudio();
            }
        });

        // 创建一个按钮，当点击后可以执行其他操作
        builder.setNegativeButton("等着罚款吧", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行取消操作
                dialog.dismiss();
            }
        });

        // 创建并显示弹窗
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void playAudio() {
        try {
            // 初始化MediaPlayer
            if (mediaPlayer != null) {
                // 开始播放
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAudio() {
        try {
            // 初始化MediaPlayer
            if (mediaPlayer != null) {
                // 开始播放
                mediaPlayer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        // 释放MediaPlayer资源
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 处理权限请求的结果
        switch (requestCode) {
            case 200: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被授予，执行相关操作
                } else {
                    // 权限被拒绝，提示用户或执行备选操作
                }
                break;
            }
        }
    }
}



