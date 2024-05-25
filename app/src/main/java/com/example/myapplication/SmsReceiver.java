package com.example.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import com.example.myapplication.MusicPlayer;

public class SmsReceiver extends BroadcastReceiver {

    private MusicPlayer musicPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Log.d("111","received a sms");
            // 获取短信内容
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            // 访问短信内容
            String sender = messages[0].getOriginatingAddress();
            String messageBody = messages[0].getDisplayMessageBody();
            // 根据需要处理短信内容
            Log.d("111",sender);
            Log.d("111",messageBody);

            if (messageBody.contains("未按规定停放")) {
                if (musicPlayer == null) {
                    musicPlayer = new MusicPlayer(context, R.raw.test);
                }
                musicPlayer.start();
                showDialog(context);
            }
        }
    }

    private void showDialog(Context context) {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 设置弹窗标题
        builder.setTitle("你的车要被拍照啦!!!");

        // 设置弹窗消息
        builder.setMessage("10分钟内, 快去挪车!!!!");

        // 创建一个按钮，当点击后关闭弹窗
        builder.setPositiveButton("我现在就去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                musicPlayer.pause();
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
}
