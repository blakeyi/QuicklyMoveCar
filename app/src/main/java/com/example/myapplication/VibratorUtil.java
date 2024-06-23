package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorUtil {

    private Vibrator vibrator;

    public VibratorUtil(Context context) {
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 震动指定的模式
     * @param pattern 震动模式数组，每个元素代表震动或静止的时间（毫秒）
     * @param repeat 重复次数，0表示不重复
     */
    public void vibrate(long[] pattern, int repeat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, repeat));
        } else {
            vibrator.vibrate(pattern, repeat);
        }
    }

    /**
     * 震动一次，持续指定时间
     * @param milliseconds 震动持续时间（毫秒）
     */
    public void vibrateOnce(long milliseconds) {
        long[] pattern = {0, milliseconds}; // 0表示开始前静止时间，milliseconds表示震动时间
        vibrate(pattern, -1); // -1表示只震动一次
    }

    /**
     * 停止震动
     */
    public void cancel() {
        vibrator.cancel();
    }
}