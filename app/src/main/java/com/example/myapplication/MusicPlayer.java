package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.service.autofill.FillEventHistory;

import androidx.core.content.ContextCompat;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private boolean isPrepared; // 标记是否已经准备好播放

    private long[] pattern = {0, 500, 500, 500};
    private  Context ctx1;
    private  VibratorUtil vibratorUtil;
    public MusicPlayer(Context ctx, int resID) {
        ctx1 = ctx;
        mediaPlayer = MediaPlayer.create(ctx, resID);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
        }
        vibratorUtil = new VibratorUtil(ctx);
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            vibratorUtil.vibrate(pattern, 0);
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            vibratorUtil.cancel();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
           return mediaPlayer.isPlaying();
        }
        return true;
    }



    // 其他 MediaPlayer 相关的方法，例如 pause, seekTo, etc.
}